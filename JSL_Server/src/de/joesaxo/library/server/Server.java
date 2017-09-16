package de.joesaxo.library.server;

import de.joesaxo.library.server.basicserver.IServerEvent;
import de.joesaxo.library.server.basicserver.IServerMessageHandler;
import de.joesaxo.library.server.basicserver.server.BasicServer;
import org.jarcraft.library.iotools.ByteHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 09.08.2017.
 */
public class Server extends BasicServer {

    public Server(int port, int maxclients) {
        super(port, maxclients);
    }

    @Deprecated
    @Override
    public void setIServerMessageHandler(IServerMessageHandler iServerMessageHandler) {
        super.setIServerMessageHandler(iServerMessageHandler);
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        super.setIServerMessageHandler(messageHandler);
    }

    private boolean sendPreFix(String client, char character) {
        if (!super.send(client, '/')) return false;
        return super.send(client, character);
    }

    @Override
    public boolean send(String client, char c) {
        return sendCharacter(client, c);
    }

    private boolean sendCharacter(String client, char c) {
        if (c == '/') {
            return sendPreFix(client, c);
        } else {
            return super.send(client, c);
        }

    }

    public boolean sendAll(String message) {
        String[] clients = getClients();
        for (int i = 0; i < clients.length; i++) {
            if (!send(clients[i], message)) return false;
        }
        return true;
    }

    public boolean send(String client, String message) {
        if (!sendPreFix(client, 's')) return false;
        for (char character : (message).toCharArray()) {
            if (!sendCharacter(client, character)) return false;
        }
        return sendPreFix(client, 's');
    }

    public boolean sendAll(byte[] bytes) {
        String[] clients = getClients();
        for (int i = 0; i < clients.length; i++) {
            if (!send(clients[i], bytes)) return false;
        }
        return true;
    }

    public boolean send(String client, byte[] bytes) {
        if (!sendPreFix(client, 'b')) return false;
        for (byte b : bytes) {
            if (!sendCharacter(client, (char)b)) return false;
        }
        return sendPreFix(client, 'b');
    }

    public boolean sendAll(byte[] bytes, String name) {
        String[] clients = getClients();
        for (int i = 0; i < clients.length; i++) {
            if (!send(clients[i], bytes, name)) return false;
        }
        return true;
    }

    public boolean send(String client, byte[] bytes, String name) {
        if (!sendPreFix(client, 'f')) return false;
        if (!send(client, name)) return false;
        if (!sendPreFix(client, 't')) return false;
        if (!send(client, bytes)) return false;
        return sendPreFix(client, 'f');
    }

    public boolean sendAll(File file) {
        String[] clients = getClients();
        for (int i = 0; i < clients.length; i++) {
            if (!send(clients[i], file)) return false;
        }
        return true;
    }

    public boolean send(String client, File file) {
        try {
            return send(client, ByteHandler.read(file), file.getName());
        } catch (IOException e) {
            return false;
        }
    }

}
