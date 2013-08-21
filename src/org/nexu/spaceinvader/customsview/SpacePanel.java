package org.nexu.spaceinvader.customsview;

import java.util.ArrayList;
import java.util.List;

import org.nexu.spaceinvader.activities.GameContext;
import org.nexu.spaceinvader.behaviours.Movable;
import org.nexu.spaceinvader.behaviours.controller.CollisionController;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpacePanel extends SurfaceView implements SurfaceHolder.Callback {

	private ViewThread thread;
	private List<Shape> elements = new ArrayList<Shape>();
	private GameContext mGameContext;
	private Paint wPaint;

    private CollisionController collisionController;

	public float width;
	public float height;

	public SpacePanel(Context context) {
		super(context);
		mGameContext = GameContext.loadContext(getContext(), this);
		
		getHolder().addCallback(this);
		thread = new ViewThread(this);
		wPaint = new Paint();
		wPaint.setColor(Color.WHITE);
	}

    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    public void addShape(Shape shape) {
		synchronized (elements) {
			elements.add(shape);
		}
	}
	
	public void removeShape(Shape shape) {
		synchronized (elements) {
			elements.remove(shape);
		}
	}

	public void doDraw(long elapsed, Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		List<Shape> elementIndex = new ArrayList<Shape>();
		synchronized (elements) {
			for (Shape element : elements) {
				if(element.isOutOfScreen(width, height) || element.isHidden()) {
					elementIndex.add(element);
				}
				element.doDraw(canvas);
			}
		}

        collisionController.checkForCollision();

		for(Shape cleanUp : elementIndex) {
			elements.remove(cleanUp);
		}
		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: "
				+ elements.size(), 10, 10, wPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
		
		mGameContext.getEventBus().publish(new ScreenDimensionChanged(height, width));
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!thread.isAlive()) {
			thread = new ViewThread(this);
			thread.setRun(true);
			thread.start();
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (thread.isAlive()) {
			thread.setRun(false);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGameContext.getGuiEventManager().onTouch(this, event);
	}
	

	public void animate(long elapsedTime) {
		synchronized (elements) {
			for (Shape element : elements) {
				if(element instanceof Movable) {
					Movable movable = (Movable) element;
					movable.animate(elapsedTime);					
				}
			}
		}
	}

}

