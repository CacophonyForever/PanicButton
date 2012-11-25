package videoCapture;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JLabel;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoSource.
 */
public class VideoSource {

	private static Pipeline pipe;
	private static BufferedImage currentImage = null;
	private static JLabel currentImageLabel = new JLabel();
	private String deviceName;
	private String name;
	private CapturerStorageServer storageServer;
	private static final Logger logger = Logger.getLogger("log");

	/**
	 * Instantiates a new video source.
	 * 
	 * @param name
	 *            the name of this source
	 * @param deviceName
	 *            the device name
	 * @param srv
	 *            the storage server
	 */
	public VideoSource(String name, String deviceName, CapturerStorageServer srv) {
		this.name = name;
		this.deviceName = deviceName;
		storageServer = srv;
	}

	/**
	 * Begin recording and streaming.
	 * 
	 * @param hostname
	 *            the hostname of the destination server 
	 * @param port
	 *            the port of the destination server
	 */
	public void beginRecordingAndStreaming(String hostname, int port) {
		logger.info("Starting recording");
		
		// initialize Gstreamer
		String args[] = new String[0];
		args = Gst.init("PanicButtonStream-" + name, args);
		pipe = new Pipeline("pipeline");
		
		// set up Source
		ElementFactory.make("videotestsrc", "source");
		final Element videosrc = ElementFactory.make("v4l2src", "source");
		videosrc.set("device", deviceName);
		final Element videofilter = ElementFactory.make("capsfilter", "flt");
		videofilter.setCaps(Caps
				.fromString("video/x-raw-yuv, width=640, height=480"));
		
		// set up encoding
		final Element theoraenc = ElementFactory.make("theoraenc", "theoraenc");
		theoraenc.set("bitrate", 150);
		
		// set up stream
		final Element udpsink = ElementFactory.make("udpsink", "udpsink");
		final Element fileSink = ElementFactory.make("filesink", "filesink");	
		fileSink.set("location", "/home/paul/test.foo");
		udpsink.set("port", port);
		udpsink.set("host", hostname);
		

		// Start the pipeline processing
		logger.info("Streaming to " + hostname + " : " + port);
		pipe.addMany(videosrc, videofilter, theoraenc, udpsink);
		Element.linkMany(videosrc, videofilter, theoraenc, udpsink);
		pipe.setState(State.PLAYING);
	}

	/**
	 * Gets the my name.
	 * 
	 * @return the my name
	 */
	public String getMyName() {
		return name;
	}

	/**
	 * Sets the my name.
	 * 
	 * @param myName
	 *            the new my name
	 */
	public void setMyName(String myName) {
		this.name = myName;
	}

	/**
	 * Gets the my storage server.
	 * 
	 * @return the my storage server
	 */
	public CapturerStorageServer getMyStorageServer() {
		return storageServer;
	}

	/**
	 * Sets the my storage server.
	 * 
	 * @param myStorageServer
	 *            the new my storage server
	 */
	public void setMyStorageServer(CapturerStorageServer myStorageServer) {
		this.storageServer = myStorageServer;
	}

}
