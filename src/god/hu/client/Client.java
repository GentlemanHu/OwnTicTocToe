package god.hu.client;

import java.io.*;
import java.net.*;
import java.util.Objects;

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
    private Socket socket;
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
            socket = new Socket(hostname, port);

            readThread = new ReadThread(socket, this);
            writeThread = new WriteThread(socket, this);
            readThread.start();
            writeThread.start();

            reader = readThread.getReader();
            writer = writeThread.getSender();
            System.out.println("Connected to the god.hu.server");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return true;
    }

    public void closeSocket() {
        try {
            this.readThread.join();
            this.writeThread.join();
            this.socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                } catch (IOException ex) {
                    System.out.println("Error reading from god.hu.server: " + ex.getMessage());
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
            //Console console = System.console();

            // String userName = console.readLine("\nEnter your name: ");
            // writer.println(userName);

            String text;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                do {
                    text = reader.readLine();
                    if (text != null && !text.equals(""))
                        writer.println(text);
                }
                while (!Objects.equals(text, "END"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                socket.close();
                System.exit(0);
            } catch (IOException ex) {

                System.out.println("Error writing to god.hu.server: " + ex.getMessage());
            }
        }

        public PrintWriter getSender() {
            return writer;
        }
    }
}