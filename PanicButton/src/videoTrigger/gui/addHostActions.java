package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

public class addHostActions implements ActionListener
{

	VideoTriggerClient myClient;
	
	public addHostActions(VideoTriggerClient client)
	{
		myClient=client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK"))
		{
			addHost();
		}
		if (e.getActionCommand().equals("Cancel"))
		{
			cancel();
		}
	}
	
	private void addHost()
	{
		//myClient.addNewHost();
		String hostname=myClient.getAddWindow().getEnteredHostname();
		int port = myClient.getAddWindow().getEnteredPort();		
		myClient.addNewHost(hostname, port);
	}
	
	private void cancel()
	{
		myClient.cancelAddHostWindow();
	}
	

}
