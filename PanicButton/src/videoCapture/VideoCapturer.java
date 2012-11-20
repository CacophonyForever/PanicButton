package videoCapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import videoCapture.gui.CapturerController;

public class VideoCapturer {

	public static final int STATUS_INITIALIZING=0;
	public static final int STATUS_READY=1;
	public static final int STATUS_RECORDING=2;
	public static final int STATUS_ERROR=3;
	
	private List<VideoSource> myVideoSources;
	private CapturerStorageServer myStorage;
	private int myStatus;
	private String myName;
	private VideoCapturerListener myListener;
	private BroadcastScannerListener myBroadcastScannerListener;
	private CapturerController myController;


	public void beginRecording() throws Exception
	{
		this.setMyStatus(STATUS_RECORDING);
		for (VideoSource source : myVideoSources)
		{
			System.out.println("Telling " + source.getMyName() + " to record to " + myStorage);
			// ask storage server for ports
			Socket storageMsgSock = new Socket(myStorage.getMyHostname(), myStorage.getMyPort());
			BufferedReader br = new BufferedReader(new InputStreamReader(storageMsgSock.getInputStream()));
			PrintWriter pr = new PrintWriter(storageMsgSock.getOutputStream());
			pr.write(source.getMyName() + "\n");
			pr.flush();
			while (br.ready() == false)
			{
				System.out.print(".");
				Thread.sleep(100);
			}
			String portStr = br.readLine();
			System.out.println("port : " + portStr);
			source.beginRecordingAndStreaming(myStorage.getMyHostname(), Integer.parseInt(portStr) );
		}
	}
	
	public VideoCapturer()
	{

		myVideoSources = new ArrayList<VideoSource>();
		//VideoSource vs = new VideoSource("Test Source", myStorage);
		//myVideoSources.add(vs);
		myStatus=STATUS_INITIALIZING;
		try {
			myListener = new VideoCapturerListener(this);
			myListener.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public CapturerStorageServer getMyStorage() {
		return myStorage;
	}

	public void setMyStorage(CapturerStorageServer myStorage) {
		this.myStorage = myStorage;
	}

	public int getMyStatus() {
		return myStatus;
	}

	public void setMyStatus(int myStatus) {
		this.myStatus = myStatus;
		if (myController != null)
		myController.updateViewText();
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}
	
	
	public int getNumSources()
	{
		return myVideoSources.size();
	}
	
	public int getListenPort()
	{
		return myListener.getSocketPort();
	}
	
	public void loadSettings(ConfigSettings configSettings) 
	{
		this.setMyStatus(STATUS_INITIALIZING);
		System.out.println("loadin DAT SHIZ");
		// set name
		myName = configSettings.getMyName();
		System.out.println("Setting myName = " + myName);
		
		// restart listener on new port
		myListener.stopListening();
		try {
			myListener = new VideoCapturerListener(this,configSettings.getMyListenPort());
			myListener.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("LISTEN ON " + myListener.getSocketPort());
		
		// set broadcasting
		if (configSettings.isBroadcastPublic())
		{
			if (myBroadcastScannerListener == null || myBroadcastScannerListener.getRunningStatus() == false)
				initializeBroadcastListener();
		}
		else
		{
			if (myBroadcastScannerListener != null && myBroadcastScannerListener.getRunningStatus() == true)
				myBroadcastScannerListener.stopListen();
		}
		
		// set up storage
		// TODO: Allow for multiple servers
		System.out.println(configSettings.getMyStorages().size());
		CapturerStorageServer serv = configSettings.getMyStorages().get(0);
		myStorage=serv;
		
		// set up video sources
		myVideoSources.clear();
		try {
			for (VideoCaptureDevice dev : configSettings.getMyDevices())
			{
				System.out.println(dev + " " +dev.isEnabled());
				if (dev.isEnabled())
				{
					System.out.println("TET URE");
					VideoSource s;
					s = new VideoSource(dev.getDeviceLocation(), dev.getDeviceLocation(), serv);
					myVideoSources.add(s);
				}
			}
			System.out.println(" there a are " +myVideoSources.size() + " cams");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		

		
		
		
	}
	
	public void initializeBroadcastListener()
	{
		myBroadcastScannerListener = new BroadcastScannerListener(
				BroadcastScannerListener.DEFAULT_BROADCAST_PORT, ""+myListener.getSocketPort(), 
				BroadcastScannerListener.DEFAULT_EXPECTED_MESSAGE, 3666, 
				BroadcastScannerListener.DEFAULT_RESPONSE_MESSAGE);
	}

	public CapturerController getMyController() {
		return myController;
	}

	public void setMyController(CapturerController myController) {
		this.myController = myController;
	}
	
	public void displayView()
	{
		myController.displayView();
	}

	
	
	

}
