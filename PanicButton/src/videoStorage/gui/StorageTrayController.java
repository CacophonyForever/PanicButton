package videoStorage.gui;

import videoStorage.VideoStorageHost;

// TODO: Auto-generated Javadoc
/**
 * The controller for the video storage host system tray icon
 */
public class StorageTrayController {

	/** The my host. */
	private VideoStorageHost host;

	/**
	 * Instantiates a new storage tray controller.
	 * 
	 * @param host
	 *            the host
	 */
	public StorageTrayController(VideoStorageHost host) {
		this.host = host;
	}

	/**
	 * Show status window.
	 */
	public void showStatusWindow() {
		host.showStausWindow();
	}

}
