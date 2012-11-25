package videoStorage.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

// TODO: Auto-generated Javadoc
/**
 * The view of the video storage host status window
 */
public class StatusWindowView extends JFrame {

	private JPanel layoutPanel;
	private JTextPane textPane;
	private JButton configureButton;
	private StatusWindowController controller;

	/**
	 * Instantiates a new status window view.
	 */
	public StatusWindowView() {
		layoutPanel = new JPanel(new BorderLayout());
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		configureButton = new JButton("Configure");

		layoutPanel.add(textPane, BorderLayout.CENTER);
		layoutPanel.add(configureButton, BorderLayout.SOUTH);

		this.getContentPane().add(layoutPanel);
		this.setVisible(true);
		this.setSize(300, 400);
	}

	/**
	 * Adds the action listeners.
	 * 
	 * @param controller
	 *            the controller
	 */
	public void addActionListeners(StatusWindowController controller) {
		this.controller = controller;
		StatusWindowListener butList = new StatusWindowListener(controller);
		configureButton.addActionListener(butList);
	}

	/**
	 * Sets the text content of the status window's main text box
	 * 
	 * @param content
	 *            the new text content (html format)
	 */
	public void setTextContent(String content) {
		textPane.setText(content);
		textPane.repaint();
	}
}
