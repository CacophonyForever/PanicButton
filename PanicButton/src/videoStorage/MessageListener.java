package videoStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
	private static final Logger logger = Logger.getLogger("log");
	/** The Constant DEFAULT_LISTEN_PORT. */
	public static final int DEFAULT_LISTEN_PORT = 3605;

	private static final int CAPTURER_HI_TIMEOUT_IN_MILLIS = 5000;
	private int listenPort;

	// Boolean is whether or not the specific port is available for use
	// TRUE: ready
	// FALSE: in use
	private Map<Integer, Boolean> streamPorts;
	private boolean doListen;
	private VideoStorageHost host;

	/**
	 * Instantiates a new message listener.
	 * 
	 * @param host
	 *            the host
	 */
	public MessageListener(VideoStorageHost host, List<Integer> listenPorts) {
		listenPort = DEFAULT_LISTEN_PORT;
		streamPorts = new HashMap<Integer, Boolean>();
		for (Integer port : listenPorts) {
			streamPorts.put(port, true);
		}
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
	public MessageListener(VideoStorageHost host, int port, Integer[] sPorts) {
		this.host = host;
		listenPort = port;
		streamPorts = new HashMap<Integer, Boolean>();
		for (Integer sPort : sPorts) {
			streamPorts.put(sPort, true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#start()
	 */
	@Override
	public void run() {
		System.out.println("FFOOOO");
		doListen = true;
		ServerSocket sock = null;
		try {
			sock = new ServerSocket(listenPort);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Listening on port " + sock.getLocalPort());
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
			throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintWriter pr = new PrintWriter(os);
		long timeoutTime = System.currentTimeMillis()
				+ CAPTURER_HI_TIMEOUT_IN_MILLIS;
		while (System.currentTimeMillis() < timeoutTime) {
			Thread.sleep(100);
			if (br.ready()) {
				boolean doRecord = false;
				boolean useSoundStream = false;
				String requestCommand = br.readLine();
				if (requestCommand.equals("Status?")) {
					System.out.println("Got asked for status. Telling it.");
					handleGetStatus(br, pr);
					return;
				} else if (requestCommand.equals("RecordWithSound")) {
					doRecord = true;
					useSoundStream = true;
				} else if (requestCommand.equals("Record")) {
					doRecord = true;
				}
				if (doRecord) {
					System.out.println("Got " + requestCommand);

					Integer port = grabNextAvailableStreamPort();
					int soundStreamPort = useSoundStream ? grabNextAvailableStreamPort()
							: 0;
					if (port == null)
						throw new Exception("No available ports");

					String fileName = "~/vid" + System.currentTimeMillis()
							+ ".ogg";
					CaptureListener cLis = new CaptureListener(port, fileName,
							soundStreamPort);
					cLis.start();
					while (!cLis.isReady()) {
						Thread.sleep(100);
					}
					pr.write(port + "\n");
					if (useSoundStream) {
						pr.write(soundStreamPort + "\n");
					}
					pr.flush();
				}
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

	public Map<Integer, Boolean> getStreamPorts() {
		return streamPorts;
	}

	public void setStreamPorts(HashMap<Integer, Boolean> streamPorts) {
		this.streamPorts = streamPorts;
	}

	private Integer grabNextAvailableStreamPort() {
		for (Map.Entry<Integer, Boolean> entry : streamPorts.entrySet()) {
			if (entry.getValue() == true) {
				entry.setValue(false);
				return entry.getKey();
			}
		}

		return null;
	}

	public void setStreamPortsFromIntArray(Integer[] ports) {
		streamPorts = new HashMap<Integer, Boolean>();
		for (Integer port : ports) {
			streamPorts.put(port, true);
		}
	}

}
