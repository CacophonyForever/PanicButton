package videoTrigger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ConfigSettings implements Serializable{

	public static int CHECK_STATUS_INTERVAL_IN_MILLIS=10000;
	public static final String CONFIG_SAVE_FILE_LOCATION=".TriggerSettings";
	private List<remoteVideoCapturer> myCaps;
	private int checkInterval;
	
	public static ConfigSettings loadConfigSettingsFromDisk() throws Exception
	{
        FileInputStream fileIn =
            new FileInputStream(CONFIG_SAVE_FILE_LOCATION);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ConfigSettings configSet = (ConfigSettings) in.readObject();
		in.close();
		fileIn.close();
		
		return configSet;

	}
	
	
	public void saveToDisk() throws Exception
	{
        FileOutputStream fileOut = new FileOutputStream(CONFIG_SAVE_FILE_LOCATION);
            ObjectOutputStream out =
                               new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
             fileOut.close();

	}
	
	public ConfigSettings()
	{
		checkInterval=CHECK_STATUS_INTERVAL_IN_MILLIS;
		myCaps = new ArrayList<remoteVideoCapturer>();
	}
	
	public void addCapturer(remoteVideoCapturer cap)
	{
		myCaps.add(cap);
	}
	
	public List<remoteVideoCapturer> getMyCaps() {
		return myCaps;
	}

	public void setMyCaps(List<remoteVideoCapturer> myCaps) {
		this.myCaps = myCaps;
	}
	
	
	
}
