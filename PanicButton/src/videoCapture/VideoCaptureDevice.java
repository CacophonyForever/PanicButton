package videoCapture;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoCaptureDevice.
 */
public class VideoCaptureDevice implements Serializable {


    private String deviceLocation;
    private boolean enabled;

    /**
     * Gets the device location.
     * 
     * @return the device location
     */
    public String getDeviceLocation() {
	return deviceLocation;
    }

    /**
     * Sets the device location.
     * 
     * @param deviceLocation
     *            the new device location
     */
    public void setDeviceLocation(String deviceLocation) {
	this.deviceLocation = deviceLocation;
    }

    /**
     * Checks if is enabled.
     * 
     * @return true, if is enabled
     */
    public boolean isEnabled() {
	return enabled;
    }

    /**
     * Sets the enabled.
     * 
     * @param enabled
     *            the new enabled
     */
    public void setEnabled(boolean enabled) {
	this.enabled = enabled;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return deviceLocation;
    }

    /**
     * Instantiates a new video capture device.
     * 
     * @param dev
     *            the dev
     */
    public VideoCaptureDevice(String dev) {
	deviceLocation = dev;
    }

    /**
     * Instantiates a new video capture device.
     * 
     * @param dev
     *            the dev
     * @param en
     *            the en
     */
    public VideoCaptureDevice(String dev, boolean en) {
	deviceLocation = dev;
	enabled = en;
    }

}
