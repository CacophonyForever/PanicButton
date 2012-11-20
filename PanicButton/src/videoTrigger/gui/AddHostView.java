package videoTrigger.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import videoTrigger.VideoTriggerClient;

public class AddHostView extends JFrame
{
	JTextField myHostNameField;
	JLabel myHostNameLabel;
	
	JTextField myPortField;
	JLabel myPortLabel;
	
	JButton myOkButton;
	JButton myCancelButton;	
	
	ActionListener myActionListener;
	VideoTriggerClient myClient;
	
	public AddHostView(VideoTriggerClient client)
	{
		VideoTriggerClient myClient = client;
		JPanel borderPanel = new JPanel();
		borderPanel.setLayout(new BorderLayout());
		JPanel entryGrid = new JPanel();
		entryGrid.setLayout(new GridLayout(2,2));
		myHostNameField = new JTextField();
		myHostNameLabel = new JLabel("Hostname");
		myPortField = new JTextField();
		myPortLabel = new JLabel("Port");
		
		entryGrid.add(myHostNameLabel);
		entryGrid.add(myHostNameField);
		entryGrid.add(myPortLabel);
		entryGrid.add(myPortField);
		
		JPanel buttonGrid = new JPanel();
		buttonGrid.setLayout(new GridLayout(1,2));
		myOkButton = new JButton("OK");
		myCancelButton = new JButton("Cancel");
		buttonGrid.add(myOkButton,0,0);
		buttonGrid.add(myCancelButton,0,1);
		
		borderPanel.add(entryGrid,BorderLayout.CENTER);
		borderPanel.add(buttonGrid,BorderLayout.SOUTH);
		
		this.getContentPane().add(borderPanel);
		this.pack();

		this.setLocationByPlatform(true);
		
		this.setVisible(true);

		myActionListener = (ActionListener) new addHostActions(client);
		myOkButton.addActionListener(myActionListener);
		myCancelButton.addActionListener(myActionListener);

	}
	
	public String getEnteredHostname()
	{
		return myHostNameField.getText();
	}
	
	
	public int getEnteredPort()
	{
		return Integer.parseInt(myPortField.getText());
	}
	

}
