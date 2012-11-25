package videoCapture.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving storageSettingsAction events. The class
 * that is interested in processing a storageSettingsAction event implements
 * this interface, and the object created with that class is registered with a
 * component using the component's
 * <code>addStorageSettingsActionListener<code> method. When
 * the storageSettingsAction event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see StorageSettingsActionEvent
 */
public class StorageSettingsActionListener implements ActionListener,
		ListSelectionListener, KeyListener {

	/** The my controller. */
	CapturerController controller;

	/**
	 * Instantiates a new storage settings action listener.
	 * 
	 * @param controller
	 *            the controller
	 */
	public StorageSettingsActionListener(CapturerController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Add")) {
			// Add button clicked
			addNewStorageServer();
		}

		if (e.getActionCommand().equals("Delete")) {
			// Delete button clicked
			deleteStorageServer();
		}

		if (e.getActionCommand().equals("Test Server")) {
			// "Test Server" button clicked
			testServer();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			// User selects Storage server from list
			controller.populateFieldsFromSelectedStorage();
		}
	}

	/**
	 * Test server.
	 */
	public void testServer() {
		controller.testEnteredServer();
	}

	/**
	 * Adds the new storage server.
	 */
	public void addNewStorageServer() {
		controller.addStorage();
	}

	/**
	 * Delete storage server.
	 */
	public void deleteStorageServer() {
		controller.removeServer();
	}

	/**
	 * Update edited storage.
	 */
	public void updateEditedStorage() {
		controller.updateEditedStorage();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// User has edited text in one of the fields
		controller.updateEditedStorage();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
