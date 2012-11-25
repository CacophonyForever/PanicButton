package videoCapture.gui;

import java.awt.GridLayout;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.swing.VideoComponent;

import videoCapture.VideoCaptureDevice;

/**
 * The View portion of the Cameres Settings tab of the configuration window for
 * the Video Capturer, for selecting which cameras are actively set to record
 */
public class CamerasPanel extends JPanel {

	private static final Logger logger = Logger.getLogger("log");

	private VideoComponent cameraPreview;
	private JCheckBox selectCamera;
	private JList cameraList;
	private DefaultListModel cameraListModel;
	private CameraSettingsActionListener cameraSettingsActionListener;
	private Pipeline pipeline;
	private Element videoFliter;
	private Element videoSource;

	/**
	 * Instantiates a new cameras panel.
	 */
	public CamerasPanel() {
		this.setLayout(new GridLayout(1, 2));
		cameraListModel = new DefaultListModel();
		cameraList = new JList(cameraListModel);
		this.add(cameraList, 0, 0);
		JPanel displayGrid = new JPanel(new GridLayout(2, 1));
		String[] args = new String[0];
		Gst.init("SwingVideoTest", args);
		cameraPreview = new VideoComponent();
		selectCamera = new JCheckBox("Use This Camera");
		displayGrid.add(selectCamera, 0, 0);
		displayGrid.add(cameraPreview, 1, 0);
		this.add(displayGrid, 0, 1);
		cameraList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Adds gui interaction listeners to components on the tab
	 * 
	 * @param controller
	 *            the controller
	 */
	public void addListeners(CapturerController controller) {
		cameraSettingsActionListener = new CameraSettingsActionListener(
				controller);
		cameraList.addListSelectionListener(cameraSettingsActionListener);
		selectCamera.addActionListener(cameraSettingsActionListener);
	}

	/**
	 * Populates the device list with a set of video capture devices
	 * 
	 * @param devices
	 *            the devices
	 */
	public void populateDeviceList(VideoCaptureDevice[] devices) {
		for (VideoCaptureDevice device : devices) {
			cameraListModel.add(0, device);
		}
	}

	/**
	 * Sets the selected value.
	 * 
	 * @param value
	 *            the new selected value
	 */
	public void setSelectedValue(boolean value) {
		selectCamera.setSelected(value);
	}

	/**
	 * Gets the selected value.
	 * 
	 * @return the selected value
	 */
	public boolean getSelectedValue() {
		return selectCamera.isSelected();
	}

	/**
	 * Gets the selected camera.
	 * 
	 * @return the selected camera
	 */
	public VideoCaptureDevice getSelectedCamera() {
		return (VideoCaptureDevice) cameraList.getSelectedValue();
	}

	/**
	 * Display content from selected camera into preview box
	 * 
	 * @param device
	 *            the location of the device
	 */
	public void displayCamPreview(String device) {

		// Check if pipeline is already running and needs to be reset
		if (pipeline != null) {
			String[] args = new String[0];

			Element videosink = cameraPreview.getElement();
			logger.info("Disposing old video preview pipeline");
			// Unlink all components
			videoFliter.unlink(videosink);
			videosink.stop();
			pipeline.remove(videosink);
			pipeline.setState(State.NULL);
			pipeline.dispose();
			videoFliter.setState(State.NULL);
			videoFliter.dispose();
			videoSource.setState(State.NULL);
			videoSource.dispose();

			// Create new pipeline
			logger.info("Creating New Pipeline");
			pipeline = new Pipeline("pipeline");
			ElementFactory.make("videotestsrc", "source");
			videoSource = ElementFactory.make("v4l2src", "source");
			videoSource.set("device", device);
			videoFliter = ElementFactory.make("capsfilter", "flt");
			videoFliter.setCaps(Caps
					.fromString("video/x-raw-yuv, width=640, height=480"));
			pipeline.addMany(videoSource, videoFliter, videosink);
			Element.linkMany(videoSource, videoFliter, videosink);

			// Start the pipeline processing
			pipeline.setState(State.PLAYING);
			videosink.setState(State.PLAYING);
			logger.info("Pipeline Created");
		} else {
			// No previous pipeline exists
			logger.info("Creating Pipeline");
			pipeline = new Pipeline("pipeline");
			ElementFactory.make("videotestsrc", "source");
			videoSource = ElementFactory.make("v4l2src", "source");
			videoSource.set("device", device);
			videoFliter = ElementFactory.make("capsfilter", "flt");
			videoFliter.setCaps(Caps
					.fromString("video/x-raw-yuv, width=640, height=480"));
			Element videosink = cameraPreview.getElement();
			ElementFactory.make("xvimagesink", "sink");
			pipeline.addMany(videoSource, videoFliter, videosink);
			Element.linkMany(videoSource, videoFliter, videosink);

			// Start the pipeline processing
			pipeline.setState(State.PLAYING);
			logger.info("Pipeline Created");
		}

		// If pipeline isn't "PLAYING" for some reason, hide the
		// preview box. TODO: Make this better
		if (pipeline.getState().equals(State.PLAYING)) {
			cameraPreview.setVisible(true);
		} else {
			cameraPreview.setVisible(false);
		}
		cameraPreview.repaint();
	}

	/**
	 * Dispose any pipeline that might be playing
	 */
	public void dispose() {
		if (pipeline != null) {
			pipeline.setState(State.NULL);
			pipeline.dispose();
			videoFliter.setState(State.NULL);
			videoFliter.dispose();
			videoSource.setState(State.NULL);
			videoSource.dispose();
		}
	}

	/**
	 * Gets the camera list model.
	 * 
	 * @return the camera list model
	 */
	public ListModel getCameraListModel() {
		return cameraListModel;
	}

}
