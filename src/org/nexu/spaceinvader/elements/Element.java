package org.nexu.spaceinvader.elements;

import org.nexu.spaceinvader.activities.GameContext;
import org.nexu.spaceinvader.behaviours.CollisionListener;
import org.nexu.spaceinvader.behaviours.Movable;
import org.nexu.spaceinvader.data.domain.ShapeDescriptor;
import org.nexu.spaceinvader.domain.Point;
import org.nexu.spaceinvader.events.EventBus;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Element implements Movable, CollisionListener {
	
	private static final String TAG = "ELEMENT";

	
	protected int posX;
	protected int posY;
	protected int speedX;
	protected int speedY;
	
	protected int screenWitdh;
	protected int screenHeight; 
	
	protected int mWidth;
	protected int mHeight;

	protected Bitmap bitmap;
	
	protected float mRotation = 0;
	
	private Paint mPaint = new Paint();

    private int mLife;
	
	private EventBus mEventBus;
    private Point[] mCollisionBoxes;
    private final ShapeDescriptor.TypeShape mTypeShape;
	

	public Element(int posX, int posY, int speedX, int speedY, int width, int height, ShapeDescriptor.TypeShape typeShape) {
		this.posX = posX;
		this.posY = posY;
		this.speedX = speedX;
		this.speedY = speedY;
		this.mWidth = width;
		this.mHeight = height;
        this.mTypeShape = typeShape;
		this.mEventBus = GameContext.getInstance().getEventBus();
		this.mEventBus.subscribe(ScreenDimensionChanged.class, this);
        this.mLife = Integer.MAX_VALUE;
	}

	public Element(int posX, int posY, Bitmap bitmap, int screenWitdh, int screenHeight, ShapeDescriptor.TypeShape typeShape, int life) {
		this.posX = posX;
		this.posY = posY;
		this.bitmap = bitmap;
		
		this.screenWitdh = screenWitdh;
		this.screenHeight = screenHeight;

		this.mWidth = bitmap.getWidth();
		this.mHeight = bitmap.getHeight();

        this.mTypeShape = typeShape;
        this.mLife = life;
	}

	@Override
	public void doDraw(Canvas canvas) {
		canvas.save();
		canvas.translate(posX, posY);
		canvas.rotate(mRotation,mWidth/2,mHeight/2);
		canvas.drawBitmap(bitmap, 0, 0, mPaint);
		canvas.restore();
	}

	@Override
	public void animate(long elipsed) {
		posX += speedX * (elipsed / 20f);
		posY += speedY * (elipsed / 20f);


	}

    protected void decreaseLife(int damage) {
        mLife -= damage;
    }


	@Override
	public void changePosition(int x, int y) {
		this.posX = x - mWidth / 2;
		this.posY = y - mHeight / 2;
	}
	
	protected void rotate(float degree) {
		this.mRotation = degree;
	}

	protected void checkBorders() {
		if (posX <= screenWitdh / 2) {
			posX = screenWitdh / 2;
		} else if (posX + bitmap.getWidth() >= screenWitdh) {
			posX = (int) (screenWitdh - bitmap.getWidth());
		}
		if (posY <= 0) {
			posY = 0;
		}
		if (posY + bitmap.getHeight() >= screenHeight) {
			posY = (int) (screenHeight - bitmap.getHeight());
		}
	}

	
	@Override
	public void onEvent(ScreenDimensionChanged event) {
		this.screenHeight = event.getHeight();
		this.screenWitdh = event.getWitdh();	
	}
	

	protected final Paint getPaint() {
		return mPaint;
	}

	
	protected String getTag() {
		return TAG;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}

    public int getCollisionDamage() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Point[] getCollisionBoxes() {
        return mCollisionBoxes;
    }


    @Override
    public ShapeDescriptor.TypeShape getTypeShape() {
        return mTypeShape;
    }

    @Override
	public boolean isOutOfScreen(float screenWidth, float screenHeight) {
		return this.posX + this.mWidth < 0 || this.posX - this.mWidth > screenWidth || this.posY + this.mHeight < 0 || this.posY + this.mHeight > screenHeight;
	}

    @Override
    public boolean isHidden() {
        return mLife <= 0;
    }
}
