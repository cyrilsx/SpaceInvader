package org.nexu.spaceinvader.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nexu.spaceinvader.data.domain.EnemyDescriptor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EnemyDao {
	private final static String TAG = "SI_DAO";
	
	private final SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper;
	private final ShapeDao mShapeDao;
	private final LevelDao mLevelDao;
	
	private Map<String, EnemyDescriptor> mCacheEnemy;
	private Map<String, List<EnemyDescriptor>> mLevelEnemyCache;
	
	public  EnemyDao(SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper,
			ShapeDao shapeDao, LevelDao levelDao) {
		this.mSpaceSQLiteOpenHelper = mSpaceSQLiteOpenHelper;
		this.mShapeDao = shapeDao;
		this.mLevelDao = levelDao;
		mLevelEnemyCache = new HashMap<String, List<EnemyDescriptor>>();
	}

	private void updateLevelCache(String name, EnemyDescriptor enemy) {
		 List<EnemyDescriptor> allEnemies = mLevelEnemyCache.get(name);
		 if(allEnemies == null) {
			 allEnemies = new ArrayList<EnemyDescriptor>();
			 mLevelEnemyCache.put(name, allEnemies);
		 }
		 allEnemies.add(enemy);
	}
	

	public Collection<EnemyDescriptor> getAllEnemyDescriptor() {
		if(mCacheEnemy != null) {
			return mCacheEnemy.values();
		}
		
		Log.d(TAG, "Enemies are going to be load from db");
		final Map<String, EnemyDescriptor> rEnemies = new HashMap<String, EnemyDescriptor>();
		
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				EnemyDescriptor.MetaData._NAME.getValue(), //
				EnemyDescriptor.MetaData._HEALTH.getValue(), //
				EnemyDescriptor.MetaData._SPEED.getValue(), //
				EnemyDescriptor.MetaData._LEVEL.getValue(), //
				EnemyDescriptor.MetaData._SHAPE.getValue()};
		
		Cursor cursor = sqLiteDatabase.query(EnemyDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(EnemyDescriptor.MetaData._NAME.getValue()));
				int health = cursor.getInt(cursor.getColumnIndex(EnemyDescriptor.MetaData._HEALTH.getValue()));
				int speed = cursor.getInt(cursor.getColumnIndex(EnemyDescriptor.MetaData._SPEED.getValue()));
				String levelId = cursor.getString(cursor.getColumnIndex(EnemyDescriptor.MetaData._LEVEL.getValue()));
				int shapeId = cursor.getInt(cursor.getColumnIndex(EnemyDescriptor.MetaData._SHAPE.getValue()));
				
				EnemyDescriptor enemyDescriptor = new EnemyDescriptor(name, health, speed, mLevelDao.getLevelById(levelId), mShapeDao.getShapeDescriptor(shapeId));
				rEnemies.put(name, enemyDescriptor); //FIXME
				updateLevelCache(levelId, enemyDescriptor);
			}
			
		} finally {	
			sqLiteDatabase.close();
			cursor.close();
		}
		
		mCacheEnemy = rEnemies;
		return rEnemies.values();
	}
	
	public EnemyDescriptor getEnemyById(String name) {
		if(mCacheEnemy == null) {
			getAllEnemyDescriptor();
		}
		return mCacheEnemy.get(name);	
	}
	
	public List<EnemyDescriptor> getEnemiesByLevel(String name) {
		if(mCacheEnemy == null) {
			getAllEnemyDescriptor();
		}
		return mLevelEnemyCache.get(name);
	}
	
}
