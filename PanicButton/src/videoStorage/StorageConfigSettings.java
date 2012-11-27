package videoStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigSettings.
 */
public class StorageConfigSettings implements Serializable {

	/** The Constant Default config save file location */
	public static final String saveSettingsLocation = ".StorageSettings";

	private String name;
	private File saveLocation;
	private long allocatedFileSpace;
	private String fileSaveFormat;
	private int listenPort;

	private List<Integer> receiveVideoPorts;

	/**
	 * Instantiates a new config settings.
	 */
	public StorageConfigSettings() {
		receiveVideoPorts = new ArrayList<Integer>();
	}

	/**
	 * Sets the default settings.
	 */
	public void setDefaultSettings() {
		name = "Default Storage";
		saveLocation = new File(System.getProperty("user.dir"));
		listenPort = 3605;
		receiveVideoPorts.add(3601);
		receiveVideoPorts.add(3602);
		receiveVideoPorts.add(3603);
		receiveVideoPorts.add(3604);
	}

	/**
	 * Save to disk.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void saveToDisk() throws Exception {
		FileOutputStream fileOut = new FileOutputStream(saveSettingsLocation);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	}

	/**
	 * Load config settings from disk.
	 * 
	 * @param location
	 *            the location
	 * @return the config settings
	 * @throws Exception
	 *             the exception
	 */
	public static StorageConfigSettings loadConfigSettingsFromDisk(String location)
			throws Exception {
		FileInputStream fileIn = new FileInputStream(saveSettingsLocation);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		StorageConfigSettings configSet = (StorageConfigSettings) in.readObject();
		in.close();
		fileIn.close();

		return configSet;
	}

	/**
	 * Gets the my save location.
	 * 
	 * @return the my save location
	 */
	public File getMySaveLocation() {
		return saveLocation;
	}

	/**
	 * Sets the my save location.
	 * 
	 * @param mySaveLocation
	 *            the new my save location
	 */
	public void setMySaveLocation(File mySaveLocation) {
		this.saveLocation = mySaveLocation;
	}

	/**
	 * Gets the my max size.
	 * 
	 * @return the my max size
	 */
	public long getMyMaxSize() {
		return allocatedFileSpace;
	}

	/**
	 * Sets the my max size.
	 * 
	 * @param myMaxSize
	 *            the new my max size
	 */
	public void setMyMaxSize(long myMaxSize) {
		this.allocatedFileSpace = myMaxSize;
	}

	/**
	 * Gets the my file save format.
	 * 
	 * @return the my file save format
	 */
	public String getMyFileSaveFormat() {
		return fileSaveFormat;
	}

	/**
	 * Sets the my file save format.
	 * 
	 * @param myFileSaveFormat
	 *            the new my file save format
	 */
	public void setMyFileSaveFormat(String myFileSaveFormat) {
		this.fileSaveFormat = myFileSaveFormat;
	}

	/**
	 * Gets the my name.
	 * 
	 * @return the my name
	 */
	public String getMyName() {
		return name;
	}

	/**
	 * Sets the my name.
	 * 
	 * @param myName
	 *            the new my name
	 */
	public void setMyName(String myName) {
		this.name = myName;
	}

	/**
	 * Gets the listen port.
	 * 
	 * @return the listen port
	 */
	public int getListenPort() {
		return listenPort;
	}

	/**
	 * Sets the listen port.
	 * 
	 * @param listenPort
	 *            the new listen port
	 */
	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	/**
	 * Gets the recieve video ports.
	 * 
	 * @return the recieve video ports
	 */
	public List<Integer> getRecieveVideoPorts() {
		return receiveVideoPorts;
	}

	/**
	 * Sets the recieve video ports.
	 * 
	 * @param recieveVideoPorts
	 *            the new recieve video ports
	 */
	public void setRecieveVideoPorts(List<Integer> recieveVideoPorts) {
		this.receiveVideoPorts = recieveVideoPorts;
	}

}
