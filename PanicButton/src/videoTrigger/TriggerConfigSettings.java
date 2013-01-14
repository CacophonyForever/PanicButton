package videoTrigger;

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
public class TriggerConfigSettings implements Serializable {

	/** The check status interval in millis. */
	public static int CHECK_STATUS_INTERVAL_IN_MILLIS = 10000;

	/** The Constant CONFIG_SAVE_FILE_LOCATION. */
	public static final String CONFIG_SAVE_FILE_LOCATION = ".TriggerSettings";

	/** The my caps. */
	private List<RemoteVideoCapturer> myCaps;

	/** The check interval. */
	private int checkInterval;

	/**
	 * Load config settings from disk.
	 * 
	 * @return the config settings
	 * @throws Exception
	 *             the exception
	 */
	public static TriggerConfigSettings loadConfigSettingsFromDisk()
			throws Exception {
		FileInputStream fileIn = new FileInputStream(CONFIG_SAVE_FILE_LOCATION);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		TriggerConfigSettings configSet = (TriggerConfigSettings) in
				.readObject();
		in.close();
		fileIn.close();

		return configSet;

	}

	/**
	 * Save to disk.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void saveToDisk() throws Exception {
		FileOutputStream fileOut = new FileOutputStream(
				CONFIG_SAVE_FILE_LOCATION);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();

	}

	/**
	 * Instantiates a new config settings.
	 */
	public TriggerConfigSettings() {
		checkInterval = CHECK_STATUS_INTERVAL_IN_MILLIS;
		myCaps = new ArrayList<RemoteVideoCapturer>();
	}

	/**
	 * Adds the capturer.
	 * 
	 * @param cap
	 *            the cap
	 */
	public void addCapturer(RemoteVideoCapturer cap) {
		myCaps.add(cap);
	}

	/**
	 * Gets the my caps.
	 * 
	 * @return the my caps
	 */
	public List<RemoteVideoCapturer> getMyCaps() {
		return myCaps;
	}

	/**
	 * Sets the my caps.
	 * 
	 * @param myCaps
	 *            the new my caps
	 */
	public void setMyCaps(List<RemoteVideoCapturer> myCaps) {
		this.myCaps = myCaps;
	}

}
