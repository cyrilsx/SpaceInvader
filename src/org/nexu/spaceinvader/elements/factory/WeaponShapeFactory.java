package org.nexu.spaceinvader.elements.factory;

import java.util.HashMap;
import java.util.Map;

import org.nexu.spaceinvader.data.WeaponDao;
import org.nexu.spaceinvader.data.domain.WeaponDescriptor;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.elements.Laser;

public class WeaponShapeFactory implements ShapeBuilder {
	
	private static final String DEFAULT_WEAPON = "Gun";
	
	private final Map<String,ShapeBuilder> shapeBuilders;
	private WeaponDao mWeaponDao;
	
	public WeaponShapeFactory(WeaponDao  weaponDao) {
		mWeaponDao = weaponDao;
		shapeBuilders = new HashMap<String, ShapeBuilder>();
		shapeBuilders.put(DEFAULT_WEAPON, new LaserBuilder());
	}

	@Override
	public Shape getObject(String name, int posX, int posY, int speedX, int speedY,
			int color, int damage) {
		return shapeBuilders.get(name).getObject(name, posX, posY, speedX, speedY, color, damage) ;
	}
	
	public WeaponDescriptor getWeapon(String name) {
		WeaponDescriptor weaponDescById = mWeaponDao.getWeaponDescById(name);
		return  weaponDescById == null ? mWeaponDao.getWeaponDescById(DEFAULT_WEAPON) : weaponDescById;	
	}
	
	private static final class LaserBuilder implements ShapeBuilder {
		@Override
		public Shape getObject(String name, int posX, int posY, int speedX, int speedY, int color, int damage) {
			return new Laser(posX, posY, speedX, speedY, damage);
		}		
	}
	
	public String getDefaultWeaponName() {
		return DEFAULT_WEAPON;
	}
	


	
	
	
}
