package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerActions.
 */
public class VideoTriggerActions implements ActionListener {

    /** The my client. */
    VideoTriggerClient myClient;

    /**
     * Instantiates a new video trigger actions.
     * 
     * @param client
     *            the client
     */
    public VideoTriggerActions(VideoTriggerClient client) {
	myClient = client;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand().equals("Manual Add")) {
	    myClient.showAddHostWindow();
	}
	if (e.getActionCommand().equals("Scan Network")) {
	    myClient.scanNetwork();
	}
	if (e.getActionCommand().equals("Trigger")) {
	    myClient.trigger();
	}
    }

}
