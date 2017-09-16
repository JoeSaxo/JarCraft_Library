package de.joesaxo.library.server.basicserver.client;

import de.joesaxo.library.server.basicserver.IBasicServerEvent;
import de.joesaxo.library.server.basicserver.IServerEvent;
import de.joesaxo.library.server.basicserver.IServerMessageHandler;

import java.lang.Thread.State;

public class BasicClient implements IBasicServerEvent {

	private IServerEvent iServerEvent;
	private IServerMessageHandler iServerMessageHandler;

	private ClientRunnable runnableClient;
	private Thread thread;

	private IBasicServerEvent serverEvent;

	public BasicClient(String IP, int port) {
		runnableClient = new ClientRunnable(IP, port, this);
	}

	// ------------------------------Start / Stop -----------------------------

	public boolean start() {
		if (isRunning()) return false;
		thread = new Thread(runnableClient);
		thread.start();
		return true;
	}

	public boolean stop() {
		if (!isRunning()) return false;
		runnableClient.stopClient();
		return true;
	}

	public boolean isRunning() {
		return !(thread == null || thread.getState().equals(State.TERMINATED));
	}

	public void setIServerEvent(IServerEvent iServerEvent) {
		this.iServerEvent = iServerEvent;
	}

	public void setIServerMessageHandler(IServerMessageHandler iServerMessageHandler) {
		this.iServerMessageHandler = iServerMessageHandler;
	}

	public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		runnableClient.setDisconnectOnTimeOut(disconnectOnTimeOut);
	}

	public void setMaxTimeOut(long maxTimeOut) {
		runnableClient.setMaxTimeOut(maxTimeOut);
	}
	
	public void setDelayTime(long delayTime) {
		runnableClient.setDelayTime(delayTime);
	}

	// ---------------------- external Access --------------------------------

	public boolean send(char message) {
		if (!isRunning()) return false;
		runnableClient.send(message);
		return true;
	}

	public String getClient() {
		return runnableClient.getClientID();
	}

	public boolean isConnected() {
		return runnableClient.isConnected();
	}

	@Override
	public void establishedConnection(String clientID) {
		if (iServerEvent != null) {
			iServerEvent.establishedConnection(clientID);
		}
	}

	@Override
	public void newMessage(String clientID, char message) {
		if (iServerMessageHandler != null) {
			iServerMessageHandler.newMessage(clientID, message);
		}
	}

	@Override
	public void timedOut(String clientID, long time) {
		if (iServerEvent != null) {
			iServerEvent.timedOut(clientID, time);
		}
	}

	@Override
	public void stopped(String clientID) {
		if (iServerEvent != null) {
			iServerEvent.stopped(clientID);
		}
	}
}
