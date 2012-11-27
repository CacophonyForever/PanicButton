package videoCapture;

import java.awt.AWTException;
import java.io.IOException;

import videoCapture.gui.CapturerController;
import videoCapture.gui.CapturerView;
import videoCapture.gui.trayIcon.CapturerTrayIcon;
import videoCapture.gui.trayIcon.TrayController;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoCaptureMain.
 */
public class VideoCaptureMain {

	// Launch the capturer
	public static void main(String[] args) throws IOException, AWTException {
		CapturerView view = new CapturerView();
		VideoCapturer capturer = new VideoCapturer();

		TrayController traytrol = new TrayController(capturer);
		CapturerTrayIcon trayIcon = new CapturerTrayIcon(traytrol);

		capturer.setMyName("Default Capturer");
		CapturerController controller = new CapturerController(view, capturer);

		try {
			CapturerConfigSettings configSet = CapturerConfigSettings
					.loadConfigSettingsFromDisk(CapturerConfigSettings.saveLocation);
			capturer.loadSettings(configSet);
			controller.setMyConfigSettings(configSet);
			if (configSet.isBroadcastPublic()) {
				capturer.initializeBroadcastListener();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No config file found");
			controller.setMyConfigSettings (new CapturerConfigSettings());
		}
		capturer.setMyController(controller);
		controller.updateViewText();

	}

}
