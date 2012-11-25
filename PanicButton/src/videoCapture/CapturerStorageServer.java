/*
 * 
 */
package videoCapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

// TODO: Auto-generated Javadoc
/**
 * The Class CapturerStorageServer.
 */
public class CapturerStorageServer implements Serializable {
	private String name;
	private String hostname;
	private int port;
	private long freeSpace;
	private String saveLocation;

	/**
	 * Gets the my name.
	 * 
	 * @return the my name
	 */
	public String getMyName() {
		return name;
	}

	/**
	 * Sets the my name.
	 * 
	 * @param myName
	 *            the new my name
	 */
	public void setMyName(String myName) {
		this.name = myName;
	}

	/**
	 * Instantiates a new capturer storage server.
	 * 
	 * @param hostname
	 *            the hostname
	 * @param port
	 *            the port
	 */
	public CapturerStorageServer(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	/**
	 * Gets the my hostname.
	 * 
	 * @return the my hostname
	 */
	public String getMyHostname() {
		return hostname;
	}

	/**
	 * Sets the my hostname.
	 * 
	 * @param myHostname
	 *            the new my hostname
	 */
	public void setMyHostname(String myHostname) {
		this.hostname = myHostname;
	}

	/**
	 * Gets the my port.
	 * 
	 * @return the my port
	 */
	public int getMyPort() {
		return port;
	}

	/**
	 * Sets the my port.
	 * 
	 * @param myPort
	 *            the new my port
	 */
	public void setMyPort(int myPort) {
		this.port = myPort;
	}

	/**
	 * Gets the my free space.
	 * 
	 * @return the my free space
	 */
	public long getMyFreeSpace() {
		return freeSpace;
	}

	/**
	 * Sets the my free space.
	 * 
	 * @param myFreeSpace
	 *            the new my free space
	 */
	public void setMyFreeSpace(long myFreeSpace) {
		this.freeSpace = myFreeSpace;
	}

	/**
	 * Gets the my save location.
	 * 
	 * @return the my save location
	 */
	public String getMySaveLocation() {
		return saveLocation;
	}

	/**
	 * Sets the my save location.
	 * 
	 * @param mySaveLocation
	 *            the new my save location
	 */
	public void setMySaveLocation(String mySaveLocation) {
		this.saveLocation = mySaveLocation;
	}

	/**
	 * Attempts to establish a connection with the server. If it is
	 * successful, it will populate the freeSpace field
	 * @return the status of the storage server
	 */
	public String testConnectToServer() {
		try {
			// Establish a socket and send 'hi' message
			Socket storageServer = new Socket(hostname, port);
			PrintWriter pw = new PrintWriter(storageServer.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					storageServer.getInputStream()));
			pw.println("Status?");
			pw.flush();
			
			// Read response
			String status = br.readLine();
			String freeSpace = br.readLine();
			this.freeSpace = Long.parseLong(freeSpace);
			storageServer.close();
			return status;
		} catch (UnknownHostException e) {
			return "Unknown Host";
		} catch (IOException e) {
			return "Could not Connect";
		}
	}

	@Override
	public String toString() {
		return name + " (" + hostname + ":" + port + ")";
	}

}
