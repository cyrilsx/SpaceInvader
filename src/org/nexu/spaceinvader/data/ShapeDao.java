package org.nexu.spaceinvader.data;

import java.util.ArrayList;
import java.util.List;

import org.nexu.spaceinvader.data.domain.ShapeDescriptor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

public class ShapeDao {
	private final static String TAG = "SI_DAO";
	
	private final SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper;
	private final Context mContext;
	
	private List<ShapeDescriptor> mShapeCache; 
	
	public ShapeDao(SpaceSQLiteOpenHelper mSpaceSQLiteOpenHelper,
			Context mContext) {
		this.mSpaceSQLiteOpenHelper = mSpaceSQLiteOpenHelper;
		this.mContext = mContext;
	}


	public List<ShapeDescriptor> getAllShapeDescriptor() {
		if(mShapeCache != null) {
			return mShapeCache;
		}
		
		final List<ShapeDescriptor> rShapeDescriptors = new ArrayList<ShapeDescriptor>();
		
		SQLiteDatabase sqLiteDatabase = mSpaceSQLiteOpenHelper.getReadableDatabase();
		String[] resultColumn = new String[] {
				ShapeDescriptor.MetaData._ID.getValue(), //
				ShapeDescriptor.MetaData._COLOR.getValue(), //
				ShapeDescriptor.MetaData._DRAWING.getValue(), //
				ShapeDescriptor.MetaData._HEIGHT.getValue(), //
				ShapeDescriptor.MetaData._RADIUS.getValue(), //
				ShapeDescriptor.MetaData._SHAPE_TYPE.getValue(), //
				ShapeDescriptor.MetaData._WIDTH.getValue()};
		
		Cursor cursor = sqLiteDatabase.query(ShapeDescriptor.MetaData._TABLE_NAME.getValue(), resultColumn, null, null, null, null, null);
		try {
			while(cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(ShapeDescriptor.MetaData._ID.getValue()));
				int color = toColor(cursor.getString(cursor.getColumnIndex(ShapeDescriptor.MetaData._COLOR.getValue())));
				int witdh = cursor.getInt(cursor.getColumnIndex(ShapeDescriptor.MetaData._WIDTH.getValue()));
				int height= cursor.getInt(cursor.getColumnIndex(ShapeDescriptor.MetaData._HEIGHT.getValue()));
				int radius = cursor.getInt(cursor.getColumnIndex(ShapeDescriptor.MetaData._RADIUS.getValue()));
				int type =  cursor.getInt(cursor.getColumnIndex(ShapeDescriptor.MetaData._SHAPE_TYPE.getValue()));
				
				String resourceName = cursor.getString(cursor.getColumnIndex(ShapeDescriptor.MetaData._DRAWING.getValue()));
				int identifier;
				if(resourceName == null) {
					identifier = 0;
				} else {
					identifier = mContext.getResources().getIdentifier(resourceName, "drawable", "org.nexu.spaceinvader");
				}
				Bitmap decodeResourceShape = null;
				if(identifier == 0){
					Log.e(TAG, "Can't load resource for scene " + id);
					
				} else {
					decodeResourceShape = BitmapFactory.decodeResource(mContext.getResources(),
							identifier);	
				}
				
				rShapeDescriptors.add(new ShapeDescriptor(id, witdh, height, decodeResourceShape, color, radius, type));
			}
			
		} finally {	
			sqLiteDatabase.close();
			cursor.close();
		}
		
		mShapeCache = rShapeDescriptors;
		return rShapeDescriptors;
	}
	
	ShapeDescriptor getShapeDescriptor(int id) {
		if(mShapeCache == null) {
			getAllShapeDescriptor();
		}
		if(id > mShapeCache.size() - 1) {
			return null;
		}
		return mShapeCache.get(id);
	}
	
	/**
	 * 
	 * @param formatterStr a,r,g,b
	 * @return
	 */
	private int toColor(String formatterStr) {
		if(formatterStr == null) {
			return 0;
		}
		String[] values = formatterStr.split(",");
		if(values.length != 4) {
			Log.e(TAG, "incorrect color value: " + formatterStr);
			return 0;
		}
		return Color.argb(Integer.valueOf(values[0]), Integer.valueOf(values[1]), Integer.valueOf(values[2]), Integer.valueOf(values[3]));	
	}

	
	
}
