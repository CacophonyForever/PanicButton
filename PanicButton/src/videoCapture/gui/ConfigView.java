/*
 * 
 */
package videoCapture.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import videoCapture.ConfigSettings;

// TODO: Auto-generated Javadoc
/**
 * The view of the capturer configuration tabbed window.
 */
public class ConfigView extends JFrame {

	private JTabbedPane tabbedPane;
	private JButton okButton;
	private JButton cancelButton;
	private CamerasPanel cameraPanel;
	private StoragePanel storagePanel;
	private BasicsPanel basicsPanel;

	/** The config buttons action listener. */
	private ConfigButtonsActionListener configButtonsActionListener;

	/**
	 * Instantiates a new config view.
	 */
	public ConfigView() {
		JPanel borderPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		buttonPanel.add(okButton, 0, 0);
		buttonPanel.add(cancelButton, 0, 1);

		borderPanel.add(buttonPanel, BorderLayout.SOUTH);

		tabbedPane = new JTabbedPane();
		basicsPanel = new BasicsPanel();
		tabbedPane.addTab("Basics", null, basicsPanel,
				"Configure basic settings");

		cameraPanel = new CamerasPanel();
		tabbedPane.addTab("Cameras", null, cameraPanel, "Setup Cameras");

		storagePanel = new StoragePanel();
		tabbedPane.addTab("Storage", null, storagePanel,
				"Configure remote storage settings");

		borderPanel.add(tabbedPane, BorderLayout.CENTER);
		this.getContentPane().add(borderPanel);
		this.setSize(640, 480);
		this.setVisible(true);

	}

	/**
	 * Adds the listeners to config window components
	 * 
	 * @param controller
	 *            the controller
	 */
	public void AddListeners(CapturerController controller) {
		configButtonsActionListener = new ConfigButtonsActionListener(
				controller);
		okButton.addActionListener(configButtonsActionListener);
		cancelButton.addActionListener(configButtonsActionListener);
		cameraPanel.addListeners(controller);
		storagePanel.addListeners(controller);
	}

	/**
	 * Gets the camera panel.
	 * 
	 * @return the camera panel
	 */
	public CamerasPanel getCameraPanel() {
		return cameraPanel;
	}

	/**
	 * Sets the camera panel.
	 * 
	 * @param cameraPanel
	 *            the new camera panel
	 */
	public void setCameraPanel(CamerasPanel cameraPanel) {
		this.cameraPanel = cameraPanel;
	}

	/**
	 * Gets the storage panel.
	 * 
	 * @return the storage panel
	 */
	public StoragePanel getStoragePanel() {
		return storagePanel;
	}

	/**
	 * Sets the storage panel.
	 * 
	 * @param storagePanel
	 *            the new storage panel
	 */
	public void setStoragePanel(StoragePanel storagePanel) {
		this.storagePanel = storagePanel;
	}

	/**
	 * Close.
	 */
	public void close() {
		this.cameraPanel.dispose();
	}

	/**
	 * Gets the basics panel.
	 * 
	 * @return the basics panel
	 */
	public BasicsPanel getBasicsPanel() {
		return basicsPanel;
	}

	/**
	 * Sets the basics panel.
	 * 
	 * @param basicsPanel
	 *            the new basics panel
	 */
	public void setBasicsPanel(BasicsPanel basicsPanel) {
		this.basicsPanel = basicsPanel;
	}

	/**
	 * Load settings from configuration object
	 * 
	 * @param set
	 *            the set
	 */
	public void loadSettingsFromConfig(ConfigSettings set) {
		basicsPanel.setCapturerName(set.getMyName());
		basicsPanel.setListenPort(set.getMyListenPort());
		basicsPanel.setDoesBroadcast(set.isBroadcastPublic());
	}

}
