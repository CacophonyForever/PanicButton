package videoCapture.gui.trayIcon;

import videoCapture.VideoCapturer;

public class TrayController {
	private VideoCapturer myCapturer;
	
	public TrayController(VideoCapturer cap)
	{
		myCapturer=cap;
	}
	
	public void showCapturerView()
	{
		System.out.println("DIAPLYING VIEW");
		myCapturer.displayView();
	}

}
