package videoCapture.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class CapturerView extends JFrame
{
	
	private JPanel myLayoutPanel;
	private JTextPane myTextPane;
	private JButton myConfigureButton;
	private CapturerActionListener myActionListener;
	private CapturerController myController;
		
	public CapturerView()
	{
		myLayoutPanel = new JPanel(new BorderLayout());	
		myTextPane = new JTextPane();
		myTextPane.setContentType("text/html");
		myTextPane.setEditable(false);
		myConfigureButton = new JButton("Configure");
		
		myLayoutPanel.add(myTextPane, BorderLayout.CENTER);
		myLayoutPanel.add(myConfigureButton, BorderLayout.SOUTH);
		
		this.getContentPane().add(myLayoutPanel);
		this.setVisible(true);
		this.setSize(300,400);	
	}
	
	public void addActionListeners(CapturerController controller)
	{
		myController=controller;
		myActionListener = new CapturerActionListener(myController);
		myConfigureButton.addActionListener(myActionListener);
	}
	
	public void setTextContent(String content)
	{
		myTextPane.setText(content);
		myTextPane.repaint();
	}
}
