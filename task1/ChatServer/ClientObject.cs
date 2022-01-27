using System;
using System.Net.Sockets;
using System.Text;

namespace ChatServer
{
    public class ClientObject
    {
        protected internal string Id { get; private set; }
        protected internal NetworkStream Stream { get; private set; }
        string userName;
        TcpClient client;
        ServerObject server; // object server

        public ClientObject (TcpClient tcpClient, ServerObject serverObject)
        {
            Id = Guid.NewGuid().ToString();
            client = tcpClient;
            server = serverObject;
            serverObject.AddConnection(this);
        }

        public void Process()
        {
            try
            {
                Stream = client.GetStream();
                // get user name
                string message = GetMessage();
                userName = message;

                message = userName + " join chat";
                // send msg to join chat all connected users
                server.BroadcastMessage(message, this.Id);
                Console.WriteLine(message);
                // in while get msg from client
                while(true)
                {
                    try
                    {
                        message = GetMessage();
                        message = String.Format("{0}: {1}", userName, message);
                        Console.WriteLine(message);
                        server.BroadcastMessage(message, this.Id);
                    }
                    catch
                    {
                        message = String.Format("{0}: leave chat", userName);
                        Console.WriteLine(message);
                        server.BroadcastMessage(message, this.Id);
                        break;
                    }
                }
            }
            catch(Exception e)
            {
                Console.WriteLine(e.Message);
            }
            finally
            {
                // if exit in while close resources
                server.RemoveConnection(this.Id);
                Close();
            }
        }

        // read incoming msg and convert to string
        private string GetMessage()
        {
            byte[] data = new byte[64]; //buffer for received data
            StringBuilder builder = new StringBuilder();
            int bytes = 0;
            do
            {
                bytes = Stream.Read(data, 0, data.Length);
                builder.Append(Encoding.Unicode.GetString(data, 0, bytes));
            }
            while (Stream.DataAvailable);

            return builder.ToString();
        }

        // close connection
        protected internal void Close()
        {
            if (Stream != null) Stream.Close();
            if (client != null) client.Close();
        }
    }
}
