package videoStorage.gui;

import java.io.File;

import videoStorage.ConfigSettings;
import videoStorage.VideoStorageHost;
import Common.CommonFunctions;

// TODO: Auto-generated Javadoc
/**
 * The Controller for the Config Settings Swing UI window 
 * of the Video Storage Host
 */
public class ConfigWindowController {
	private ConfigWindowView view;
	private VideoStorageHost host;

	/**
	 * Instantiates a new config window controller.
	 * 
	 * @param view
	 *            the view
	 * @param host
	 *            the host
	 */
	public ConfigWindowController(ConfigWindowView view, VideoStorageHost host) {
		this.view = view;
		this.host = host;
	}

	/**
	 * Sets the textbox next to the slider to be the value that the slider has
	 * selected. Since sliders can only have values of int rather than long, it
	 * has to be converted
	 */
	public void setSliderText() {
		long freeSpace = host.getFreeSpace();
		String text = CommonFunctions
				.formatByteSizeHuman((long) (freeSpace * view.getSliderPct()));
		view.setMaxSizeLabelText(text);
	}

	/**
	 * Sets the slider text to a given value
	 * 
	 * @param text
	 *            the new slider text
	 */
	public void setSliderText(String text) {
		view.setMaxSizeLabelText(text);
	}

	/**
	 * Sets the value of the slider to the value of the host's allocated file
	 * space
	 */
	public void setSliderValue() {
		int sliderVal = (int) (((double) host.getMaxFileSpace() / (double) host
				.getFreeSpace()) * ConfigWindowView.SLIDER_PRECISION);
		System.out.println(sliderVal + " [ ] " + (double) host.getFreeSpace()
				/ (double) host.getMaxFileSpace());
		view.setSliderValue(sliderVal);
	}

	/**
	 * Gets the scaled value of the slider
	 * 
	 * @return the scaled long value of the slider
	 */
	private long getSetSliderValue() {
		return (long) (view.getSliderPct() * host.getFreeSpace());
	}

	/**
	 * Closes the configure settings window
	 */
	public void closeSettings() {
		view.dispose();
	}

	/**
	 * Created a ConfigSettings object with properties that are set as that of
	 * the values entered into the config window fields
	 * 
	 * @return the settings from entered values
	 */
	public ConfigSettings getSettingsFromEnteredValues() {
		ConfigSettings set = new ConfigSettings();
		set.setListenPort(view.getEnteredPort());
		set.setMyMaxSize(getSetSliderValue());
		set.setMyName(view.getEnteredName());
		set.setMySaveLocation(new File(view.getEnteredSaveLocation()));
		set.setRecieveVideoPorts(view.getEnteredIncomingStreamField());

		return set;
	}

	/**
	 * Save settings to disk and applies them to the storage host
	 */
	public void saveSettings() {
		ConfigSettings mySettings = getSettingsFromEnteredValues();
		try {
			mySettings.saveToDisk();
			host.setMySettings(mySettings);
			host.loadSettings();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSettings();
	}

}
