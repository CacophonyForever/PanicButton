package videoStorage;

import java.io.File;
import java.util.logging.Logger;

import videoStorage.gui.StatusWindowController;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoStorageHost.
 */
public class VideoStorageHost {
	
	private static final Logger logger = Logger.getLogger("log");

	private File storageLocation;
	private long maxFileSpace;
	private MessageListener messageListener;
	private String status;
	private StatusWindowController controller;
	private String name;
	private ConfigSettings mySettings;

	/**
	 * Instantiates a new video storage host.
	 * 
	 * @param saveDir
	 *            the save dir
	 */
	public VideoStorageHost(String saveDir) {
		storageLocation = new File("/home/paul/PanicB");
	}

	/**
	 * Inits the.
	 */
	public void init() {
		messageListener = new MessageListener(this);
		messageListener.run();
		logger.info("System Initialized!");
		controller.updateStatusWindowContent();
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the max file space.
	 * 
	 * @return the max file space
	 */
	public long getMaxFileSpace() {
		return maxFileSpace;
	}

	/**
	 * Sets the max file space.
	 * 
	 * @param maxFileSpace
	 *            the new max file space
	 */
	public void setMaxFileSpace(long maxFileSpace) {
		this.maxFileSpace = maxFileSpace;
	}

	/**
	 * Gets the free space.
	 * 
	 * @return the free space
	 */
	public long getFreeSpace() {
		return storageLocation.getFreeSpace();
	}

	/**
	 * Gets the disk space left.
	 * 
	 * @return the disk space left
	 */
	public long getDiskSpaceLeft() {
		// System.out.println(myStorageLocation.getFreeSpace() + " bytes free");
		long diskSpaceLeft = storageLocation.getFreeSpace();
		if (maxFileSpace > 0) {
			long allocatedSpaceLeft = maxFileSpace - getSpaceInSaveFolder();
			return Math.min(diskSpaceLeft, allocatedSpaceLeft);
		}
		return diskSpaceLeft;
	}

	/**
	 * Show staus window.
	 */
	public void showStausWindow() {
		controller.showStatusWindow();
	}

	/**
	 * Gets the space in save folder.
	 * 
	 * @return the space in save folder
	 */
	public long getSpaceInSaveFolder() {
		long totalSize = 0;
		File[] files = storageLocation.listFiles();

		for (File f : files) {
			totalSize += f.length();
		}

		return totalSize;

	}

	/**
	 * Gets the my storage location.
	 * 
	 * @return the my storage location
	 */
	public File getMyStorageLocation() {
		return storageLocation;
	}

	/**
	 * Sets the my storage location.
	 * 
	 * @param myStorageLocation
	 *            the new my storage location
	 */
	public void setMyStorageLocation(File myStorageLocation) {
		this.storageLocation = myStorageLocation;
	}

	/**
	 * Gets the my controller.
	 * 
	 * @return the my controller
	 */
	public StatusWindowController getMyController() {
		return controller;
	}

	/**
	 * Sets the my controller.
	 * 
	 * @param myController
	 *            the new my controller
	 */
	public void setMyController(StatusWindowController myController) {
		this.controller = myController;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the listen port.
	 * 
	 * @return the listen port
	 */
	public int getListenPort() {
		return messageListener.getMyListenPort();
	}

	/**
	 * Gets the num stream ports.
	 * 
	 * @return the num stream ports
	 */
	public int getNumStreamPorts() {
		return messageListener.getMyStreamPorts().length;
	}

	/**
	 * Gets the my settings.
	 * 
	 * @return the my settings
	 */
	public ConfigSettings getMySettings() {
		return mySettings;
	}

	/**
	 * Sets the my settings.
	 * 
	 * @param mySettings
	 *            the new my settings
	 */
	public void setMySettings(ConfigSettings mySettings) {
		this.mySettings = mySettings;
	}

	/**
	 * Load settings.
	 */
	public void loadSettings() {
		this.setName(mySettings.getMyName());
		this.setMyStorageLocation(mySettings.getMySaveLocation());
		this.setMaxFileSpace(mySettings.getMyMaxSize());

		// check if listener needs to be restarted on a different port
		if (messageListener == null
				|| messageListener.getMyListenPort() != mySettings
						.getListenPort()) {
			if (messageListener != null)
				messageListener.stopListening();
			messageListener = new MessageListener(this,
					mySettings.getListenPort(), mySettings
							.getRecieveVideoPorts().toArray(new Integer[0]));
		} else {
			messageListener.setMyStreamPorts(mySettings.getRecieveVideoPorts()
					.toArray(new Integer[0]));

		}

		controller.updateStatusWindowContent();
	}

}
