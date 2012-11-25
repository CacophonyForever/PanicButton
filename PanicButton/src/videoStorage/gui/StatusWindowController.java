package videoStorage.gui;

import Common.CommonFunctions;
import videoStorage.VideoStorageHost;

public class StatusWindowController {

	private StatusWindowView myView;
	private VideoStorageHost myHost;
	private ConfigWindowView myConfigView;
	private ConfigWindowController myConfigController;
	
	public StatusWindowController(StatusWindowView view, VideoStorageHost host)
	{
		myView = view;
		myHost = host;
	}
	
	public void showStatusWindow()
	{
		if (myView == null)
		{
			myView = new StatusWindowView();
			myView.addActionListeners(this);
			updateStatusWindowContent();
		}
	}
	
	public void showConfigWindow()
	{
		myConfigView = new ConfigWindowView();
		myConfigController = new ConfigWindowController(myConfigView, myHost);
		myConfigView.populateFields(myHost.getMySettings());		
		myConfigView.addListeners(myConfigController);
		myConfigController.setSliderText();
		myConfigController.setSliderValue();
	}
	
	public void updateStatusWindowContent()
	{
		String status = "Storage: " + myHost.getName() + "<br />";
		status += "Save Location: " + myHost.getMyStorageLocation().getAbsolutePath() + "<br />";
		status += "Listening on: " + myHost.getListenPort()+ "<br />";
		status += "Available Stream Ports: " + myHost.getNumStreamPorts()+ "<br />";
		status += "Disk Space Available: " + CommonFunctions.formatByteSizeHuman(myHost.getMaxFileSpace())+ "<br />";
		
		if (myView != null)myView.setTextContent(status);
	}
	
	
}
