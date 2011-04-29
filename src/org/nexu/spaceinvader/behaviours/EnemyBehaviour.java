package org.nexu.spaceinvader.behaviours;

import java.util.List;
import java.util.Random;

import org.nexu.spaceinvader.customsview.SpacePanel;
import org.nexu.spaceinvader.elements.Enemy;

public class EnemyBehaviour extends Thread {

	private List<Enemy> enemies;
	private boolean run = false;

	public EnemyBehaviour(List<Enemy> enemies) {
		super();
		this.enemies = enemies;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		Random rand = new Random();
		while (run) {
			for (Enemy elem : enemies) {
				int nextX = 300;
				int nextY = 300;
				if (SpacePanel.width != 0) {
					nextX = rand.nextInt((int) SpacePanel.width);
				}
				if (SpacePanel.height != 0) {
					nextY = rand.nextInt((int) SpacePanel.height);
				}
				synchronized (elem) {
					elem.changePosition(nextX, nextY);
				}
			}
		}
	}
}
