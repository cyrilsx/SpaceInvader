package org.nexu.spaceinvader.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.nexu.spaceinvader.data.domain.WeaponDescriptor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class WeaponDao {
	private final static String TAG = "SI_DAO";
	
	private final SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper;
	private final ShapeDao mShapeDao;
	
	private Map<String, WeaponDescriptor> mCacheWeapons;
	
	public  WeaponDao(SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper,
			ShapeDao shapeDao) {
		this.mSpaceSQLiteOpenHelper = mSpaceSQLiteOpenHelper;
		this.mShapeDao = shapeDao;
	}


	public Collection<WeaponDescriptor> getAllShapeDescriptor() {
		if(mCacheWeapons != null) {
			return mCacheWeapons.values();
		}
		mCacheWeapons = new HashMap<String, WeaponDescriptor>();
		
		Log.d(TAG, "Weapons are going to be loaded");
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				WeaponDescriptor.MetaData._NAME.getValue(), //
				WeaponDescriptor.MetaData._DAMAGE.getValue(), //
				WeaponDescriptor.MetaData._FREQUENCY.getValue(), //
				WeaponDescriptor.MetaData._SHAPE.getValue(), //
				WeaponDescriptor.MetaData._SPEED_X.getValue(), //
				WeaponDescriptor.MetaData._SPEED_Y.getValue(), //
				WeaponDescriptor.MetaData._SUB_WEAPON.getValue()};
		
		Cursor cursor = sqLiteDatabase.query(WeaponDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(WeaponDescriptor.MetaData._NAME.getValue()));
				int damage = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._DAMAGE.getValue()));
				int frequency = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._FREQUENCY.getValue()));
				int shapeId = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._SHAPE.getValue()));
				int speedX = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._SPEED_X.getValue()));
				int speedY = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._SPEED_Y.getValue()));
				int sub = cursor.getInt(cursor.getColumnIndex(WeaponDescriptor.MetaData._SUB_WEAPON.getValue()));
				
				mCacheWeapons.put(name, new WeaponDescriptor(name, frequency, damage, speedX, speedY, sub == 0 ? null : null, mShapeDao.getShapeDescriptor(shapeId))); //FIXME
			}
			
		} finally {	
			sqLiteDatabase.close();
			cursor.close();
		}
		
		return mCacheWeapons.values();
	}
	
	public WeaponDescriptor getWeaponDescById(String name) {
		if(mCacheWeapons == null) {
			getAllShapeDescriptor();
		}
		return mCacheWeapons.get(name);
	}
	
}
