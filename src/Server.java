import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    private boolean stopRequested;
    public static final int PORT = 7777; // some unused port number
    private ArrayList<ChatConnection> connections;
    private ThreadPool pool;

    public Server()
    {
        stopRequested = false;
        connections = new ArrayList<>();
        pool = new ThreadPool(5);
    }


    public void startServer()
    {
        stopRequested = false;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started at " + InetAddress.getLocalHost() + " on port " + PORT);
        }
        catch (IOException e)
        {  System.err.println("Server can't listen on port: " + e);
            System.exit(-1);
        }

        try
        {
            while (!stopRequested)
            {

                Socket socket = serverSocket.accept();
                System.out.println("Connection made with " + socket.getInetAddress());

                ChatConnection conn = new ChatConnection(socket);
                Thread thread = new Thread(conn);
                thread.start();
                connections.add(conn);
            }
            serverSocket.close();
        }
        catch (IOException e)
        {  System.err.println("Can't accept client connection: " + e);
        }
        System.out.println("Server finishing");
    }

    private void broadcast(String message, int sender)
    {
        for(ChatConnection c : connections)
        {
            c.sendMessage(sender + ": "  + message);
        }
    }

    // driver main method to test the class
    public static void main(String[] args)
    {
        Server server = new Server();
        server.startServer();
    }

    private class ChatConnection implements Runnable, TaskObserver
    {
        private Socket socket;
        private boolean stopRequested;

        private PrintWriter pw; //out to client
        private BufferedReader br; // in from client
        public ChatConnection(Socket socket)
        {
            this.socket = socket;
        }

        @Override
        public void run() {
            try
            {
                pw = new PrintWriter(socket.getOutputStream(), true);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                do{
                    String message = br.readLine();
                    if(message.equals("QUIT")) {
                        pw.println("confirm_quit");
                        //stopRequested = true;
                        break;
                    }
                    else {
                        //Server.this.broadcast(message, connections.indexOf(this));
                        Task t = new CipherTask(message);
                        t.addListener(this);
                        pool.perform(t);
                    }
                }
                while (!stopRequested);
                pw.close();
                br.close();
                System.out.println("Closing connection with " + socket.getInetAddress());
                socket.close();
            }

            catch (IOException e) {
                System.err.println("Server error with chatConnection: " + e);
            }
        }

        private void sendMessage(String message)
        {
            pw.println(message);
        }

        @Override
        public void update(Object progress) {
            sendMessage((String) progress);
        }
    }
}
