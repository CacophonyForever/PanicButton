package videoStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving message events. The class that is
 * interested in processing a message event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addMessageListener<code> method. When
 * the message event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see MessageEvent
 */
public class MessageListener extends Thread {

	/** The Constant DEFAULT_LISTEN_PORT. */
	public static final int DEFAULT_LISTEN_PORT = 3605;

	private static final int CAPTURER_HI_TIMEOUT_IN_MILLIS = 5000;
	private int listenPort;
	private Integer[] streamPorts;
	private boolean doListen;
	private VideoStorageHost host;

	/**
	 * Instantiates a new message listener.
	 * 
	 * @param host
	 *            the host
	 */
	public MessageListener(VideoStorageHost host) {
		listenPort = DEFAULT_LISTEN_PORT;
		streamPorts = new Integer[] { 3602, 3603, 3604 };
		this.host = host;
	}

	/**
	 * Stop listening.
	 */
	public void stopListening() {
		doListen = false;
	}

	/**
	 * Instantiates a new message listener.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param streamPorts
	 *            the stream ports
	 */
	public MessageListener(VideoStorageHost host, int port,
			Integer[] streamPorts) {
		this.host = host;
		listenPort = port;
		this.streamPorts = streamPorts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#start()
	 */
	@Override
	public void start() {
		doListen = true;
		ServerSocket sock = null;
		try {
			sock = new ServerSocket(listenPort);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (doListen) {
			try {
				Socket s = sock.accept();
				handleCaptureListener(s.getInputStream(), s.getOutputStream());

			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Handle capture listener.
	 * 
	 * @param is
	 *            the is
	 * @param os
	 *            the os
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void handleCaptureListener(InputStream is, OutputStream os)
			throws InterruptedException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintWriter pr = new PrintWriter(os);
		long timeoutTime = System.currentTimeMillis()
				+ CAPTURER_HI_TIMEOUT_IN_MILLIS;
		while (System.currentTimeMillis() < timeoutTime) {
			Thread.sleep(100);
			if (br.ready()) {
				String camName = br.readLine();
				if (camName.equals("Status?")) {
					System.out.println("Got asked for status. Telling it.");
					handleGetStatus(br, pr);
					return;
				}
				System.out.println("Got " + camName);
				int port = streamPorts[0];
				String fileName = "~/vid" + System.currentTimeMillis() + ".ogg";
				CaptureListener cLis = new CaptureListener(port, fileName);
				cLis.start();
				while (!cLis.isReady()) {
					Thread.sleep(100);
				}
				pr.write(streamPorts[0] + "\n");
				pr.flush();
			}
		}
	}

	/**
	 * Handle get status.
	 * 
	 * @param br
	 *            the br
	 * @param pr
	 *            the pr
	 */
	private void handleGetStatus(BufferedReader br, PrintWriter pr) {
		pr.println("OK");
		pr.println((host.getMaxFileSpace()));
		pr.flush();
	}

	/**
	 * Gets the my listen port.
	 * 
	 * @return the my listen port
	 */
	public int getMyListenPort() {
		return listenPort;
	}

	/**
	 * Sets the my listen port.
	 * 
	 * @param myListenPort
	 *            the new my listen port
	 */
	public void setMyListenPort(int myListenPort) {
		this.listenPort = myListenPort;
	}

	/**
	 * Gets the my stream ports.
	 * 
	 * @return the my stream ports
	 */
	public Integer[] getMyStreamPorts() {
		return streamPorts;
	}

	/**
	 * Sets the my stream ports.
	 * 
	 * @param myStreamPorts
	 *            the new my stream ports
	 */
	public void setMyStreamPorts(Integer[] myStreamPorts) {
		this.streamPorts = myStreamPorts;
	}

}
