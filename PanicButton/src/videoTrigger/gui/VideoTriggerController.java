package videoTrigger.gui;

import videoTrigger.RemoteVideoCapturer;
import videoTrigger.VideoTriggerClient;

public class VideoTriggerController {
	
	private VideoTriggerClient client;
	private VideoTriggerView view;
	private AddHostView addHostView;
	
	public VideoTriggerController(VideoTriggerClient client, VideoTriggerView view)
	{
		this.view=view;
		this.client=client;
		client.setController(this);
	}
	
	public boolean doesListContain(RemoteVideoCapturer cap)
	{
		return view.getCapturerListModel().contains(cap);
	}
	
	public void addCapturerToList(RemoteVideoCapturer cap)
	{
		view.addItemToList(cap);
	}
	
	public void setScanButtonInUse()
	{
		view.setScanActive();
	}
	
	public void setScanButtonReady()
	{
		view.setScanReady();
	}
	
	public Object[] getListSelected()
	{
		return view.getSelected();
	}
	
	public void updateList()
	{
		view.updateList();
	}
	
	public void addHost() {
		// myClient.addNewHost();
		String hostname = addHostView.getEnteredHostname();
		int port = addHostView.getEnteredPort();
		client.addNewHost(hostname, port);
		addHostView.dispose();
	}
	
	public void closeAddHostWindow() {
		addHostView.dispose();
	}
	
	public void showAddHostWindow() {
		addHostView = new AddHostView(this);
	}
	
	public void scanNetwork()
	{
		client.scanNetwork();
	}
	
	public void trigger()
	{
		client.trigger();
	}

}
