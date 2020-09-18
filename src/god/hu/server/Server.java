package god.hu.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/*
 * @Author: Gentleman.Hu
 * @Date: 2020-03-31 15:05:27
 * @Last Modified by: Gentleman.Hu
 * @Last Modified time: 2020-03-31 20:21:12
 */

public class Server {
    private final static int port = 3839;
    private Set<String> clients = new HashSet<>();
    private Set<ClientThread> clientThreads = new HashSet<>();

    public static void main(String[] args) {
        new Server().execute();
    }

    public Server() {
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port:" + port);
            for (; ; ) {
                Socket socket = serverSocket.accept();
                System.out.print("new Client connected" + "---");

                ClientThread clientThread = new ClientThread(socket, this);
                clientThreads.add(clientThread);
                clients.add(clientThread.getGenerateID());
                clientThread.start();
                System.out.println("id:" + clientThread.getGenerateID());

                for (ClientThread client : clientThreads) {
                    System.out.println(client.getGenerateID());
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    void broadcast(String message, ClientThread clientThread) {
        for (ClientThread aThread : clientThreads) {
            if (aThread != clientThread) {
                aThread.sendMessage(message);
            }
        }
    }

    void addClient(String id) {
        clients.add(id);
    }

    void removeClient(String id, ClientThread clientThread) {
        boolean removed = clients.remove(id);
        if (removed) {
            for (ClientThread thread : clientThreads) {
                if(thread.getGenerateID()==id)
                    thread.stop();
            }
            clientThreads.remove(clientThread);
        }
        System.out.println("成功下机:" + id);
    }

}

/**
 * InnerServer
 */
class ClientThread extends Thread {
    private Socket socket;
    private String id;
    private Server server;
    private PrintWriter writer;

    public ClientThread() {
    }

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            OutputStream out = socket.getOutputStream();
            writer = new PrintWriter(out, true);

            String serverMessage = "New god.hu.client connected: ";
            server.broadcast(serverMessage, this);

            String clientMessage;
            while (!socket.isClosed()) {
                    clientMessage = reader.readLine();
                    serverMessage = "[" +this+ "]: " + clientMessage;
                    server.broadcast(serverMessage, this);
            }
            try {
                server.removeClient(this.getGenerateID(), this);
            } catch (Exception e) {
                System.out.println("客户端异常退出-");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }

    String getGenerateID() {
        String id = Long.toString(getId());
        return id;
    }

    void sendMessage(String message) {
        writer.println(message);
    }
}