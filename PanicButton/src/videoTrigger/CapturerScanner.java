/*
 * 
 */
package videoTrigger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class CapturerScanner.
 */
public class CapturerScanner extends Thread {

    /** The Constant RESPONSE_TIMEOUT_IN_MILLIS. */
    public final static int RESPONSE_TIMEOUT_IN_MILLIS = 2500;

    /** The Constant RESPONSE_LISTEN_PORT. */
    public final static int RESPONSE_LISTEN_PORT = 36001;

    /** The response listen socket. */
    private int responseListenSocket;

    /** The my client. */
    private VideoTriggerClient myClient;

    /**
     * Instantiates a new capturer scanner.
     * 
     * @param responseListenPort
     *            the response listen port
     * @param client
     *            the client
     */
    public CapturerScanner(int responseListenPort, VideoTriggerClient client) {
	myClient = client;
    }

    /**
     * Scan network.
     */
    public void scanNetwork() {
	try {
	    System.out.println(this.getState());
	    start();
	    Thread.sleep(100);
	    sendBroadcast();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    @Override
    public void run() {
	try {
	    listenForResponse();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println("STOOOPED");
    }

    /**
     * Listen for response.
     * 
     * @throws Exception
     *             the exception
     */
    public void listenForResponse() throws Exception {
	DatagramSocket serverSocket = new DatagramSocket(RESPONSE_LISTEN_PORT);

	long timeoutTime = System.currentTimeMillis()
		+ RESPONSE_TIMEOUT_IN_MILLIS;
	byte[] receiveData = new byte[2];

	List<InetAddress> respondedHosts = new ArrayList<InetAddress>();

	while (System.currentTimeMillis() < timeoutTime) {
	    try {
		DatagramPacket p = new DatagramPacket(receiveData, 2);
		serverSocket.setSoTimeout(RESPONSE_TIMEOUT_IN_MILLIS);
		System.out.println(p.getLength());
		serverSocket.receive(p);
		byte[] data = p.getData();
		System.out.println("{" + data + "}");
		int port = (data[0] & 0xFF) * 256
			+ (data[1] & 0xFF);
		System.out.println("RECIEVED RESPONSE from " + p.getAddress()
			+ ":" + p.getPort() + "=\"" + port + "\"");
		myClient.addNewHost(p.getAddress().toString().substring(1),
			port);
		respondedHosts.add(p.getAddress());
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	serverSocket.close();
	myClient.doneScanning();
    }

    /**
     * Send broadcast.
     * 
     * @throws Exception
     *             the exception
     */
    public void sendBroadcast() throws Exception {
	String Broadcastaddress = new String("255.255.255.255");
	int port = 9876;
	DatagramSocket serverSocket = new DatagramSocket();
	serverSocket.setBroadcast(true);
	InetAddress IPAddress = InetAddress.getByName(Broadcastaddress);
	System.out.println("Sending Discovery message to " + IPAddress
		+ "Via UDP port " + port);

	byte[] sendData = new byte[6];
	sendData[0] = 'H';
	sendData[1] = 'E';
	sendData[2] = 'L';
	sendData[3] = 'L';
	sendData[4] = 'O';
	sendData[5] = '\n';

	DatagramPacket sendPacket = new DatagramPacket(sendData,
		sendData.length, IPAddress, port);

	serverSocket.send(sendPacket);
    }

}
