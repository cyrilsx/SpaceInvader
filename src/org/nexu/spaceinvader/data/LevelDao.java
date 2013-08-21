package org.nexu.spaceinvader.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nexu.spaceinvader.data.domain.LevelDescriptor;
import org.nexu.spaceinvader.data.domain.SceneDescriptor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class LevelDao {
	
	private final static String TAG = "SI_DAO";
	
	private final SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper;
	private final Context mContext;
	private Map<String, LevelDescriptor> mCacheLevels;
	private List<SceneDescriptor> mCacheScenes;
	
	public LevelDao(SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper, Context context) {
		this.mSpaceSQLiteOpenHelper = mSpaceSQLiteOpenHelper;
		this.mContext = context;
	}

	private List<SceneDescriptor> getAllScene() {
		if(mCacheScenes != null) {
			return mCacheScenes;
		}
		
		final List<SceneDescriptor> rScenes = new ArrayList<SceneDescriptor>();
		
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				SceneDescriptor.MetaData._ID.getValue(), //
				SceneDescriptor.MetaData._SPEED_X.getValue(), //
				SceneDescriptor.MetaData._SPEED_Y.getValue(), //
				SceneDescriptor.MetaData._BACKGROUND_IMAGE.getValue(), //
		};
		Cursor cursor = sqLiteDatabase.query(SceneDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(SceneDescriptor.MetaData._ID.getValue()));
				int speedX = cursor.getInt(cursor.getColumnIndex(SceneDescriptor.MetaData._SPEED_X.getValue()));
				int speedY = cursor.getInt(cursor.getColumnIndex(SceneDescriptor.MetaData._SPEED_Y.getValue()));
				String resourceName = cursor.getString(cursor.getColumnIndex(SceneDescriptor.MetaData._BACKGROUND_IMAGE.getValue()));
				
				int identifier = mContext.getResources().getIdentifier(resourceName, "drawable", "org.nexu.spaceinvader");
				if(identifier == 0){
					Log.w(TAG, "Can't load resource for scene, let's try to parse color " + id);
					int parseColor = Color.BLACK;
					try {
						parseColor = Color.parseColor(resourceName);
					} catch(Exception ex) {
						Log.w(TAG, "The following color cant be recognized " + resourceName);
					}
					rScenes.add(new SceneDescriptor(id, parseColor, speedX, speedY));
					continue;
				}
				rScenes.add(new SceneDescriptor(id, mContext.getResources().getDrawable(identifier), speedX, speedY));
			}
			
		} finally {	
			sqLiteDatabase.close();
			cursor.close();
		}
		
		mCacheScenes = rScenes;
		return mCacheScenes;
	}


	public Collection<LevelDescriptor> getAllLevel() {
		if(mCacheLevels != null) {
			return mCacheLevels.values();
		}
		
		final Map<String, LevelDescriptor> rLevel = new HashMap<String, LevelDescriptor>();
		final List<SceneDescriptor> scenes = getAllScene();
		
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				LevelDescriptor.MetaData._NAME.getValue(), //
				LevelDescriptor.MetaData._LOCKED.getValue(), //
				LevelDescriptor.MetaData._SCENE_ID.getValue(), //
		};
		Cursor cursor = sqLiteDatabase.query(LevelDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(LevelDescriptor.MetaData._NAME.getValue()));
				boolean locked = cursor.getInt(cursor.getColumnIndex(LevelDescriptor.MetaData._LOCKED.getValue())) > 0;
				int scene_id = cursor.getInt(cursor.getColumnIndex(LevelDescriptor.MetaData._SCENE_ID.getValue()));
				rLevel.put(name, new LevelDescriptor(name, locked, scenes.get(scene_id)));
			}
		} finally {		
			sqLiteDatabase.close();
			cursor.close();
		}
		
		mCacheLevels = rLevel;
		return mCacheLevels.values();
	}
	
	public LevelDescriptor getLevelById(String name) {
		if(mCacheLevels == null) {
			getAllLevel();
		}
		return mCacheLevels.get(name);
	}
	

}
