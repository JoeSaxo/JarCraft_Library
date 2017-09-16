package de.joesaxo.library.server;

import de.joesaxo.library.server.basicserver.IServerMessageHandler;
import de.joesaxo.library.server.basicserver.client.BasicClient;
import org.jarcraft.library.iotools.ByteHandler;

import java.io.File;
import java.io.IOException;


/**
 * Created by Jens on 09.08.2017.
 */
public class Client extends BasicClient {

    public Client(String ip, int port) {
        super(ip, port);
    }

    @Deprecated
    @Override
    public void setIServerMessageHandler(IServerMessageHandler iServerMessageHandler) {
        super.setIServerMessageHandler(iServerMessageHandler);
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        super.setIServerMessageHandler(messageHandler);
    }

    private boolean sendPreFix(char character) {
        if (!super.send('/')) return false;
        return super.send(character);
    }

    @Override
    public boolean send(char c) {
        return sendCharacter(c);
    }

    private boolean sendCharacter(char c) {
        if (c == '/') {
            return sendPreFix(c);
        } else {
            return super.send(c);
        }

    }

    public boolean send(String message) {
        if (!sendPreFix('s')) return false;
        for (char character : (message).toCharArray()) {
            if (!sendCharacter(character)) return false;
        }
        return sendPreFix('s');
    }

    public boolean send(byte[] bytes) {
        if (!sendPreFix('b')) return false;
        for (byte b : bytes) {
            if (!sendCharacter((char)b)) return false;
        }
        return sendPreFix('b');
    }

    public boolean send(byte[] bytes, String name) {
        if (!sendPreFix('f')) return false;
        if (!send(name)) return false;
        if (!sendPreFix('t')) return false;
        if (!send(bytes)) return false;
        return sendPreFix('f');
    }

    public boolean send(File file) {
        try {
            return send(ByteHandler.read(file), file.getName());
        } catch (IOException e) {
            return false;
        }
    }
}
