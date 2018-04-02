/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                            SendItemActivity.java                            *
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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class SendItemsActivity extends Activity implements OnClickListener {

	private Button m_sendData;

	private TextView m_dateFromLabel;
	private TextView m_dateToLabel;

	private TextView m_dateFrom;
	private TextView m_dateTo;

	private int m_fromYear;
	private int m_fromMonth;
	private int m_fromDay;

	private int m_toYear;
	private int m_toMonth;
	private int m_toDay;

	private final int DATE_FROM = 1;
	private final int DATE_TO = 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.send_items_layout);

		m_sendData = (Button) findViewById(R.id.Send);
		m_sendData.setOnClickListener(SendItemsActivity.this);

		m_dateFromLabel = (TextView) findViewById(R.id.fromDateLabel);
		m_dateFromLabel.setText(Lang.get(Lang.FROM));
		m_dateFromLabel.setTextColor(Color.BLACK);

		m_dateFrom = (TextView) findViewById(R.id.fromDateDisplay);
		m_dateFrom.setBackgroundColor(Color.LTGRAY);
		m_dateFrom.setTextColor(Color.BLACK);

		m_dateToLabel = (TextView) findViewById(R.id.toDateLabel);
		m_dateToLabel.setText(Lang.get(Lang.TO));
		m_dateToLabel.setTextColor(Color.BLACK);

		m_dateTo = (TextView) findViewById(R.id.toDateDisplay);
		m_dateTo.setBackgroundColor(Color.LTGRAY);
		m_dateTo.setTextColor(Color.BLACK);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// DB handling:
		DBAdapter ds = new DBAdapter(this);

		ds.open();

		Cursor c = ds.getRunning();

		if (c.moveToFirst()) {

			m_dateFrom.setText("---");
			m_dateTo.setText("---");

			GenericAlertMessage.alert(this, Lang.get(Lang.CANNOT_SEND_DATA_WHILE_RECORDING));
			m_sendData.setVisibility(View.INVISIBLE);
			return;
		}

		c = ds.getAllItems();

		if (c.moveToFirst() == false) {
			
			m_dateFrom.setText("---");
			m_dateTo.setText("---");

			GenericAlertMessage.alert(this, Lang.get(Lang.NO_DATA_TO_SEND));
			m_sendData.setVisibility(View.INVISIBLE);

		} else {
			m_sendData.setVisibility(View.VISIBLE);

			m_dateFrom.setText(DateTimeFormat.getDate(c.getLong(c
					.getColumnIndex(Const.KEY_START_TIME))));

			c.moveToLast();
			m_dateTo.setText(DateTimeFormat.getDate(c.getLong(c
					.getColumnIndex(Const.KEY_END_TIME))));

			ds.close();

			// DB handling end.

			String[] dateComponents = null;

			dateComponents = m_dateFrom.getText().toString().split("-");

			m_fromYear = Integer.parseInt(dateComponents[2]);
			m_fromMonth = Integer.parseInt(dateComponents[1]);
			m_fromDay = Integer.parseInt(dateComponents[0]);

			dateComponents = m_dateTo.getText().toString().split("-");

			m_toYear = Integer.parseInt(dateComponents[2]);
			m_toMonth = Integer.parseInt(dateComponents[1]);
			m_toDay = Integer.parseInt(dateComponents[0]);
		}

	}

	@Override
	public void onClick(View v) {
		if (v == m_sendData) {

			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long milliseconds = 50;
			vib.vibrate(milliseconds);

			if (Config.isFullVersion()) {
				String fileName = File_IO.writeFile(this, DateTimeFormat
						.dateToLong(getDateFrom()), DateTimeFormat
						.dateToLong(getDateToUpperLimit()), DateTimeFormat
						.dateToLong(getDateTo()));
			} else {
				GenericAlertMessage.alert(this, Lang
						.get(Lang.DEMO_VERSION_MESSAGE));
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_FROM:
			return new DatePickerDialog(this, fromDateSetListener, m_fromYear,
					m_fromMonth - 1, m_fromDay);
		case DATE_TO:
			return new DatePickerDialog(this, toDateSetListener, m_toYear,
					m_toMonth - 1, m_toDay);
		default:
			break;

		}
		return null;
	}

	private String getDate(int day, int month, int year) {
		return String.format("%02d", day) + "-" + String.format("%02d", month)
				+ "-" + year;

	}

	private String getDateFrom() {
		return String.format("%02d", m_fromDay) + "-"
				+ String.format("%02d", m_fromMonth) + "-" + m_fromYear;

	}

	private String getDateTo() {
		return String.format("%02d", m_toDay) + "-"
				+ String.format("%02d", m_toMonth) + "-" + m_toYear;
	}

	private String getDateToUpperLimit() {
		return String.format("%02d", (m_toDay + 1)) + "-"
				+ String.format("%02d", m_toMonth) + "-" + m_toYear;
	}

	Boolean isFromGreaterThanTo() {

		if (DateTimeFormat.dateToLong(getDateFrom()) > DateTimeFormat
				.dateToLong(getDateTo())) {
			return true;
		} else {
			return false;
		}

	}

	private void update() {

		m_dateFrom.setText(getDateFrom());
		m_dateTo.setText(getDateTo());

	}

	private void warningMessage(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg,
				Toast.LENGTH_LONG);
		toast.show();

	}

	private DatePickerDialog.OnDateSetListener fromDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			if (DateTimeFormat.dateToLong(getDateTo()) < DateTimeFormat
					.dateToLong(getDate(dayOfMonth, monthOfYear + 1, year))) {
				warningMessage(Lang.getErrorMessage(Lang.START_BEFORE_END_DATE));

			} else {
				m_fromYear = year;
				m_fromMonth = monthOfYear + 1;
				m_fromDay = dayOfMonth;
				update();
			}
		}
	};

	private DatePickerDialog.OnDateSetListener toDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (DateTimeFormat.dateToLong(getDateFrom()) > DateTimeFormat
					.dateToLong(getDate(dayOfMonth, monthOfYear + 1, year))) {
				warningMessage(Lang.getErrorMessage(Lang.END_BEFORE_START_DATE));

			} else {
				m_toYear = year;
				m_toMonth = monthOfYear + 1;
				m_toDay = dayOfMonth;
				update();
			}
		}
	};

	public void onClickDateView(View v) {

		Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long milliseconds = 50;
		vib.vibrate(milliseconds);

		if (v == m_dateFrom) {
			showDialog(DATE_FROM);
		} else if (v == m_dateTo) {
			showDialog(DATE_TO);
		}
	}
}
