package de.joesaxo.library.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 10.08.2017.
 */
public class MessageClientHandler {

    private MessageHandler messageHandler;
    private String clientID;

    private boolean slash;
    private byte command;
    private static final byte NO_COMMAND = 0;

    private List<Character> sequenceBuffer;


    public MessageClientHandler(MessageHandler messageHandler, String clientID) {
        this.messageHandler = messageHandler;
        this.clientID = clientID;
        sequenceBuffer = new ArrayList<>();
        slash = false;
        command = NO_COMMAND;
    }

    public void newMessage(String clientID, char message) {
        //System.out.println("CHAR: " + (byte)message);
        if (!slash && message == '/') {
            slash = true;
        } else if (!slash && command == NO_COMMAND) {
            messageHandler.newCharacter(clientID, message);
        } else if (slash && command == NO_COMMAND && message == '/') {
            slash = false;
            messageHandler.newCharacter(clientID, message);
        } else if (slash && command == NO_COMMAND) {
            slash = false;
            command = (byte) message;
        } else if (!slash && command != NO_COMMAND) {
            sequenceBuffer.add(message);
        } else if (slash && command != (byte) message) {
            slash = false;
            sequenceBuffer.add('/');
            sequenceBuffer.add(message);
        } else {
            slash = false;
            command = NO_COMMAND;
            convertSequence(message);
            sequenceBuffer.clear();
        }
    }

    private void convertSequence(char keyCharacter) {
        switch (keyCharacter) {
            case 's':
                messageHandler.newString(clientID, convertSequenceToString(sequenceBuffer));
                break;
            case 'b':
                String temp = convertSequenceToString(sequenceBuffer);
                byte[] bytes = new byte[temp.length()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = (byte)temp.charAt(i);
                }
                messageHandler.newByteArray(clientID, bytes);
                break;
            case 'f':
                boolean slash = false;
                for (int i = 2; i < sequenceBuffer.size(); i++) {
                    if (sequenceBuffer.get(i).equals('/')) {
                        if (!slash) {
                            slash = true;
                        } else {
                            slash = false;
                        }
                    } else if (slash && sequenceBuffer.get(i).equals('t')) {
                        String name = convertSequenceToString(sequenceBuffer.subList(2, i - 2));
                        byte[] data = convertSequenceToString(sequenceBuffer.subList(i + 1, sequenceBuffer.size() - 1 - 2)).getBytes();
                        messageHandler.newFile(clientID, name, data);
                    }
                }


                break;
        }
    }

    private String convertSequenceToString(List<Character> chars) {
        StringBuilder b = new StringBuilder(chars.size());
        boolean slash = false;
        for (char c : chars) {
            if (!slash && c == '/') {
                slash = true;
            } else if (slash && c != '/') {
                slash = false;
            } else {
                slash = false;
                b.append(c);
            }
        }
        return b.toString();
    }
}
