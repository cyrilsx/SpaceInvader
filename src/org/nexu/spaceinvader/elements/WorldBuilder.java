package org.nexu.spaceinvader.elements;

import java.util.ArrayList;
import java.util.List;

import org.nexu.spaceinvader.behaviours.EnemyBehaviour;
import org.nexu.spaceinvader.behaviours.Movable;
import org.nexu.spaceinvader.customsview.SpacePanel;

import android.graphics.Bitmap;

public class WorldBuilder {
	
	
	private SpaceShip ship;
	private List<Movable> elements;
	private int elementNumber = 0;
	private EnemyBehaviour enemyBehaviour;
	/**
	 * Singleton
	 */
	private WorldBuilder() {
		elements = new ArrayList<Movable>();
	}
	
	public static WorldBuilder getInstance() {
		return Loader.instance;
	}
	
	private static class Loader {
		private static final WorldBuilder instance = new WorldBuilder();
	}
	
	public List<Movable> getAllWorldElement() {
		return elements;
	}
	
	public Movable getShip() {
		return ship;
	}
	
	public void buildMainShip(Bitmap image, int x, int y) {
		this.ship = new SpaceShip(x, y, image);
		elements.add(ship);
		elementNumber++;
	}
	
	public void buildEnemies(Bitmap image, int nbEnemies) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		int y = image.getHeight() + 10;
		int x = image.getWidth() + 10;
		for(int i = 0; i < nbEnemies; i++) {
			Enemy elem = new Enemy(image, x, y);
			elements.add(elem);
			enemies.add(elem);
			elementNumber++;
			x += image.getWidth() + 10;
			y += image.getHeight() + 10;
			if(x > SpacePanel.width) {
				y += image.getHeight() + 10;
				x = image.getWidth() + 10;
			}
		}
		this.enemyBehaviour = new EnemyBehaviour(enemies);
		enemyBehaviour.setRun(true);
//		enemyBehaviour.start();
	}
	
	public int getWorldSize() {
		return elementNumber;
	}
	
	

}
