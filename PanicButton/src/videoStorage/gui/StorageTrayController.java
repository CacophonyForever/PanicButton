package videoStorage.gui;

import videoStorage.VideoStorageHost;

public class StorageTrayController {
	private VideoStorageHost myHost;

	
	public StorageTrayController(VideoStorageHost host)
	{
		myHost=host;
	}
	
	public void showStausWindow()
	{
		myHost.showStausWindow();
	}
	
}
