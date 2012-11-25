/*
 * 
 */
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

// TODO: Auto-generated Javadoc
/**
 * The Class AddHostView.
 */
public class AddHostView extends JFrame {

    /** The my host name field. */
    JTextField myHostNameField;

    /** The my host name label. */
    JLabel myHostNameLabel;

    /** The my port field. */
    JTextField myPortField;

    /** The my port label. */
    JLabel myPortLabel;

    /** The my ok button. */
    JButton myOkButton;

    /** The my cancel button. */
    JButton myCancelButton;

    /** The my action listener. */
    ActionListener myActionListener;

    /** The my client. */
    VideoTriggerClient myClient;

    /**
     * Instantiates a new adds the host view.
     * 
     * @param client
     *            the client
     */
    public AddHostView(VideoTriggerClient client) {
	VideoTriggerClient myClient = client;
	JPanel borderPanel = new JPanel();
	borderPanel.setLayout(new BorderLayout());
	JPanel entryGrid = new JPanel();
	entryGrid.setLayout(new GridLayout(2, 2));
	myHostNameField = new JTextField();
	myHostNameLabel = new JLabel("Hostname");
	myPortField = new JTextField();
	myPortLabel = new JLabel("Port");

	entryGrid.add(myHostNameLabel);
	entryGrid.add(myHostNameField);
	entryGrid.add(myPortLabel);
	entryGrid.add(myPortField);

	JPanel buttonGrid = new JPanel();
	buttonGrid.setLayout(new GridLayout(1, 2));
	myOkButton = new JButton("OK");
	myCancelButton = new JButton("Cancel");
	buttonGrid.add(myOkButton, 0, 0);
	buttonGrid.add(myCancelButton, 0, 1);

	borderPanel.add(entryGrid, BorderLayout.CENTER);
	borderPanel.add(buttonGrid, BorderLayout.SOUTH);

	this.getContentPane().add(borderPanel);
	this.pack();

	this.setLocationByPlatform(true);

	this.setVisible(true);

	myActionListener = new addHostActions(client);
	myOkButton.addActionListener(myActionListener);
	myCancelButton.addActionListener(myActionListener);

    }

    /**
     * Gets the entered hostname.
     * 
     * @return the entered hostname
     */
    public String getEnteredHostname() {
	return myHostNameField.getText();
    }

    /**
     * Gets the entered port.
     * 
     * @return the entered port
     */
    public int getEnteredPort() {
	return Integer.parseInt(myPortField.getText());
    }

}
