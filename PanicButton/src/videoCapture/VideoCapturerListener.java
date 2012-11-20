package videoCapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class VideoCapturerListener extends Thread
{
	private static final int TIMEOUT_IN_MILLIS=5000;
	public static final int DEFAULT_PORT=3601;
	private boolean doListen=false;
	ServerSocket mySock;
	
	public VideoCapturer myVideoCapturer;
	
	public VideoCapturerListener(VideoCapturer vidcap) throws Exception
	{
		doListen=true;
		myVideoCapturer=vidcap;
		mySock = new ServerSocket(DEFAULT_PORT);
	}
	
	public VideoCapturerListener(VideoCapturer vidcap, int port) throws Exception
	{
		doListen=true;
		myVideoCapturer=vidcap;
		mySock = new ServerSocket(port);
	}
	
	public void run()
	{
		listen();
	}
	
	public void listen()
	{
		myVideoCapturer.setMyStatus(VideoCapturer.STATUS_READY);
		while (doListen==true)
		{
			System.out.println("LSTEING on port " + mySock.getLocalPort());
			try
			{
				System.out.println("lsnt");
				Socket s = mySock.accept();
				System.out.println("AAA");
				handleConnection(s.getInputStream(), s.getOutputStream());
			}
			catch (Exception e)
			{				
				System.out.println("AAAAAAA");
				e.printStackTrace();
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("QQ" + doListen);
		}
		try {
			mySock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleConnection(InputStream is, OutputStream os) throws Exception, InterruptedException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintWriter pw  = new PrintWriter(os);
		long timeoutTime = System.currentTimeMillis() + TIMEOUT_IN_MILLIS;
		while (!br.ready() && System.currentTimeMillis() < timeoutTime)
		{
			Thread.sleep(100);
		}
		assert br.ready();
		String command = br.readLine();
		System.out.println("HANLDE " + command);
		if (command.equals("Hello"))
		{
			pw.write("Hello\n");
			pw.write(myVideoCapturer.getMyName() + "testcam\n");
			pw.write("Ready\n");
			pw.flush();			
		}
		else if (command.equals("Trigger"))
		{
			myVideoCapturer.beginRecording();
			pw.write("Recording\n");
			pw.flush();
		}
		else
		{
			throw new Exception("Unknown Command");
		}
		
	}
	
	public int getSocketPort()
	{
		return mySock.getLocalPort();
	}
	
	public void stopListening()
	{
		try {
			mySock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doListen=false;
	}

}

