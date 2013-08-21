package org.nexu.spaceinvader.activities;

import java.util.ArrayList;
import java.util.List;

import org.nexu.spaceinvader.behaviours.controller.*;
import org.nexu.spaceinvader.customsview.SpacePanel;
import org.nexu.spaceinvader.data.EnemyDao;
import org.nexu.spaceinvader.data.LevelDao;
import org.nexu.spaceinvader.data.ShapeDao;
import org.nexu.spaceinvader.data.ShipDao;
import org.nexu.spaceinvader.data.SpaceSQLiteOpenHelper;
import org.nexu.spaceinvader.data.WeaponDao;
import org.nexu.spaceinvader.elements.factory.GuiFactory;
import org.nexu.spaceinvader.elements.factory.ShipFactory;
import org.nexu.spaceinvader.events.EventBus;
import org.nexu.spaceinvader.events.GUIEventManager;
import org.nexu.spaceinvader.events.PhoneOrientation;

import android.content.Context;
import android.util.Log;

public class GameContext {
	
	private final static String TAG = "CONTEXT";
	
//	private final UnModifiableVariable<SurfaceView> rootSurfaceView = new UnModifiableVariable<SurfaceView>();
	
	private final Context mAndroidContext;
	private static GameContext instance;
	private GUIEventManager mGuiEventManager;
	private EventBus mEventBus;
	
	private final EnemyDao mEnemyDao;
	private final LevelDao mLevelDao;
	private final ShapeDao mShapeDao;
	private final ShipDao mShipDao;
	private final WeaponDao mWeaponDao;
	
	private final PhoneOrientation mPhoneOrientation;

    private final CollisionController mCollisionController;
	
	private final ShipFactory mSpaceShipFactory; 
	
	private final SpacePanel mScreen;
	
	private GameContext(Context context, SpacePanel screen) {
		mAndroidContext = context;
		mScreen = screen;

        InFieldEnemyRegistry inFieldEnemyRegistry = new InFieldEnemyRegistry();

        mCollisionController = new CollisionController(inFieldEnemyRegistry);
		mScreen.setCollisionController(mCollisionController);
		// Load Event
		mGuiEventManager = new GUIEventManager();
		mEventBus = new EventBus();
	
		mPhoneOrientation = new PhoneOrientation(mAndroidContext);
		
		
		// Load Databases
		SpaceSQLiteOpenHelper spaceSQLiteOpenHelper = new SpaceSQLiteOpenHelper(mAndroidContext);
		mLevelDao = new LevelDao(spaceSQLiteOpenHelper, mAndroidContext);
		mShapeDao = new ShapeDao(spaceSQLiteOpenHelper, mAndroidContext);
		
		mWeaponDao = new WeaponDao(spaceSQLiteOpenHelper, mShapeDao);
		
		mEnemyDao = new EnemyDao(spaceSQLiteOpenHelper, mShapeDao, mLevelDao);
		mShipDao = new ShipDao(spaceSQLiteOpenHelper, mShapeDao);
		
		// Create factory
		mSpaceShipFactory = new ShipFactory(mShipDao);


		
		// Create Controller
		WeaponController weaponController = new WeaponController(mScreen, mWeaponDao, inFieldEnemyRegistry);
		ShipController shipController = new ShipController(mSpaceShipFactory, weaponController, mPhoneOrientation, inFieldEnemyRegistry);
		LevelController levelController = new LevelController(mLevelDao);
		EnemyController enemyController = new EnemyController(mEnemyDao, levelController, mCollisionController);


		
		// Gui Factory
		GuiFactory guiFactory = new GuiFactory(shipController, mGuiEventManager);
		
		
		// Screen Loader.
		new ScreenLoader(newArrayList(shipController, enemyController, guiFactory), screen, mEventBus);
	
	}
	
	private final <T> List<T> newArrayList(T... argv) {
		List<T> result = new ArrayList<T>();
		for(T arg : argv) {
			result.add(arg);
		}
		return result;
	}
	
	public static GameContext loadContext(Context context, SpacePanel surfaceView) {
		if(instance != null) {
			Log.w(TAG, "try to load context, while context is already loaded");
			return instance;
		}
		instance = new GameContext(context, surfaceView);
		return instance;
	}
	
	public static GameContext getInstance() {
		if(instance == null) {
			throw new IllegalStateException("Call load context... before to use context");
		}
		return instance;
	}
		
	public Context getActivityContext() {
		return mAndroidContext;
	}
	
	public EventBus getEventBus() {
		return mEventBus;
	}
	
	public GUIEventManager getGuiEventManager() {
		return mGuiEventManager;
	}
	


}
