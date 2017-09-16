package de.joesaxo.library.server.basicserver;

/**
 * Created by Jens on 10.08.2017.
 */
public interface IServerMessageHandler {

    void newMessage(String clientID, char message);

}
