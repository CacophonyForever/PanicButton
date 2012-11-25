package videoCapture.gui.trayIcon;

import videoCapture.VideoCapturer;

// TODO: Auto-generated Javadoc
/**
 * Controller for the video capturer tray icon.
 */
public class TrayController {

	/** The my capturer. */
	private VideoCapturer capturer;

	/**
	 * Instantiates a new tray controller.
	 * 
	 * @param cap
	 *            the cap
	 */
	public TrayController(VideoCapturer cap) {
		capturer = cap;
	}

	/**
	 * Opens the capturer's status window.
	 */
	public void showCapturerView() {
		capturer.displayView();
	}

}
