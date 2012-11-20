package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

public class VideoTriggerActions implements ActionListener{

	
	VideoTriggerClient myClient;
	
	public VideoTriggerActions(VideoTriggerClient client)
	{
		myClient=client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Manual Add"))
		{
			myClient.showAddHostWindow();
		}
		if (e.getActionCommand().equals("Scan Network"))
		{
			myClient.scanNetwork();
		}		
		if (e.getActionCommand().equals("Trigger"))
		{
			myClient.trigger();
		}	
	}
	

}
