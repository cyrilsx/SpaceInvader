package org.nexu.spaceinvader.behaviours.controller;

import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;

import org.nexu.spaceinvader.customsview.SpacePanel;
import org.nexu.spaceinvader.data.WeaponDao;
import org.nexu.spaceinvader.data.domain.WeaponDescriptor;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.elements.Element;
import org.nexu.spaceinvader.elements.factory.WeaponShapeFactory;

import android.graphics.Color;

public class WeaponController {
	
	private final WeaponShapeFactory mWeaponShapeFactory;
	private final SpacePanel mScreen;
    private final InFieldEnemyRegistry mInFieldEnemyRegistry;
	
	private final Map<Shape, WeaponDescriptor> inGameShape = new IdentityHashMap<Shape, WeaponDescriptor>();
	private final Map<Element, FireFilter> fireFilter = new IdentityHashMap<Element, FireFilter>();
	
	public WeaponController(SpacePanel screen, WeaponDao weaponDao, InFieldEnemyRegistry inFieldEnemyRegistry) {
		this.mWeaponShapeFactory = new WeaponShapeFactory(weaponDao);
		this.mScreen = screen;
        this.mInFieldEnemyRegistry = inFieldEnemyRegistry;
	}
	
	
	public void fire(Element src, String weaponName) {
		WeaponDescriptor weaponDescriptor = mWeaponShapeFactory.getWeapon(weaponName);

		FireFilter filter = fireFilter.get(src);
		if(filter == null) {
			filter = new FireFilter();
			fireFilter.put(src, filter);
		}
		
		if(!filter.canFire(weaponDescriptor.getFrequency())) {
			// already fired.
			return;
		}
		
		Shape weaponShape = mWeaponShapeFactory.getObject(weaponName, src.getPosX(), src.getPosY() + src.getHeight()/2, weaponDescriptor.getSpeedX(), weaponDescriptor.getSpeedY(), Color.BLUE, weaponDescriptor.getDamage());
		
		inGameShape.put(weaponShape, weaponDescriptor);
        mInFieldEnemyRegistry.addTo(InFieldEnemyRegistry.Group.ENEMY, weaponShape);
        this.mScreen.addShape(weaponShape);
		
	}
	
	public String getDefaultWeaponName() {
		return mWeaponShapeFactory.getDefaultWeaponName();
	}
	


	
	private static final class FireFilter {
		
		private long lastTimeFire;
			
		public boolean canFire(int frequency) {
			long now = new Date().getTime();
			boolean res =  lastTimeFire < now - frequency;
			lastTimeFire = now;
			return res;
		}
	}


	


}
