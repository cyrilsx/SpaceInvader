package org.nexu.spaceinvader.data.domain;

public class WeaponDescriptor {
	
	private final String name;
	private final int frequency;
	private final int damage;
	private final int speedX;
	private final int speedY;
	private final WeaponDescriptor subWeapon;
	private final ShapeDescriptor shape;
	

	public WeaponDescriptor(String name, int frequency, int damage, int speedX,
			int speedY, WeaponDescriptor subWeapon, ShapeDescriptor shape) {
		this.name = name;
		this.frequency = frequency;
		this.damage = damage;
		this.speedX = speedX;
		this.speedY = speedY;
		this.subWeapon = subWeapon;
		this.shape = shape;
	}	
	
	public String getName() {
		return name;
	}


	public int getFrequency() {
		return frequency;
	}


	public int getDamage() {
		return damage;
	}

	public int getSpeedX() {
		return speedX;
	}
 
	public int getSpeedY() {
		return speedY;
	}

	public WeaponDescriptor getSubWeapon() {
		return subWeapon;
	}

	public ShapeDescriptor getShape() {
		return shape;
	}


	public enum MetaData {
		_TABLE_NAME("weapon"),
		_NAME("name"),
		_FREQUENCY("frequency"),
		_DAMAGE("damage"),
		_SPEED_X("speed_x"),
		_SPEED_Y("speed_y"),
		_SUB_WEAPON("sub_weapon"),
		_SHAPE("shape");
		
		private final String value;

		private MetaData(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}

}
