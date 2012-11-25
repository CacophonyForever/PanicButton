/*
 * 
 */
package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

// TODO: Auto-generated Javadoc
/**
 * The Class addHostActions.
 */
public class addHostActions implements ActionListener {

    /** The my client. */
    VideoTriggerClient myClient;

    /**
     * Instantiates a new adds the host actions.
     * 
     * @param client
     *            the client
     */
    public addHostActions(VideoTriggerClient client) {
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
	if (e.getActionCommand().equals("OK")) {
	    addHost();
	}
	if (e.getActionCommand().equals("Cancel")) {
	    cancel();
	}
    }

    /**
     * Adds the host.
     */
    private void addHost() {
	// myClient.addNewHost();
	String hostname = myClient.getAddWindow().getEnteredHostname();
	int port = myClient.getAddWindow().getEnteredPort();
	myClient.addNewHost(hostname, port);
    }

    /**
     * Cancel.
     */
    private void cancel() {
	myClient.cancelAddHostWindow();
    }

}
