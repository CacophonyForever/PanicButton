package videoTrigger;

import java.util.List;

import videoTrigger.gui.AddHostView;
import videoTrigger.gui.VideoTriggerActions;
import videoTrigger.gui.VideoTriggerView;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerClient.
 */
public class VideoTriggerClient {

    /** The capturers. */
    private List<remoteVideoCapturer> capturers;

    /** The my view. */
    private VideoTriggerView myView;

    /** The add window. */
    private AddHostView addWindow;

    /** The my actions. */
    private VideoTriggerActions myActions;

    /** The my scanner. */
    private CapturerScanner myScanner;

    /** The my settings. */
    private ConfigSettings mySettings;

    /**
     * Instantiates a new video trigger client.
     */
    public VideoTriggerClient() {
	try {
	    mySettings = ConfigSettings.loadConfigSettingsFromDisk();
	} catch (Exception e) {
	    System.out.println("No saved settings found.");
	    mySettings = new ConfigSettings();
	}
	myView = new VideoTriggerView(this);

	for (remoteVideoCapturer r : mySettings.getMyCaps()) {
	    this.addNewHost(r.getCapturerHost(), r.getCapturerPort());
	}
    }

    /**
     * Show add host window.
     */
    public void showAddHostWindow() {
	addWindow = new AddHostView(this);
    }

    /**
     * Cancel add host window.
     */
    public void cancelAddHostWindow() {
	addWindow.dispose();
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
	remoteVideoCapturer newCap = new remoteVideoCapturer();
	newCap.setCapturerHost(hostname);
	newCap.setCapturerPort(port);
	// ensure its not a dup
	if (myView.getCapturerListModel().contains(newCap))
	    return;

	newCap.confirmExistence();
	mySettings.addCapturer(newCap);
	try {
	    mySettings.saveToDisk();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	myView.addItemToList(newCap);
    }

    /**
     * Gets the my view.
     * 
     * @return the my view
     */
    public VideoTriggerView getMyView() {
	return myView;
    }

    /**
     * Sets the my view.
     * 
     * @param myView
     *            the new my view
     */
    public void setMyView(VideoTriggerView myView) {
	this.myView = myView;
    }

    /**
     * Gets the adds the window.
     * 
     * @return the adds the window
     */
    public AddHostView getAddWindow() {
	return addWindow;
    }

    /**
     * Sets the adds the window.
     * 
     * @param addWindow
     *            the new adds the window
     */
    public void setAddWindow(AddHostView addWindow) {
	this.addWindow = addWindow;
    }

    /**
     * Scan network.
     */
    public void scanNetwork() {
	myView.setScanActive();
	myScanner = new CapturerScanner(36001, this);
	myScanner.scanNetwork();
    }

    /**
     * Trigger.
     */
    public void trigger() {
	for (Object rvc : myView.getSelected()) {

	    System.out.println("Yo, " + rvc.getClass());
	    remoteVideoCapturer r = (remoteVideoCapturer) rvc;
	    System.out.println("Hey there, " + r.getCapturerHost() + ":"
		    + r.getCapturerPort());

	    try {
		r.triggerRecording();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    myView.updateList();

	}
    }

    /**
     * Ready scan.
     */
    public void readyScan() {
	myView.setScanReady();
    }
}
