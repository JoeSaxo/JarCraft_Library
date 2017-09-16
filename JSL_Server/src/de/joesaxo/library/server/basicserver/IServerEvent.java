package de.joesaxo.library.server.basicserver;

/**
 * Created by Jens on 07.08.2017.
 */
public interface IServerEvent {

    void establishedConnection(String clientID);
    void timedOut(String clientID, long time);
    void stopped(String clientID);

}
