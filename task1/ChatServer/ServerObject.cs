using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Net;
using System.Text;
using System.Threading;

namespace ChatServer
{
    public class ServerObject
    {
        static TcpListener tcpListener; // listening server
        List<ClientObject> clients = new List<ClientObject>(); // all connections

        protected internal void AddConnection(ClientObject clientObject)
        {
            clients.Add(clientObject);
        }

        protected internal void RemoveConnection(string id)
        {
            // we get a closed connection by id
            ClientObject client = clients.FirstOrDefault(c => c.Id == id);
            // and remove it from the list of connections
            if (client != null) clients.Remove(client);
        }

        // listening for incoming connections
        protected internal void Listen()
        {
            try
            {
                tcpListener = new TcpListener(IPAddress.Any, 8888);
                tcpListener.Start();
                Console.WriteLine("The server is running. Waiting for connections ...");

                while (true)
                {
                    TcpClient tcpClient = tcpListener.AcceptTcpClient();

                    ClientObject clientObject = new ClientObject(tcpClient, this);
                    Thread clientThread = new Thread(new ThreadStart(clientObject.Process));
                    clientThread.Start();
                }
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                Disconnect();
            }
        }

        // broadcast msg to connected clients
        protected internal void BroadcastMessage(string message, string id)
        {
            byte[] data = Encoding.Unicode.GetBytes(message);
            for(int i = 0; i < clients.Count; i++)
            {
                if(clients[i].Id != id) // if client id is not equal to sender id
                {
                    clients[i].Stream.Write(data, 0, data.Length); // data transfer
                }
            }
        }

        // disconnecting all clients
        protected internal void Disconnect()
        {
            tcpListener.Stop(); // server stop

            for(int i = 0; i < clients.Count; i++)
            {
                clients[i].Close(); // disconnect client
            }

            Environment.Exit(0); // terminate the process
        }
    }
}
