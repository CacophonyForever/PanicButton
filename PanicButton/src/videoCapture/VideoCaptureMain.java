package videoCapture;

import java.awt.AWTException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	public static void main(String[] args) throws IOException, AWTException, NoSuchAlgorithmException {
		CapturerView view = new CapturerView();
		VideoCapturer capturer = new VideoCapturer();

	
		CapturerTrayIcon trayIcon = new CapturerTrayIcon();
		TrayController traytrol = new TrayController(capturer, trayIcon);
		trayIcon.addListeners(traytrol);
		capturer.setMyName("Default Capturer");
		CapturerController controller = new CapturerController(view, capturer);
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] hashedPassword = md.digest("Dingdong".getBytes("UTF-8"));
		capturer.setPasswordHash(hashedPassword);
		try {
			CapturerConfigSettings configSet = CapturerConfigSettings
					.loadConfigSettingsFromDisk(CapturerConfigSettings.saveLocation);
			capturer.loadSettings(configSet);
			controller.setMyConfigSettings(configSet);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No config file found");
			controller.setMyConfigSettings (new CapturerConfigSettings());
		}
		capturer.setMyController(controller);
		controller.updateViewText();

	}

}
