//Dante Avetyan
//12.28.2023
//In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    static ServerGUI serverGui = new ServerGUI();
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    static boolean notificationDisplayed = false;
    public void startServer()
    {
        try
        {
            while (!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");

                /*
                if (!notificationDisplayed)
                {
                    serverGui.displayServer("[SERVER NOTIFICATION TAB]");
                    notificationDisplayed = true;
                }*/

                serverGui.displayServer("SERVER: A new client has connected!");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e)
        {

        }
    }

    public void closeServerSocket()
    {
        try
        {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9999);
        Server server = new Server(serverSocket);
        System.out.println("Server started on port: " + serverSocket);
        server.startServer();
    }
}
