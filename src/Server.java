import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
    private static ArrayList<Connection> connections = new ArrayList<Connection>();
    private final int port = 8080;
    private ServerSocket ss;

    public static void main(String[] args) {
        Server serverWindow = new Server();
    }

    public Server() {
        try {
            ss = new ServerSocket(port);
            System.out.println("Server started");
            while (true) {
                Socket socket = ss.accept();
                connections.add(new Connection(socket));
                System.out.println("We have new client");
            }
        } catch (IOException ex) {
        }
    }

    private class Connection extends Thread {
        private ObjectInputStream input;
        private ObjectOutputStream output;
        private String name = "";

        public Connection(Socket socket) {
            try {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                start();
            } catch (IOException ex) {
            }
        }

        public void run() {
            try {
                while (true) {
                    Sender data = (Sender) input.readObject();
                    if (data != null) {
                        receiveText(data);
                    }
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void receiveText(Sender text) {
            System.out.println(text.soutText());
            sendText(text);
        }

        private void sendText(Sender text) {
            try {
                Iterator<Connection> iter = Server.connections.iterator();
                while (iter.hasNext()) {
                    iter.next().output.writeObject(text);
                }
            } catch (IOException ex) {
            }
        }
    }
}