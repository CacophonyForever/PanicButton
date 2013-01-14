package videoTrigger;

import java.util.List;
import java.util.logging.Logger;

import videoTrigger.gui.AddHostView;
import videoTrigger.gui.VideoTriggerController;
import videoTrigger.gui.VideoTriggerListener;
import videoTrigger.gui.VideoTriggerView;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerClient.
 */
public class VideoTriggerClient {
	private static final Logger logger = Logger.getLogger("log");

	private List<RemoteVideoCapturer> capturers;
	private VideoTriggerController controller;
	private AddHostView addWindow;
	private VideoTriggerListener actions;
	private CapturerScanner scanner;
	private TriggerConfigSettings settings;

	/**
	 * Instantiates a new video trigger client.
	 */

	public void loadSettings()
	{
		try {
			settings = TriggerConfigSettings.loadConfigSettingsFromDisk();
			this.populateListFromSettings();
		} catch (Exception e) {
			System.out.println("No saved settings found.");
			settings = new TriggerConfigSettings();
		}


	}

	/**
	 * Adds the new host.
	 * 
	 * @param hostname
	 *            the hostname
	 * @param port
	 *            the port
	 */
	public void addNewHost(String hostname, int port) {
		// addWindow.dispose();
		RemoteVideoCapturer newCap = new RemoteVideoCapturer();
		newCap.setCapturerHost(hostname);
		newCap.setCapturerPort(port);
		// ensure its not a dup

		if (controller.doesListContain(newCap))
			return;

		newCap.confirmExistence();
		settings.addCapturer(newCap);

		controller.addCapturerToList(newCap);
		try {
			settings.saveToDisk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void populateListFromSettings()
	{
		for (RemoteVideoCapturer newCap :settings.getMyCaps())
		{
			controller.addCapturerToList(newCap);
		}		
	}

	/**
	 * Scan network.
	 */
	public void scanNetwork() {
		controller.setScanButtonInUse();
		scanner = new CapturerScanner(36001, this);
		scanner.scanNetwork();
	}
	
	public void doneScanning()
	{
		controller.setScanButtonReady();
	}

	/**
	 * Trigger.
	 */
	public void trigger() {
		logger.info("Triggering");
		for (Object rvc : controller.getListSelected()) {
			RemoteVideoCapturer r = (RemoteVideoCapturer) rvc;
			logger.info("Giving " + r.getCapturerHost() + ":"
					+ r.getCapturerPort() + " the record command");
			try {
				r.triggerRecording();
			} catch (Exception e) {
				logger.severe("Could not trigger");
			}
			controller.updateList();
		}
	}

	public VideoTriggerController getController() {
		return controller;
	}

	public void setController(VideoTriggerController controller) {
		this.controller = controller;
	}
	
	
	
	

}
