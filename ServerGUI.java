//Dante Avetyan
//12.28.2023
//In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame
{
    private final JTextArea chatArea;
    private final JTextField messageField;
    private String name = null;
    String messageToSend = null;
    private boolean changeName = false;

    public ServerGUI()
    {
        setTitle("[CHAT APP SERVER NOTIFICATION TAB]");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the location to the center of the screen
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        int x = (screenWidth - frameWidth) / 3;
        int y = (screenHeight - frameHeight) / 3;
        setLocation(x, y);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        /*JButton sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);*/
        setVisible(true);
    }

    public void displayServer(String message)
    {
        chatArea.append(message + "\n");
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(GUI::new);
    }
}