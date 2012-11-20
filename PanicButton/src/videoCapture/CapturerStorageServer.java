package videoCapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class CapturerStorageServer  implements Serializable{
	private String myName;
	private String myHostname;
	private int myPort;
	private long myFreeSpace;
	private String mySaveLocation;
	
	
	
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public CapturerStorageServer(String hostname, int port)
	{
		myHostname = hostname;
		myPort = port;
	}

	public String getMyHostname() {
		return myHostname;
	}

	public void setMyHostname(String myHostname) {
		this.myHostname = myHostname;
	}
	public int getMyPort() {
		return myPort;
	}

	public void setMyPort(int myPort) {
		this.myPort = myPort;
	}

	public long getMyFreeSpace() {
		return myFreeSpace;
	}

	public void setMyFreeSpace(long myFreeSpace) {
		this.myFreeSpace = myFreeSpace;
	}

	public String getMySaveLocation() {
		return mySaveLocation;
	}

	public void setMySaveLocation(String mySaveLocation) {
		this.mySaveLocation = mySaveLocation;
	}
	
	public String testConnectToServer()
	{
		try {
			Socket storageServer = new Socket(myHostname,myPort);
			PrintWriter pw = new PrintWriter(storageServer.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(storageServer.getInputStream()));
			pw.println("Status?");
			pw.flush();
			System.out.println("Said 'sup'");
			String status = br.readLine();
			System.out.println("got status = " + status);
			String freeSpace = br.readLine();
			myFreeSpace = Long.parseLong(freeSpace);
			System.out.println("got freeSpace = " + freeSpace);
			storageServer.close();
			return status;			
		} catch (UnknownHostException e) {			
			return "Unknown Host";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "Could not Connect";
		}		
	}
	
	public String toString()
	{
		return myName + " (" + myHostname + ":" + myPort +")";
	}
	
	
	
}

