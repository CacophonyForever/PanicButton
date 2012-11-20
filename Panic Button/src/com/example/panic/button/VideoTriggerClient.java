package com.example.panic.button;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class VideoTriggerClient {

	private List<remoteVideoCapturer> capturers;

	private CapturerScanner myScanner;
	private PanicButton myPanicButton;
	
	public VideoTriggerClient(PanicButton pb)
	{
		myPanicButton=pb;
		capturers= new ArrayList<remoteVideoCapturer>();
	}
	
	public void addNewHost(String hostname, int port)
	{
		//addWindow.dispose();
		remoteVideoCapturer newCap = new remoteVideoCapturer(hostname,port);
		newCap.confirmExistence();
		capturers.add(newCap);
		myPanicButton.updateList();
	}

	
	
	public void scanNetwork()
	{
		myScanner= new CapturerScanner(36001, this);
		myScanner.scanNetwork();
	}
	
	public void trigger()
	{
		for (remoteVideoCapturer cap : capturers)
		{
			try {
				cap.triggerRecording();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void readyScan()
	{

	}



	public List<remoteVideoCapturer> getCapturers() {
		return capturers;
	}



	public void setCapturers(List<remoteVideoCapturer> capturers) {
		this.capturers = capturers;
	}
	
	
}
