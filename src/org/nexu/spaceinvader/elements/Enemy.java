package org.nexu.spaceinvader.elements;

import java.util.Random;

import android.graphics.Bitmap;
import android.util.Log;
import org.nexu.spaceinvader.data.domain.ShapeDescriptor;

public class Enemy extends Element{

	private static final String TAG = "ENEMY";
	
	
	public Enemy(Bitmap image, int posX, int posY, int screenWitdh, int screenHeight, ShapeDescriptor.TypeShape typeShape, int health) {
		super(posX,posY,image, screenWitdh, screenHeight, typeShape, health);
		Random rand = new Random();
		speedX = rand.nextInt(7);
		speedY = rand.nextInt(7);
		Log.d(TAG, "Create a new enemy: " +  this);
	}

	@Override
	public void animate(long elipsed) {
		super.animate(elipsed);
		checkBorders();
	}

	@Override
	protected void checkBorders() {
		if (posX <= screenWitdh / 2) {
			speedX = -speedX;
			posX = screenWitdh / 2;
		} else if (posX + bitmap.getWidth() >= screenWitdh) {
			speedX = -speedX;
			posX = (int) (screenWitdh - bitmap.getWidth());
		}
		if (posY <= 0) {
			posY = 0;
			speedY = -speedY;
		}
		if (posY + bitmap.getHeight() >= screenHeight) {
			speedY = -speedY;
			posY = (int) (screenHeight - bitmap.getHeight());
		}
	}
	
	@Override
	public String toString() {
		return "Enemy[(x,y) = (" + posX + "," + posY +")" + " -- (sX, sY(" + speedX + "," + speedY +")";  
	}


    @Override
    public void onCollision(Element srcObject) {
        decreaseLife(srcObject.getCollisionDamage());
    }
}
