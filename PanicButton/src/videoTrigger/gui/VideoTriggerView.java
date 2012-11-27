/*
 * 
 */
package videoTrigger.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import videoTrigger.VideoTriggerClient;
import videoTrigger.RemoteVideoCapturer;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerView.
 */
public class VideoTriggerView extends JFrame {
	private JPanel borderPanel;
	private JList capturerList;
	private JPanel buttonPanel;
	private JButton addManualButton;
	private JButton scanNetworkButton;
	private JButton triggerCaptureButton;
	ActionListener actListen;
	private DefaultListModel capturerListModel;

	/**
	 * Instantiates a new video trigger view.
	 * 
	 * @param client
	 *            the client
	 */
	public VideoTriggerView() {
		borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		capturerListModel = new DefaultListModel();
		capturerList = new JList(capturerListModel);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 3));
		addManualButton = new JButton("Manual Add");
		scanNetworkButton = new JButton("Scan Network");
		triggerCaptureButton = new JButton("Trigger");
		borderPanel.add(capturerList, BorderLayout.CENTER);
		borderPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(addManualButton, 0, 0);
		buttonPanel.add(scanNetworkButton, 0, 1);
		buttonPanel.add(triggerCaptureButton, 0, 2);
		this.getContentPane().add(borderPanel);
		this.setSize(600, 400);
		this.setLocationByPlatform(true);
		this.setVisible(true);

	}
	
	public void addActionListeners (VideoTriggerController control)
	{
		ActionListener actListen = new VideoTriggerListener(control);
		
		
		addManualButton.addActionListener(actListen);
		scanNetworkButton.addActionListener(actListen);
		triggerCaptureButton.addActionListener(actListen);
	}

	/**
	 * Adds the item to list.
	 * 
	 * @param cap
	 *            the cap
	 */
	public void addItemToList(RemoteVideoCapturer cap) {
		capturerListModel.addElement(cap);
		capturerList.repaint();
	}

	/**
	 * Gets the selected.
	 * 
	 * @return the selected
	 */
	public Object[] getSelected() {
		return capturerList.getSelectedValues();
	}

	/**
	 * Sets the scan active.
	 */
	public void setScanActive() {
		scanNetworkButton.setText("Scanning...");
		scanNetworkButton.setEnabled(false);
	}

	/**
	 * Sets the scan ready.
	 */
	public void setScanReady() {
		scanNetworkButton.setText("Scan Network");
		scanNetworkButton.setEnabled(true);
	}

	/**
	 * Gets the capturer list model.
	 * 
	 * @return the capturer list model
	 */
	public DefaultListModel getCapturerListModel() {
		return capturerListModel;
	}

	/**
	 * Sets the capturer list model.
	 * 
	 * @param capturerListModel
	 *            the new capturer list model
	 */
	public void setCapturerListModel(DefaultListModel capturerListModel) {
		this.capturerListModel = capturerListModel;
	}

	/**
	 * Update list.
	 */
	public void updateList() {
		capturerList.repaint();
	}

}
