package videoCapture.gui.trayIcon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CapturerTrayIcon extends TrayIcon {
	private static final File imageFile = new File("/home/paul/PanicB/CapturerTrayIcon.png");
	TrayController myController;
	
	public CapturerTrayIcon(TrayController control) throws IOException, AWTException
	{
		super((Image) ImageIO.read(imageFile), "PanicButton Capturer");
		myController = control;
		SystemTray tray = SystemTray.getSystemTray();
		tray.add(this);
		this.addActionListener(
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						myController.showCapturerView();
					}
					
				});
	}
	
	

}
