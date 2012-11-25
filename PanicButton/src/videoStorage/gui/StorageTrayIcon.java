package videoStorage.gui;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// TODO: Auto-generated Javadoc
/**
 * The video storage host system tray icon
 */
public class StorageTrayIcon extends TrayIcon {

	private static final File imageFile = new File(
			"/home/paul/PanicB/StorageTrayIcon.png");

	private StorageTrayController controller;

	/**
	 * Instantiates a new storage tray icon.
	 * 
	 * @param control
	 *            the control
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws AWTException
	 *             the aWT exception
	 */
	public StorageTrayIcon(StorageTrayController control) throws IOException,
			AWTException {
		super(ImageIO.read(imageFile), "PanicButton Storage");
		controller = control;
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(this);
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showStatusWindow();
			}
		});
	}

}
