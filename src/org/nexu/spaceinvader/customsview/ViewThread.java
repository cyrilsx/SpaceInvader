package org.nexu.spaceinvader.customsview;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class ViewThread extends Thread {

	private SpacePanel panel;
	private SurfaceHolder holder;
	private Boolean run;
	private long startTime;
	private long elapsed;

	public ViewThread(SpacePanel panel) {
		super();
		this.panel = panel;
		this.holder = panel.getHolder();
	}

	public Boolean getRun() {
		return run;
	}

	public void setRun(Boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		startTime = System.currentTimeMillis();
		while(run) {
			canvas = holder.lockCanvas();
			if(canvas != null) {
				panel.animate(elapsed);
				panel.doDraw(elapsed, canvas);
				elapsed = System.currentTimeMillis() - startTime;
				holder.unlockCanvasAndPost(canvas);
			}
			startTime = System.currentTimeMillis();
		}
	}
	
	
	
	
	
	
}
