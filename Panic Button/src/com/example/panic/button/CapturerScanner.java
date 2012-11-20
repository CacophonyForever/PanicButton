package com.example.panic.button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class CapturerScanner{
	
	public final static int RESPONSE_TIMEOUT_IN_MILLIS=2500;
	public final static int RESPONSE_LISTEN_PORT=36001;
	private int responseListenSocket;
	private VideoTriggerClient myClient;
	
	public CapturerScanner(int responseListenPort, VideoTriggerClient client)
	{
		myClient = client;
	}
	
	public void scanNetwork()
	{
		try {
			broadcastAndListenForResponse();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public void broadcastAndListenForResponse() throws Exception
	{
		DatagramSocket serverSocket = new DatagramSocket(RESPONSE_LISTEN_PORT);
		
		long timeoutTime = System.currentTimeMillis()+RESPONSE_TIMEOUT_IN_MILLIS;
        byte[] receiveData = new byte[2];

        List<InetAddress> respondedHosts = new ArrayList<InetAddress>();
        Thread.sleep(100);
		sendBroadcast();
		while (System.currentTimeMillis() < timeoutTime)
		{
			try {
				DatagramPacket p = new DatagramPacket(receiveData, 2);
				serverSocket.setSoTimeout(RESPONSE_TIMEOUT_IN_MILLIS);
				System.out.println(p.getLength());
				serverSocket.receive(p);
				byte[] data =p.getData();
				System.out.println("{" + data +"}");
				int port = (int)(data[0]& 0xFF)*256+(int)(data[1]& 0xFF);
				System.out.println("RECIEVED RESPONSE from " + p.getAddress() + ":" +p.getPort() +"=\"" + port +"\"") ;
				myClient.addNewHost(p.getAddress().toString().substring(1), port);
				respondedHosts.add(p.getAddress());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		serverSocket.close();
		myClient.readyScan();
	}
	
	public void sendBroadcast() throws Exception
	{
		String Broadcastaddress = new String("255.255.255.255");
	    int port = 9876;
	    DatagramSocket serverSocket = new DatagramSocket();
	    serverSocket.setBroadcast(true);
	    InetAddress IPAddress = InetAddress.getByName(Broadcastaddress);
	    System.out.println("Sending Discovery message to " + IPAddress + "Via UDP port " + port);

	    byte[] sendData = new byte[6];
	    sendData[0] = 'H';
	    sendData[1] = 'E';
	    sendData[2] = 'L';
	    sendData[3] = 'L';
	    sendData[4] = 'O';
	    sendData[5] = '\n';

	    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress,port);

	    serverSocket.send(sendPacket);
	}

}
