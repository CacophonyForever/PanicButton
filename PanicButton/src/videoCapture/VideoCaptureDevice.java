package videoCapture;

import java.io.Serializable;

public class VideoCaptureDevice  implements Serializable{

		private String deviceLocation;
		private boolean enabled;
		
		public String getDeviceLocation() {
			return deviceLocation;
		}
		public void setDeviceLocation(String deviceLocation) {
			this.deviceLocation = deviceLocation;
		}
		public boolean isEnabled() {
			return enabled;
		}
		
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		public String toString()
		{
			return deviceLocation;
		}
		
		public VideoCaptureDevice (String dev)
		{
			deviceLocation = dev;
		}
		
		public VideoCaptureDevice (String dev, boolean en)
		{
			deviceLocation=dev;
			enabled=en;
		}
		
		
}
