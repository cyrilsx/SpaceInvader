package org.nexu.spaceinvader.elements.factory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.nexu.spaceinvader.activities.LifeCycle;
import org.nexu.spaceinvader.behaviours.controller.ShipController;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.events.GUIEventManager;
import org.nexu.spaceinvader.events.gui.FireButton;

import android.util.Log;

public class GuiFactory implements LifeCycle{
	
	private static final String TAG = "FACTORY";
	
	private ShipController mShipController;
	private GUIEventManager mGuiEventManager;
	private Map<GuiConfiguration, GuiBuilder> mGuiBuilder = new EnumMap<GuiConfiguration, GuiBuilder>(GuiConfiguration.class);
	
	public enum GuiConfiguration {
		IN_GAME,
	}
	
	private interface GuiBuilder {
		List<Shape> getStaticGuiShape(int screenWitdh, int screenHeight);
	}
	
	public GuiFactory(ShipController shipController, GUIEventManager guiEventManager) {
		this.mShipController = shipController;
		this.mGuiEventManager = guiEventManager;
		mGuiBuilder.put(GuiConfiguration.IN_GAME, new InGameGuiBuilder());
	}


	public List<Shape> getStaticGuiShape(GuiConfiguration config, int screenWitdh, int screenHeight) {
		return mGuiBuilder.get(config).getStaticGuiShape(screenWitdh, screenHeight);
	}
	
	@Override
	public List<Shape> onLoad(int sWitdh, int sHeight) {
		return getStaticGuiShape(GuiConfiguration.IN_GAME, sWitdh, sHeight);
	}
	
	private final class InGameGuiBuilder implements  GuiBuilder {

		
		@Override
		public List<Shape> getStaticGuiShape(int screenWitdh, int screenHeight) {
			List<Shape> shapes = new ArrayList<Shape>(1);
			Log.d(TAG, "Building fire button on screen frame (" + (screenWitdh - 105) + "," + (screenHeight - 105) +")");
			FireButton fireButton = new FireButton(screenWitdh - 105, screenHeight - 105, 100);
			mGuiEventManager.subscribeShape(fireButton, mShipController, 10);
			shapes.add(fireButton);
			return shapes;
		}
		
	}


	

}
