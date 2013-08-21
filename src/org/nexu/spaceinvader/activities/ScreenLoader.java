package org.nexu.spaceinvader.activities;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.nexu.spaceinvader.customsview.SpacePanel;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.events.EventBus;
import org.nexu.spaceinvader.events.EventBus.Subcriber;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

import android.util.Log;

public class ScreenLoader implements Subcriber<ScreenDimensionChanged> {
	
	private static final String TAG = "SCREEN";
	
	private long mScreeWidth = 0;
	private long mScreeHeight = 0;
	
	private AtomicBoolean loadRequired = new AtomicBoolean(true);
//	private AtomicBoolean started = new AtomicBoolean(false);
	
	private SpacePanel mScreen;
	private EventBus mEventBus;
	
	private List<LifeCycle> mLifeController;
	
	public ScreenLoader(List<LifeCycle> lifeCycleController,SpacePanel screen, EventBus eventBus) {
		mScreen = screen;
		mEventBus = eventBus;
		mEventBus.subscribe(ScreenDimensionChanged.class, this);
		mLifeController = lifeCycleController;
	}
	
	private void load() {
		
		for(LifeCycle lifeCycle : mLifeController) {
			List<Shape> toLoad = lifeCycle.onLoad((int)mScreeWidth, (int)mScreeHeight);
			for(Shape element : toLoad) {
				Log.i(TAG, "Loading element " + element);
				mScreen.addShape(element);
			}
		}
		
	}

	@Override
	public void onEvent(ScreenDimensionChanged event) {
		Log.d(TAG, "onDimensionChanged [" + event.getWitdh() + "," + event.getHeight() +"]");
		if(event.getWitdh() == 0 ||  event.getHeight() == 0) {
			return;
		}

		mScreeWidth = event.getWitdh();
		mScreeHeight = event.getHeight();

		if(loadRequired.getAndSet(false)) {				
			load();
		}
		
	}

}
