/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                            ItemListActivity.java                            *
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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ItemListActivity extends ListActivity implements
		Button.OnClickListener {

	private ItemListAdapter m_adapter;
	private ItemListSingleton ils = ItemListSingleton.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.items_list_layout);
		this.setTitle("Item List");

		ils.fillList(this);

		ItemListActivity.this.m_adapter = new ItemListAdapter(
				ItemListActivity.this, R.layout.items_layout, ils.getItemList());
		setListAdapter(ItemListActivity.this.m_adapter);

		m_adapter.notifyDataSetChanged();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	private void deleteTextMessage(final int position) {

		ItemListSingleton ils = ItemListSingleton.getInstance();

		final Item item = ils.getElement(position);

		final DBAdapter ds = new DBAdapter(this);

		new AlertDialog.Builder(this).setIcon(
				android.R.drawable.ic_dialog_alert).setTitle(
				Lang.get(Lang.DELETE_MESSAGE)).setMessage(item.getMessage())
				.setPositiveButton(Lang.get(Lang.OK),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								ds.open();
								ds.deleteItem(item.getStartTimeLong());
								ds.close();
								updateList();
							}
						}).setNegativeButton(Lang.get(Lang.CANCEL),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}

	@Override
	protected void onListItemClick(ListView l, View v, final int position,
			long id) {
		super.onListItemClick(l, v, position, id);

		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(this);
		builder.setTitle("");

		String arraySpinner[] = new String[2];

		arraySpinner[0] = Lang.get(Lang.EDIT);

		arraySpinner[1] = Lang.get(Lang.DELETE);

		builder.setItems(arraySpinner, new DialogInterface.OnClickListener() {
			// Click listener
			public void onClick(DialogInterface dialog, int index) {
				if (index == 0) {
					ItemListSingleton ils = ItemListSingleton.getInstance();

					Item item = ils.getElement(position);

					Intent intent = new Intent(ItemListActivity.this,
							EditItemActivity.class);
					Bundle bundle = new Bundle();
					bundle.putLong("ItemNo", item.getId());
					intent.putExtras(bundle);
					startActivity(intent);

				} else if (index == 1) {
					deleteTextMessage(position);
				}
			}
		});
		AlertDialog alert = builder.create();
		// display dialog box
		alert.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, Lang.get(Lang.DELETE_ALL));
		// menu.add(0, 1, 0, Lang.get(Lang.DELETE_RANGE));
		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			if (!ils.isEmpty()) {
				final DBAdapter ds = new DBAdapter(this);
				new AlertDialog.Builder(this).setIcon(
						android.R.drawable.ic_dialog_alert).setTitle(
						Lang.get(Lang.DELETE_ALL) + "?").setMessage("")
						.setPositiveButton(Lang.get(Lang.OK),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										ds.open();
										ds.deleteAll();
										ds.close();
										updateList();
									}
								}).setNegativeButton(Lang.get(Lang.CANCEL),
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();

			}
			break;
		// case 1:
		default:
			break;
		}
		return false;
	}

	private void updateList() {
		ils.clear();
		ils.fillList(this);
		m_adapter.notifyDataSetChanged();

	}
}
