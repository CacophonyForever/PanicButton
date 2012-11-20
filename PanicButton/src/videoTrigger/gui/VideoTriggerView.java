package videoTrigger.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

import videoTrigger.VideoTriggerClient;
import videoTrigger.remoteVideoCapturer;

public class VideoTriggerView extends JFrame
{
	private JPanel myBorderPanel;
	private JList myCapturerList;
	private JPanel myButtonPanel;
	private JButton myAddManualButton;
	private JButton myScanNetworkButton;
	private JButton myTriggerCaptureButton;
	ActionListener myActListen;
	
	private VideoTriggerClient myClient;
	private DefaultListModel capturerListModel;
	
	public VideoTriggerView(VideoTriggerClient client)
	{
		myBorderPanel = new JPanel();
		myBorderPanel.setLayout(new BorderLayout());
		capturerListModel = new DefaultListModel();
		myCapturerList = new JList(capturerListModel);
		myButtonPanel = new JPanel();
		myButtonPanel.setLayout( new GridLayout(1,3));
		myAddManualButton = new JButton("Manual Add");
		myScanNetworkButton = new JButton("Scan Network");
		myTriggerCaptureButton = new JButton("Trigger");
		myBorderPanel.add(myCapturerList, BorderLayout.CENTER);
		myBorderPanel.add(myButtonPanel, BorderLayout.SOUTH);
		myButtonPanel.add(myAddManualButton,0,0);
		myButtonPanel.add(myScanNetworkButton,0,1);
		myButtonPanel.add(myTriggerCaptureButton,0,2);
		this.getContentPane().add(myBorderPanel);
		this.setSize(600,400);		
		this.setLocationByPlatform(true);
		this.setVisible(true);		

		
		myClient=client;
		
		ActionListener myActListen = new VideoTriggerActions(client);		
		myAddManualButton.addActionListener(myActListen);
		myScanNetworkButton.addActionListener(myActListen);
		myTriggerCaptureButton.addActionListener(myActListen);
	}
	
	public void addItemToList(remoteVideoCapturer cap)
	{
		capturerListModel.addElement(cap);
		myCapturerList.repaint();
	}
	
	public Object[] getSelected()
	{
		return (Object[])myCapturerList.getSelectedValues(); 
	}
	
	public void setScanActive()
	{
		myScanNetworkButton.setText("Scanning...");
		myScanNetworkButton.setEnabled(false);
	}
	
	public void setScanReady()
	{
		myScanNetworkButton.setText("Scan Network");
		myScanNetworkButton.setEnabled(true);
	}

	public DefaultListModel getCapturerListModel() {
		return capturerListModel;
	}

	public void setCapturerListModel(DefaultListModel capturerListModel) {
		this.capturerListModel = capturerListModel;
	}
	
	public void updateList()
	{
		myCapturerList.repaint();
	}
	
	
	
	

}

