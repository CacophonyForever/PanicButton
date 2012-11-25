package videoStorage.gui;

import videoStorage.VideoStorageHost;
import Common.CommonFunctions;

// TODO: Auto-generated Javadoc
/**
 * The controller for the storage status window
 */
public class StatusWindowController {

	private StatusWindowView view;
	private VideoStorageHost host;
	private ConfigWindowView configView;
	private ConfigWindowController configController;

	/**
	 * Instantiates a new status window controller.
	 * 
	 * @param view
	 *            the view
	 * @param host
	 *            the host
	 */
	public StatusWindowController(StatusWindowView view, VideoStorageHost host) {
		this.view = view;
		this.host = host;
	}

	/**
	 * Show status window.
	 */
	public void showStatusWindow() {
		if (view == null) {
			view = new StatusWindowView();
			view.addActionListeners(this);
			updateStatusWindowContent();
		}
	}

	/**
	 * Show config window.
	 */
	public void showConfigWindow() {
		configView = new ConfigWindowView();
		configController = new ConfigWindowController(configView, host);
		configView.populateFields(host.getMySettings());
		configView.addListeners(configController);
		configController.setSliderText();
		configController.setSliderValue();
	}

	/**
	 * Update status window content.
	 */
	public void updateStatusWindowContent() {
		String status = "Storage: " + host.getName() + "<br />";
		status += "Save Location: "
				+ host.getMyStorageLocation().getAbsolutePath() + "<br />";
		status += "Listening on: " + host.getListenPort() + "<br />";
		status += "Available Stream Ports: " + host.getNumStreamPorts()
				+ "<br />";
		status += "Disk Space Available: "
				+ CommonFunctions.formatByteSizeHuman(host.getMaxFileSpace())
				+ "<br />";

		if (view != null)
			view.setTextContent(status);
	}

}
