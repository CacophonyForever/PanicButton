package videoCapture.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Common.Constants;

public class BasicsPanel extends JPanel {
	
	private JLabel myNameLabel;
	private JTextField myNameField;
	
	private JLabel myListenPortLabel;
	private JTextField myListenPortField;

	private JLabel myPublicLabel;
	private JCheckBox myPublicCheckbox;

	public BasicsPanel()
	{
		this.setMaximumSize(new Dimension(50,50));
		JPanel fieldPanel = new JPanel(new GridLayout(3,2)); 
		myNameLabel = new JLabel("Name: ");
		myNameField = new JTextField();
		
		myListenPortLabel = new JLabel("Port to listen on: ");
		myListenPortField = new JTextField();
				
		myPublicLabel = new JLabel("Advertise on local network: ");
		myPublicCheckbox = new JCheckBox();

		fieldPanel.add(myNameLabel);
		fieldPanel.add(myNameField);
		
		fieldPanel.add(myListenPortLabel);
		fieldPanel.add(myListenPortField);

		fieldPanel.add(myPublicLabel);
		fieldPanel.add(myPublicCheckbox);	
		
		fieldPanel.setPreferredSize(new Dimension(600,Constants.VIEW_FIELD_HEIGHT*3));
		
		this.add(fieldPanel);				
	}
	
	public String getCapturerName()
	{
		return myNameField.getText();
	}
	
	public String getListenPort()
	{
		return myListenPortField.getText();
	}
	
	public boolean doesBroadcastPublic()
	{
		return myPublicCheckbox.isSelected();
	}
	
	public void setCapturerName(String name)
	{
		myNameField.setText(name);
	}
	
	public void setListenPort(int port)
	{
		myListenPortField.setText(""+port);
	}
	
	public void setDoesBroadcast(boolean broadcast)
	{
		this.myPublicCheckbox.setSelected(broadcast);
	}
}
