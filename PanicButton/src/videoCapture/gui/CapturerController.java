package videoCapture.gui;

import java.util.ArrayList;

import javax.swing.ListModel;

import videoCapture.CapturerStorageServer;
import videoCapture.CapturerConfigSettings;
import videoCapture.VideoCaptureDevice;
import videoCapture.VideoCapturer;
import Common.CommonFunctions;

/**
 * The Controller for the Video Capturer GUI
 */
public class CapturerController {
	CapturerView view;
	VideoCapturer videoCapturer;
	ConfigView configView;
	CapturerConfigSettings configSettings;

	/**
	 * Instantiates a new capturer controller.
	 * 
	 * @param view
	 *            the view
	 * @param capturer
	 *            the capturer
	 */
	public CapturerController(CapturerView view, VideoCapturer capturer) {
		this.view = view;
		view.addActionListeners(this);
		videoCapturer = capturer;
	}

	/**
	 * Update view text.
	 */
	public void updateViewText() {
		String name = videoCapturer.getMyName();
		String status = capturerStatusToString(videoCapturer.getMyStatus());
		String numCameras = "" + videoCapturer.getNumSources();
		String storageServer = videoCapturer.getMyStorage() == null ? "None"
				: videoCapturer.getMyStorage().getMyHostname() + ":"
						+ videoCapturer.getMyStorage().getMyPort();
		String listenPort = "" + videoCapturer.getListenPort();

		String text = "<b>Name:</b> " + name + "<br />";
		text = text + "<b>Status:</b> " + status + "<br />";
		text = text + "<b>Cameras:</b> " + numCameras + "<br />";
		text = text + "<b>Listening on: </b> " + listenPort + "<br />";
		text = text + "<b>Storage Server:</b> " + storageServer;

		view.setTextContent(text);

	}

	/**
	 * Capturer status to string.
	 * 
	 * @param status
	 *            the status
	 * @return the string
	 */
	public String capturerStatusToString(int status) {
		switch (status) {
		case VideoCapturer.STATUS_INITIALIZING:
			return "Initializing";
		case VideoCapturer.STATUS_READY:
			return "Ready";
		case VideoCapturer.STATUS_RECORDING:
			return "Recording";
		case VideoCapturer.STATUS_ERROR:
			return "Error";
		}
		return "Unknown";
	}

	/**
	 * Show config window.
	 */
	public void showConfigWindow() {
		configView = new ConfigView();
		configView.AddListeners(this);
		// myConfigSettings = new ConfigSettings();
		populateCameras();
		updateBasicInfo();
		configView.getStoragePanel().updateList(
				configSettings.getMyStorages().toArray(
						new CapturerStorageServer[1]));
	}

	/**
	 * Adds the storage.
	 */
	public void addStorage() {
		if (configView.getStoragePanel().getSelectedStorage() == null) {
			configSettings.getMyStorages()
					.add(configView.getStoragePanel()
							.getStorageServerFromFields());
			configView.getStoragePanel().updateList(
					configSettings.getMyStorages().toArray(
							new CapturerStorageServer[1]));
		} else {
			configSettings.getMyStorages().add(
					new CapturerStorageServer("", 0));
			configView.getStoragePanel().updateList(
					configSettings.getMyStorages().toArray(
							new CapturerStorageServer[1]));
		}
	}

	/**
	 * Removes the server.
	 */
	public void removeServer() {
		if (configView.getStoragePanel().getSelectedStorage() != null) {
			CapturerStorageServer delServ = configView.getStoragePanel()
					.getSelectedStorage();
			configSettings.getMyStorages().remove(delServ);
			configView.getStoragePanel().deleteServer(delServ);
		}
	}

	/**
	 * Populate fields from selected storage.
	 */
	public void populateFieldsFromSelectedStorage() {
		configView.getStoragePanel().populateFieldsFromSelected();
	}

