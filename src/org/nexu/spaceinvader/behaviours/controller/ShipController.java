package org.nexu.spaceinvader.behaviours.controller;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.nexu.spaceinvader.activities.LifeCycle;
import org.nexu.spaceinvader.data.domain.ShipDescriptor;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.elements.SpaceShip;
import org.nexu.spaceinvader.elements.factory.ShipFactory;
import org.nexu.spaceinvader.events.GUIEventManager;
import org.nexu.spaceinvader.events.GUIEventManager.UserEvent;
import org.nexu.spaceinvader.events.PhoneOrientation;
import org.nexu.spaceinvader.events.gui.FireButton;

import android.util.Log;
import android.view.View;

public class ShipController implements UserEvent, LifeCycle {
	private static final String TAG = "CONTROLLER";

	private final Map<Class<?>, UserEvent> eventsHandler = new IdentityHashMap<Class<?>, GUIEventManager.UserEvent>();
	
	private SpaceShip mCurrentShip;
	private WeaponController mWeaponController;
    private final InFieldEnemyRegistry mInFieldEnemyRegistry;
	
	private ShipFactory mShipFactory;
	private int nbWeapon = 0;
	private String mCurrentWeapon;
	
	private final PhoneOrientation mPhoneOrientation;
	
	private String mNextShip;
	
	public ShipController(ShipFactory spaceShipFactory, WeaponController weaponController, PhoneOrientation phoneOrientation, InFieldEnemyRegistry inFieldEnemyRegistry) {
		mShipFactory = spaceShipFactory;
		eventsHandler.put(FireButton.class, new FireHandler());
		mWeaponController = weaponController;
		mPhoneOrientation = phoneOrientation;
		mCurrentWeapon = mWeaponController.getDefaultWeaponName();
        mInFieldEnemyRegistry = inFieldEnemyRegistry;
	}
	
	
	SpaceShip getCurrentSpaceShip() {
		return mCurrentShip;
	}
	
	@Override
	public List<Shape> onLoad(int sWitdh, int sHeight) {
		List<Shape> rShapes = new ArrayList<Shape>();
		ShipDescriptor shipDescriptor = mShipFactory.getDescriptor(mNextShip);
		
		mCurrentShip = mShipFactory.getSpaceShip(
				shipDescriptor.getShapeDescriptor().getDrawable().getWidth() / 2 , 
				sHeight /2 + shipDescriptor.getShapeDescriptor().getDrawable().getHeight() /2, sWitdh, sHeight);
		
		
		Log.d(TAG, "Subscribing ship to user hardware control");
		mPhoneOrientation.register(mCurrentShip);

        mInFieldEnemyRegistry.addTo(InFieldEnemyRegistry.Group.PLAYER, mCurrentShip);
		rShapes.add(mCurrentShip);
		return rShapes;
	}
	
	private final class FireHandler implements UserEvent {

		@Override
		public void onTouch(Class<?> source, View v) {
			mWeaponController.fire(getCurrentSpaceShip(), mCurrentWeapon);
		}
		
	}

	@Override
	public void onTouch(Class<?> source, View v) {
		if(mCurrentShip == null) {
			Log.d(TAG, "Space ship not treated event from " + source);
			return;
		}
		UserEvent userEvent = eventsHandler.get(source);
		if(userEvent == null) {
			Log.w(TAG, "Space ship received an unhandler user event from " + source);
			return;
		}
		userEvent.onTouch(source, v);
	}



	
}
