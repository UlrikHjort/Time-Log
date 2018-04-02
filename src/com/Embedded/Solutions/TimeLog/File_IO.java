/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                File_IO.java                                 *
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

public class File_IO {

	// private Context m_context;

	// File_IO(Context ctx) { m_context = ctx; }

	public static void cleanUp() {

		try {
			File directory = new File(Environment.getExternalStorageDirectory()
					.toString()
					+ "/DrivingLog/");

			File[] files = directory.listFiles();

			for (File f : files) {
				f.delete();
			}

			directory.delete();
		} catch (Exception e) {
		}

	}

	private static String getRecords(Context m_context, long from, long to) {
		DBAdapter ds = new DBAdapter(m_context);

		ds.open();
		// Cursor c = ds.getAllItems();

		Cursor c = ds.getRange(from, to);

		String data = Lang.get(Lang.FILE_HEADLINE);
		if (c.moveToFirst()) {
			do {
				data += Lang.get(Integer.parseInt(c.getString(c.getColumnIndex(Const.KEY_TYPE))))
						+ ";"
						+ DateTimeFormat.getDateTime(c.getLong(c
								.getColumnIndex(Const.KEY_START_TIME)))
						+ ";"
						+ DateTimeFormat.getDateTime(c.getLong(c
								.getColumnIndex(Const.KEY_END_TIME)))
						+ ";"
						+ c.getLong(c.getColumnIndex(Const.KEY_TRIP_START))
						+ ";"
						+ c.getLong(c.getColumnIndex(Const.KEY_TRIP_END))
						+ ";" +
							 (c.getLong(c.getColumnIndex(Const.KEY_TRIP_END)) - c
									.getLong(c.getColumnIndex(Const.KEY_TRIP_START)))
							+ ";" + c.getString(c.getColumnIndex(Const.KEY_MESSAGE)) + ";";// + "\n";

				
				 if (c.getString(c.getColumnIndex(Const.KEY_TYPE)).equals(Lang.get(Lang.BUSINESS))) {
						data += (c.getLong(c.getColumnIndex(Const.KEY_TRIP_END)) - c
								.getLong(c.getColumnIndex(Const.KEY_TRIP_START)))
						+ ";" + "\n";
				 } else {
						data += ";" + (c.getLong(c.getColumnIndex(Const.KEY_TRIP_END)) - c
								.getLong(c.getColumnIndex(Const.KEY_TRIP_START)))
						+ ";" + "\n";
					 
				 }
				
			} while (c.moveToNext());
		}
		ds.close();

		return data;

	}

	public static String writeFile(Context ctx, long from, long toUpperLimit,
			long to) {
		String storageStatus = Environment.getExternalStorageState(); // ok =
		// mounted

		if (storageStatus.equals("mounted")) {
			String fileName = DateTimeFormat.getDate(from) + "---"
					+ DateTimeFormat.getDate(to) + ".csv";

			File directory = new File(Environment.getExternalStorageDirectory()
					.toString()
					+ "/DrivingLog/");

			directory.mkdirs();

			File outputFile = new File(directory, fileName);

			try {
				FileOutputStream fos = new FileOutputStream(outputFile);
				fos.write(getRecords(ctx, from, toUpperLimit).getBytes());

				fos.close();
				
				File sendFile = new File(Environment
						.getExternalStorageDirectory().toString()
						+ "/DrivingLog/", fileName);

				Uri uri = Uri.fromFile(sendFile);
				Intent i = new Intent(Intent.ACTION_SEND);

				i.putExtra(Intent.EXTRA_SUBJECT, fileName);
				i.putExtra(Intent.EXTRA_TEXT, fileName);
				i.putExtra(Intent.EXTRA_STREAM, uri);
				i.setType("text/plain");
				ctx.startActivity(Intent.createChooser(i, "Send mail"));

				return fileName;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// TODO: Error
		}
		return "";

	}

	public static String writeErrorMessage(Context ctx, String errorMsg) {
		String storageStatus = Environment.getExternalStorageState(); // ok =
		// mounted

		if (storageStatus.equals("mounted")) {
			String fileName = "dump.gz";

			File directory = new File(Environment.getExternalStorageDirectory()
					.toString()
					+ "/DrivingLog/");

			directory.mkdirs();

			File outputFile = new File(directory, fileName);

			try {

				GZIPOutputStream fos = new GZIPOutputStream(
						new FileOutputStream(outputFile));
				fos.write(errorMsg.getBytes());

				fos.finish();
				fos.close();
				
				File sendFile = new File(Environment
						.getExternalStorageDirectory().toString()
						+ "/DrivingLog/", fileName);

				Uri uri = Uri.fromFile(sendFile);
				Intent i = new Intent(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"SOMEEMAIL@FOO.BAR"});
				i.putExtra(Intent.EXTRA_SUBJECT, "[DrivingLog Failure Report]");
				i.putExtra(Intent.EXTRA_TEXT, fileName);
				i.putExtra(Intent.EXTRA_STREAM, uri);
				i.setType("text/plain");
				ctx.startActivity(Intent.createChooser(i, "Send mail"));

				return fileName;

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// TODO: Error
		}
		return "";

	}

}
