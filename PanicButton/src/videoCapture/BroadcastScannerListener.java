/*
 * 
 */
package videoCapture;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving broadcastScanner events. The class that
 * is interested in processing a broadcastScanner event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addBroadcastScannerListener<code> method. When
 * the broadcastScanner event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see BroadcastScannerEvent
 */
public class BroadcastScannerListener extends Thread {

	public static final int DEFAULT_RESPONSE_PORT = 36001;
	public static final int DEFAULT_LISTEN_PORT=3666;
	public static final String DEFAULT_RESPONSE_MESSAGE = "Hello";
	public static final String DEFAULT_EXPECTED_MESSAGE = "Hello";
	private static final Logger logger = Logger.getLogger("log");

	private int portToRunOn;
	private String messsageToRespondWith;
	private boolean doListen;
	private String expectedMessage;
	private int responsePort;

	/**
	 * Instantiates a new broadcast scanner listener.
	 * 
	 * @param runPort
	 *            the port to listen on
	 * @param message
	 *            the response message to send back to scanner
	 * @param expMessage
	 *            the expected message
	 * @param resPort
	 *            the port to respond with
	 */
	public BroadcastScannerListener(int runPort, String message,
			String expMessage, int resPort) {
		portToRunOn = runPort;
		messsageToRespondWith = message;
		expectedMessage = expMessage;
		responsePort = resPort;
		doListen = true;
		start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		logger.info("Initializing listening for discovering triggerers");
		try {
			Listen();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.severe("Could not initiate broadcast scanner listener. This capturer will not publicly advertise");
		}
	}

	/**
	 * Listen.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void Listen() throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(DEFAULT_LISTEN_PORT);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[2];
		
		// Begin listening loop
		while (doListen == true) {
			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				
				// get packet and check that its whats expected
				String message = new String(receivePacket.getData());
				assert message.equals(expectedMessage);
				InetAddress IPAddress = receivePacket.getAddress();
				
				logger.info("RECEIVED message from " + IPAddress);
				int port = Integer.parseInt(messsageToRespondWith);
				
				// TODO: There's GOTTA be a better way
				sendData[0] = (byte) Math.floor(port / 256.0);
				sendData[1] = (byte) (port % 256);

				DatagramPacket sendPacket = new DatagramPacket(sendData, 2,
						IPAddress, DEFAULT_RESPONSE_PORT);

				serverSocket.send(sendPacket);
				logger.info("SENT : " + sendPacket.getAddress() + ":"
						+ sendPacket.getPort() + " =\""
						+ new String(sendPacket.getData(), "UTF-8") + "\"");
			} catch (Exception e) {
				logger.warning("Recieved a packet, but it didn't work out for some reason.");
			}
		}
	}

	/**
	 * Break the listening loop, ends the thread
	 */
	public void stopListen() {
		doListen = false;
	}

	/**
	 * Gets the running status.
	 * 
	 * @return the running status
	 */
	public boolean getRunningStatus() {
		return doListen;
	}

}
