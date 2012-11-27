/*
 * 
 */
package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving addHost events. The class that is
 * interested in processing a addHost event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addAddHostListener<code> method. When
 * the addHost event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see AddHostEvent
 */
public class AddHostListener implements ActionListener {

	VideoTriggerController controller;

	/**
	 * Instantiates a new adds the host actions.
	 * 
	 * @param client
	 *            the client
	 */
	public AddHostListener(VideoTriggerController controller) {
		this.controller = controller;
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
		controller.addHost();
	}

	/**
	 * Cancel.
	 */
	private void cancel() {
		controller.closeAddHostWindow();
	}

}
