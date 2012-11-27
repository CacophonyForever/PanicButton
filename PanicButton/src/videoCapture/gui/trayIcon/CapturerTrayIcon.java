package videoCapture.gui.trayIcon;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * System Tray icon for the Capturer
 * 
 * @author paul
 */
public class CapturerTrayIcon extends TrayIcon {

	// Location of tray icon image file
	private static final File imageFile = new File(
			"/home/paul/PanicB/CapturerTrayIcon.png");

	private static final Logger logger = Logger.getLogger("log");
	TrayController controller;

	/**
	 * Video Capturer Tray Icon View.
	 * 
	 * @param control
	 *            Controller of tray icon
	 * @throws IOException
	 *             when tray icon is unavailable
	 * @throws AWTException
	 *             if unable to access the system tray
	 */
	public CapturerTrayIcon(TrayController control) throws IOException,
			AWTException {
		super(ImageIO.read(imageFile), "PanicButton Capturer");
		controller = control;
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(this);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Tray icon activated");
				controller.showCapturerView();
			}
		});
	}

}
