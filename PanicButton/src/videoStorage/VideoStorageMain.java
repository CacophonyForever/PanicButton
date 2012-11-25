package videoStorage;

import java.awt.AWTException;
import java.io.IOException;

import videoStorage.gui.ConfigWindowController;
import videoStorage.gui.ConfigWindowView;
import videoStorage.gui.StatusWindowController;
import videoStorage.gui.StatusWindowView;
import videoStorage.gui.StorageTrayController;
import videoStorage.gui.StorageTrayIcon;

public class VideoStorageMain {

		private static final String DEFAULT_SAVE_LOCATION = "~/PanicB";
	public static void main(String[] args)
	  {						
		
		ConfigSettings set;
		try {
			set = ConfigSettings.loadConfigSettingsFromDisk(ConfigSettings.saveSettingsLocation);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			 set = new ConfigSettings();	
			set.setDefaultSettings();
		}


		VideoStorageHost host = new VideoStorageHost(DEFAULT_SAVE_LOCATION);
		host.setMyController (new StatusWindowController(null,host));
		host.setMySettings(set);
		host.loadSettings();
		try {
			StorageTrayIcon trayIcon = new StorageTrayIcon(new StorageTrayController(host));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		host.init();
		

	  }
}
