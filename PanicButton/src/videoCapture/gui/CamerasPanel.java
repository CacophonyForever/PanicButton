package videoCapture.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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

public class CamerasPanel extends JPanel{
	
	private VideoComponent myCameraPreview;
	private JCheckBox mySelectCamera;
	private JList myCameraList;
	private DefaultListModel myCameraListModel;
	private CameraSettingsActionListener myCameraSettingsActionListener;
	private Pipeline pipe;
	Element videofilter;
	Element videosrc;
	private int i;
	
	public CamerasPanel()
	{
		i=1;
		this.setLayout(new GridLayout(1,2));
		myCameraListModel = new DefaultListModel();
		myCameraList = new JList(myCameraListModel);
		this.add(myCameraList,0,0);
		JPanel displayGrid = new JPanel(new GridLayout(2,1));
		String[] args = new String[0];
		Gst.init("SwingVideoTest", args); 
		myCameraPreview = new VideoComponent();
		mySelectCamera = new JCheckBox("Use This Camera");
		displayGrid.add(mySelectCamera,0,0);
		displayGrid.add(myCameraPreview,1,0);
		this.add(displayGrid,0,1);
		myCameraList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		myCameraList.addListSelectionListener(myCameraSettingsActionListener);
	}
	
	public void addListeners(CapturerController controller)
	{
		myCameraSettingsActionListener = new CameraSettingsActionListener(controller);
		myCameraList.addListSelectionListener(myCameraSettingsActionListener);
		mySelectCamera.addActionListener(myCameraSettingsActionListener);
	}
	
	public void populateDeviceList(VideoCaptureDevice[] devices)
	{
		for (VideoCaptureDevice device : devices)
		{
			myCameraListModel.add(0,device);
		}
	}
	
	public void setSelectedValue(boolean value)
	{
		mySelectCamera.setSelected(value);
	}
	
	public boolean getSelectedValue()
	{
		return mySelectCamera.isSelected();
	}
	
	public VideoCaptureDevice getSelectedCamera()
	{
		return (VideoCaptureDevice)myCameraList.getSelectedValue();
	}
	
	public void displayCamPreview(String device)
	{
		if (pipe != null)
		{
			String[] args = new String[0];
			System.out.println("AGH");
			Element videosink = myCameraPreview.getElement();
			videofilter.unlink(videosink);
			videosink.stop();
			pipe.remove(videosink);
			pipe.setState(State.NULL);
			pipe.dispose();
			videofilter.setState(State.NULL);
			videofilter.dispose();
			videosrc.setState(State.NULL);
			videosrc.dispose();
			
			pipe = new Pipeline("pipeline");
	ElementFactory.make("videotestsrc", "source"); 
	        // This gives black window with VideoComponent 
	        videosrc = ElementFactory.make("v4l2src", 
	"source"); 
	        
	        videosrc.set("device",device);

	        videofilter = ElementFactory.make("capsfilter", 
	"flt"); 
	        videofilter.setCaps(Caps.fromString("video/x-raw-yuv, width=640, height=480")); 
	                // This gives only black window 	                
	                // This gives 2nd window with stream from webcam 
	                // Element videosink = 
					System.out.println("AAHH");
	                pipe.addMany(videosrc, videofilter, videosink);
	                System.out.println("BUUHH");
	                Element.linkMany(videosrc, videofilter, videosink);

	                // Start the pipeline processing 
	                pipe.setState(State.PLAYING);
	                videosink.setState(State.PLAYING);
		}
		else
		{
			System.out.println("OOOOoo");
	       pipe = new Pipeline("pipeline");
	ElementFactory.make("videotestsrc", "source"); 
	        // This gives black window with VideoComponent 
	        videosrc = ElementFactory.make("v4l2src", 
	"source"); 
	        
	        videosrc.set("device",device);

	        videofilter = ElementFactory.make("capsfilter", 
	"flt"); 
	        videofilter.setCaps(Caps.fromString("video/x-raw-yuv, width=640, height=480")); 
	                // This gives only black window 
	                Element videosink = myCameraPreview.getElement();
	                // This gives 2nd window with stream from webcam 
	                // Element videosink = 
	ElementFactory.make("xvimagesink", "sink"); 
	                pipe.addMany(videosrc, videofilter, videosink); 
	                Element.linkMany(videosrc, videofilter, videosink);

	                // Start the pipeline processing 
	                pipe.setState(State.PLAYING);
		}      
		
		System.out.println("The state is " + pipe.getState());
		if (pipe.getState().equals(State.PLAYING))
		{
			myCameraPreview.setVisible(true);
		}
		else
		{
			myCameraPreview.setVisible(false);
		}
		myCameraPreview.repaint();
	}
	
	public void dispose()
	{
		if (pipe != null)
		{
		pipe.setState(State.NULL);
		pipe.dispose();
		videofilter.setState(State.NULL);
		videofilter.dispose();
		videosrc.setState(State.NULL);
		videosrc.dispose();
		}
	}
	
	public ListModel getCameraListModel()
	{
		return myCameraListModel;
	}
	


}

