package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {


    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public static int gameID = 1;
    private Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    private String clientUsername;

    ClientHandler(Socket socket) {
        try {//messege = packet-------------------
            this.socket = socket;
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            // add client to the static arrayList of clients
            clientHandlers.add(this);

            // receive the object message sent by the client
            Message messageObject = (Message) objectInputStream.readObject();

            // get the username from the new connected client
            clientUsername = messageObject.username;//id------------------------
            // in this case, at first connection we send this message to all clients
            SendMessageToClients(messageObject);


        } catch (Exception e) {
            closeEveryThing(socket, objectOutputStream, objectInputStream);
        }
    }

    /* create user-list names to pass to all client
    // this is done only when a new user is connected to the server
     */
    public void clientList_ToPassToClients() {
        // this function creates "user-list names" to pass to all clients
        // send current gameID
        Message messageObject = new Message("A message from SERVER", clientUsername,
                Message.Commands.SEND_LIST_OF_CLIENTS,
                 new ArrayList<>(), Message.GameStates.NONE,
                0 );

        // increment gameID
        gameID++;


        // create the object with all the client-list
        for (ClientHandler clientHandler : clientHandlers)
            messageObject.clientsToSendTo.add(clientHandler.clientUsername);

        // sent the message object to all clients
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                clientHandler.objectOutputStream.writeObject(messageObject);
            } catch (IOException e) {
                e.printStackTrace();
                closeEveryThing(socket, objectOutputStream, objectInputStream);
            }
        }
    }


    /*
         Check To Which Clients To Send The Message
         and then send them
     */
    public void SendMessageToClients(Message messageObject) {//-------------------------

        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.objectOutputStream.writeObject(messageObject);
                }
            } catch (IOException e) {
                closeEveryThing(socket, objectOutputStream, objectInputStream);
            }

        }
    }


    public void removeClientHandler() {

        // remove current client from clientHandlers
        clientHandlers.remove(this);


        // first send message to all client -- message "client has disconnected"
        // build message object to be sent
        Message messageObject = new Message("SERVER: " + clientUsername + " has left ",
                "",
                Message.Commands.CLIENT_DISCONNECTED,
                 new ArrayList<>(), Message.GameStates.NONE,
                0);
        SendMessageToClients(messageObject);

        // and now, update the client list to every client
        clientList_ToPassToClients();


    }

    // close the connection when an Exception error occurred
    public void closeEveryThing(Socket socket, ObjectOutputStream objectOutputStream,
                                ObjectInputStream objectInputStream) {
        removeClientHandler();
        try {
            if (socket != null) {
                socket.close();

                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // we will create threads for sending  messages
        while (socket.isConnected()) {
            try {

                // receive the object message
                Message messageObject = (Message) objectInputStream.readObject();

                // send the message
                SendMessageToClients(messageObject);

            } catch (Exception e) {
                closeEveryThing(socket, objectOutputStream, objectInputStream);
                break;
            }
        }
    }
}
