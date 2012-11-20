package videoStorage;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.GstException;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

public class CaptureListener extends Thread {
	
	private int myInPort;
	private String mySaveFile;
	private boolean ready;
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void run()
	{
		init();
	}

	public CaptureListener(int port, String saveFile)
	{
		ready=false;
		myInPort=port;
		mySaveFile=saveFile;
	}
	
	private boolean init()
	{
	  try {
		String args[] = new String[0];
		  args = Gst.init("SwingVideoTest", args);
		  Pipeline pipe = new Pipeline("pipeline");
		  ElementFactory.make("videotestsrc", "source");
		  final Element udpsrc = ElementFactory.make("udpsrc", "source");
		  udpsrc.set("port", myInPort);      
		  final Element theoraDecoder = ElementFactory.make("theoradec", "theoradec");
		  final Element queue1 = ElementFactory.make("queue", "queue");
		  final Element videoRate = ElementFactory.make("videorate", "videorate");
		  final Element theoraEncoder = ElementFactory.make("theoraenc", "theoraenc");
		  final Element queue2 = ElementFactory.make("queue", "queue2");
		  final Element oggMux = ElementFactory.make("oggmux", "oggmux");
		  final Element fileSink = ElementFactory.make("filesink", "filesink");
		  fileSink.set("location","/home/paul/vid" + System.currentTimeMillis() + ".ogg");
		  System.out.println("Saving to " + mySaveFile);
		  pipe.addMany(udpsrc,theoraDecoder, queue1,videoRate,theoraEncoder,queue2,oggMux,fileSink);
		  Element.linkMany(udpsrc,theoraDecoder, queue1,videoRate,theoraEncoder,queue2,oggMux,fileSink);
	      ready=true;
		  pipe.setState(org.gstreamer.State.PLAYING);
		  System.out.println(pipe.getState());
	} catch (GstException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      return false;
	}
	
}
