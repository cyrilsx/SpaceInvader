package org.nexu.spaceinvader.behaviours.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.nexu.spaceinvader.activities.LifeCycle;
import org.nexu.spaceinvader.data.EnemyDao;
import org.nexu.spaceinvader.data.domain.EnemyDescriptor;
import org.nexu.spaceinvader.domain.Shape;
import org.nexu.spaceinvader.elements.Enemy;

import android.graphics.Bitmap;

public class EnemyController implements LifeCycle {

	private EnemyDao mEnemyDao;
	private LevelController mLevelController;
    private CollisionController mCollisionController;
	
	public EnemyController(EnemyDao enemyDao, LevelController levelController, CollisionController collisionController) {
		mEnemyDao = enemyDao;
		mLevelController = levelController;
        mCollisionController = collisionController;
	}
	
	@Override
	public List<Shape> onLoad(int sWitdh, int sHeight) {
		List<EnemyDescriptor> enemiesByLevel = mEnemyDao.getEnemiesByLevel(mLevelController.findLevelToPlay().getName());
		List<Shape> shapeToRender = new ArrayList<Shape>();
		for(EnemyDescriptor enemyDescriptor : enemiesByLevel) {
			for(int i = 0; i < 6; i++) {
				Bitmap drawable = enemyDescriptor.getShapeDescriptor().getDrawable();
				Enemy enemy = new Enemy(drawable, sWitdh - drawable.getWidth() + 15, sHeight - drawable.getHeight() + 15, sWitdh, sHeight, enemyDescriptor.getShapeDescriptor().getTypeShape(), enemyDescriptor.getHealth());
				shapeToRender.add(enemy);

			}
		}
		
		return shapeToRender;
	}

	
}
