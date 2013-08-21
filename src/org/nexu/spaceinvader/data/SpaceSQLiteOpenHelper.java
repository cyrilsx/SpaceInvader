package org.nexu.spaceinvader.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SpaceSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "space_invader.db";

	private final List<StringBuilder> mCreateQuery = new ArrayList<StringBuilder>();

	public SpaceSQLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		init();
		try {
			for(StringBuilder queries : mCreateQuery) {				
				db.execSQL(queries.toString());
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("DROP TABLE IF EXISTS scene");
        db.execSQL("DROP TABLE IF EXISTS bonus");
        db.execSQL("DROP TABLE IF EXISTS level");
        db.execSQL("DROP TABLE IF EXISTS shape");
        db.execSQL("DROP TABLE IF EXISTS weapon");
        db.execSQL("DROP TABLE IF EXISTS enemy");
        db.execSQL("DROP TABLE IF EXISTS ship");
		
		onCreate(db);
	}
	
	public String getDbName() {
		return DB_NAME;
	}

	private void init() {

		// create table background
		mCreateQuery.add(new StringBuilder("CREATE TABLE scene (")//
				.append(" id INTEGER PRIMARY KEY,")//
				.append(" speed_x INTEGER NOT NULL,") //
				.append(" speed_y INTEGER NOT NULL,") //
				.append(" background VARCHAR(120) NOT NULL); ")); //

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO scene (")//
				.append("id, speed_x, speed_y, background)")//
				.append(" VALUES (0, 0, 0, 'bg_black');"));

		// create table background
		mCreateQuery.add(new StringBuilder("CREATE TABLE bonus (")//
				.append(" id INTEGER PRIMARY KEY,")//
				.append(" name VARCHAR(120),") //
				.append(" bonus_type INTEGER NOT NULL,") //
				.append(" target_id VARCHAR(120) NOT NULL); ")); //

		// TYPE 0 -> unlock next scene
		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO bonus (")//
				.append("id, name, bonus_type, target_id)")//
				.append(" VALUES (0, 'next_scene', 0, 'beginning');"));

		// create table level
		mCreateQuery.add(new StringBuilder("CREATE TABLE level (")//
				.append(" name VARCHAR(60) PRIMARY KEY,")//
				.append(" scene_id INTEGER NOT NULL,")// Add foreign key
				.append(" locked INTEGER(1) NOT NULL); ")); //

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO level (")//
				.append("name, scene_id, locked)")//
				.append(" VALUES ('beginning', 0, 0);"));

		// create table shape (shape to associate with weapon)
		mCreateQuery.add(new StringBuilder("CREATE TABLE shape (")//
				.append(" id INTEGER PRIMARY KEY,")//
				.append(" width INTEGER,") //
				.append(" height INTEGER,") //
				.append(" radius INTEGER,") //
				.append(" shape_type INTEGER NOT NULL,") //
				.append(" color_argb VARCHAR(15),") //
				.append(" drawing VARCHAR(120)); "));

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO shape (")//
				.append("id, width, height, color_argb, shape_type, drawing)")//
				.append(" VALUES (0, 20, 3, '255,255,0,255', 0, NULL);"));
		mCreateQuery.add(new StringBuilder("INSERT INTO shape (")//
				.append("id, width, height, color_argb, shape_type, drawing)")//
				.append(" VALUES (1, NULL, NULL, NULL, 2, 'alienblaster');"));
		mCreateQuery.add(new StringBuilder("INSERT INTO shape (")//
				.append("id, width, height, color_argb, shape_type, drawing)")//
				.append(" VALUES (2, NULL, NULL, NULL, 2, 'thermaldetonator');"));

		// create table weapons
		mCreateQuery.add(new StringBuilder("CREATE TABLE weapon (")//
				.append(" name VARCHAR(60) PRIMARY KEY,")//
				.append(" frequency INTEGER,") //
				.append(" damage INTEGER NOT NULL,") //
				.append(" speed_x INTEGER NOT NULL,") //
				.append(" speed_y INTEGER NOT NULL,") //
				.append(" sub_weapon VARCHAR(60),") //
				.append(" shape INTEGER NOT NULL); "));

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO weapon (")//
				.append("name, frequency, damage, speed_x, speed_y, shape)")//
				.append(" VALUES ('Gun', 1, 2, 15, 0, 0);"));

		// create table weapons
		mCreateQuery.add(new StringBuilder("CREATE TABLE enemy (")//
				.append(" id INTEGER PRIMARY KEY,")//
				.append(" name VARCHAR(60),")//
				.append(" health INTEGER NOT NULL,") //
				.append(" speed INTEGER NOT NULL,") //
				.append(" level_name VARCHAR(60) NOT NULL,") //
				.append(" shape INTEGER NOT NULL); "));

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO enemy (")//
				.append("id, name, health, speed, level_name, shape)")//
				.append(" VALUES (0, 'detector', 1, 15,'beginning', 2);"));

		// create table weapons
		mCreateQuery.add(new StringBuilder("CREATE TABLE ship (")//
				.append(" name VARCHAR(60) PRIMARY KEY,")//
				.append(" health INTEGER NOT NULL,") //
				.append(" nb_weapon INTEGER NOT NULL,") //
				.append(" shape INTEGER NOT NULL); "));

		// insert data
		mCreateQuery.add(new StringBuilder("INSERT INTO ship (")//
				.append("name, health, nb_weapon ,shape)")//
				.append(" VALUES ('basic_ship', 1, 1, 1);"));
		
		

	}

}
