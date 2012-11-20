package videoTrigger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import videoCapture.VideoCapturer;
import videoCapture.VideoCapturerListener;
import videoTrigger.gui.AddHostView;
import videoTrigger.gui.VideoTriggerActions;
import videoTrigger.gui.VideoTriggerView;

public class VideoTriggerClient {

	private List<remoteVideoCapturer> capturers;
	private VideoTriggerView myView;
	private AddHostView addWindow;
	private VideoTriggerActions myActions;
	private CapturerScanner myScanner;
	
	private ConfigSettings mySettings;
	
	
	public VideoTriggerClient()
	{
		try {
			mySettings = ConfigSettings.loadConfigSettingsFromDisk();
		} catch (Exception e) {
			System.out.println("No saved settings found.");
			mySettings = new ConfigSettings();
		}
		myView = new VideoTriggerView(this);
		
		for (remoteVideoCapturer r : mySettings.getMyCaps())
		{
			this.addNewHost(r.getCapturerHost(), r.getCapturerPort());
		}
	}
	
	public void showAddHostWindow()
	{
		addWindow = new AddHostView(this);
	}
	
	public void cancelAddHostWindow()
	{
		addWindow.dispose();
	}

	
	public void addNewHost(String hostname, int port)
	{
		//addWindow.dispose();
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

	public VideoTriggerView getMyView() {
		return myView;
	}

	public void setMyView(VideoTriggerView myView) {
		this.myView = myView;
	}

	public AddHostView getAddWindow() {
		return addWindow;
	}

	public void setAddWindow(AddHostView addWindow) {
		this.addWindow = addWindow;
	}
	
	public void scanNetwork()
	{
		myView.setScanActive();
		myScanner= new CapturerScanner(36001, this);
		myScanner.scanNetwork();
	}
	
	public void trigger()
	{
		for (Object rvc : myView.getSelected())
		{
	
			System.out.println("Yo, " + rvc.getClass());
			remoteVideoCapturer r = (remoteVideoCapturer)rvc;
			System.out.println("Hey there, " + r.getCapturerHost() + ":" + r.getCapturerPort());

			try {
				r.triggerRecording();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myView.updateList();
			
		}
	}
	
	public void readyScan()
	{
		myView.setScanReady();
	}
}
