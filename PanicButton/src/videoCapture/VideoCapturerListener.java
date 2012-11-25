package videoCapture;

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
 * The listener interface for receiving videoCapturer events. The class that is
 * interested in processing a videoCapturer event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addVideoCapturerListener<code> method. When
 * the videoCapturer event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see VideoCapturerEvent
 */
public class VideoCapturerListener extends Thread {

	private static final int TIMEOUT_IN_MILLIS = 5000;
	public static final int DEFAULT_PORT = 3601;
	private boolean doListen = false;
	ServerSocket listeningSocket;
	public VideoCapturer videoCapturer;

	/**
	 * Instantiates a new video capturer listener.
	 * 
	 * @param vidcap
	 *            The video capturer
	 * @throws Exception
	 *             it can't open a listen socket
	 */
	public VideoCapturerListener(VideoCapturer vidcap) throws Exception {
		doListen = true;
		videoCapturer = vidcap;
		listeningSocket = new ServerSocket(DEFAULT_PORT);
	}

	/**
	 * Instantiates a new video capturer listener.
	 * 
	 * @param vidcap
	 *            the vidcap
	 * @param port
	 *            the port
	 * @throws Exception
	 *             the exception
	 */
	public VideoCapturerListener(VideoCapturer vidcap, int port)
			throws Exception {
		doListen = true;
		videoCapturer = vidcap;
		listeningSocket = new ServerSocket(port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		listen();
	}

	/**
	 * Listen.
	 */
	public void listen() {
		videoCapturer.setMyStatus(VideoCapturer.STATUS_READY);
		while (doListen == true) {			
			try {
				Socket s = listeningSocket.accept();
				handleConnection(s.getInputStream(), s.getOutputStream());	
				Thread.sleep(250);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		try {
			listeningSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Handle an incoming connection
	 * 
	 * @param is
	 *            the connection's input stream
	 * @param os
	 *            the connection's output stream
	 * @throws Exception
	 *             the exception
	 */
	private void handleConnection(InputStream is, OutputStream os)
			throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintWriter pw = new PrintWriter(os);
		
		// Wait for response
		long timeoutTime = System.currentTimeMillis() + TIMEOUT_IN_MILLIS;		
		while (!br.ready() && System.currentTimeMillis() < timeoutTime) {
			Thread.sleep(100);
		}
		
		// Make sure there was a response and not a timeout
		assert br.ready();
		
		String command = br.readLine();
		
		if (command.equals("Hello")) {
			pw.write("Hello\n");
			pw.write(videoCapturer.getMyName() + "\n");
			pw.write("Ready\n");
			pw.flush();
		} else if (command.equals("Trigger")) {
			videoCapturer.beginRecording();
			pw.write("Recording\n");
			pw.flush();
		} else {
			throw new Exception("Unknown Command");
		}

	}

	/**
	 * Gets the socket port.
	 * 
	 * @return the socket port
	 */
	public int getSocketPort() {
		return listeningSocket.getLocalPort();
	}

	/**
	 * Stop listening. This kills the thread.
	 */
	public void stopListening() {
		try {
			listeningSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doListen = false;
	}

}
