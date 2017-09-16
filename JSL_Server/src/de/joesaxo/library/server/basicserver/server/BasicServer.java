package de.joesaxo.library.server.basicserver.server;

import de.joesaxo.library.server.basicserver.IBasicServerEvent;
import de.joesaxo.library.server.basicserver.IServerEvent;
import de.joesaxo.library.server.basicserver.IServerMessageHandler;

public class BasicServer implements IBasicServerEvent {

	private IServerEvent iServerEvent;
	private IServerMessageHandler iServerMessageHandler;

	private ServerRunnable runnableServer;

	// ----------------------- Constructors -----------------------------------

	public BasicServer(int port, int maxclients) {
		runnableServer = new ServerRunnable(port, maxclients, this);
	}

	// ------------------------------- start / stop
	// --------------------------------

	public boolean start() {
		if (runnableServer.isAlive()) return false;
		runnableServer.start();
		return true;
	}

	public boolean stop() {
		if (!runnableServer.isAlive()) return false;
		runnableServer.stopServer();
		return true;
	}

	public boolean isRunning() {
		return runnableServer.isAlive();
	}

    public void setIServerEvent(IServerEvent iServerEvent) {
        this.iServerEvent = iServerEvent;
    }

    public void setIServerMessageHandler(IServerMessageHandler iServerMessageHandler) {
        this.iServerMessageHandler = iServerMessageHandler;
    }

    public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		runnableServer.setDisconnectOnTimeOut(disconnectOnTimeOut);
	}
	
	public void setMaxTimeOut(long maxTimeOut) {
		runnableServer.setMaxTimeOut(maxTimeOut);
	}
	
	public void setDelayTime(long delayTime) {
		runnableServer.setDelayTime(delayTime);
	}

	// ----------------------- external Access ----------------------------

	public boolean disconnect(String client) {
		if (!runnableServer.isAlive()) return false;
		return runnableServer.disconnect(client);
	}

	public boolean disconnectAll() {
		if (!runnableServer.isAlive()) return false;
		runnableServer.disconnectAll();
		return true;
	}

	public boolean isConnected(String client) {
		if (!runnableServer.isAlive()) return false;
		return runnableServer.isConnected(client);
	}

	public int connectedClients() {
		return runnableServer.connectedClients();
	}

	public String getClient(int clientId) {
		return runnableServer.getClient(clientId);
	}

	public String[] getClients() {
		String[] clients = new String[connectedClients()];
		for (int i = 0; i < clients.length; i++) {
			clients[i] = runnableServer.getClient(i);
		}
		return clients;
	}

	// ------------------------ Send ---------------------------------------

	public boolean sendAll(char message) {
		if (!runnableServer.isAlive()) return false;
		String[] clients = getClients();
		for (int i = 0; i < clients.length; i++) {
			send(clients[i], message);
		}
		return true;
	}

	public boolean send(String client, char message) {
		if (!runnableServer.isAlive()) return false;
		runnableServer.Send(client, message);
		return true;
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
