package de.joesaxo.library.server.basicserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class IOStream {

    public static final boolean PING_REQUEST = true;
    public static final boolean PING_ANSWER = false;
    private static final int FIRST_BUFFER_INDEX = 0;

	Socket socket;

	private InputStream inputstream;
	private OutputStream outputstream;
	private BufferedReader bufferedreader;
	private PrintWriter printwriter;

    private long lastWroteTime;
    private long lastReadTime;

	List<Character> buffer;
	boolean backslash;

	IOStream(Socket socket) throws IOException {
		this.socket = socket;
		buffer = new ArrayList<>();
		sendBuffer = new ArrayList<>();
		backslash = false;
        lastWroteTime = -1;
        lastReadTime = -1;
		createInput();
		createOutput();
	}

	private void createInput() throws IOException {
		inputstream = socket.getInputStream();
		bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
	}

	private void createOutput() throws IOException {
		outputstream = socket.getOutputStream();
		printwriter = new PrintWriter(outputstream);
	}

	public void close() throws IOException {
		inputstream.close();
		outputstream.close();
		bufferedreader.close();
		printwriter.close();
		socket.close();

	}
	
	public boolean ready() {
		update();
		return buffer.size() > 0;
	}

	public char read() {
		update();
        return buffer.remove(FIRST_BUFFER_INDEX);
	}

    public boolean ping() {
        return ping(PING_REQUEST);
    }

    public void write(char character) {
        sendBuffer.add(character);
    }

    public long getLastWroteTime() {
	    return lastWroteTime;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    private void update() {
		try {
			while (bufferedreader.ready()) {
				char input = (char)bufferedreader.read();
				lastReadTime = System.currentTimeMillis();
				if (backslash && input != '\\') {
				    if (input == 'r') ping(PING_ANSWER);
				    backslash = false;
                } else if (!backslash && input == '\\') {
				    backslash = true;
                } else {
				    backslash = false;
                    buffer.add(input);
                }
			}
			while (sendBuffer.size() > 0) {
			    System.out.println("COUNTER: " + sendBuffer.size());
			    writeBufferedCharacter(sendBuffer.remove(FIRST_BUFFER_INDEX));
            }
            printwriter.flush();
		} catch (IOException e) {}
	}

	private boolean ping(boolean request) {
		char character;
		if (request) {
		    character = 'r';
        } else {
		    character = 'a';
        }
		if (!writeCharacter('\\')) return false;
		if (!writeCharacter(character)) return false;
		printwriter.flush();
		return true;
	}

	private List<Character> sendBuffer;

	private void writeBufferedCharacter(char character) {
        if (character == '\\') {
            writeCharacter('\\');
        }
        writeCharacter(character);
	}

	private boolean writeCharacter(char character) {
        if (!printwriter.checkError()) {
            printwriter.write(character);
            if (!printwriter.checkError()) {
                lastWroteTime = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }

}
