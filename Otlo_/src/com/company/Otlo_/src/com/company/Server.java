package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // listen from incoming clients
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {

        this.serverSocket = serverSocket;
    }

    // responsible for keeping the server running
    public void startServer() {

        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    // a sample method for handling the error, to avoid try catch's
    public void closeServerSocket() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //todo add in online btn in try catch
    /*
        ServerSocket serverSocket = new ServerSocket(1235);
        Server server = new Server(serverSocket);
        server.startServer();

     */

}
