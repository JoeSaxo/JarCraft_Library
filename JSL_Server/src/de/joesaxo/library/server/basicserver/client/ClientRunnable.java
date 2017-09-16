package de.joesaxo.library.server.basicserver.client;

import de.joesaxo.library.server.basicserver.DevClient;
import de.joesaxo.library.server.basicserver.IBasicServerEvent;
import org.jarcraft.library.time.Time;

import java.io.IOException;
import java.net.Socket;

class ClientRunnable extends DevClient implements Runnable {

	private int port;
	private String IP;
	private boolean stopped;

	ClientRunnable(String IP, int port, IBasicServerEvent iBasicServerEvent) {
		super(iBasicServerEvent);
		this.IP = IP;
		this.port = port;
		stopped = false;
	}
	
	private Socket createSocket() {
		Socket client = null;
		while (client == null && !stopped) {
			try {
				client = new Socket(IP, port);
			} catch (IOException e) {}
		}
		return client;
	}

	@Override
	public void run() {
		start(createSocket());
		while (isConnected()) {
			update();
			Time.WaitMillis(50);
		}
	}
	
	@Override
	public void stopClient() {
		stopped = true;
		super.stopClient();
	}

}
