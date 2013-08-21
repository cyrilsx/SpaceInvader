package org.nexu.spaceinvader.data.domain;

public class ShipDescriptor {

	private final String name;
	private final int health;
	private final int nb_weapon;
	private final ShapeDescriptor shapeDescriptor;
	
	public ShipDescriptor(String name, int health, int nb_weapon,
			ShapeDescriptor shapeDescriptor) {
		this.name = name;
		this.health = health;
		this.nb_weapon = nb_weapon;
		this.shapeDescriptor = shapeDescriptor;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}

	public int getNb_weapon() {
		return nb_weapon;
	}

	public ShapeDescriptor getShapeDescriptor() {
		return shapeDescriptor;
	}

	public enum MetaData {
		_TABLE_NAME("ship"),
		_NAME("name"),
		_HEALTH("health"),
		_NB_WEAPON("nb_weapon"),
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
