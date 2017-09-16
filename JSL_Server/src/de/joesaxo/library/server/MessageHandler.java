package de.joesaxo.library.server;

import de.joesaxo.library.server.basicserver.IServerMessageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jens on 10.08.2017.
 */
public abstract class MessageHandler implements IServerMessageHandler {

    private HashMap<String, MessageClientHandler> clients;

    public MessageHandler() {
        clients = new HashMap<>();
    }

    @Override
    public void newMessage(String clientID, char message) {
        //System.out.println("Char: " + message);
        if (!clients.containsKey(clientID)) clients.put(clientID, new MessageClientHandler(this, clientID));
        clients.get(clientID).newMessage(clientID, message);
    }

    public abstract void newCharacter(String clientID, char c);

    public abstract void newString(String clientID, String string);

    public abstract void newByteArray(String clientID, byte[] bytes);

    public abstract void newFile(String clientID, String name, byte[] data);

}
