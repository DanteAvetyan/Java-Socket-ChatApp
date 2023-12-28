//Dante Avetyan
//12.28.2023
//In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    static ClientListGUI clientListGUI= new ClientListGUI();
    static GUI gui = new GUI();

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    static boolean notificationDisplayed = false;
    public ClientHandler(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);

            /*
            if (!notificationDisplayed)
            {
                clientListGUI.displayClientList("[SERVER USER LIST]");
                notificationDisplayed = true;
            }*/

            broadcastMessage("SERVER: " + clientUsername + " has entered the chat.");
            clientListGUI.displayClientList("SERVER: " + clientUsername + " has entered the chat.");

        }
        catch(IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run()
    {
        String messageFromClient;
        String messageFromUser;

        while(socket.isConnected())
        {
            try
            {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);

                messageFromUser = gui.getMessageToSend();
                broadcastMessage(messageFromClient);
            }
            catch(IOException e)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend)
    {
        for(ClientHandler clientHandler : clientHandlers)
        {
            try
            {
                if(!clientHandler.clientUsername.equals(clientUsername))
                {
                    clientHandler.bufferedWriter.write(messageToSend);
                    gui.displayMessage(messageToSend);

                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();

                }
            }
            catch(IOException e)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }

    public void removeClientHandler()
    {
        clientHandlers.remove(this);
        broadcastMessage("SERVER: "+clientUsername +" has left the chat.");
        clientListGUI.displayClientList("SERVER: "+clientUsername +" has left the chat.");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
        removeClientHandler();
        try
        {
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null)
            {
                bufferedWriter.close();
            }
            if(socket != null)
            {
                socket.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}