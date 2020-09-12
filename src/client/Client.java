package client;

import java.io.*;
import java.net.*;

/*
 * @Author: Gentleman.Hu 
 * @Date: 2020-03-31 11:32:56 
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-31 22:29:05
 */
/**
 * Client
 */
/**
 * Client
 */
public class Client {
    private String hostname;
    private final static int port = 3839;
    private String clientID;
    private WriteThread writeThread;
    private ReadThread readThread;
    private PrintWriter writer;
    private BufferedReader reader;
    private String msg;

    public static void main(String[] args) {
        try {
            new Client("192.168.43.11").execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Client() {
    }

    public Client(String hostname) {
        this.hostname = hostname;
    }

    public boolean execute() throws Exception {
        try {
            Socket socket = new Socket(hostname, port);

            readThread = new ReadThread(socket, this);
            writeThread = new WriteThread(socket, this);
            readThread.start();
            writeThread.start();

            reader = readThread.getReader();
            writer = writeThread.getSender();
            System.out.println("Connected to the server");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return true;
    }

    public void sendMes(String mes) {
        writer.println(mes);
    }

    public void setMessage2null() {
        readThread.setMessage2null();
    }

    public String getMes() {
        return readThread.getMessage();
    }

    class ReadThread extends Thread {
        private BufferedReader reader;
        private Socket socket;
        private Client client;
        private String msg;

        public ReadThread(Socket socket, Client client) {
            this.socket = socket;
            this.client = client;

            try {
                InputStream input = socket.getInputStream();
                reader = new BufferedReader(new InputStreamReader(input));
            } catch (IOException ex) {
                System.out.println("Error getting input stream: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                try {
                    String response = reader.readLine();
                    if (response != null && response != "") {
                        msg = response;
                    }

                    // buttons[Integer.parseInt(msg)].doClick();
                    // buttons[Integer.parseInt(msg)].setEnabled(false);
                    // System.out.println("\n" + response);

                    // prints the username after displaying the server's message
                    // if (client.getUserName() != null) {
                    // System.out.print("[" + client.getUserName() + "]: ");
                    // }
                } catch (IOException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }
            }
        }

        public BufferedReader getReader() {
            return reader;
        }

        public String getMessage() {
            return msg;
        }

        public void setMessage2null() {
            msg = null;
        }
    }

    class WriteThread extends Thread {
        private PrintWriter writer;
        private Socket socket;
        private Client client;

        public WriteThread(Socket socket, Client client) {
            this.socket = socket;
            this.client = client;

            try {
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
            } catch (IOException ex) {
                System.out.println("Error getting output stream: " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        public void run() {
            Console console = System.console();
            // String userName = console.readLine("\nEnter your name: ");
            // writer.println(userName);

            String text;

            do {
                text = console.readLine();
                if (text != null && text != "")
                    writer.println(text);
            } while (!text.equals("END"));

            try {
                socket.close();
                System.exit(0);
            } catch (IOException ex) {

                System.out.println("Error writing to server: " + ex.getMessage());
            }
        }

        public PrintWriter getSender() {
            return writer;
        }
    }
}