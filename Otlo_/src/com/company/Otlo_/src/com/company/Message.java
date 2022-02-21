package com.company;

import java.io.Serializable;
import java.util.ArrayList;

/* this class objects will be sent over the network, so
   must implement Serializable in order to be sent
 */
public class Message implements Serializable {

    public Message(String message, String username,
                   Commands commands,
                   ArrayList<String> clientsToSendTo,
                   GameStates gameStates,
                   int moveTaken) {

        this.message = message;
        this.username = username;
        this.commands = commands;
        this.clientsToSendTo = clientsToSendTo;
        this.gameStates = gameStates;
        this.moveTaken = moveTaken;
    }

    // the message to be sent
    String message;

    // client username that sends this message object
    String username;

    // these used for chat
    public enum Commands {NONE, SEND_LIST_OF_CLIENTS, CLIENT_DISCONNECTED}
    Commands commands = Commands.NONE;

    // list of clientS THAT will receive this message object
    ArrayList<String> clientsToSendTo = new ArrayList<>();

    /*
    game
     */
    // these used for game playing
    public enum GameStates {NONE, WAIT_FOR_TURN, NOW_YOUR_TURN, REQUEST_NET_PLAY,
        ANSWER_YES_PLAY, ANSWER_NO_PLAY, OPPONENT_GAME_CLOSED}
    GameStates gameStates = GameStates.WAIT_FOR_TURN;


    // this will store the move that was taken in the game
    int moveTaken;

}
