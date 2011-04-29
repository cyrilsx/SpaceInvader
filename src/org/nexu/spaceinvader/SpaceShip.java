package org.nexu.spaceinvader;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SpaceShip implements Element {

	private int posX;
	private int posY;
	private int speedX;
	private int speedY;

	private Bitmap spaceBitmap;

	public SpaceShip(int posX, int posY, Bitmap spaceBitmap) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.spaceBitmap = spaceBitmap;
	}

	@Override
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(spaceBitmap, posX, posY, null);
	}

	@Override
	public void animate(long elipsed) {
		posX += speedX * (elipsed / 20f);
		posY += speedY * (elipsed / 20f);
		checkBorders();

	}

	@Override
	public void changePosition(int x, int y) {
		this.posX = x - spaceBitmap.getWidth() / 2;
		this.posY = y- spaceBitmap.getHeight() / 2;
	}

	private void checkBorders() {
		if (posX <= 0) {
			speedX = -speedX;
			posX = 0;
		} else if (posX + spaceBitmap.getWidth() >= SpacePanel.width) {
			speedX = -speedX;
			posX = (int) (SpacePanel.width - spaceBitmap.getWidth());
		}
		if (posY <= 0) {
			posY = 0;
			speedY = -speedY;
		}
		if (posY + spaceBitmap.getHeight() >= SpacePanel.height) {
			speedY = -speedY;
			posY = (int) (SpacePanel.height - spaceBitmap.getHeight());
		}
	}

}
