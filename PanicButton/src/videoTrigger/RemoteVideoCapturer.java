package videoTrigger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class remoteVideoCapturer.
 */
public class RemoteVideoCapturer implements Serializable {

    /** The capturer host. */
    private String capturerHost;

    /** The capturer port. */
    private int capturerPort;

    /** The capturer name. */
    private String capturerName;

    /** The status. */
    private String status;

    /** The capturer status. */
    private String capturerStatus;

    /** The Constant HELLO_TIMEOUT_IN_MILLIS. */
    private static final int HELLO_TIMEOUT_IN_MILLIS = 15000;

    /** The Constant HELLO_RESPONSE_STRING. */
    private static final String HELLO_RESPONSE_STRING = "Hello";

    /**
     * Trigger recording.
     * 
     * @throws Exception
     *             the exception
     */
    public void triggerRecording() throws Exception {
	capturerStatus = "Triggering";
	Socket sock = new Socket(capturerHost, capturerPort);
	PrintWriter pw = new PrintWriter(sock.getOutputStream());
	BufferedReader br = new BufferedReader(new InputStreamReader(
		sock.getInputStream()));
	pw.write("Trigger\n");
	pw.flush();
	long timeoutTime = System.currentTimeMillis() + HELLO_TIMEOUT_IN_MILLIS;

	while (!br.ready() && System.currentTimeMillis() < timeoutTime) {
	    System.out.print(".");
	    Thread.sleep(100);
	}

	if (br.ready()) {
	    capturerStatus = br.readLine();
	} else {
	    capturerStatus = "ERROR";
	}
	sock.close();
    }

    /**
     * Gets the capturer host.
     * 
     * @return the capturer host
     */
    public String getCapturerHost() {
	return capturerHost;
    }

    /**
     * Sets the capturer host.
     * 
     * @param capturerHost
     *            the new capturer host
     */
    public void setCapturerHost(String capturerHost) {
	this.capturerHost = capturerHost;
    }

    /**
     * Gets the capturer port.
     * 
     * @return the capturer port
     */
    public int getCapturerPort() {
	return capturerPort;
    }

    /**
     * Sets the capturer port.
     * 
     * @param capturerPort
     *            the new capturer port
     */
    public void setCapturerPort(int capturerPort) {
	this.capturerPort = capturerPort;
    }

    /**
     * Confirm existence.
     * 
     * @return true, if successful
     */
    public boolean confirmExistence() {
	try {
	    System.out.println("connexing to " + capturerHost + " : "
		    + capturerPort);
	    Socket helloSocket = new Socket(capturerHost, capturerPort);
	    BufferedReader br = new BufferedReader(new InputStreamReader(
		    helloSocket.getInputStream()));
	    PrintWriter pw = new PrintWriter(helloSocket.getOutputStream());
	    pw.write("Hello\n");
	    pw.flush();
	    long timeoutTime = System.currentTimeMillis()
		    + HELLO_TIMEOUT_IN_MILLIS;

	    while (!br.ready() && System.currentTimeMillis() < timeoutTime) {
		System.out.print(".");
		Thread.sleep(100);
	    }
	    assert (br.ready());
	    String confirmHello = br.readLine();
	    assert confirmHello.equals(HELLO_RESPONSE_STRING);
	    capturerName = br.readLine();
	    status = br.readLine();
	    helloSocket.close();
	    System.out.println(toString());
	    return true;
	} catch (Exception e) {
	    e.printStackTrace();
	    return false;
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return capturerHost + ":" + capturerPort + " (" + capturerName + ") - "
		+ capturerStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object compobj) {
	RemoteVideoCapturer compare = (RemoteVideoCapturer) compobj;
	System.out.println("hey!!!");
	if (!capturerHost.equals(compare.getCapturerHost()))
	    return false;

	if (capturerPort != compare.getCapturerPort())
	    return false;

	return true;
    }

}
