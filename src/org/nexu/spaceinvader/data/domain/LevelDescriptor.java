package org.nexu.spaceinvader.data.domain;


public class LevelDescriptor {
	
	private final  String name;
	private final boolean locked;
	private final SceneDescriptor scene;
	
	public LevelDescriptor(String name, boolean locked, SceneDescriptor scene) {
		this.name = name;
		this.locked = locked;
		this.scene = scene;
	}

	public String getName() {
		return name;
	}
	
	public SceneDescriptor getScene() {
		return scene;
	}
	
	public boolean isLocked() {
		return locked;
	}
	
	
	public enum MetaData {
		_TABLE_NAME("level"),
		_NAME("name"),
		_SCENE_ID("scene_id"),
		_LOCKED("locked");
		
		private final String value;

		private MetaData(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}

	}
	
	
}
