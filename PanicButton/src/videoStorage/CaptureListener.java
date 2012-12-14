package videoStorage;

import java.util.logging.Logger;

import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.GstException;
import org.gstreamer.Pipeline;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving capture events. The class that is
 * interested in processing a capture event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addCaptureListener<code> method. When
 * the capture event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see CaptureEvent
 */
public class CaptureListener extends Thread {

	private static final Logger logger = Logger.getLogger("log");

	private int incomingStreamPort;
	private int incomingAudioStreamPort;
	private String saveFile;
	private boolean ready;

	/**
	 * Checks if is ready.
	 * 
	 * @return true, if is ready
	 */
	public boolean isReady() {
		return ready;
	}

	/**
	 * Sets the ready.
	 * 
	 * @param ready
	 *            the new ready
	 */
	public void setReady(boolean ready) {
		this.ready = ready;
	}

	@Override
	public void run() {
		init();
	}

	/**
	 * Instantiates a new capture listener.
	 * 
	 * @param port
	 *            the port
	 * @param saveFile
	 *            the save file
	 */
	public CaptureListener(int port, String saveFile) {
		ready = false;
		incomingStreamPort = port;
		incomingAudioStreamPort = port + 1;
		this.saveFile = saveFile;
	}

	/**
	 * Inititalizes the stream
	 * 
	 * @return true, if successful
	 */
	private boolean init() {
	
		try {
			// Initialize Gstreamer
			String args[] = new String[0];
			args = Gst.init("SwingVideoTest", args);
			Pipeline pipe = new Pipeline("pipeline");

			// Set up source
			ElementFactory.make("videotestsrc", "source");
			final Element udpsrc = ElementFactory.make("udpsrc", "source");
			udpsrc.set("port", incomingStreamPort);

			// Set up decoder/encoder
			final Element theoraDecoder = ElementFactory.make("theoradec",
					"theoradec");
			final Element queue1 = ElementFactory.make("queue", "queue");
			final Element videoRate = ElementFactory.make("videorate",
					"videorate");
			final Element theoraEncoder = ElementFactory.make("theoraenc",
					"theoraenc");
			final Element queue2 = ElementFactory.make("queue", "queue2");
			final Element oggMux = ElementFactory.make("oggmux", "oggmux");
			
			// Set up audio stuff
			final Element audioUdpSrc = ElementFactory.make("udpsrc", "audsource");
			audioUdpSrc.set("port", incomingAudioStreamPort);
			
			final Element vorbisDecoder = ElementFactory.make("vorbisdec",
					"vorbisdec");
			
			final Element audioQueue = ElementFactory.make("queue", "audioqueue");			
			
			final Element vorbisEnc = ElementFactory.make("vorbisenc",
					"vorbisenc");

			// set up save file
			final Element fileSink = ElementFactory
					.make("filesink", "filesink");
			fileSink.set("location",
					"/home/paul/vid" + System.currentTimeMillis() + ".ogg");
			logger.info("Listening on port(s) " + incomingStreamPort + " Saving to " + saveFile);

			// start pipeline
			pipe.addMany(udpsrc, theoraDecoder, queue1, videoRate,
					theoraEncoder, queue2, oggMux, fileSink);
			pipe.addMany(audioUdpSrc, vorbisDecoder, audioQueue, vorbisEnc);
			Element.linkMany(udpsrc, theoraDecoder, queue1, videoRate,
					theoraEncoder, queue2, oggMux, fileSink);			
			Element.linkMany(audioUdpSrc, vorbisDecoder, audioQueue, vorbisEnc,
					oggMux);
			
			ready = true;
			pipe.setState(org.gstreamer.State.PLAYING);
			logger.info("Stream is : " + pipe.getState());
			return true;
		} catch (GstException e) {
			logger.severe("Something bad happened. Can't save stream.");
		}

		return false;
	}

}
