package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving cameraSettingsAction events. The class
 * that is interested in processing a cameraSettingsAction event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addCameraSettingsActionListener<code> method. When
 * the cameraSettingsAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see CameraSettingsActionEvent
 */
public class CameraSettingsActionListener implements ActionListener,
		ListSelectionListener {

	private static final Logger logger = Logger.getLogger("log");
	CapturerController controller;

	/**
	 * Instantiates a new camera settings action listener.
	 * 
	 * @param controller
	 *            the controller
	 */
	public CameraSettingsActionListener(CapturerController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// When the "Use This Camera" checkbox value is changed
		if (e.getActionCommand().equals("Use This Camera")) {
			selectedCameraUpdate();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		logger.info("Camera selected");
		// When a different camera is selected from the list		
		if (e.getValueIsAdjusting() == false) {
			controller.updateCheckboxStatus();
			controller.previewSelectedCamera();
		}
	}

	/**
	 * Selected camera update.
	 */
	private void selectedCameraUpdate() {
		controller.setSelectedCameraCheckd();
	}

}
