package org.nexu.spaceinvader.events;

public interface DeviceOrientationListener {
	
	/**
	 * Values in degrees.
	 * @param azimuth
	 * @param pitch
	 * @param roll
	 */
	void onOrientationChanged(float azimuth, float pitch, float roll);

}
