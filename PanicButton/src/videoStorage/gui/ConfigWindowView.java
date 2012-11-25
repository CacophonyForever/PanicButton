package videoStorage.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import videoStorage.ConfigSettings;

// TODO: Auto-generated Javadoc
/**
 * The Config Window view for the video storage host
 */
public class ConfigWindowView extends JFrame {

	/**
	 * The actual max value of the slider. Since sliders can't use longs, this
	 * is scaled up to a long value whenever the associated value needs to be
	 * accessed
	 */
	public static final int SLIDER_PRECISION = 1000;

	private JLabel storageNameLabel;
	private JLabel listenPortLabel;
	private JLabel saveLocationLabel;
	private JLabel maxSaveSizeLabel;
	private JLabel incomingStreamLabel;
	private JTextField nameField;
	private JTextField listenPortField;
	private JSlider maxSaveSizeSlider;
	private JLabel maxSaveSizeText;
	private JTextField saveLocationField;
	private JButton saveLocationBrowseButton;
	private JTextField incomingStreamField;
	private JButton okButton;
	private JButton cancelButton;
	private ConfigWindowListener listener;

	/**
	 * Instantiates a new config window view.
	 */
	public ConfigWindowView() {
		storageNameLabel = new JLabel("Name: ");
		listenPortLabel = new JLabel("Commuication Port: ");
		saveLocationLabel = new JLabel("Save Location: ");
		maxSaveSizeLabel = new JLabel("Saved video size limit: ");
		incomingStreamLabel = new JLabel("Incoming Stream Ports: ");

		nameField = new JTextField();
		listenPortField = new JTextField();
		maxSaveSizeSlider = new JSlider(0, SLIDER_PRECISION);
		maxSaveSizeText = new JLabel("bytes");
		saveLocationField = new JTextField();
		saveLocationBrowseButton = new JButton("...");
		incomingStreamField = new JTextField();

		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		JPanel myBorderPanel = new JPanel(new BorderLayout());
		JPanel myButtonPanel = new JPanel(new GridLayout(1, 2));
		JPanel myFieldPanel = new JPanel(new GridLayout(6, 2));

		myButtonPanel.add(okButton);
		myButtonPanel.add(cancelButton);

		myFieldPanel.add(storageNameLabel);
		myFieldPanel.add(nameField);

		myFieldPanel.add(listenPortLabel);
		myFieldPanel.add(listenPortField);

		JPanel saveLocationFieldPanel = new JPanel(new BorderLayout());

		saveLocationFieldPanel.add(saveLocationField, BorderLayout.CENTER);
		saveLocationFieldPanel.add(saveLocationBrowseButton, BorderLayout.EAST);

		myFieldPanel.add(saveLocationLabel);
		myFieldPanel.add(saveLocationFieldPanel);

		myFieldPanel.add(maxSaveSizeLabel);

		JPanel maxSizePanel = new JPanel(new BorderLayout());
		maxSizePanel.add(maxSaveSizeSlider, BorderLayout.CENTER);
		maxSizePanel.add(maxSaveSizeText, BorderLayout.EAST);

		myFieldPanel.add(maxSizePanel);

		saveLocationFieldPanel.add(saveLocationField, BorderLayout.CENTER);
		saveLocationFieldPanel.add(saveLocationBrowseButton, BorderLayout.EAST);

		myFieldPanel.add(incomingStreamLabel);
		myFieldPanel.add(incomingStreamField);

		myBorderPanel.add(myFieldPanel, BorderLayout.CENTER);
		myBorderPanel.add(myButtonPanel, BorderLayout.SOUTH);

		this.add(myBorderPanel);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Adds the listeners.
	 * 
	 * @param control
	 *            the control
	 */
	public void addListeners(ConfigWindowController control) {
		listener = new ConfigWindowListener(control);
		maxSaveSizeSlider.addChangeListener(listener);
		okButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
	}

	/**
	 * Populate fields.
	 * 
	 * @param settings
	 *            the settings
	 */
	public void populateFields(ConfigSettings settings) {
		nameField.setText(settings.getMyName());
		listenPortField.setText("" + settings.getListenPort());
		saveLocationField.setText(settings.getMySaveLocation()
				.getAbsolutePath());
		incomingStreamField.setText(convertReceivePortsToString(settings
				.getRecieveVideoPorts()));
	}

	/**
	 * Gets the entered name.
	 * 
	 * @return the entered name
	 */
	public String getEnteredName() {
		return nameField.getText();
	}

	/**
	 * Gets the entered port.
	 * 
	 * @return the entered port
	 */
	public int getEnteredPort() {
		return Integer.parseInt(listenPortField.getText());
	}

	/**
	 * Gets the entered save location.
	 * 
	 * @return the entered save location
	 */
	public String getEnteredSaveLocation() {
		return saveLocationField.getText();
	}

	/**
	 * Parses the incoming stream field into a list of integers
	 * 
	 * @return the list of integers
	 */
	public List<Integer> getEnteredIncomingStreamField() {
		String[] ints = incomingStreamField.getText().split("\\-");
		int min = Integer.parseInt(ints[0]);
		int max = Integer.parseInt(ints[1]);

		List<Integer> retArray = new ArrayList<Integer>();
		for (int i = min; i <= max; i++) {
			retArray.add(i);
		}

		return retArray;
	}

	/**
	 * Convert receive ports to string.
	 * 
	 * @param ports
	 *            the ports
	 * @return the string
	 */
	public String convertReceivePortsToString(List<Integer> ports) {
		int min = 65536;
		int max = 0;
		for (Integer i : ports) {
			if (i < min)
				min = i;
			if (i > max)
				max = i;
		}
		return min + "-" + max;
	}

	/**
	 * Gets the slider pct.
	 * 
	 * @return the slider pct
	 */
	public double getSliderPct() {
		return (double) maxSaveSizeSlider.getValue()
				/ (double) maxSaveSizeSlider.getMaximum();
	}

	/**
	 * Sets the max size label text.
	 * 
	 * @param string
	 *            the new max size label text
	 */
	public void setMaxSizeLabelText(String string) {
		maxSaveSizeText.setText(string);
		maxSaveSizeText.repaint();
	}

	/**
	 * Sets the slider value.
	 * 
	 * @param val
	 *            the new slider value
	 */
	public void setSliderValue(int val) {
		maxSaveSizeSlider.setValue(val);
	}

}
