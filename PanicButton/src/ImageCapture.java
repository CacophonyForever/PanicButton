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
import org.gstreamer.elements.RGBDataSink;

/**
* Prototype of image capturing mechanism.
*
* @author neo
*/
public class ImageCapture {

   private static Pipeline pipe;
   private static BufferedImage currentImage = null;
   private static JLabel currentImageLabel = new JLabel();

   public static void main(String[] args) throws IOException {
   
      args = Gst.init("SwingVideoTest", args);
      pipe = new Pipeline("pipeline");
      ElementFactory.make("videotestsrc", "source");
      final Element videosrc = ElementFactory.make("v4l2src", "source");
      final Element videofilter = ElementFactory.make("capsfilter", "flt");
      videofilter.setCaps(Caps.fromString(
         "video/x-raw-yuv, width=640, height=480"));

      SwingUtilities.invokeLater(new Runnable() {

         public void run() {
            RGBDataSink.Listener imageCaptureListener =
               new RGBDataSink.Listener() {
                  @Override
                  public void rgbFrame(boolean isPreRollImage, int width,
                     int height, IntBuffer rgb) {
                     currentImage = getBufferedImage(width, height);
                     copyDataToImage(rgb, currentImage, width, height);

                     // one way to display image on JPanel
                     ImageIcon icon = new ImageIcon(currentImage);
                     currentImageLabel.setIcon(icon);
               }

               private BufferedImage getBufferedImage(int width, int height) {
                  BufferedImage bufferedImage =
                     new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                  bufferedImage.setAccelerationPriority(0.0f);
                  return bufferedImage;
               }

               private void copyDataToImage(
                  IntBuffer rgb, BufferedImage image, int width, int height) {
                  int[] pixels = ((DataBufferInt)image.getRaster().
                     getDataBuffer()).getData();
                  rgb.get(pixels, 0, width * height);
               }
            };

         final RGBDataSink videosink =
            new RGBDataSink("rgb", imageCaptureListener);

         pipe.addMany(videosrc, videofilter, videosink);
         Element.linkMany(videosrc, videofilter, videosink);

         final JButton captureButton = new JButton("Capture");
         captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
               String outputFileName = "output-"+(new Date().toString()+".png");
               try {
                  ImageIO.write(currentImage, "png", new File(outputFileName));
               } catch (IOException e) {
                  System.err.println("File write error");
               }
            }
         });

         // Now create a JFrame to display the video output
         JFrame frame = new JFrame("Swing Video Test");
         frame.setLayout(new GridLayout(2,1));
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         currentImageLabel.setPreferredSize(new Dimension(720, 576));
         frame.add(currentImageLabel);
         frame.add(captureButton);
         frame.pack();
         frame.setVisible(true);

         // Start the pipeline processing
         pipe.setState(State.PLAYING);
      }
   });
}
} 