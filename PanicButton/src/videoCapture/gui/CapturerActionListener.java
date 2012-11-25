/*
 * 
 */
package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving capturerAction events. The class that is
 * interested in processing a capturerAction event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addCapturerActionListener<code> method. When
 * the capturerAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see CapturerActionEvent
 */
public class CapturerActionListener implements ActionListener {

	/** The my controller. */
	private CapturerController controller;

	/**
	 * Instantiates a new capturer action listener.
	 * 
	 * @param control
	 *            the control
	 */
	public CapturerActionListener(CapturerController control) {
		controller = control;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Configure")) {
			// Configuration button is clicked on main status window
			showSetup();
		}
	}

	/**
	 * Show setup.
	 */
	private void showSetup() {
		controller.showConfigWindow();
	}

}
