import java.io.*;
import java.util.*;
import java.net.*;

public class Client {
    private final int port = 8080;
    private String address = "127.0.0.1";
    private Socket cs;


    public  Client() {
        try {
            cs = new Socket(address, port);
            new OutputToServer();
            new InputFromServer();
        } catch (IOException ex) {
        }
    }
    private class InputFromServer extends Thread { // Подкласс, который принимиает информацию от сервера
        private ObjectInputStream input;
        Sender sender;
        public InputFromServer() {
            try {
                input = new ObjectInputStream(cs.getInputStream());
                start();
            } catch (IOException ex) {
            }
        }
        public void run() {
            try {
                while (true) {
                    sender = (Sender) input.readObject();
                    System.out.println(sender.soutText());
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private class OutputToServer extends Thread { // Подкласс, который отправляет информацию серверу
        private ObjectOutputStream output;
        private Scanner scan;
        private String name;
        Sender data;
        public OutputToServer() {
            try {
                output = new ObjectOutputStream(cs.getOutputStream());
                scan = new Scanner(System.in);
                System.out.println("Enter you nick");
                name = scan.nextLine();
                start();
            } catch (IOException ex) {
            }
        }
        public void run() {
            while (true) {
                data = new Sender(name+" "+scan.nextLine());
                try {
                    if (data != null) {
                        output.writeObject(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}