package videoCapture;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class BroadcastScannerListener extends Thread {
	public static final int DEFAULT_BROADCAST_PORT=36001;
	public static final String DEFAULT_RESPONSE_MESSAGE="Hello";
	public static final String DEFAULT_EXPECTED_MESSAGE="Hello";
	private int portToRunOn;
	private String serviceToAdvertise;
	private boolean doListen;
	private String expectedMessage;
	private int responsePort;
	private String responseMessage;
	
	public BroadcastScannerListener(int runPort, String service, String expMessage, int resPort, String resMessage)
	{
		portToRunOn=runPort;
		serviceToAdvertise=service;
		expectedMessage=expMessage;
		responsePort=resPort;
		responseMessage=resMessage;
		doListen=true;
		start();
	}
	
	public void run()
	{
		try {
			Listen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Listen() throws Exception
	{
	       DatagramSocket serverSocket = new DatagramSocket(9876);
           byte[] receiveData = new byte[1024];
           byte[] sendData = new byte[2];
           while(doListen == true)
              {
                 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                 serverSocket.receive(receivePacket);
                 String message = new String( receivePacket.getData());
                 assert message.equals(expectedMessage);
                 InetAddress IPAddress = receivePacket.getAddress();
                 System.out.println("RECEIVED message from " + IPAddress);
                 int port = Integer.parseInt(serviceToAdvertise);
                 // There's GOTTA be a better way
                 sendData[0] = (byte) Math.floor(port/256.0);
                 sendData[1] = (byte) (port % 256);
            	 
                 DatagramPacket sendPacket =
                     new DatagramPacket(sendData, 2, IPAddress, DEFAULT_BROADCAST_PORT);
                
                     serverSocket.send(sendPacket);
                 System.out.println ("SENT : " + sendPacket.getAddress() + ":" + sendPacket.getPort() + " =\""+ new String(sendPacket.getData(),"UTF-8") +"\"");
              }
	}
	
	public void stopListen()
	{
		doListen = false;
	}
	
	public boolean getRunningStatus()
	{
		return doListen;
	}
	

}
