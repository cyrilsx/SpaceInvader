package org.nexu.spaceinvader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SpacePanel extends SurfaceView implements SurfaceHolder.Callback {

	// private Bitmap space;
	private ViewThread thread;
	private List<Movable> elements = new ArrayList<Movable>();
	private Paint wPaint;
	private WorldBuilder world;

	public static float width;
	public static float height;

	public SpacePanel(Context context) {
		super(context);
		world = WorldBuilder.getInstance();
		world.buildMainShip(BitmapFactory.decodeResource(getResources(),
				R.drawable.alienblaster), this.getWidth() / 2,
				this.getHeight() - 10);
		world.buildEnemies(BitmapFactory.decodeResource(getResources(),
				R.drawable.thermaldetonator), 5);
		getHolder().addCallback(this);
		thread = new ViewThread(this);
		// setFocusable(true);
		wPaint = new Paint();
		wPaint.setColor(Color.WHITE);
	}

	public void doDraw(long elapsed, Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		elements = world.getAllWorldElement();
		synchronized (elements) {
			for (Movable element : elements) {
				element.doDraw(canvas);
			}
		}
		canvas.drawText("FPS: " + Math.round(1000f / elapsed) + " Elements: "
				+ world.getWorldSize(), 10, 10, wPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;

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
		world.getShip().changePosition((int) event.getX(), (int) event.getY());
		return super.onTouchEvent(event);
	}

	public void animate(long elapsedTime) {
		elements = world.getAllWorldElement();
		synchronized (elements) {
			for (Movable element : elements) {
				element.animate(elapsedTime);
			}
		}
	}

}
