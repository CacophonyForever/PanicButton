/*
 * 
 */
package videoCapture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import videoCapture.gui.CapturerController;

/**
 * The Class VideoCapturer.
 */
public class VideoCapturer {

	public static final int STATUS_INITIALIZING = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_RECORDING = 2;
	public static final int STATUS_ERROR = 3;
	private List<VideoSource> videoSources;
	private CapturerStorageServer storage;
	private int status;
	private String name;
	private VideoCapturerListener listener;
	private BroadcastScannerListener broadcastScannerListener;
	private CapturerController controller;
	private static final Logger logger = Logger.getLogger("log");

	/**
	 * Begin recording. Set status to recording and send the "record" message to
	 * all to the video sources that are available
	 * 
	 * @throws Exception
	 *             there was a problem
	 */
	public void beginRecording() throws Exception {
		this.setMyStatus(STATUS_RECORDING);

		for (VideoSource source : videoSources) {
			logger.info("Telling " + source.getMyName() + " to record to "
					+ storage);

			// ask storage server for ports
			Socket storageMsgSock = new Socket(storage.getMyHostname(),
					storage.getMyPort());
			BufferedReader br = new BufferedReader(new InputStreamReader(
					storageMsgSock.getInputStream()));
			PrintWriter pr = new PrintWriter(storageMsgSock.getOutputStream());
			pr.write(source.getMyName() + "\n");
			pr.flush();

			// wait for response
			// TODO: add timeout
			while (br.ready() == false) {
				Thread.sleep(100);
			}

			// process response
			String portStr = br.readLine();
			source.beginRecordingAndStreaming(storage.getMyHostname(),
					Integer.parseInt(portStr));
		}
	}

	/**
	 * Instantiates a new video capturer.
	 */
	public VideoCapturer() {

		videoSources = new ArrayList<VideoSource>();
		status = STATUS_INITIALIZING;
		try {
			listener = new VideoCapturerListener(this);
			listener.start();
		} catch (Exception e) {
			logger.severe("Really bad... Couldn't initialize capturer");
		}

	}

	/**
	 * Gets the my storage.
	 * 
	 * @return the my storage
	 */
	public CapturerStorageServer getMyStorage() {
		return storage;
	}

	/**
	 * Sets the my storage.
	 * 
	 * @param myStorage
	 *            the new my storage
	 */
	public void setMyStorage(CapturerStorageServer myStorage) {
		this.storage = myStorage;
	}

	/**
	 * Gets the my status.
	 * 
	 * @return the my status
	 */
	public int getMyStatus() {
		return status;
	}

	/**
	 * Sets the my status.
	 * 
	 * @param myStatus
	 *            the new my status
	 */
	public void setMyStatus(int myStatus) {
		this.status = myStatus;
		if (controller != null)
			controller.updateViewText();
	}

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
	 * Gets the num sources.
	 * 
	 * @return the num sources
	 */
	public int getNumSources() {
		return videoSources.size();
	}

	/**
	 * Gets the listen port.
	 * 
	 * @return the listen port
	 */
	public int getListenPort() {
		return listener.getSocketPort();
	}

	/**
	 * Load settings.
	 * 
	 * @param configSettings
	 *            the config settings
	 */
	public void loadSettings(CapturerConfigSettings configSettings) {
		this.setMyStatus(STATUS_INITIALIZING);
		// set name
		name = configSettings.getMyName();

		// restart listener on new port
		listener.stopListening();
		try {
			listener = new VideoCapturerListener(this,
					configSettings.getMyListenPort());
			listener.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Listening on " + listener.getSocketPort());

		// set broadcasting
		if (configSettings.isBroadcastPublic()) {
			if (broadcastScannerListener == null
					|| broadcastScannerListener.getRunningStatus() == false)
				initializeBroadcastListener();
		} else {
			if (broadcastScannerListener != null
					&& broadcastScannerListener.getRunningStatus() == true)
				broadcastScannerListener.stopListen();
		}

		// set up storage

		// TODO: Allow for multiple servers
		CapturerStorageServer serv = configSettings.getMyStorages().get(0);
		storage = serv;

		// set up video sources
		videoSources.clear();
		try {
			for (VideoCaptureDevice dev : configSettings.getMyDevices()) {
				if (dev.isEnabled()) {
					VideoSource s;
					s = new VideoSource(dev.getDeviceLocation(),
							dev.getDeviceLocation(), serv);
					videoSources.add(s);
				}
			}
			logger.info(" there a are " + videoSources.size() + " cams");
		} catch (Exception e) {
			logger.warning("Couldn't set up video sources");
		}

	}

	/**
	 * Initialize broadcast listener.
	 */
	public void initializeBroadcastListener() {
		broadcastScannerListener = new BroadcastScannerListener(
				BroadcastScannerListener.DEFAULT_LISTEN_PORT, ""
						+ listener.getSocketPort(),
				BroadcastScannerListener.DEFAULT_EXPECTED_MESSAGE,
				BroadcastScannerListener.DEFAULT_RESPONSE_PORT);
	}

	/**
	 * Gets the my controller.
	 * 
	 * @return the my controller
	 */
	public CapturerController getMyController() {
		return controller;
	}

	/**
	 * Sets the my controller.
	 * 
	 * @param myController
	 *            the new my controller
	 */
	public void setMyController(CapturerController myController) {
		this.controller = myController;
	}

	/**
	 * Display view.
	 */
	public void displayView() {
		controller.displayView();
	}

}
