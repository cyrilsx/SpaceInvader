package org.nexu.spaceinvader.events;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * TODO must be improve. Notify change only if necessary.
 * 
 *
 */
public class PhoneOrientation {
	
	private SensorManager mSensorManager;
	
	private float[] mAccelerometerValues;
	private float[] mMagneticFieldValues;
	
	private final Set<DeviceOrientationListener> mOrientationListener = new HashSet<DeviceOrientationListener>(2);
	
	public PhoneOrientation(Context context) {
		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor aSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor mfSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		
		
		mSensorManager.registerListener(mAccelerometerListener, aSensor, SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(mMagneticFieldListener, mfSensor, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void register(DeviceOrientationListener pListener) {
		mOrientationListener.add(pListener);
	}
	
	public void unregister(DeviceOrientationListener pListener) {
		mOrientationListener.remove(pListener);
	}
	

	
	private SensorEventListener mAccelerometerListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				mAccelerometerValues = event.values;
				notifyOrientationChanges();
			}
			
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	
	private SensorEventListener mMagneticFieldListener = new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				mMagneticFieldValues = event.values;
				notifyOrientationChanges();
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};
	
	private void notifyOrientationChanges() {
		if(mMagneticFieldValues == null || mAccelerometerValues == null) {
			return;
		}
		float[] values = getOrientation();
		for(DeviceOrientationListener listener : mOrientationListener) {
			listener.onOrientationChanged(values[0], values[1], values[2]);
		}
	}
	
	
	private float[] getOrientation() {
		float[] R = new float[9];
		float[] values = new float[3];
		SensorManager.getRotationMatrix(R, null, mAccelerometerValues, mMagneticFieldValues);
		SensorManager.getOrientation(R, values);
		// Convert radians to degrees
		values[0] = (float) Math.toDegrees(values[0]); // Azimut
		values[1] = (float) Math.toDegrees(values[1]); // Pitch
		values[2] = (float) Math.toDegrees(values[2]); // Roll
		return values;
	}
	

}
