package videoCapture.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import videoCapture.CapturerStorageServer;
import Common.Constants;

/**
 * The View portion of the Cameras Settings tab of the configuration window for
 * the Video Capturer, for selecting which cameras are actively set to record
 */

public class StoragePanel extends JPanel {
	private CapturerController controller;
	private DefaultListModel storageListModel;
	private JList storageList;

	private JButton addButton;
	private JButton deleteButton;

	private JLabel nameLabel;
	private JTextField nameField;

	private JLabel hostnameLabel;
	private JTextField hostnameField;

	private JLabel portLabel;
	private JTextField portField;

	private JLabel freeSpaceLabel;
	private JLabel freeSpaceValueLabel;

	private JLabel fileLocationLabel;
	private JLabel fileLocationValueLabel;

	private JButton testConnectionButton;
	private JLabel testConnectionLabel;

	private StorageSettingsActionListener actionListener;

	/**
	 * Instantiates a new storage panel.
	 */
	public StoragePanel() {
		this.setLayout(new GridLayout(1, 2));
		JPanel listAndListButtonsPanel = new JPanel(new BorderLayout());
		JPanel listButtonsPanel = new JPanel(new GridLayout(1, 2));
		JPanel fieldsPanel = new JPanel(new GridLayout(6, 2));

		storageListModel = new DefaultListModel();
		storageList = new JList(storageListModel);

		storageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");

		listButtonsPanel.add(addButton);
		listButtonsPanel.add(deleteButton);

		listAndListButtonsPanel.add(listButtonsPanel, BorderLayout.SOUTH);
		listAndListButtonsPanel.add(storageList, BorderLayout.CENTER);

		nameLabel = new JLabel("Name: ");
		hostnameLabel = new JLabel("Hostname: ");
		portLabel = new JLabel("Port: ");
		freeSpaceLabel = new JLabel("Free Space: ");
		fileLocationLabel = new JLabel("Storage Location: ");

		nameField = new JTextField("");
		hostnameField = new JTextField("");
		portField = new JTextField("");
		freeSpaceValueLabel = new JLabel("N/A");
		fileLocationValueLabel = new JLabel("N/A");

		testConnectionButton = new JButton("Test Server");
		testConnectionLabel = new JLabel("");

		fieldsPanel.add(nameLabel);
		fieldsPanel.add(nameField);

		fieldsPanel.add(hostnameLabel);
		fieldsPanel.add(hostnameField);

		fieldsPanel.add(portLabel);
		fieldsPanel.add(portField);

		fieldsPanel.add(freeSpaceLabel);
		fieldsPanel.add(freeSpaceValueLabel);

		fieldsPanel.add(fileLocationLabel);
		fieldsPanel.add(fileLocationValueLabel);

		fieldsPanel.add(testConnectionButton);
		fieldsPanel.add(testConnectionLabel);

		JPanel rightPanel = new JPanel();

		rightPanel.add(fieldsPanel);

		fieldsPanel.setPreferredSize(new Dimension(300,
				Constants.VIEW_FIELD_HEIGHT * 6));

		this.add(listAndListButtonsPanel);
		this.add(rightPanel);
	}

	/**
	 * Gets the selected storage.
	 * 
	 * @return the selected storage
	 */
	public CapturerStorageServer getSelectedStorage() {
		return (CapturerStorageServer) storageList.getSelectedValue();
	}

	/**
	 * Populate fields from the selected server
	 * 
	 * @param server
	 *            the server
	 */
	public void populateFieldsFromSrv(CapturerStorageServer server) {
		nameField.setText(server.getMyName());
		hostnameField.setText(server.getMyHostname());
		portField.setText("" + server.getMyPort());
		freeSpaceValueLabel.setText(convertSizeToHumanReadable(server
				.getMyFreeSpace()));
		fileLocationValueLabel.setText(server.getMySaveLocation());
	}

	/**
	 * Convert size to human readable.
	 * 
	 * @param sizeInBytes
	 *            the size in bytes
	 * @return the string
	 */
	public String convertSizeToHumanReadable(long sizeInBytes) {
		return "" + sizeInBytes;
	}

	/**
	 * Update list.
	 * 
	 * @param myServers
	 *            the my servers
	 */
	public void updateList(CapturerStorageServer[] myServers) {

		for (CapturerStorageServer srv : myServers) {
			if (storageListModel.contains(srv) == false)
				storageListModel.add(0, srv);
		}

		storageList.repaint();
	}

	/**
	 * Delete server.
	 * 
	 * @param server
	 *            the server
	 */
	public void deleteServer(CapturerStorageServer server) {
		storageListModel.removeElement(server);
	}

	/**
	 * Populate fields from selected.
	 */
	public void populateFieldsFromSelected() {
		if (getSelectedStorage() != null) {
			populateFieldsFromSrv(getSelectedStorage());
		}
	}

	/**
	 * Adds the listeners.
	 * 
	 * @param cont
	 *            the cont
	 */
	public void addListeners(CapturerController cont) {
		actionListener = new StorageSettingsActionListener(cont);
		addButton.addActionListener(actionListener);
		deleteButton.addActionListener(actionListener);
		storageList.addListSelectionListener(actionListener);
		testConnectionButton.addActionListener(actionListener);

		portField.addKeyListener(actionListener);
		hostnameField.addKeyListener(actionListener);
		nameField.addKeyListener(actionListener);
	}

	/**
	 * Gets the storage server from fields.
	 * 
	 * @return the storage server from fields
	 */
	public CapturerStorageServer getStorageServerFromFields() {
		CapturerStorageServer capserv;
		try {
			capserv = new CapturerStorageServer(hostnameField.getText(),
					Integer.parseInt(portField.getText()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			capserv = new CapturerStorageServer(hostnameField.getText(), 0);
		}
		capserv.setMyName(nameField.getText());

		return capserv;
	}

	/**
	 * Sets the connect status.
	 * 
	 * @param status
	 *            the new connect status
	 */
	public void setConnectStatus(String status) {
		testConnectionLabel.setText(status);
	}

	/**
	 * Sets the free space.
	 * 
	 * @param space
	 *            the new free space
	 */
	public void setFreeSpace(String space) {
		freeSpaceValueLabel.setText(space);
	}

}
