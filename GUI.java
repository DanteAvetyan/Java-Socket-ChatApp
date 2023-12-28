//Dante Avetyan
//12.28.2023
//In IntelliJ or another IDE, enable "allow multiple instances" for Client.java.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private final JTextArea chatArea;
    private final JTextField messageField;
    private String name = null;
    String messageToSend = null;
    private boolean changeName = false;

    public GUI()
    {
        setTitle("Chat App");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the location to the center of the screen
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        int x = (screenWidth - frameWidth) / 2;
        int y = (screenHeight - frameHeight) / 2;
        setLocation(x, y);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        messageField = new JTextField();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void displayMessage(String message)
    {
        chatArea.append(message + "\n");
    }

    public void setName(String newName)
    {
        name = newName;
        //displayMessage("Name set to " + name + "\n");
    }

    void sendMessage()
    {
        String message = messageField.getText();
        if (message.isEmpty()) {
            messageField.setText("");
            return;
        }

        if (name == null) {
            name = message;
            setName(name);
            messageField.setText("");
            return;
        }

        if (message.equals("/quit")) {
            displayMessage("Closing Chat...");
            dispose();
        }
        else if (message.equals("/nick"))
        {
            String newName = JOptionPane.showInputDialog("Enter new name:");
            if (newName != null && !newName.isEmpty())
            {
                String oldName = name;
                setName(newName);
                displayMessage(oldName + " changed their name to: " + name);
            }
            else
            {
                displayMessage("Invalid name. Please try again.");
            }
            messageField.setText("");
        }
        else
        {
            messageToSend = (name + ": " + message + "\n");
            chatArea.append(name + ": " + message + "\n");
            messageField.setText("");
        }
    }

    public String getMessageToSend()
    {
        return messageToSend;
    }

    public String getName()
    {
        return name;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(GUI::new);
    }
}