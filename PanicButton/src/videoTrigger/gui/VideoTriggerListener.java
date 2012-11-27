package videoTrigger.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import videoTrigger.VideoTriggerClient;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving videoTrigger events. The class that is
 * interested in processing a videoTrigger event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addVideoTriggerListener<code> method. When
 * the videoTrigger event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see VideoTriggerEvent
 */
public class VideoTriggerListener implements ActionListener {

	VideoTriggerController controller;

	/**
	 * Instantiates a new video trigger actions.
	 * 
	 * @param client
	 *            the client
	 */
	public VideoTriggerListener(VideoTriggerController control) {
		controller = control;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Manual Add")) {
			controller.showAddHostWindow();
		}
		if (e.getActionCommand().equals("Scan Network")) {
			controller.scanNetwork();
		}
		if (e.getActionCommand().equals("Trigger")) {
			controller.trigger();
		}
	}

}
