package videoStorage.gui;

import java.io.File;

import Common.CommonFunctions;
import videoStorage.ConfigSettings;
import videoStorage.VideoStorageHost;

public class ConfigWindowController {
	private ConfigWindowView myView;
	private VideoStorageHost myHost;
	
	public ConfigWindowController (ConfigWindowView view, VideoStorageHost host)
	{
		myView = view;
		myHost = host;
	}
	
	public void setSliderText()
	{
		long freeSpace = myHost.getFreeSpace();		
		String text = CommonFunctions.formatByteSizeHuman((long)((double)freeSpace*myView.getSliderPct()));
		myView.setMaxSizeLabelText(text);	
	}
	
	public void setSliderText(String text)
	{
		myView.setMaxSizeLabelText(text);	
	}
	
		
	public void setSliderValue()
	{
		int sliderVal = (int)(((double)myHost.getMaxFileSpace()/(double)myHost.getFreeSpace())*myView.SLIDER_PRECISION);
		System.out.println(sliderVal +" [ ] " + (double)myHost.getFreeSpace()/(double)myHost.getMaxFileSpace());
		myView.setSliderValue(sliderVal);
	}
	
	private long getSetSliderValue()
	{
		return (long)(myView.getSliderPct()*myHost.getFreeSpace());
	}
	
	public void closeSettings()
	{
		myView.dispose();
	}
	
	public ConfigSettings getSettingsFromEnteredValues()
	{
		ConfigSettings set = new ConfigSettings();
		set.setListenPort(myView.getEnteredPort());
		set.setMyMaxSize(getSetSliderValue());
		System.out.println("SIZE = " + getSetSliderValue());
		set.setMyName(myView.getEnteredName());
		set.setMySaveLocation(new File(myView.getEnteredSaveLocation()));
		System.out.println(set.getMySaveLocation());
		set.setRecieveVideoPorts(myView.getEnteredIncomingStramField());
		
		return set;
	}
	
	public void saveSettings()
	{
		ConfigSettings mySettings = getSettingsFromEnteredValues();
		try {
			mySettings.saveToDisk();
			myHost.setMySettings(mySettings);
			myHost.loadSettings();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeSettings();
	}


}
