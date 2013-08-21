package org.nexu.spaceinvader.behaviours;

import java.util.List;
import java.util.Random;

import org.nexu.spaceinvader.elements.Enemy;
import org.nexu.spaceinvader.events.EventBus.Subcriber;
import org.nexu.spaceinvader.events.ScreenDimensionChanged;

public class EnemyBehaviour extends Thread implements Subcriber<ScreenDimensionChanged> {

	private List<Enemy> enemies;
	private boolean run = false;
	private volatile int witdh;
	private volatile int height;

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
		// why create a random in run
		Random rand = new Random();
		while (run) {
			for (Enemy elem : enemies) {
				int nextX = 300;
				int nextY = 300;
				if (this.witdh != 0) {
					nextX = rand.nextInt((int) this.witdh);
				}
				if (this.height != 0) {
					nextY = rand.nextInt((int) this.height);
				}
				synchronized (elem) {
					elem.changePosition(nextX, nextY);
				}
			}
		}
	}


	@Override
	public void onEvent(ScreenDimensionChanged event) {
		this.height = event.getHeight();
		this.witdh = event.getWitdh();	
	}
}
