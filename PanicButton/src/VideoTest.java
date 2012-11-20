 
import java.awt.BorderLayout; 
import java.awt.Dimension; 
import javax.swing.JFrame; 
import javax.swing.SwingUtilities; 
import org.gstreamer.Caps; 
import org.gstreamer.Element; 
import org.gstreamer.ElementFactory; 
import org.gstreamer.Gst; 
import org.gstreamer.Pipeline; 
import org.gstreamer.State; 
import org.gstreamer.elements.RGBDataSink;
import org.gstreamer.swing.VideoComponent; 


public class VideoTest {
    private static Pipeline pipe;
  
    public VideoTest() {}

    public static void main(String[] args) {
        args = Gst.init("SimplePipeline", args);
        Pipeline pipe = new Pipeline("SimplePipeline");
        Element src = ElementFactory.make("fakesrc", "Source");
        Element sink = ElementFactory.make("fakesink", "Destination");
        pipe.addMany(src, sink);
        src.link(sink);
        pipe.setState(State.PLAYING);
        Gst.main();


    }
}