	/**
	 * Populate cameras.
	 */
	public void populateCameras() {
		configView.getCameraPanel().populateDeviceList(
				configSettings.getMyDevices().toArray(
						new VideoCaptureDevice[0]));
	}

	/**
	 * Preview selected camera.
	 */
	public void previewSelectedCamera() {
		VideoCaptureDevice dev = configView.getCameraPanel()
				.getSelectedCamera();
		System.out.println(dev);
		configView.getCameraPanel()
				.displayCamPreview(dev.getDeviceLocation());
		configView.getCameraPanel().setSelectedValue(dev.isEnabled());
	}

	/**
	 * Update checkbox status.
	 */
	public void updateCheckboxStatus() {
		boolean status = configView.getCameraPanel().getSelectedCamera()
				.isEnabled();
		configView.getCameraPanel().setSelectedValue(status);
	}

	/**
	 * Sets the selected camera checkd.
	 */
	public void setSelectedCameraCheckd() {
		boolean status = configView.getCameraPanel().getSelectedValue();
		configView.getCameraPanel().getSelectedCamera().setEnabled(status);
	}

	/**
	 * Test entered server.
	 */
	public void testEnteredServer() {
		CapturerStorageServer capServ;
		capServ = configView.getStoragePanel().getStorageServerFromFields();
		String status = capServ.testConnectToServer();
		configView.getStoragePanel().setConnectStatus(status);
		configView.getStoragePanel().setFreeSpace(
				CommonFunctions.formatByteSizeHuman(capServ.getMyFreeSpace()));
	}

	/**
	 * Update edited storage.
	 */
	public void updateEditedStorage() {
		CapturerStorageServer oldServ = configView.getStoragePanel()
				.getSelectedStorage();
		if (oldServ != null) {
			CapturerStorageServer newServ = configView.getStoragePanel()
					.getStorageServerFromFields();
			configSettings.updateStorage(oldServ, newServ);
			configView.getStoragePanel().updateList(
					configSettings.getMyStorages().toArray(
							new CapturerStorageServer[1]));
		}
	}

	/**
	 * Ok config window.
	 */
	public void okConfigWindow() {
		// Create new config setings object with properties set to values
		// that are entered in the window's fields
		configSettings.setMyName(configView.getBasicsPanel()
				.getCapturerName());
		configSettings.setMyListenPort(Integer.parseInt(configView
				.getBasicsPanel().getListenPort()));
		configSettings.setBroadcastPublic(configView.getBasicsPanel()
				.doesBroadcastPublic());
		ArrayList<VideoCaptureDevice> devs = new ArrayList<VideoCaptureDevice>();
		ListModel l = configView.getCameraPanel().getCameraListModel();
		for (int i = 0; i < l.getSize(); i++) {
			VideoCaptureDevice dev = (VideoCaptureDevice) l.getElementAt(i);
			devs.add(dev);
		}
		configSettings.setMyDevices(devs);

		videoCapturer.loadSettings(configSettings);
		try {
			configSettings.saveToDisk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateViewText();
		configView.close();
		configView.dispose();
	}

	/**
	 * Cancel config window.
	 */
	public void cancelConfigWindow() {
		configView.close();
		configView.dispose();
	}

	/**
	 * Update basic info.
	 */
	public void updateBasicInfo() {
		configView.loadSettingsFromConfig(configSettings);
	}

	/**
	 * Gets the my config settings.
	 * 
	 * @return the my config settings
	 */
	public CapturerConfigSettings getMyConfigSettings() {
		return configSettings;
	}

	/**
	 * Sets the my config settings.
	 * 
	 * @param myConfigSettings
	 *            the new my config settings
	 */
	public void setMyConfigSettings(CapturerConfigSettings myConfigSettings) {
		this.configSettings = myConfigSettings;
	}

	/**
	 * Display view.
	 */
	public void displayView() {
		if (view == null) {
			view = new CapturerView();
			view.addActionListeners(this);
		} else {
			view.setVisible(true);
		}
	}

}
