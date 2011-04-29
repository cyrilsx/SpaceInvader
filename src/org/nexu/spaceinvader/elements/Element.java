package org.nexu.spaceinvader.elements;

import org.nexu.spaceinvader.behaviours.Movable;
import org.nexu.spaceinvader.customsview.SpacePanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

public abstract class Element implements Movable {

	protected int posX;
	protected int posY;
	protected int speedX;
	protected int speedY;

	protected Bitmap bitmap;

	public Element() {
	}

	public Element(int posX, int posY, int speedX, int speedY, Bitmap bitmap) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.bitmap = bitmap;
	}

	public Element(int posX, int posY, Bitmap bitmap) {
		this.posX = posX;
		this.posY = posY;
		this.bitmap = bitmap;
	}

	@Override
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap, posX, posY, null);

	}

	@Override
	public void animate(long elipsed) {
		posX += speedX * (elipsed / 20f);
		posY += speedY * (elipsed / 20f);
	}

	@Override
	public void changePosition(int x, int y) {
		this.posX = x - bitmap.getWidth() / 2;
		this.posY = y - bitmap.getHeight() / 2;
	}

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
		if (posY + bitmap.getHeight() >= SpacePanel.height) {
			speedY = -speedY;
			posY = (int) (SpacePanel.height - bitmap.getHeight());
		}
	}

	/**
	 * 
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	protected BitmapDrawable resizeImage(float newWidth, float newHeight) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// rotate the Bitmap
		matrix.postRotate(45);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);

		// make a Drawable from Bitmap to allow to set the BitMap
		// to the ImageView, ImageButton or what ever
		return new BitmapDrawable(resizedBitmap);

	}

}
