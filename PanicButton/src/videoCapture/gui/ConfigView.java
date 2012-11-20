package videoCapture.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import videoCapture.ConfigSettings;

public class ConfigView extends JFrame{
	private JTabbedPane myTabbedPane;
	private JButton myOkButton;
	private JButton myCancelButton; 
	private CamerasPanel cameraPanel;
	private StoragePanel storagePanel;
	private BasicsPanel basicsPanel;
	private ConfigButtonsActionListener configButtonsActionListener;

	public ConfigView()
	{
		JPanel borderPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		
		myOkButton = new JButton("OK");
		myCancelButton = new JButton("Cancel");
		
		buttonPanel.add(myOkButton,0,0);
		buttonPanel.add(myCancelButton,0,1);
		
		borderPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		myTabbedPane = new JTabbedPane();
		basicsPanel = new BasicsPanel();
		myTabbedPane.addTab("Basics", null, basicsPanel,
		                  "Configure basic settings");
		
		
		cameraPanel = new CamerasPanel();
		myTabbedPane.addTab("Cameras", null, cameraPanel,
		                  "Setup Cameras");
		
		storagePanel = new StoragePanel();
		myTabbedPane.addTab("Storage", null, storagePanel,
		                  "Configure remote storage settings");

		borderPanel.add(myTabbedPane,BorderLayout.CENTER);
		this.getContentPane().add(borderPanel);
		this.setSize(640,480);
		this.setVisible(true);

	}
	
	public void AddListeners(CapturerController controller)
	{
		System.out.println("ADDINGLSIST");
		configButtonsActionListener = new ConfigButtonsActionListener(controller);
		myOkButton.addActionListener(configButtonsActionListener);
		myCancelButton.addActionListener(configButtonsActionListener);
		cameraPanel.addListeners(controller);
		storagePanel.addListeners(controller);
	}

	public CamerasPanel getCameraPanel() {
		return cameraPanel;
	}

	public void setCameraPanel(CamerasPanel cameraPanel) {
		this.cameraPanel = cameraPanel;
	}

	public StoragePanel getStoragePanel() {
		return storagePanel;
	}

	public void setStoragePanel(StoragePanel storagePanel) {
		this.storagePanel = storagePanel;
	}
	
	public void close()
	{
		this.cameraPanel.dispose();
	}

	public BasicsPanel getBasicsPanel() {
		return basicsPanel;
	}

	public void setBasicsPanel(BasicsPanel basicsPanel) {
		this.basicsPanel = basicsPanel;
	}
	
	public void loadSettingsFromConfig(ConfigSettings set)
	{
		basicsPanel.setCapturerName(set.getMyName());
		basicsPanel.setListenPort(set.getMyListenPort());
		basicsPanel.setDoesBroadcast(set.isBroadcastPublic());
	}
	
	
	
	
	
	
	

}
