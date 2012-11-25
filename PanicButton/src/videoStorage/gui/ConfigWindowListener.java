package videoStorage.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving configWindow events. The class that is
 * interested in processing a configWindow event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addConfigWindowListener<code> method. When
 * the configWindow event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see ConfigWindowEvent
 */
public class ConfigWindowListener implements ActionListener, ChangeListener {
	private ConfigWindowController controller;

	/**
	 * Instantiates a new config window listener.
	 * 
	 * @param control
	 *            the control
	 */
	public ConfigWindowListener(ConfigWindowController control) {
		controller = control;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK"))
			// OK Button clicked by user
			controller.saveSettings();
		if (e.getActionCommand().equals("Cancel"))
			// Cancel Button clicked by user
			controller.closeSettings();

	}


	@Override
	public void stateChanged(ChangeEvent e) {
		// Slider value changed
		controller.setSliderText();
	}

}
