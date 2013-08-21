package org.nexu.spaceinvader.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.nexu.spaceinvader.data.domain.ShipDescriptor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShipDao {
	private final static String TAG = "SI_DAO";
	
	
	private final SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper;
	private final ShapeDao mShapeDao;
	private String defaultSpaceShip;
	
	private Map<String, ShipDescriptor> mCacheShip;
	
	public ShipDao(SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper,
			ShapeDao shapeDao) {
		this.mSpaceSQLiteOpenHelper = mSpaceSQLiteOpenHelper;
		this.mShapeDao = shapeDao;
		getAllSpaceShipDescriptor();
	}
	
	public String getDefaultSpaceShipId() {
		return defaultSpaceShip;
	}


	public Collection<ShipDescriptor> getAllSpaceShipDescriptor() {
		if(mCacheShip != null) {
			return mCacheShip.values();
		}
		Log.d(TAG, "Ships are going to be load from db");
		final Map<String, ShipDescriptor> rShips = new HashMap<String, ShipDescriptor>();
		
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				ShipDescriptor.MetaData._NAME.getValue(), //
				ShipDescriptor.MetaData._HEALTH.getValue(), //
				ShipDescriptor.MetaData._NB_WEAPON.getValue(), //
				ShipDescriptor.MetaData._SHAPE.getValue()};
		
		Cursor cursor = sqLiteDatabase.query(ShipDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				String name = cursor.getString(cursor.getColumnIndex(ShipDescriptor.MetaData._NAME.getValue()));
				if(defaultSpaceShip == null) {
					defaultSpaceShip = name;
				}
				int health = cursor.getInt(cursor.getColumnIndex(ShipDescriptor.MetaData._HEALTH.getValue()));
				int nbWeapon = cursor.getInt(cursor.getColumnIndex(ShipDescriptor.MetaData._NB_WEAPON.getValue()));
				int shapeId = cursor.getInt(cursor.getColumnIndex(ShipDescriptor.MetaData._SHAPE.getValue()));
				
				
				
				rShips.put(name, new ShipDescriptor(name, health, nbWeapon, mShapeDao.getShapeDescriptor(shapeId))); 
			}
			
		} finally {	
			sqLiteDatabase.close();
			cursor.close();
		}
		
		mCacheShip = rShips;
		return rShips.values();
	}
	
	public ShipDescriptor getShipById(String name) {
		if(mCacheShip == null) {
			getAllSpaceShipDescriptor();
		}
		return mCacheShip.get(name);
		
	}
	
}
