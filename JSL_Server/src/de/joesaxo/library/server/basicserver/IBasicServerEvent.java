package de.joesaxo.library.server.basicserver;

/**
 * Created by Jens on 07.08.2017.
 */
public interface IBasicServerEvent extends IServerEvent, IServerMessageHandler {

    void establishedConnection(String clientID);
    void newMessage(String clientID, char message);
    void timedOut(String clientID, long time);
    void stopped(String clientID);

}
