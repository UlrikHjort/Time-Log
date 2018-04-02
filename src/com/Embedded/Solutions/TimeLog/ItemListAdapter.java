/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                              ItemListAdapter.java                           *
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



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ItemListAdapter extends ArrayAdapter<Item>{

	private ArrayList<Item> items;

	
	public ItemListAdapter(Context context, int textViewResourceId,
			ArrayList<Item> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.items_layout, null);
		}

		Item item = items.get(position);

		
		if (item != null) {
			TextView date = (TextView) v.findViewById(R.id.Date);
			TextView description = (TextView) v.findViewById(R.id.Description);

			date.setText(item.getEndTimeString());

			if (item.getType().equals(""+Lang.BUSINESS))
				date.setTextColor(Color.BLUE);
			else	
			    date.setTextColor(Color.WHITE);
			
			
			
			description.setText(item.getMessage());
			description.setTextColor(Color.BLACK);
				


			}
		return v;
	}

	
}
