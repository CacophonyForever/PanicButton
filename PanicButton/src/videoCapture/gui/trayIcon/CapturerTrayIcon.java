package videoCapture.gui.trayIcon;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPopupMenu;

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
	private TrayController controller;
	private PopupMenu rightClickMenu;

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
	public CapturerTrayIcon() throws IOException,
			AWTException {
		super(ImageIO.read(imageFile), "PanicButton Capturer");
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(this);

	}
	
	public void addListeners(TrayController control)
	{
		controller=control;
		rightClickMenu = new PopupMenu();
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(
				new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				logger.info("Exit item clicked on tray icon");
				controller.exitCapturer();
			}
			});
		rightClickMenu.add(exitItem);
		this.setPopupMenu(rightClickMenu);
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Tray icon activated");
				controller.showCapturerView();
			}
		});
	}
	
	public void die()
	{
		SystemTray tray = SystemTray.getSystemTray();
		tray.remove(this);
	}

}
