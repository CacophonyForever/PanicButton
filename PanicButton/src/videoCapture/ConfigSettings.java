package videoCapture;

import java.awt.Container;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigSettings implements Serializable{
	private List<CapturerStorageServer> myStorages;
	private List<VideoCaptureDevice> myDevices;
	private String myName;
	private int myListenPort;
	private boolean broadcastPublic;
	
	public static ConfigSettings loadConfigSettingsFromDisk(String location) throws Exception
	{
        FileInputStream fileIn =
            new FileInputStream("employee.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ConfigSettings configSet = (ConfigSettings) in.readObject();
		in.close();
		fileIn.close();
		
		return configSet;

	}
	
	public ConfigSettings()
	{
		myStorages = new ArrayList<CapturerStorageServer>();
		myDevices = new ArrayList<VideoCaptureDevice>();
		try {
			myDevices = (popCaptureDevices());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public List<CapturerStorageServer> getMyStorages() {
		return myStorages;
	}

	public void setMyStorages(List<CapturerStorageServer> myStorages) {
		this.myStorages = myStorages;
	}	
	
	
	public List<VideoCaptureDevice> getMyDevices() {
		return myDevices;
	}

	public void setMyDevices(List<VideoCaptureDevice> myDevices) {
		this.myDevices = myDevices;
	}

	public void updateStorage(CapturerStorageServer oldStor, CapturerStorageServer newStor)
	{
		oldStor.setMyName(newStor.getMyName());
		oldStor.setMyHostname(newStor.getMyHostname());
		oldStor.setMyPort(newStor.getMyPort());	
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyListenPort() {
		return myListenPort;
	}

	public void setMyListenPort(int myListenPort) {
		this.myListenPort = myListenPort;
	}

	public boolean isBroadcastPublic() {
		return broadcastPublic;
	}

	public void setBroadcastPublic(boolean broadcastPublic) {
		this.broadcastPublic = broadcastPublic;
	}
	
	private List<VideoCaptureDevice> popCaptureDevices() throws Exception
	{
		// yo this is a cheap fucking hack and only works for *nix
		Process proc =null;
        int inBuffer, errBuffer;
        int result = 0;
        StringBuffer outputReport = new StringBuffer();
        StringBuffer errorBuffer = new StringBuffer();

        try {
    		proc = Runtime.getRuntime().exec(new String[]{"/bin/sh",
    				"-c",
    				"ls /dev/video*"});
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


       System.out.println( outputReport.toString());
		String[] devNames= outputReport.toString().split("\n");
		
		List<VideoCaptureDevice> devs = new ArrayList<VideoCaptureDevice>();
		int i=0;
		
		for (String devName : devNames)
		{
			devs.add(new VideoCaptureDevice(devName));
			i++;
		}
		
		return devs;
	}
	
	public void saveToDisk() throws Exception
	{
        FileOutputStream fileOut =
            new FileOutputStream("employee.ser");
            ObjectOutputStream out =
                               new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
             fileOut.close();

	}
}
