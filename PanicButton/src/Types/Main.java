package Types;

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
import org.gstreamer.swing.VideoComponent; 

public class Main { 
    private static Pipeline pipe; 
    
    public static void main(String[] args)
    { 
        args = Gst.init("SwingVideoTest", args); 
        pipe = new Pipeline("pipeline"); 
ElementFactory.make("videotestsrc", "source"); 
        // This gives black window with VideoComponent 
        final Element videosrc = ElementFactory.make("v4l2src", 
"source"); 
        final Element videofilter = ElementFactory.make("capsfilter", 
"flt"); 
        videofilter.setCaps(Caps.fromString("video/x-raw-yuv, width=640, height=480")); 
                VideoComponent videoComponent = new VideoComponent(); 
                // This gives only black window 
                Element videosink = videoComponent.getElement(); 
                // This gives 2nd window with stream from webcam 
                // Element videosink = 
ElementFactory.make("xvimagesink", "sink"); 
                pipe.addMany(videosrc, videofilter, videosink); 
                Element.linkMany(videosrc, videofilter, videosink); 
                JFrame frame = new JFrame("Swing Video Test"); 
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                frame.add(videoComponent, BorderLayout.CENTER); 
                videoComponent.setPreferredSize(new Dimension(640, 
480)); 
                frame.pack(); 
                frame.setVisible(true); 
                // Start the pipeline processing 
                pipe.setState(State.PLAYING);
    }
}