package br.com.android.montamega.mega;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.android.montamega.extras.Extras;

public class MegaDAO extends SQLiteOpenHelper {
	public MegaDAO(Context context) {
		super(context, Extras.DATABASE_NAME, null, Extras.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + Extras.TABLE_MEGA + " "
				+ "(id INTEGER PRIMARY KEY, " + "lastResult INTEGER, "
				+ "mega TEXT, " + "date TEXT);";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Extras.TABLE_MEGA);
		onCreate(db);
	}

	// Save up
	public void add(Mega mega) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put("lastResult", mega.getLastResult());
		values.put("mega", mega.getMega());
		values.put("date", mega.getDate());

		db.insert(Extras.TABLE_MEGA, null, values);
		db.close();
	}

	// Get All
	public List<Mega> getAll() {
		SQLiteDatabase db = this.getReadableDatabase();

		List<Mega> list = new ArrayList<Mega>();
		String selectQuery = "SELECT id, lastResult, mega, date FROM "
				+ Extras.TABLE_MEGA;
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				Mega mega = new Mega();
				mega.setId(Integer.parseInt(cursor.getString(0)));
				mega.setLastResult(Integer.parseInt(cursor.getString(1)));
				mega.setMega(cursor.getString(2));
				mega.setDate(cursor.getString(3));
				list.add(mega);
			} while (cursor.moveToNext());
		}

		db.close();
		Collections.reverse(list);
		return list;
	}

	// Get size
	public int size() {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + Extras.TABLE_MEGA;
		Cursor cursor = db.rawQuery(selectQuery, null);

		cursor.moveToFirst();
		cursor.close();

		db.close();
		return cursor.getCount();
	}

	// Remove
	public void remove(int id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(Extras.TABLE_MEGA, "id=" + id, null);
		db.close();
	}

	// Save up with 2 parameters
	public boolean add(Mega mega, int numberTotal) {
		if (size() >= numberTotal) {
			remove(getMinId());
			add(mega);
			return true;
		} else {
			add(mega);
			return true;
		}
	}

	// Get minimum id
	private int getMinId() {
		SQLiteDatabase db = this.getReadableDatabase();

		int id = 0;
		String sql = "SELECT MIN(id) AS id FROM " + Extras.TABLE_MEGA;

		Cursor cursor = db.rawQuery(sql, null);

		if (cursor.getCount() > 0) {
			cursor.moveToLast();
			id = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		db.close();

		return id;
	}

	// Get
	public Mega get(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(Extras.TABLE_MEGA, new String[] { "id",
				"lastResult", "mega", "date" }, "id=?",
				new String[] { String.valueOf(id) }, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Mega mega = new Mega(Integer.parseInt(cursor.getString(0)),
				Integer.parseInt(cursor.getString(1)), cursor.getString(2),
				cursor.getString(3));
		db.close();

		return mega;
	}

}
