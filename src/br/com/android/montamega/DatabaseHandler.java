package br.com.android.montamega;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	// All static variables
	private static final int DATABASE_VERSION = 8;
	private static final String DATABASE_NAME = "megaManager";
	private static final String TABLE_MEGA = "table_mega";

	// Constructor
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_MEGA + " "
				+ "(id INTEGER PRIMARY KEY, " + "mega text );";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEGA);
		onCreate(db);
	}

	public void addMega(Mega mega) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("mega", mega.getMega());

		// Inserting row
		db.insert(TABLE_MEGA, null, values);
		db.close();
	}

	public List<Mega> getAllMega() {
		List<Mega> megaList = new ArrayList<Mega>();

		// Select All Mega
		String selectQuery = "SELECT * FROM " + TABLE_MEGA;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Mega mega = new Mega();
				mega.setId(Integer.parseInt(cursor.getString(0)));
				mega.setMega(cursor.getString(1));
				megaList.add(mega);
			} while (cursor.moveToNext());
		}
		// if(cursor.moveToFirst()){
		// while(cursor.isAfterLast()==false){
		// String name = cursor.getString(cursor
		// .getColumnIndex("mega"));
		//
		// megaList.add(name);
		// cursor.moveToNext();
		// }
		// }

		return megaList;
	}

	public int getMegaCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_MEGA;
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		cursor.close();

		return cursor.getCount();
	}

	public void removeMega(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEGA, "id=" + id, null);
	}

	public void insertTest(Mega mega, int numberTotal) {
		if (getMegaCount() >= numberTotal) {
			removeMega(getMinID());
			addMega(mega);
			//return true;
		} else {
			addMega(mega);
			//return false;
		}
	}

	private int getMinID() {
		SQLiteDatabase db = this.getReadableDatabase();

		int id = 0;
		String sql = "SELECT MIN(id) AS id FROM " + TABLE_MEGA;

		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.getCount() > 0) {
			cursor.moveToLast();
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		return id;
	}

	public Mega getMega(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MEGA, new String[] { "id", "mega" },
				"id=?", new String[] { String.valueOf(id) }, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Mega mega = new Mega(Integer.parseInt(cursor.getString(1)),
				cursor.getString(2));
		return mega;
	}

}
