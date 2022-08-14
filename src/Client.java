import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    public static final String HOST_NAME = "localhost";
    public static final int HOST_PORT = 7777; // host port number
    public Socket listener;

    public Client()
    {
    }
    public Client(Socket socket)
    {
        this.listener = socket;
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
        {  System.err.println("Client could not make connection: " + e);
            System.exit(-1);
        }
        PrintWriter pw; // output stream to server
        BufferedReader br; // input stream from server
        try
        {
            // create an autoflush output stream for the socket
            pw = new PrintWriter(socket.getOutputStream(), true);

            boolean finished = false;

            Client listener = new Client(socket);
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
                    pw.println(keyboardInput.nextLine());
                }
            }
            pw.close();
            socket.close();
        }
        catch (IOException e)
        {  System.err.println("Client error: " + e);
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
                    System.out.println(serverResponse);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args)
    {
        Client client = new Client();
        client.startClient();
    }

}
