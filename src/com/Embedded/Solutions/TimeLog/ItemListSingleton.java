/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                            ItemListSingleton.java                           *
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

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;

public class ItemListSingleton {

	private ArrayList<Item> m_itemList = null;

	private ItemListSingleton() {
		m_itemList = new ArrayList<Item>();
	}

	private static class SingletonHolder {
		public static final ItemListSingleton INSTANCE = new ItemListSingleton();
	}

	public static ItemListSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void updateMessageList(ArrayList<Item> list) {
		Iterator<Item> iterator = list.iterator();
		while (iterator.hasNext()) {
			m_itemList.add(iterator.next());
		}
	}

	public synchronized boolean add(Item Item) {
		m_itemList.add(Item);
		return true;
	}

	public synchronized boolean remove(int index) {
		Item tm = getElement(index);
		m_itemList.remove(index);
		return true;
	}

	public void clear() {
		m_itemList.clear();
	}

	public int getSize() {
		return m_itemList.size();
	}

	public boolean isEmpty() {
		return m_itemList.isEmpty();
	}

	public Item getElement(int index) {
		return m_itemList.get(index);
	}

	public ArrayList<Item> getItemList() {
		return m_itemList;
	}

	public void fillList(Context ctx) {

		DBAdapter ds = new DBAdapter(ctx);

		ds.open();
		Cursor c = ds.getAllItems();
		m_itemList.clear();

		if (c.moveToFirst()) {
			do {
				if (c.getLong(c.getColumnIndex(Const.KEY_END_TIME)) != -1) {
					m_itemList.add(new Item(c.getLong(0), c.getString(1), c
							.getString(4), c.getLong(2), c.getLong(3)));
				}
			} while (c.moveToNext());
		}
		ds.close();

	}

}
