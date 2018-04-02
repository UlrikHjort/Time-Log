/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                DBAdapter.java                               *
 *                                                                             *
 *                                   Module                                    *
 *                                                                             *
 *                    Copyright (C) 2010 Ulrik H¿rlyk Hjort                    *
 *                                                                             *
 *   Time Log is free software;  you can  redistribute it                      *
 *   and/or modify it under terms of the  GNU General Public License           *
 *   as published  by the Free Software  Foundation;  either version 2,        *
 *   or (at your option) any later version.                                    *
 *   Time Log is distributed in the hope that it will be                       *
 *   useful, but WITHOUT ANY WARRANTY;  without even the  implied warranty     *
 *   of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                   *
 *   See the GNU General Public License for  more details.                     *
 *   You should have  received  a copy of the GNU General                      *
 *   Public License  distributed with Yolk.  If not, write  to  the  Free      *
 *   Software Foundation,  51  Franklin  Street,  Fifth  Floor, Boston,        *
 *   MA 02110 - 1301, USA.                                                     *
 *                                                                             *
 ******************************************************************************/

package com.Embedded.Solutions.TimeLog;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "DrivingJournal";
	private static final String DATABASE_TABLE = "items";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table items (_id integer primary key autoincrement, "
			+ "type text not null, startTime long not null, "
			+ "endTime long not null, message text not null, tripStart long not null, tripEnd long not null);";

	private Context context;

	private static DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	/*
	 * private DBAdapterSingleton() { }
	 * 
	 * 
	 * public void setContext(Context ctx) {
	 * 
	 * context = ctx; DBHelper = new DatabaseHelper(context);
	 * 
	 * }
	 * 
	 * private static class SingletonHolder { public static final
	 * DBAdapterSingleton INSTANCE = new DBAdapterSingleton(); }
	 * 
	 * public static DBAdapterSingleton getInstance() { return
	 * SingletonHolder.INSTANCE; }
	 */

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS titles");
			onCreate(db);
		}
	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	// ---insert an item into the database---
	public long insertItem(String type, long startTime, String message,
			long tripStart) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Const.KEY_TYPE, type);
		initialValues.put(Const.KEY_START_TIME, startTime);
		initialValues.put(Const.KEY_END_TIME, -1);
		initialValues.put(Const.KEY_MESSAGE, message);
		initialValues.put(Const.KEY_TRIP_START, tripStart);
		initialValues.put(Const.KEY_TRIP_END, -1);

		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	public boolean deleteAll() {
		db.execSQL("DELETE FROM " + DATABASE_TABLE + ";");
		return false;

	}

	// ---deletes a particular item---
	public boolean deleteItem(long startTime) {
		// try {
		db.execSQL("DELETE FROM " + DATABASE_TABLE + " WHERE "
				+ Const.KEY_START_TIME + " = " + startTime);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// TODO
		return true;
		// return db.delete(DATABASE_TABLE, KEY_START_TIME +
		// "=" + rowId, null) > 0;
	}

	// ---retrieves all the titles---
	public Cursor getAllItems() {
		return db.query(DATABASE_TABLE, new String[] { Const.KEY_ROWID,
				Const.KEY_TYPE, Const.KEY_START_TIME, Const.KEY_END_TIME,
				Const.KEY_MESSAGE, Const.KEY_TRIP_START, Const.KEY_TRIP_END },
				null, null, null, null, null);
	}

	public Cursor getRange(long from, long to) {
		
		
		
		return db.query(DATABASE_TABLE, new String[] { Const.KEY_ROWID,
				Const.KEY_TYPE, Const.KEY_START_TIME, Const.KEY_END_TIME,
				Const.KEY_MESSAGE, Const.KEY_TRIP_START, Const.KEY_TRIP_END },
				Const.KEY_START_TIME + " >= " + from + " and "
						+ Const.KEY_END_TIME + " < " + to, null, null, null,
				null);
	}

	// ---retrieves a particular title---
	public Cursor getRunning() throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				Const.KEY_ROWID, Const.KEY_TYPE, Const.KEY_START_TIME,
				Const.KEY_END_TIME, Const.KEY_MESSAGE, Const.KEY_TRIP_START,
				Const.KEY_TRIP_END }, Const.KEY_END_TIME + "=" + -1, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// ---retrieves a particular title---
	public Cursor getRunning(long id) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				Const.KEY_ROWID, Const.KEY_TYPE, Const.KEY_START_TIME,
				Const.KEY_END_TIME, Const.KEY_MESSAGE, Const.KEY_TRIP_START,
				Const.KEY_TRIP_END }, Const.KEY_ROWID + "=" + id, null, null,
				null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public long getTripStart() {
		Cursor c = getRunning();
		c.moveToFirst();
		return c.getLong(c.getColumnIndex("tripStart"));
	}

	public void stopRunning(long tripEnd) {
		Cursor c = getRunning();
		c.moveToFirst();
		updateRunning(c.getLong(c.getColumnIndex("_id")), c.getString(c
				.getColumnIndex("type")), c.getLong(c
				.getColumnIndex("startTime")), System.currentTimeMillis(), c
				.getString(c.getColumnIndex("message")), tripEnd);
	}

	public void stopRunningTest(long tripEnd, long endTime) {
		Cursor c = getRunning();
		c.moveToFirst();
		updateRunning(c.getLong(c.getColumnIndex("_id")), c.getString(c
				.getColumnIndex("type")), c.getLong(c
				.getColumnIndex("startTime")), endTime, c
				.getString(c.getColumnIndex("message")), tripEnd);
	}
	
	
	// ---updates an item---
	public boolean updateRunning(long rowId, String type, long startTime,
			long endTime, String message, long tripEnd) {
		ContentValues args = new ContentValues();
		// args.put(KEY_TYPE, type);
		// args.put(KEY_START_TIME, startTime);
		args.put(Const.KEY_END_TIME, endTime);
		args.put(Const.KEY_TRIP_END, tripEnd);

		// args.put(KEY_MESSAGE, message);
		// args.put(KEY_RECORDING, recording);

		return db.update(DATABASE_TABLE, args, Const.KEY_ROWID + "=" + rowId,
				null) > 0;
	}

	// ---updates an item---
	public boolean updateRecord(long rowId, String type, String message,
			long tripStart, long tripEnd) {
		ContentValues args = new ContentValues();
		args.put(Const.KEY_TYPE, type);
		// args.put(Const.KEY_START_TIME, startTime);
		// args.put(Const.KEY_END_TIME, endTime);
		args.put(Const.KEY_MESSAGE, message);
		args.put(Const.KEY_TRIP_START, tripStart);
		args.put(Const.KEY_TRIP_END, tripEnd);

		return db.update(DATABASE_TABLE, args, Const.KEY_ROWID + "=" + rowId,
				null) > 0;
	}

}
