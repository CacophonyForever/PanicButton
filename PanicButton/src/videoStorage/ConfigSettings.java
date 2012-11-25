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
	private String myName;
	private File mySaveLocation;
	private long myMaxSize;
	private String myFileSaveFormat;
	private int listenPort;
	private List<Integer> recieveVideoPorts;
	
	public ConfigSettings()
	{
		recieveVideoPorts = new ArrayList<Integer>();		
	}
	
	public void setDefaultSettings()
	{
		myName="Default Storage";
		mySaveLocation = new File(System.getProperty("user.dir"));
		listenPort=3605;
		recieveVideoPorts.add(3601);
		recieveVideoPorts.add(3602);
		recieveVideoPorts.add(3603);
		recieveVideoPorts.add(3604);
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


	public String getMyName() {
		return myName;
	}


	public void setMyName(String myName) {
		this.myName = myName;
	}


	public int getListenPort() {
		return listenPort;
	}


	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}


	public List<Integer> getRecieveVideoPorts() {
		return recieveVideoPorts;
	}


	public void setRecieveVideoPorts(List<Integer> recieveVideoPorts) {
		this.recieveVideoPorts = recieveVideoPorts;
	}
	
	

	
	
}
