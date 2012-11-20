package videoCapture;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.elements.FileSink;
import org.gstreamer.elements.RGBDataSink;

import Types.VideoStream;

public class VideoSource {
	
	   private static Pipeline pipe;
	   private static BufferedImage currentImage = null;
	   private static JLabel currentImageLabel = new JLabel();
	   private String myDeviceName;
	   public static int foo;
	   private String myName;
	   private CapturerStorageServer myStorageServer;
	   
	   public VideoSource (String name, String deviceName, CapturerStorageServer srv)
	   {
		   myName = name;
		   myDeviceName=deviceName;
		   myStorageServer = srv;
	   }
	   
	public void beginRecordingAndStreaming(String hostname, int port)
	{
		// Don't initialize recording interface until activated
		System.out.println("HOO");
		String args[] = new String[0];
		   args = Gst.init("PanicButtonStream-" + myName, args);
		      pipe = new Pipeline("pipeline");
		      ElementFactory.make("videotestsrc", "source");
		      final Element videosrc = ElementFactory.make("v4l2src", "source");
		      videosrc.set("device", myDeviceName);
		      final Element videofilter = ElementFactory.make("capsfilter", "flt");
		      videofilter.setCaps(Caps.fromString(
		         "video/x-raw-yuv, width=640, height=480"));
		      final Element theoraenc = ElementFactory.make("theoraenc", "theoraenc");
		      theoraenc.set("bitrate", 150);
		      final Element udpsink = ElementFactory.make("udpsink", "udpsink");
			  final Element fileSink = ElementFactory.make("filesink", "filesink");
			  fileSink.set("location","/home/paul/test.foo");
		      udpsink.set("port", port);
		      udpsink.set("host", hostname);
		      System.out.println("Streaming to " + hostname + " : " + port);

		      
		         pipe.addMany(videosrc, videofilter, theoraenc, udpsink);
		         Element.linkMany(videosrc, videofilter, theoraenc, udpsink);

		         // Start the pipeline processing
		         pipe.setState(State.PLAYING);
		      }



	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public CapturerStorageServer getMyStorageServer() {
		return myStorageServer;
	}

	public void setMyStorageServer(CapturerStorageServer myStorageServer) {
		this.myStorageServer = myStorageServer;
	}
	
	
	
	
}
