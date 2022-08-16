//Byron Goldsack
//20111390

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CipherProgram implements Runnable, ActionListener {

    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 7777; // host port number
    public Socket listener;

    private JFrame frame;
    private JTextField inputField;
    private JTextArea label;
    private JTextArea helpText;
    private String message;

    public CipherProgram()
    {
        frame = new JFrame("Cipher Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);

        inputField = new JTextField();
        inputField.addActionListener(this);
        inputField.setBackground(Color.lightGray);
        inputField.setSize(20,10);
        message = "";

        label = new JTextArea();
        label.setLineWrap(true);

        helpText = new JTextArea("\n\n\n\n\nWelcome to the ROT13 cipher program!\nenter text to be encrypted below");

        frame.getContentPane().add(BorderLayout.NORTH, label);
        frame.getContentPane().add(BorderLayout.SOUTH, inputField);
        frame.getContentPane().add(helpText);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public CipherProgram(Socket socket, JTextArea label)
    {
        this.listener = socket;
        this.label = label;
    }

    public void startClient()
    {
        Socket socket = null;
        Scanner keyboardInput = new Scanner(System.in);
        try
        {
            socket = new Socket(HOST_NAME, HOST_PORT);
        }
        catch (IOException e)
        {  System.err.println("CipherProgram could not make connection: " + e);
            System.exit(-1);
        }
        PrintWriter pw; // output stream to server
        BufferedReader br; // input stream from server
        try
        {
            // create an autoflush output stream for the socket
            pw = new PrintWriter(socket.getOutputStream(), true);

            boolean finished = false;

            CipherProgram listener = new CipherProgram(socket, label);
            Thread thread = new Thread(listener);
            thread.start();
            while(!finished)
            {
                if(!thread.isAlive())
                {
                    finished = true;
                    System.out.println("Stopping waiting for input");
                }
                else {
                    //pw.println(keyboardInput.nextLine());
                    if(!message.equals(""))
                    {
                        pw.println(message);
                        message = "";
                    }
                }
            }
            pw.close();
            socket.close();
        }
        catch (IOException e)
        {  System.err.println("CipherProgram error: " + e);
        }

    }
    @Override
    public void run() {
        BufferedReader br = null;
        boolean stop = false;
        try {
            br = new BufferedReader(new InputStreamReader(listener.getInputStream()));
            while(!stop) {
                String serverResponse = br.readLine();
                if(serverResponse.equals("confirm_quit"))
                {
                    stop = true;
                    System.out.println("Stopping listening");
                }
                else {
                    //System.out.println(serverResponse);
                    label.setText(serverResponse);
                    //setLabel(serverResponse);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        CipherProgram cipherProgram = new CipherProgram();
        cipherProgram.startClient();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == inputField)
        {
            message = inputField.getText();
        }
    }
}
