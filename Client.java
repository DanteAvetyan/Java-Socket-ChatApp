//Dante Avetyan
//12.28.2023
//In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    static GUI gui = new GUI();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username = "";

    public Client(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }
        catch(IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage()
    {
        try
        {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while(socket.isConnected())
            {
                String messageToSend = scanner.nextLine();
                messageToSend = gui.getMessageToSend();
                bufferedWriter.write(username + ": "+ messageToSend);
                gui.displayMessage(username + ": "+ messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch(IOException e)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String msgFromGroupChat;
                String msgFromGUI = gui.getMessageToSend();
                while(socket.isConnected())
                {
                    try
                    {
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                        gui.displayMessage(msgFromGUI);

                        String msgFrom = gui.getMessageToSend();
                        gui.displayMessage(msgFrom);
                    }
                    catch(IOException e)
                    {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter)
    {
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


    public static void main(String[] args) throws IOException
    {
        //In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.
        gui.displayMessage("[Type /nick to change your name.]\n[Type /quit to shutdown the program.]\nEnter your username for the chat: ");
        while(gui.getName()==null)
        {
            try{
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter your username for the chat: ");
        //String username = scanner.nextLine();

        String username = gui.getName();
        gui.displayMessage(username);
        Socket socket = new Socket("localhost", 9999);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}