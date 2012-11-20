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

import Common.Constants;

import videoCapture.CapturerStorageServer;

public class StoragePanel extends JPanel {
	private CapturerController myController;
	
	private DefaultListModel myStorageListModel;
	private JList myStorageList;
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
 	
	public StoragePanel()
	{
		this.setLayout(new GridLayout(1,2));
		JPanel listAndListButtonsPanel = new JPanel(new BorderLayout());
		JPanel listButtonsPanel = new JPanel(new GridLayout(1,2));
		JPanel fieldsPanel = new JPanel(new GridLayout(6,2));
		
		myStorageListModel = new DefaultListModel();
		myStorageList = new JList(myStorageListModel);
		
		myStorageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		 addButton = new JButton("Add");
		 deleteButton = new JButton("Delete");
		
		listButtonsPanel.add(addButton);
		listButtonsPanel.add(deleteButton);
		
		listAndListButtonsPanel.add(listButtonsPanel,BorderLayout.SOUTH);
		listAndListButtonsPanel.add(myStorageList,BorderLayout.CENTER);
		
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
		
		JPanel rightPanel= new JPanel(); 
		
		rightPanel.add(fieldsPanel);
		
		fieldsPanel.setPreferredSize(new Dimension(300,Constants.VIEW_FIELD_HEIGHT*6));
		
		this.add(listAndListButtonsPanel);
		this.add(rightPanel);
	}
	
	public CapturerStorageServer getSelectedStorage()
	{
		return (CapturerStorageServer) myStorageList.getSelectedValue();
	}
	
	public void populateFieldsFromSrv(CapturerStorageServer server)
	{
		System.out.println("POPULATING FIELDS FRMO " + server);
		nameField.setText(server.getMyName());
		hostnameField.setText(server.getMyHostname());
		portField.setText(""+server.getMyPort());
		freeSpaceValueLabel.setText(convertSizeToHumanReadable(server.getMyFreeSpace()));
		fileLocationValueLabel.setText(server.getMySaveLocation());
	}
	
	public String convertSizeToHumanReadable(long sizeInBytes)
	{
		return "" + sizeInBytes;
	}
	
	public void updateList(CapturerStorageServer[] myServers)
	{
		
		for (CapturerStorageServer srv : myServers)
		{
			if (myStorageListModel.contains(srv) == false)
			myStorageListModel.add(0,srv);
		}
		
		myStorageList.repaint();
	}
	
	public void deleteServer(CapturerStorageServer server)
	{
		myStorageListModel.removeElement(server);
	}
	
	public void populateFieldsFromSelected()
	{
		if (getSelectedStorage() != null)
		{
		populateFieldsFromSrv(getSelectedStorage());
		}
	}
	
	public void addListeners(CapturerController cont)
	{
		actionListener = new StorageSettingsActionListener(cont);
		addButton.addActionListener(actionListener);
		deleteButton.addActionListener(actionListener);
		myStorageList.addListSelectionListener(actionListener);
		testConnectionButton.addActionListener(actionListener);
		
		portField.addKeyListener(actionListener);
		hostnameField.addKeyListener(actionListener);
		nameField.addKeyListener(actionListener);
	}
	
	public CapturerStorageServer getStorageServerFromFields()
	{
		CapturerStorageServer capserv;
		try {
			capserv = new CapturerStorageServer(hostnameField.getText(), Integer.parseInt(portField.getText()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			capserv = new CapturerStorageServer(hostnameField.getText(), 0);
		}
		capserv.setMyName(nameField.getText());
		
		return capserv;
	}
	
	public void setConnectStatus(String status)
	{
		testConnectionLabel.setText(status);
	}
	
	public void setFreeSpace(String space)
	{
		freeSpaceValueLabel.setText(space);		
	}
	
}
