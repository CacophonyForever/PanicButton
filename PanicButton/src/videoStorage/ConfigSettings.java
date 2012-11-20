package videoStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigSettings implements Serializable{

	public static final String saveSettingsLocation=".StorageSettings";	
	private File mySaveLocation;
	private long myMaxSize;
	private String myFileSaveFormat;
	private int listenPort;
	private List<Integer> recieveVideoPorts;
	
	public ConfigSettings()
	{
		recieveVideoPorts = new ArrayList<Integer>();
	}
	
	
	public void saveToDisk() throws Exception
	{
        FileOutputStream fileOut = new FileOutputStream(saveSettingsLocation);
            ObjectOutputStream out =
                               new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
             fileOut.close();

	}
	
	public static ConfigSettings loadConfigSettingsFromDisk(String location) throws Exception
	{
        FileInputStream fileIn =
            new FileInputStream(saveSettingsLocation);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ConfigSettings configSet = (ConfigSettings) in.readObject();
		in.close();
		fileIn.close();
		
		return configSet;
	}
	

	public File getMySaveLocation() {
		return mySaveLocation;
	}
	public void setMySaveLocation(File mySaveLocation) {
		this.mySaveLocation = mySaveLocation;
	}
	public long getMyMaxSize() {
		return myMaxSize;
	}
	public void setMyMaxSize(long myMaxSize) {
		this.myMaxSize = myMaxSize;
	}
	public String getMyFileSaveFormat() {
		return myFileSaveFormat;
	}
	public void setMyFileSaveFormat(String myFileSaveFormat) {
		this.myFileSaveFormat = myFileSaveFormat;
	}

	
	
}
