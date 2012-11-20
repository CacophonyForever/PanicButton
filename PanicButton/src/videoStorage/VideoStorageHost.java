package videoStorage;

import java.io.File;

public class VideoStorageHost {

	private File myStorageLocation;
	private long maxFileSpace;
	private MessageListener myMessageListener;
	private String status;
	
	public VideoStorageHost(String saveDir)
	{
		myStorageLocation = new File("/home/paul/PanicB");
		System.out.println(saveDir);
		myMessageListener = new MessageListener(this);
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
	
	
	
	
	
	
	
	
}
