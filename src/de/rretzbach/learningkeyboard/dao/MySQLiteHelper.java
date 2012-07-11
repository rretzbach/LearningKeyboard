package de.rretzbach.learningkeyboard.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "words.db";
	private static final int DATABASE_VERSION = 1;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("create table words (_id integer primary key autoincrement," +
				         "                    word text);");
		database.execSQL("create table followers (lastword integer," +
				         "                        follower integer," +
				         "                        frequency integer," +
				         "                        FOREIGN KEY(lastword) REFERENCES words(_id),"+
	                     "                        FOREIGN KEY(follower) REFERENCES words(_id));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		Log.w(MySQLiteHelper.class.getName(),
//				"Upgrading database from version " + oldVersion + " to "
//						+ newVersion + ", which will destroy all old data");
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
//		onCreate(db);
	}

}
