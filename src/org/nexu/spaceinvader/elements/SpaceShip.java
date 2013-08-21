package org.nexu.spaceinvader.elements;

import java.util.IdentityHashMap;
import java.util.Map;

import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.events.DeviceOrientationListener;
import org.nexu.spaceinvader.events.GUIEventManager;
import org.nexu.spaceinvader.events.GUIEventManager.UserEvent;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

public class SpaceShip extends Element implements DeviceOrientationListener, UserEvent {

	private final Map<Class<?>, UserEvent> eventsHandler = new IdentityHashMap<Class<?>, GUIEventManager.UserEvent>();
	
	public SpaceShip(int posX, int posY, Bitmap bitmap, int screenWitdh, int screenHeight, ShapeDescriptor.TypeShape typeShape, int health) {
		super(posX, posY, bitmap, screenWitdh, screenHeight, typeShape, health);
		rotate(-90);
	}

	@Override
	public void onOrientationChanged(float azimuth, float pitch, float roll) {
		speedY = (int) -roll;
	}

	
	@Override
	public void animate(long elipsed) {
		super.animate(elipsed);
		checkBorders();
	}
	
	@Override
	public void onTouch(Class<?> source, View v) {
		UserEvent userEvent = eventsHandler.get(source);
		if(userEvent == null) {
			Log.w(getTag(), "Space ship received an unhandler user event from " + source);
			return;
		}
		userEvent.onTouch(source, v);
	}
	
	
	@Override
	public boolean isOutOfScreen(float screenWidth, float screenHeight) {
		return false;
	}

	@Override
	protected void checkBorders() {
		if(posY < -mHeight/2) {
			posY = -mHeight/2;
		}
		if(posY > screenHeight - mHeight/2) {
			posY = (int) screenHeight - mHeight/2;
		}
	}
	
	@Override
	public String toString() {
		return new StringBuilder("SpaceShip [{").append(this.posX).append(",").append(this.posY).append("}").toString();
	}


    @Override
    public void onCollision(Element srcObject) {
         decreaseLife(srcObject.getCollisionDamage());
    }
}
