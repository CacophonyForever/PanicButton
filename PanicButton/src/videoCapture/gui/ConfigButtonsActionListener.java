/*
 * 
 */
package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving configButtonsAction events. The class
 * that is interested in processing a configButtonsAction event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addConfigButtonsActionListener<code> method. When
 * the configButtonsAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ConfigButtonsActionEvent
 */
public class ConfigButtonsActionListener implements ActionListener {

	/** The my controller. */
	private CapturerController controller;

	/**
	 * Instantiates a new config buttons action listener.
	 * 
	 * @param cont
	 *            the Capturer Controller
	 */
	public ConfigButtonsActionListener(CapturerController controller) {
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
		if (e.getActionCommand().equals("Cancel")) {
			// Cancel button clicked
			cancelButtonAction();
		}

		if (e.getActionCommand().equals("OK")) {
			// OK Button Clicked
			OkButtonAction();
		}
	}

	/**
	 * Cancel button action.
	 */
	public void cancelButtonAction() {
		controller.cancelConfigWindow();
	}

	/**
	 * Ok button action.
	 */
	public void OkButtonAction() {
		controller.okConfigWindow();
	}

}
