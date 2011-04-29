package org.nexu.spaceinvader.elements;

import java.util.Random;

import org.nexu.spaceinvader.customsview.SpacePanel;

import android.graphics.Bitmap;

public class Enemy extends Element{

	
	public Enemy(Bitmap image, int posX, int posY) {
		super(posX,posY,image);
		Random rand = new Random();
		speedX = rand.nextInt(7) - 3;
		speedY = rand.nextInt(7) - 3;
	}

	@Override
	public void animate(long elipsed) {
		super.animate(elipsed);
		checkBorders();
	}

	@Override
	protected void checkBorders() {
		if (posX <= 0) {
			speedX = -speedX;
			posX = 0;
		} else if (posX + bitmap.getWidth() >= SpacePanel.width) {
			speedX = -speedX;
			posX = (int) (SpacePanel.width - bitmap.getWidth());
		}
		if (posY <= 0) {
			posY = 0;
			speedY = -speedY;
		}
		if (posY + bitmap.getHeight() >= SpacePanel.height /2) {
			speedY = -speedY;
			posY = (int) (SpacePanel.height /2 - bitmap.getHeight());
		}
	}
	

}
