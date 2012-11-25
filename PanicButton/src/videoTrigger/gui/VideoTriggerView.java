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
import videoTrigger.remoteVideoCapturer;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerView.
 */
public class VideoTriggerView extends JFrame {

    /** The my border panel. */
    private JPanel myBorderPanel;

    /** The my capturer list. */
    private JList myCapturerList;

    /** The my button panel. */
    private JPanel myButtonPanel;

    /** The my add manual button. */
    private JButton myAddManualButton;

    /** The my scan network button. */
    private JButton myScanNetworkButton;

    /** The my trigger capture button. */
    private JButton myTriggerCaptureButton;

    /** The my act listen. */
    ActionListener myActListen;

    /** The my client. */
    private VideoTriggerClient myClient;

    /** The capturer list model. */
    private DefaultListModel capturerListModel;

    /**
     * Instantiates a new video trigger view.
     * 
     * @param client
     *            the client
     */
    public VideoTriggerView(VideoTriggerClient client) {
	myBorderPanel = new JPanel();
	myBorderPanel.setLayout(new BorderLayout());
	capturerListModel = new DefaultListModel();
	myCapturerList = new JList(capturerListModel);
	myButtonPanel = new JPanel();
	myButtonPanel.setLayout(new GridLayout(1, 3));
	myAddManualButton = new JButton("Manual Add");
	myScanNetworkButton = new JButton("Scan Network");
	myTriggerCaptureButton = new JButton("Trigger");
	myBorderPanel.add(myCapturerList, BorderLayout.CENTER);
	myBorderPanel.add(myButtonPanel, BorderLayout.SOUTH);
	myButtonPanel.add(myAddManualButton, 0, 0);
	myButtonPanel.add(myScanNetworkButton, 0, 1);
	myButtonPanel.add(myTriggerCaptureButton, 0, 2);
	this.getContentPane().add(myBorderPanel);
	this.setSize(600, 400);
	this.setLocationByPlatform(true);
	this.setVisible(true);

	myClient = client;

	ActionListener myActListen = new VideoTriggerActions(client);
	myAddManualButton.addActionListener(myActListen);
	myScanNetworkButton.addActionListener(myActListen);
	myTriggerCaptureButton.addActionListener(myActListen);
    }

    /**
     * Adds the item to list.
     * 
     * @param cap
     *            the cap
     */
    public void addItemToList(remoteVideoCapturer cap) {
	capturerListModel.addElement(cap);
	myCapturerList.repaint();
    }

    /**
     * Gets the selected.
     * 
     * @return the selected
     */
    public Object[] getSelected() {
	return myCapturerList.getSelectedValues();
    }

    /**
     * Sets the scan active.
     */
    public void setScanActive() {
	myScanNetworkButton.setText("Scanning...");
	myScanNetworkButton.setEnabled(false);
    }

    /**
     * Sets the scan ready.
     */
    public void setScanReady() {
	myScanNetworkButton.setText("Scan Network");
	myScanNetworkButton.setEnabled(true);
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
	myCapturerList.repaint();
    }

}
