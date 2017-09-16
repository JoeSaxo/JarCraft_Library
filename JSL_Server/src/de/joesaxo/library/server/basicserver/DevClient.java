package de.joesaxo.library.server.basicserver;

import java.io.IOException;
import java.net.Socket;

public class DevClient {

	public static final long defaultMaxTimeOut = 500;
	public static final long defaultDelayTime = 250;
	
	
	private boolean connected;
	
	private boolean disconnectOnTimeOut;

	private long maxTimeOut;
	private long delayTime;

	private String clientId;

	private IOStream stream;

	private IBasicServerEvent iBasicServerEvent;

	// ------------------------------ constructors
	// ----------------------------------

	public DevClient(IBasicServerEvent iBasicServerEvent) {
		this.iBasicServerEvent = iBasicServerEvent;
		connected = false;
		disconnectOnTimeOut = true;
		setMaxTimeOut(defaultMaxTimeOut);
		setDelayTime(defaultDelayTime);
	}
	
	public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		this.disconnectOnTimeOut = disconnectOnTimeOut;
	}

	public void setMaxTimeOut(long maxTimeOut) {
		this.maxTimeOut = maxTimeOut;
		if (delayTime >= maxTimeOut) delayTime = maxTimeOut / 2;
	}
	
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
		if (delayTime >= maxTimeOut) maxTimeOut = delayTime * 2;
	}
	
	public boolean start(Socket client) {
		if (connected) return false;
		// establishing connection to Server
		clientId = client.getRemoteSocketAddress().toString();
		// creating streams for communication
		openStreams(client);
		// connection established
		connected = true;
		iBasicServerEvent.establishedConnection(getClientID());
		return true;
	}
	
	public void update() {

		long time = System.currentTimeMillis();
	    while (stream.ready() && connected && deltaTime(time) < delayTime) {
	        iBasicServerEvent.newMessage(getClientID(), stream.read());
        }

        if (stream.getLastReadTime() == -1 || deltaTime(stream.getLastReadTime()) > delayTime) {
	        stream.ping();
        } else if (stream.getLastReadTime() != -1 && deltaTime(stream.getLastReadTime()) > maxTimeOut) {
            iBasicServerEvent.timedOut(getClientID(), deltaTime(stream.getLastReadTime()));
            if (disconnectOnTimeOut) stopClient();
        }

		
	}


	// ------------------------ Help methodes ------------------------
	
	private long deltaTime(long time) {
		return System.currentTimeMillis() - time;
	}

	private void openStreams(Socket client) {
		try {
			stream = new IOStream(client);
		} catch (IOException e) {}
	}

	private void closeStreams() {
		try {
			stream.close();
		} catch (IOException e) {}
	}

	// ----------------------- external Access -------------------------------

    public void send(char message) {
        stream.write(message);
    }

	public String getClientID() {
		return clientId;
	}

	public boolean isConnected() {
		return connected;
	}

	public void stopClient() {
		connected = false;
		if (stream != null) {
			closeStreams();
		}
		iBasicServerEvent.stopped(getClientID());
	}

	// ---------------------------- Annotation call ----------------------

    /*
    private void callAnnotation(EServerNotification notification, Object attribute) {
        AnnotationFilter module = notification.getModule();
		Parameter clientSet = new Parameter("client", getClientID());
		Parameter clientUnSet = new Parameter("client", "");
		Parameter message = new Parameter("message", attribute);
	    switch (notification) {
            case ESTABLISHEDCONNECTION:
                annotationManager.invokeMethods(module.setParameters(new Parameter[]{clientUnSet}), clientId);
                annotationManager.invokeMethods(module.setParameters(clientSet));
                break;
            case NEWMESSAGE:
                annotationManager.invokeMethods(module.addParameterr("client", "", true).addParameterr("message", ""), new Object[]{clientId, atribute});
                annotationManager.invokeMethods(module.addParameterr("client", clientId, true).addParameterr("message", ""), atribute);
                module = module.addParameterr("message", atribute, true);
                annotationManager.invokeMethods(module, clientId);
                annotationManager.invokeMethods(module.addParameterr("client", clientId));
                break;
            case TIMEDOUT:
                annotationManager.invokeMethods(module.addParameterr("client", "", true), new Object[]{clientId, atribute});
                annotationManager.invokeMethods(module.addParameterr("client", clientId, true), atribute);
                break;
            case STOPPED:
                annotationManager.invokeMethods(module.addParameterr("client", "", true), clientId);
                annotationManager.invokeMethods(module.addParameterr("client", clientId, true));
                break;
            default:
                break;
        }
    }
    //*/

}
