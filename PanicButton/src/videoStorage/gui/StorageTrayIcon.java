package videoStorage.gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import videoCapture.gui.trayIcon.TrayController;

public class StorageTrayIcon extends TrayIcon {
	
	private static final File imageFile = new File("/home/paul/PanicB/StorageTrayIcon.png");
	StorageTrayController myController;
	
	public StorageTrayIcon(StorageTrayController control) throws IOException, AWTException
	{
		super((Image) ImageIO.read(imageFile), "PanicButton Storage");
		myController = control;
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(this);
		this.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						myController.showStausWindow();
					}
				});
	}
	

}
