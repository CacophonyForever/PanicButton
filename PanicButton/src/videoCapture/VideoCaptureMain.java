package videoCapture;

import java.awt.AWTException;
import java.io.IOException;

import videoCapture.gui.CapturerController;
import videoCapture.gui.CapturerView;
import videoCapture.gui.ConfigView;
import videoCapture.gui.trayIcon.CapturerTrayIcon;
import videoCapture.gui.trayIcon.TrayController;

public class VideoCaptureMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws AWTException 
	 */
	public static void main(String[] args) throws IOException, AWTException {
		CapturerView view = new CapturerView();
		VideoCapturer cap = new VideoCapturer();

		TrayController traytrol = new TrayController(cap);
		CapturerTrayIcon trayIcon = new CapturerTrayIcon(traytrol);

		
		cap.setMyName("Default Capturer");
		CapturerController comptroll = new CapturerController(view, cap);
		
		try {
			ConfigSettings configSet = ConfigSettings.loadConfigSettingsFromDisk("");
			cap.loadSettings(configSet);
			comptroll.setMyConfigSettings(configSet);
			if (configSet.isBroadcastPublic())
			{
				cap.initializeBroadcastListener();
			}
			}
		 catch (Exception e) {
			e.printStackTrace();
			System.out.println("No config file found");
		}
		cap.setMyController(comptroll);
		comptroll.updateViewText();

		
	}

}
