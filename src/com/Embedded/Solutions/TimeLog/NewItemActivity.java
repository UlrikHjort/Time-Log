/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                             NewItemActivity.java                            *
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

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class NewItemActivity extends Activity implements Button.OnClickListener
{

	private RadioButton m_radioPrivate;
	private RadioButton m_radioBusiness;

	private EditText m_descriptionField;
	private EditText m_tripStartField;
	private EditText m_tripStopField;

	private Button m_toggleButton;
	private Boolean m_running;

	// private DBAdapterSingleton ds;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.newitem_layout);

		m_radioPrivate = (RadioButton) findViewById(R.id.RadioPrivate);
		m_radioPrivate.setText(Lang.get(Lang.PRIVATE));
		m_radioPrivate.setTextColor(Color.WHITE);

		m_radioBusiness = (RadioButton) findViewById(R.id.RadioBusiness);
		m_radioBusiness.setText(Lang.get(Lang.BUSINESS));
		m_radioBusiness.setTextColor(Color.BLUE);

		m_descriptionField = (EditText) findViewById(R.id.Description);
		m_descriptionField.setHint(Lang.get(Lang.DESCRIPTION));

		m_tripStartField = (EditText) findViewById(R.id.TripStart);
		m_tripStartField.setHint(Lang.get(Lang.TRIP_START));

		m_tripStopField = (EditText) findViewById(R.id.TripStop);
		m_tripStopField.setHint(Lang.get(Lang.TRIP_STOP));

		m_toggleButton = (Button) findViewById(R.id.Start);
		m_toggleButton.setOnClickListener(NewItemActivity.this);

		m_running = initWithRunning();
		setState();

	}

	@Override
	public void onClick(View v)
	{
		try {
			if (v == m_toggleButton) {

				Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				long milliseconds = 50;
				vib.vibrate(milliseconds);

				if (m_running) {
					if (m_tripStopField.getText()
							.toString().length() == 0) {
						Toast toast = Toast
								.makeText(getApplicationContext(),
										Lang.get(Lang.FILLIN_TRIP_STOP),
										Toast.LENGTH_SHORT);
						toast.show();
						return;
					}
					// FM: Stop tid > start tid
					long tripEnd = Long
							.parseLong(m_tripStopField
									.getText()
									.toString());

					DBAdapter ds = new DBAdapter(this);

					ds.open();
					if (tripEnd < ds.getTripStart()) {
						ds.close();
						GenericAlertMessage
								.alert(this,
										Lang.get(Lang.TRIP_STOP_GT_TRIP_START));
						return;
					}
					ds.stopRunning(tripEnd);
					ds.close();

					toggleStatus();

				} else { // Start Recording:

					if (m_tripStartField.getText()
							.toString().length() == 0) {
						Toast toast = Toast
								.makeText(getApplicationContext(),
										Lang.get(Lang.FILLIN_TRIP_START),
										Toast.LENGTH_SHORT);
						toast.show();
						return;
					}

					if (m_descriptionField.getText()
							.toString()
							.contains(";")) {
						Toast toast = Toast
								.makeText(getApplicationContext(),
										Lang.get(Lang.NO_SEMICOLON),
										Toast.LENGTH_SHORT);
						toast.show();
						return;

					}
					String type;
					if (m_radioPrivate.isChecked())
						type = "" + Lang.PRIVATE;
					else
						type = "" + Lang.BUSINESS;

					DBAdapter ds = new DBAdapter(this);

					ds.open();

					if (!Config.isFullVersion()) {
						Cursor c = ds.getAllItems();
						if (c.getCount() >= 5) {
							GenericAlertMessage
									.alert(this,
											Lang.get(Lang.MAX_RECORDS_IN_DEMO_VERSION));
							ds.close();
							return;

						}
					}

					long tripStart = Long
							.parseLong(m_tripStartField
									.getText()
									.toString());
					ds.insertItem(type,
							System.currentTimeMillis(),
							m_descriptionField
									.getText()
									.toString(),
							tripStart);
					ds.close();
					toggleStatus();

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		m_running = savedInstanceState.getBoolean("CurrentState");
		setState();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putBoolean("CurrentState", m_running);
		super.onSaveInstanceState(savedInstanceState);

	}

	private void setState()
	{
		if (m_running == true) { // Recording:
			m_radioPrivate.setEnabled(false);
			m_radioBusiness.setEnabled(false);
			m_tripStartField.setEnabled(false);
			m_tripStartField.setFocusableInTouchMode(false);
			m_tripStopField.setEnabled(true);
			m_tripStopField.setFocusableInTouchMode(true);
			m_descriptionField.setEnabled(false);
			m_descriptionField.setFocusableInTouchMode(false);

			m_toggleButton.setText(Lang.get(Lang.STOP));

			Drawable d = findViewById(R.id.Start).getBackground();
			PorterDuffColorFilter filter = new PorterDuffColorFilter(
					Color.argb(100, 200, 0, 0),
					PorterDuff.Mode.SRC_ATOP);
			d.setColorFilter(filter);

		} else {
			m_radioPrivate.setEnabled(true);
			m_radioBusiness.setEnabled(true);
			m_tripStartField.setEnabled(true);
			m_tripStartField.setFocusableInTouchMode(true);
			m_tripStopField.setEnabled(false);
			m_tripStopField.setFocusableInTouchMode(false);
			m_descriptionField.setEnabled(true);
			m_descriptionField.setFocusableInTouchMode(true);
			m_tripStartField.setText("");
			m_tripStopField.setText("");
			m_descriptionField.setText("");
			m_toggleButton.setText(Lang.get(Lang.START));

			Drawable d = findViewById(R.id.Start).getBackground();
			findViewById(R.id.Start).invalidateDrawable(d);
			d.clearColorFilter();
		}
	}

	private void toggleStatus()
	{
		m_running = !m_running;
		setState();
	}

	private Boolean initWithRunning()
	{
		DBAdapter ds = new DBAdapter(this);

		ds.open();
		Cursor c = ds.getRunning();
		if (!c.moveToFirst()) {
			ds.close();
			return false;
		} else {

			if (c.getString(c.getColumnIndex(Const.KEY_TYPE))
					.equals(Lang.get(Lang.PRIVATE))) {
				m_radioPrivate.setChecked(true);
				m_radioBusiness.setChecked(false);
			} else {
				m_radioPrivate.setChecked(false);
				m_radioBusiness.setChecked(true);
			}

			m_descriptionField.setText(c.getString(c
					.getColumnIndex(Const.KEY_MESSAGE)));
			m_tripStartField.setText(""
					+ c.getLong(c.getColumnIndex(Const.KEY_TRIP_START)));

			long startTime = c.getLong(c
					.getColumnIndex(Const.KEY_START_TIME));
			ds.close();

			Toast toast = Toast
					.makeText(getApplicationContext(),
							Lang.get(Lang.RECORDING_STARTED)
									+ DateTimeFormat.getTime(startTime)
									+ Lang.get(Lang.CONTINUE),
							Toast.LENGTH_LONG);
			// TODO: play sound notification
			toast.show();

			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long milliseconds = 500;
			v.vibrate(milliseconds);

			return true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		File_IO.cleanUp();
	}
}
