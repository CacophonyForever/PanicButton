package videoStorage.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import videoStorage.ConfigSettings;

public class ConfigWindowView extends JFrame{
	
	public static final int SLIDER_PRECISION = 1000;
	private JLabel myStorageNameLabel;
	private JLabel myListenPortLabel;
	private JLabel mySaveLocationLabel;
	private JLabel myMaxSaveSizeLabel;
	private JLabel myIncomingStreamLabel;
	
	private JTextField myNameField;
	private JTextField myListenPortField;
	private JSlider myMaxSaveSizeSlider;
	private JLabel myMaxSaveSizeText;
	private JTextField mySaveLocationField;
	private JButton mySaveLocationBrowseButton;
	private JTextField myIncomingStreamField;
	
	private JButton myOkButton;
	private JButton myCancelButton;
	
	private ConfigWindowListener myListener;
	
	public ConfigWindowView() 
	{
		myStorageNameLabel = new JLabel("Name: ");
		myListenPortLabel = new JLabel("Commuication Port: ");
		mySaveLocationLabel = new JLabel("Save Location: ");		
		myMaxSaveSizeLabel = new JLabel("Saved video size limit: ");
		myIncomingStreamLabel = new JLabel("Incoming Stream Ports: ");
		
		myNameField = new JTextField();
		myListenPortField = new JTextField();
		myMaxSaveSizeSlider = new JSlider(0,SLIDER_PRECISION);
		myMaxSaveSizeText = new JLabel("bytes");
		mySaveLocationField = new JTextField();
		mySaveLocationBrowseButton = new JButton("...");		
		myIncomingStreamField = new JTextField();
		
		myOkButton = new JButton("OK");
		myCancelButton = new JButton("Cancel");


		
		JPanel myBorderPanel = new JPanel(new BorderLayout());
		JPanel myButtonPanel = new JPanel(new GridLayout(1,2));
		JPanel myFieldPanel = new JPanel(new GridLayout(6,2));
		
		myButtonPanel.add(myOkButton);
		myButtonPanel.add(myCancelButton);
		
		myFieldPanel.add(myStorageNameLabel);
		myFieldPanel.add(myNameField);
		
		myFieldPanel.add(myListenPortLabel);
		myFieldPanel.add(myListenPortField);
		
		JPanel saveLocationFieldPanel = new JPanel(new BorderLayout());
		
		saveLocationFieldPanel.add(mySaveLocationField,BorderLayout.CENTER);
		saveLocationFieldPanel.add(mySaveLocationBrowseButton,BorderLayout.EAST);
		
		myFieldPanel.add(mySaveLocationLabel);
		myFieldPanel.add(saveLocationFieldPanel);
		
		myFieldPanel.add(myMaxSaveSizeLabel);
		
	
		JPanel maxSizePanel = new JPanel(new BorderLayout());
		maxSizePanel.add(myMaxSaveSizeSlider, BorderLayout.CENTER);
		maxSizePanel.add(myMaxSaveSizeText, BorderLayout.EAST);
		
		myFieldPanel.add(maxSizePanel);
		
		saveLocationFieldPanel.add(mySaveLocationField,BorderLayout.CENTER);
		saveLocationFieldPanel.add(mySaveLocationBrowseButton,BorderLayout.EAST);

		

		myFieldPanel.add(myIncomingStreamLabel);
		myFieldPanel.add(myIncomingStreamField);
		
		myBorderPanel.add(myFieldPanel,BorderLayout.CENTER);
		myBorderPanel.add(myButtonPanel,BorderLayout.SOUTH);
		
		
		this.add(myBorderPanel);
		this.pack();
		this.setVisible(true);		
	}
	
	public void addListeners(ConfigWindowController control)
	{
		myListener = new ConfigWindowListener(control);
		myMaxSaveSizeSlider.addChangeListener(myListener);
		myOkButton.addActionListener(myListener);
		myCancelButton.addActionListener(myListener);
	}
	
	public void populateFields(ConfigSettings settings)
	{
		myNameField.setText(settings.getMyName());
		myListenPortField.setText(""+settings.getListenPort());
		mySaveLocationField.setText(settings.getMySaveLocation().getAbsolutePath());
		myIncomingStreamField.setText(convertRecievePortsToString(settings.getRecieveVideoPorts()));		
	}
	
	
	public String getEnteredName()
	{
		return myNameField.getText();
	}
	
	public int getEnteredPort()
	{
		return Integer.parseInt(myListenPortField.getText());
	}
	
	public String getEnteredSaveLocation()
	{
		return mySaveLocationField.getText();
	}
	
	public List<Integer> getEnteredIncomingStramField()
	{
		String[] ints = myIncomingStreamField.getText().split("\\-");
		int min = Integer.parseInt(ints[0]);
		int max = Integer.parseInt(ints[1]);
		
		 List<Integer> retArray = new ArrayList<Integer>();
		for (int i=min;i<=max;i++)
		{		
			retArray.add(i);
		}
		
		return retArray;
	}

	public String convertRecievePortsToString(List<Integer> ports)
	{
		int min=65536;
		int max=0;
		for (Integer i : ports)
		{
			if (i<min)
				min=i;
			if (i>max)
				max=i;
		}
		return min+"-"+max;
	}
	
	
	public double getSliderPct()
	{
		return (double)myMaxSaveSizeSlider.getValue()/(double)myMaxSaveSizeSlider.getMaximum();
	}
	
	public void setMaxSizeLabelText(String string)
	{
		myMaxSaveSizeText.setText(string);
		myMaxSaveSizeText.repaint();
	}
	
	public void setSliderValue(int val)
	{
		myMaxSaveSizeSlider.setValue(val);
	}
	
}

