package videoStorage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

import Common.CommonFunctions;

public class MessageListener 
{
	public static final int DEFAULT_LISTEN_PORT=3605;
	private static final int CAPTURER_HI_TIMEOUT_IN_MILLIS = 5000;
	private int myListenPort;
	private int[] myStreamPorts;
	private boolean doListen;
	private VideoStorageHost myHost;
	
	public MessageListener(VideoStorageHost host)
	{
		myListenPort=DEFAULT_LISTEN_PORT;
		myStreamPorts = new int[]{3602,3603,3604};
		myHost=host;
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MessageListener(int port)
	{
		myListenPort=port;
		myStreamPorts = new int[]{3602,3603,3604};
	}
	
	public void init() throws Exception
	{
		doListen=true;
		ServerSocket sock = new ServerSocket(myListenPort);
		while (doListen)
		{		
			System.out.println("Listening");
			try {
				Socket s = sock.accept();
				System.out.println("TE EACCEPTION");
				handleCaptureListener(s.getInputStream(), s.getOutputStream());
				
			} catch (Exception e)
			{
				e.printStackTrace();
				Thread.sleep(250);
			}
		}
	}
	
	private void handleCaptureListener(InputStream is, OutputStream os) throws InterruptedException, IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		PrintWriter pr = new PrintWriter(os);
		long timeoutTime = System.currentTimeMillis() + CAPTURER_HI_TIMEOUT_IN_MILLIS;
		while ( System.currentTimeMillis() < timeoutTime)
		{
			Thread.sleep(100);			
			System.out.print(";");
			if (br.ready())
			{
				System.out.println("NOPER");
				String camName = br.readLine();
				System.out.println("Dude says '" + camName+"'");
				if (camName.equals("Status?"))
				{
					System.out.println("Got asked for status. Telling it.");
					handleGetStatus(br, pr);
					return;
				}
				System.out.println("Got " + camName);
				int port = myStreamPorts[0];
				String fileName = "~/vid" + System.currentTimeMillis() + ".ogg";
				CaptureListener cLis = new CaptureListener(port,fileName);
				System.out.println("Initializing listener on port " + port);
				cLis.start();
				while (!cLis.isReady())
				{
					System.out.print(".");
					Thread.sleep(100);
				}
				System.out.println("Initialized!");
				pr.write(myStreamPorts[0] + "\n");
				System.out.println ("Telling " + camName + " to stream to my port " + myStreamPorts[0]);
				pr.flush();
			}
		}
	}
	
	private void handleGetStatus(BufferedReader br, PrintWriter pr)
	{
		pr.println("OK");
		pr.println((myHost.getDiskSpaceLeft()));
		pr.flush();
	}
	
	
}
