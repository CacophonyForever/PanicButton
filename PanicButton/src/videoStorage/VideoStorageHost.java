package videoStorage;

import java.io.File;

import videoStorage.gui.StatusWindowController;

public class VideoStorageHost {

	private File myStorageLocation;
	private long maxFileSpace;
	private MessageListener myMessageListener;
	private String status;
	private StatusWindowController myController;
	private String name;
	private ConfigSettings mySettings;
	
	public VideoStorageHost(String saveDir)
	{
		myStorageLocation = new File("/home/paul/PanicB");
		System.out.println(saveDir);
	}
	
	public void init()
	{
		myMessageListener = new MessageListener(this);
		myMessageListener.run();
		System.out.println("System Initialized!");
		myController.updateStatusWindowContent();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getMaxFileSpace() {
		return maxFileSpace;
	}

	public void setMaxFileSpace(long maxFileSpace) {
		this.maxFileSpace = maxFileSpace;
	}
	
	public long getFreeSpace()	
	{
		return myStorageLocation.getFreeSpace();
	}
	
	public long getDiskSpaceLeft()
	{
		//System.out.println(myStorageLocation.getFreeSpace() + " bytes free");
		long diskSpaceLeft = myStorageLocation.getFreeSpace();
		if (maxFileSpace>0)
		{
		long allocatedSpaceLeft = maxFileSpace-getSpaceInSaveFolder();
		return Math.min(diskSpaceLeft,allocatedSpaceLeft);
		}
		return diskSpaceLeft;
	}
	
	public void showStausWindow()
	{
		myController.showStatusWindow();
	}
	
	public long getSpaceInSaveFolder()
	{
		long totalSize=0;
		File[] files = myStorageLocation.listFiles();

		for (File f : files)
		{
			totalSize += f.length();
		}

		return totalSize;
				
	}

	public File getMyStorageLocation() {
		return myStorageLocation;
	}

	public void setMyStorageLocation(File myStorageLocation) {
		this.myStorageLocation = myStorageLocation;
	}

	public StatusWindowController getMyController() {
		return myController;
	}

	public void setMyController(StatusWindowController myController) {
		this.myController = myController;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getListenPort()
	{
		return myMessageListener.getMyListenPort();			
	}
	
	public int getNumStreamPorts()
	{
		return myMessageListener.getMyStreamPorts().length;
	}

	public ConfigSettings getMySettings() {
		return mySettings;
	}

	public void setMySettings(ConfigSettings mySettings) {
		this.mySettings = mySettings;
	}
	
	public void loadSettings()
	{
		this.setName(mySettings.getMyName());
		this.setMyStorageLocation(mySettings.getMySaveLocation());
		this.setMaxFileSpace(mySettings.getMyMaxSize());
		
		// check if listener needs to be restarted on a different port
		if (myMessageListener == null || 
				myMessageListener.getMyListenPort()!= mySettings.getListenPort())
		{
			if (myMessageListener != null)
				myMessageListener.stopListening();
			myMessageListener = new MessageListener(this, mySettings.getListenPort(), mySettings.getRecieveVideoPorts().toArray(new Integer[0]));			
		}
		else
		{
			myMessageListener.setMyStreamPorts(mySettings.getRecieveVideoPorts().toArray(new Integer[0]));
			
		}

		myController.updateStatusWindowContent();
	}
	
	
	
}
