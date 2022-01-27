using System;
using System.Threading;

namespace ChatServer
{
    class Program
    {
        static ServerObject server; // server
        static Thread listenThread; // stream to listen
        static void Main(string[] args)
        {
            try
            {
                server = new ServerObject();
                listenThread = new Thread(new ThreadStart(server.Listen));
                listenThread.Start(); // start stream
            }
            catch (Exception ex)
            {
                server.Disconnect();
                Console.WriteLine(ex.Message);
            }
        }
    }
}
