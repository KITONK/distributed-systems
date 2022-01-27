using System;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace ChatClient
{
    class Program
    {
        static string userName;
        private const string host = "127.0.0.1";
        private const int port = 8888;
        static TcpClient client;
        static NetworkStream stream;

        static void Main(string[] args)
        {
            Console.WriteLine("Enter your name: ");
            userName = Console.ReadLine();
            client = new TcpClient();
            try
            {
                client.Connect(host, port); // client connection
                stream = client.GetStream(); // get the stream

                string message = userName;
                byte[] data = Encoding.Unicode.GetBytes(message);
                stream.Write(data, 0, data.Length);

                // start a new thread to receive data
                Thread receiveThread = new Thread(new ThreadStart(ReceiveMessage));
                receiveThread.Start(); // stream start
                Console.WriteLine("Welcome, {0}", userName);
                SendMessage();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                Disconnect();
            }
        }

        // send msgs
        static void SendMessage()
        {
            Console.WriteLine("Enter message: ");

            while(true)
            {
                string message = Console.ReadLine();
                byte[] data = Encoding.Unicode.GetBytes(message);
                stream.Write(data, 0, data.Length);
            }
        }

        // receive msgs
        static void ReceiveMessage()
        {
            while(true)
            {
                try
                {
                    byte[] data = new byte[64]; // buffer for received data
                    StringBuilder builder = new StringBuilder();
                    int bytes = 0;
                    do
                    {
                        bytes = stream.Read(data, 0, data.Length);
                        builder.Append(Encoding.Unicode.GetString(data, 0, bytes));
                    }
                    while (stream.DataAvailable);

                    string message = builder.ToString();
                    Console.WriteLine(message); // display the message
                }
                catch
                {
                    Console.WriteLine("Connection interrupted!"); // connection was interrupted
                    Console.ReadLine();
                    Disconnect();
                }
            }
        }

        static void Disconnect()
        {
            if (stream != null) stream.Close(); // turn off the stream
            if (client != null) client.Close(); // disconnect client

            Environment.Exit(0); // terminate the process
        }
    }
}
