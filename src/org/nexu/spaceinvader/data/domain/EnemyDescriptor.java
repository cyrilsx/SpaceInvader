package org.nexu.spaceinvader.data.domain;


public class EnemyDescriptor {

	private final String name;
	private final int health;
	private final int speed;
	private final LevelDescriptor level;
	private final ShapeDescriptor shapeDescriptor;
	
	
	public EnemyDescriptor(String name, int health, int speed,
			LevelDescriptor level, ShapeDescriptor shapeDescriptor) {
		this.name = name;
		this.health = health;
		this.speed = speed;
		this.level = level;
		this.shapeDescriptor = shapeDescriptor;
	}

	public String getName() {
		return name;
	}

	public int getHealth() {
		return health;
	}

	public int getSpeed() {
		return speed;
	}
	
	public LevelDescriptor getLevel() {
		return level;
	}

	public ShapeDescriptor getShapeDescriptor() {
		return shapeDescriptor;
	}
	public enum MetaData {
		_TABLE_NAME("enemy"),
		_NAME("name"),
		_HEALTH("health"),
		_SPEED("speed"),
		_LEVEL("level_name"),
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