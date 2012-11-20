package com.example.panic.button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class remoteVideoCapturer {
	private String capturerHost;
	private int capturerPort;
	private String capturerName;
	private String status;
	private String capturerStatus;
	
	private static final int HELLO_TIMEOUT_IN_MILLIS=5000;
	private static final String HELLO_RESPONSE_STRING="Hello";
	
	public remoteVideoCapturer(String host, int port)
	{
		this.capturerHost=host;
		this.capturerPort=port;
	}
	
	public void triggerRecording() throws Exception
	{
		Socket sock = new Socket(capturerHost, capturerPort);
		PrintWriter pw = new PrintWriter(sock.getOutputStream());
		pw.write("Trigger");
		pw.flush();
		sock.close();
	}

	public String getCapturerHost() {
		return capturerHost;
	}

	public void setCapturerHost(String capturerHost) {
		this.capturerHost = capturerHost;
	}

	public int getCapturerPort() {
		return capturerPort;
	}

	public void setCapturerPort(int capturerPort) {
		this.capturerPort = capturerPort;
	}
	
	public boolean confirmExistence()
	{
		try {
			System.out.println ("connexing to " + capturerHost + " : " + capturerPort);
			Socket helloSocket = new Socket(capturerHost,capturerPort);
			BufferedReader br = new BufferedReader(new InputStreamReader(helloSocket.getInputStream()));
			PrintWriter pw = new PrintWriter(helloSocket.getOutputStream());			
			pw.write("Hello\n");
			pw.flush();
			long timeoutTime = System.currentTimeMillis()+HELLO_TIMEOUT_IN_MILLIS;
			
			while (!br.ready() && System.currentTimeMillis()<timeoutTime)
			{
				System.out.print(".");
				Thread.sleep(100);
			}			
			assert(br.ready());
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
	
	public String toString()
	{
		return capturerHost + ":" + capturerPort + " (" + capturerName + ") - " + capturerStatus;
	}
	
	

}
