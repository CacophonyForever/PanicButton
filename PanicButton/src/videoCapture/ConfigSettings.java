/*
 * 
 */
package videoCapture;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigSettings.
 */
public class ConfigSettings implements Serializable {

	private List<CapturerStorageServer> storages;
	private List<VideoCaptureDevice> devices;
	private String name;
	private int listenPort;
	private boolean broadcastPublic;
	private static final Logger logger = Logger.getLogger("log");

	/**
	 * Load config settings from disk.
	 * 
	 * @param location
	 *            the location of the saved config file
	 * @return the parsed config settings
	 * @throws Exception
	 *             if settings could not be loaded
	 */
	public static ConfigSettings loadConfigSettingsFromDisk(String location)
			throws Exception {
		FileInputStream fileIn = new FileInputStream(location);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ConfigSettings configSet = (ConfigSettings) in.readObject();
		in.close();
		fileIn.close();

		return configSet;
	}

	/**
	 * Instantiates a new config settings.
	 */
	public ConfigSettings() {
		storages = new ArrayList<CapturerStorageServer>();
		devices = new ArrayList<VideoCaptureDevice>();
		try {
			devices = (popCaptureDevices());
		} catch (Exception e1) {
			logger.severe("Could not find any devices");
		}
	}

	/**
	 * Gets the my storages.
	 * 
	 * @return the my storages
	 */
	public List<CapturerStorageServer> getMyStorages() {
		return storages;
	}

	/**
	 * Sets the my storages.
	 * 
	 * @param myStorages
	 *            the new my storages
	 */
	public void setMyStorages(List<CapturerStorageServer> myStorages) {
		this.storages = myStorages;
	}

	/**
	 * Gets the my devices.
	 * 
	 * @return the my devices
	 */
	public List<VideoCaptureDevice> getMyDevices() {
		return devices;
	}

	/**
	 * Sets the my devices.
	 * 
	 * @param myDevices
	 *            the new my devices
	 */
	public void setMyDevices(List<VideoCaptureDevice> myDevices) {
		this.devices = myDevices;
	}

	/**
	 * Update storage.
	 * 
	 * @param oldStor
	 *            the old stor
	 * @param newStor
	 *            the new stor
	 */
	public void updateStorage(CapturerStorageServer oldStor,
			CapturerStorageServer newStor) {
		oldStor.setMyName(newStor.getMyName());
		oldStor.setMyHostname(newStor.getMyHostname());
		oldStor.setMyPort(newStor.getMyPort());
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
	 * Gets the my listen port.
	 * 
	 * @return the my listen port
	 */
	public int getMyListenPort() {
		return listenPort;
	}

	/**
	 * Sets the my listen port.
	 * 
	 * @param myListenPort
	 *            the new my listen port
	 */
	public void setMyListenPort(int myListenPort) {
		this.listenPort = myListenPort;
	}

	/**
	 * Checks if is broadcast public.
	 * 
	 * @return true, if is broadcast public
	 */
	public boolean isBroadcastPublic() {
		return broadcastPublic;
	}

	/**
	 * Sets the broadcast public.
	 * 
	 * @param broadcastPublic
	 *            the new broadcast public
	 */
	public void setBroadcastPublic(boolean broadcastPublic) {
		this.broadcastPublic = broadcastPublic;
	}

	/**
	 * Pop capture devices.
	 * 
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<VideoCaptureDevice> popCaptureDevices() throws Exception {
		// yo this is a cheap fucking hack and only works for *nix
		Process proc = null;
		int inBuffer, errBuffer;
		int result = 0;
		StringBuffer outputReport = new StringBuffer();
		StringBuffer errorBuffer = new StringBuffer();

		try {
			proc = Runtime.getRuntime().exec(
					new String[] { "/bin/sh", "-c", "ls /dev/video*" });
		} catch (IOException e) {
			return null;
		}
		try {
			result = proc.waitFor();
		} catch (InterruptedException e) {
			return null;
		}
		if (proc != null && null != proc.getInputStream()) {
			InputStream is = proc.getInputStream();
			InputStream es = proc.getErrorStream();
			OutputStream os = proc.getOutputStream();

			try {
				while ((inBuffer = is.read()) != -1) {
					outputReport.append((char) inBuffer);
				}

				while ((errBuffer = es.read()) != -1) {
					errorBuffer.append((char) errBuffer);
				}

			} catch (IOException e) {
				return null;
			}
			try {
				is.close();
				is = null;
				es.close();
				es = null;
				os.close();
				os = null;
			} catch (IOException e) {
				return null;
			}

			proc.destroy();
			proc = null;
		}

		if (errorBuffer.length() > 0) {
			return null;
		}

		System.out.println(outputReport.toString());
		String[] devNames = outputReport.toString().split("\n");

		List<VideoCaptureDevice> devs = new ArrayList<VideoCaptureDevice>();
		int i = 0;

		for (String devName : devNames) {
			devs.add(new VideoCaptureDevice(devName));
			i++;
		}

		return devs;
	}

	/**
	 * Save to disk.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void saveToDisk() throws Exception {
		FileOutputStream fileOut = new FileOutputStream("employee.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();

	}
}
