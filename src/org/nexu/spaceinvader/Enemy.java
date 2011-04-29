package org.nexu.spaceinvader;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Enemy implements Element {


	private int posX;
	private int posY;
	private int speedX;
	private int speedY;
	
	private Bitmap enemyBitmap;
	
	public Enemy(Bitmap image, int posX, int posY) {
		Random rand = new Random();
		this.posX = posX;
		this.posY = posY;
		this.enemyBitmap = image;
		speedX = rand.nextInt(7) - 3;
		speedY = rand.nextInt(7) - 3;
	}

	@Override
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(enemyBitmap, posX, posY, null);

	}

	@Override
	public void animate(long elipsed) {
		posX += speedX * (elipsed / 20f);
		posY += speedY * (elipsed / 20f);
		checkBorders();

	}

	@Override
	public void changePosition(int x, int y) {
		this.posX = x - enemyBitmap.getWidth() / 2;
		this.posY = y- enemyBitmap.getHeight() / 2;

	}
	
	private void checkBorders() {
		if (posX <= 0) {
			speedX = -speedX;
			posX = 0;
		} else if (posX + enemyBitmap.getWidth() >= SpacePanel.width) {
			speedX = -speedX;
			posX = (int) (SpacePanel.width - enemyBitmap.getWidth());
		}
		if (posY <= 0) {
			posY = 0;
			speedY = -speedY;
		}
		if (posY + enemyBitmap.getHeight() >= SpacePanel.height /2) {
			speedY = -speedY;
			posY = (int) (SpacePanel.height /2 - enemyBitmap.getHeight());
		}
	}
	
	public BitmapDrawable resizeImage(float newWidth, float newHeight) {
		
			int width = enemyBitmap.getWidth();
	        int height = enemyBitmap.getHeight();
	    
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        
	        
			Matrix matrix = new Matrix();
	        // resize the bit map
	        matrix.postScale(scaleWidth, scaleHeight);
	        // rotate the Bitmap
	        matrix.postRotate(45);

	        // recreate the new Bitmap
	        Bitmap resizedBitmap = Bitmap.createBitmap(enemyBitmap, 0, 0, 
	                          width, height, matrix, true); 
	    
	        // make a Drawable from Bitmap to allow to set the BitMap 
	        // to the ImageView, ImageButton or what ever
	        return new BitmapDrawable(resizedBitmap);
	        
	}

}
