/*
 * 
 */
package videoCapture.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

// TODO: Auto-generated Javadoc
/**
 * The view of the Capturer status window
 */
public class CapturerView extends JFrame {

	private JPanel layoutPanel;
	private JTextPane textPane;
	private JButton configureButton;
	private CapturerActionListener actionListener;
	private CapturerController controller;

	/**
	 * Instantiates a new capturer view.
	 */
	public CapturerView() {
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
	public void addActionListeners(CapturerController controller) {
		this.controller = controller;
		actionListener = new CapturerActionListener(controller);
		configureButton.addActionListener(actionListener);
	}

	/**
	 * Sets the text content of the status box in the window
	 * 
	 * @param content
	 *            the new text content
	 */
	public void setTextContent(String content) {
		textPane.setText(content);
		textPane.repaint();
	}
}
