/*
 * 
 */
package videoTrigger;

import videoTrigger.gui.VideoTriggerController;
import videoTrigger.gui.VideoTriggerView;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoTriggerMain.
 */
public class VideoTriggerMain {

    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args) {
	CapturerScanner cs;

	// new CapturerScanner(36000);
	VideoTriggerClient vc = new VideoTriggerClient();
	VideoTriggerView view = new VideoTriggerView();
	VideoTriggerController control = new VideoTriggerController(vc,view);
	view.addActionListeners(control);
	vc.loadSettings();
	
    }

}
