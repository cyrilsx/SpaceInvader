package org.nexu.spaceinvader.elements.factory;

import java.util.HashMap;
import java.util.Map;

import org.nexu.spaceinvader.data.ShipDao;
import org.nexu.spaceinvader.data.domain.ShipDescriptor;
import org.nexu.spaceinvader.elements.SpaceShip;

public class ShipFactory {
	
	private final ShipDao mShipDao;
	private final Map<String, SpaceShip> mInGameShip = new HashMap<String, SpaceShip>();
	
	public ShipFactory(ShipDao shipDao) {
		this.mShipDao = shipDao;
	}
	
	
	public SpaceShip getSpaceShip(String spaceShipName, int initialPosX, int initialPosY, int screenWitdh, int screenHeight) {
		ShipDescriptor shipDescriptor = mShipDao.getShipById(spaceShipName);
		SpaceShip spaceShip = new SpaceShip(initialPosX, initialPosY, shipDescriptor.getShapeDescriptor().getDrawable(),screenWitdh, screenHeight, shipDescriptor.getShapeDescriptor().getTypeShape(), shipDescriptor.getHealth());
		mInGameShip.put(spaceShipName, spaceShip);
		return spaceShip;
	}
	
	public ShipDescriptor getDescriptor(String spaceShipName) {
		if(spaceShipName == null)
			spaceShipName = mShipDao.getDefaultSpaceShipId();
		return mShipDao.getShipById(spaceShipName);
	}
	
	
	public SpaceShip getSpaceShip(int initialPosX, int initialPosY, int screenWitdh, int screenHeight) {
		return getSpaceShip(mShipDao.getDefaultSpaceShipId(), initialPosX, initialPosY, screenWitdh, screenHeight);
	}
	
	
	public void cleanUpShip(String spaceShipName) {
		mInGameShip.remove(spaceShipName);
	}

}
