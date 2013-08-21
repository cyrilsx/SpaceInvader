package org.nexu.spaceinvader.behaviours.controller;

import java.util.Collection;
import java.util.List;

import org.nexu.spaceinvader.activities.LifeCycle;
import org.nexu.spaceinvader.data.LevelDao;
import org.nexu.spaceinvader.data.domain.LevelDescriptor;
import org.nexu.spaceinvader.data.domain.SceneDescriptor;
import org.nexu.spaceinvader.domain.ScreenConfiguration;
import org.nexu.spaceinvader.domain.Shape;

public class LevelController implements LifeCycle {

	private LevelDao mLevelDao;

	public LevelController(LevelDao levelDao) {
		mLevelDao = levelDao;
	}

	public ScreenConfiguration getBackgroundConfiguration() {
		return toConfiguration(findLevelToPlay().getScene());
	}

	private ScreenConfiguration toConfiguration(SceneDescriptor sceneDescriptor) {
		return new ScreenConfiguration(sceneDescriptor.getSpeedX(),
				sceneDescriptor.getSpeedY(),
				sceneDescriptor.getBackgroundImage());
	}

	public LevelDescriptor findLevelToPlay() {
		Collection<LevelDescriptor> allLevels = mLevelDao.getAllLevel();
		for (LevelDescriptor level : allLevels) {
			if (!level.isLocked()) {
				return level;
			}
		}
		return null;
	}

	@Override
	public List<Shape> onLoad(int sWitdh, int sHeight) {
		// Nothing to do yet...
		return null;
	}

}
