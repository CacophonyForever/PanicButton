package videoStorage.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving statusWindow events. The class that is
 * interested in processing a statusWindow event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addStatusWindowListener<code> method. When
 * the statusWindow event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see StatusWindowEvent
 */
public class StatusWindowListener implements ActionListener {

	/** The my controller. */
	StatusWindowController controller;

	/**
	 * Instantiates a new status window listener.
	 * 
	 * @param control
	 *            the control
	 */
	public StatusWindowListener(StatusWindowController control) {
		controller = control;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.showConfigWindow();
	}

}
