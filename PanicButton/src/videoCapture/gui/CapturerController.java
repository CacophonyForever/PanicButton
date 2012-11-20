package videoCapture.gui;

import java.awt.List;
import java.util.ArrayList;

import javax.swing.ListModel;

import Common.CommonFunctions;

import videoCapture.CapturerStorageServer;
import videoCapture.ConfigSettings;
import videoCapture.VideoCaptureDevice;
import videoCapture.VideoCapturer;

public class CapturerController {
	
	CapturerView myView;
	VideoCapturer myVideoCapturer;
	ConfigView myConfigView;
	ConfigSettings myConfigSettings;
	
	public CapturerController(CapturerView view, VideoCapturer capturer)
	{
		myView = view;
		myView.addActionListeners(this);
		myVideoCapturer = capturer;
	}
	

	public void updateViewText()
	{
		String name = myVideoCapturer.getMyName();
		String status = capturerStatusToString(myVideoCapturer.getMyStatus());
		String numCameras = ""+myVideoCapturer.getNumSources();
		String storageServer =  myVideoCapturer.getMyStorage() == null ?
				"None" : myVideoCapturer.getMyStorage().getMyHostname() + ":" + myVideoCapturer.getMyStorage().getMyPort();
		String listenPort = "" + myVideoCapturer.getListenPort();
		
		String text = "<b>Name:</b> " + name + "<br />";
		text = text + "<b>Status:</b> " + status + "<br />";
		text = text + "<b>Cameras:</b> " + numCameras + "<br />";
		text = text + "<b>Listening on: </b> " + listenPort +"<br />";
		text = text + "<b>Storage Server:</b> " + storageServer;
		
		myView.setTextContent(text);
	
	}
	
	public String capturerStatusToString(int status)
	{
		switch (status)
		{
			case VideoCapturer.STATUS_INITIALIZING: return "Initializing";
			case VideoCapturer.STATUS_READY: return "Ready";
			case VideoCapturer.STATUS_RECORDING: return "Recording";
			case VideoCapturer.STATUS_ERROR: return "Error";
		}
		return "Unknown";	
	}
	
	public void showConfigWindow()
	{
		myConfigView = new ConfigView();
		myConfigView.AddListeners(this);
		//myConfigSettings = new ConfigSettings();
		populateCameras();
		updateBasicInfo();
		myConfigView.getStoragePanel().updateList(myConfigSettings.getMyStorages().toArray(new CapturerStorageServer[1]));
	}
	
	public void addStorage()
	{
		if (myConfigView.getStoragePanel().getSelectedStorage() == null)
		{
			myConfigSettings.getMyStorages().add(myConfigView.getStoragePanel().getStorageServerFromFields());
			myConfigView.getStoragePanel().updateList(myConfigSettings.getMyStorages().toArray(new CapturerStorageServer[1]));
		}
		else
		{
		myConfigSettings.getMyStorages().add(new CapturerStorageServer("",0));
		myConfigView.getStoragePanel().updateList(myConfigSettings.getMyStorages().toArray(new CapturerStorageServer[1]));
		}
	}
	
	public void removeServer()
	{
		if (myConfigView.getStoragePanel().getSelectedStorage() != null)
		{
			CapturerStorageServer delServ = myConfigView.getStoragePanel().getSelectedStorage();
			myConfigSettings.getMyStorages().remove(delServ);
			myConfigView.getStoragePanel().deleteServer(delServ);
		}
	}
	
	public void populateFieldsFromSelectedStorage()
	{
		myConfigView.getStoragePanel().populateFieldsFromSelected();
	}
	
	public void populateCameras()
	{
		myConfigView.getCameraPanel().populateDeviceList(myConfigSettings.getMyDevices().toArray(new VideoCaptureDevice[0]));
	}
	
	public void previewSelectedCamera()
	{
		VideoCaptureDevice dev = myConfigView.getCameraPanel().getSelectedCamera();
		System.out.println(dev);
		myConfigView.getCameraPanel().displayCamPreview(dev.getDeviceLocation());
		myConfigView.getCameraPanel().setSelectedValue(dev.isEnabled());
	}
	
	public void updateCheckboxStatus()
	{
		boolean status = myConfigView.getCameraPanel().getSelectedCamera().isEnabled();
		myConfigView.getCameraPanel().setSelectedValue(status);
	}
	
	public void setSelectedCameraCheckd()
	{
		boolean status = myConfigView.getCameraPanel().getSelectedValue();
		myConfigView.getCameraPanel().getSelectedCamera().setEnabled(status);
	}
	
	public void testEnteredServer()
	{
		CapturerStorageServer capServ;
		capServ = myConfigView.getStoragePanel().getStorageServerFromFields();
		String status = capServ.testConnectToServer();
		myConfigView.getStoragePanel().setConnectStatus(status);
		myConfigView.getStoragePanel().setFreeSpace(CommonFunctions.formatByteSizeHuman(capServ.getMyFreeSpace()));
	}
	
	public void updateEditedStorage()
	{		
		CapturerStorageServer oldServ =  myConfigView.getStoragePanel().getSelectedStorage();
		if (oldServ != null)
		{
		CapturerStorageServer newServ =  myConfigView.getStoragePanel().getStorageServerFromFields();
		myConfigSettings.updateStorage(oldServ, newServ);
		myConfigView.getStoragePanel().updateList(myConfigSettings.getMyStorages().toArray(new CapturerStorageServer[1]));
		}
	}
	
	public void okConfigWindow()
	{
		myConfigSettings.setMyName(myConfigView.getBasicsPanel().getCapturerName());
		myConfigSettings.setMyListenPort(Integer.parseInt(myConfigView.getBasicsPanel().getListenPort()));
		myConfigSettings.setBroadcastPublic(myConfigView.getBasicsPanel().doesBroadcastPublic());
		ArrayList<VideoCaptureDevice> devs = new ArrayList<VideoCaptureDevice>();
		ListModel l = myConfigView.getCameraPanel().getCameraListModel();
		 for(int i = 0; i < l.getSize(); i++) {
		     VideoCaptureDevice dev = (VideoCaptureDevice)l.getElementAt(i);
		     System.out.println (dev.getDeviceLocation() + " = " + dev.isEnabled());
		     devs.add(dev);
		 }
		 myConfigSettings.setMyDevices(devs);

		
		myVideoCapturer.loadSettings(myConfigSettings);
		try {
			myConfigSettings.saveToDisk();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateViewText();
		myConfigView.close();
		myConfigView.dispose();
	}
	
	public void cancelConfigWindow()
	{
		myConfigView.close();
		myConfigView.dispose();
	}
	
	public void updateBasicInfo()
	{
		myConfigView.loadSettingsFromConfig(myConfigSettings);
	}


	public ConfigSettings getMyConfigSettings() {
		return myConfigSettings;
	}


	public void setMyConfigSettings(ConfigSettings myConfigSettings) {
		this.myConfigSettings = myConfigSettings;
	}
	
	public void displayView()
	{
		if (myView == null)
		{
			myView = new CapturerView();
			myView.addActionListeners(this);
		}
		else
		{
			myView.setVisible(true);
		}
	}

	
	
}

