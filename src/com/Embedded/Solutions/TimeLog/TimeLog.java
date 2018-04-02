/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                TimeLog.java                                  *
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



import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class TimeLog extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        Config.setLanguage();
		this.setTitle(Lang.getTitle());
		
		
		// TODO: Remove
//		Test.fillDB(this);
//		File_IO.writeErrorMessage(this, "Dette er en est errorMsg");

        
		Resources res = getResources(); // Resource object to get Drawables
		final TabHost tabHost = getTabHost(); // The activity TabHost
         
		Intent intent = new Intent(this, NewItemActivity.class);
		tabHost.addTab(tabHost.newTabSpec("Ny Reg")
				.setIndicator("Ny Reg", res.getDrawable(R.drawable.ic_menu_add))
				.setContent(intent));

		
		intent = new Intent(this, ItemListActivity.class);
		tabHost.addTab(tabHost.newTabSpec("Nyt Projekt")
				.setIndicator("Nyt Projekt", res.getDrawable(R.drawable.ic_menu_sort_by_size))
				.setContent(intent));
		
		
		
		intent = new Intent(this, ItemListActivity.class);
		tabHost.addTab(tabHost.newTabSpec(Lang.get(Lang.LIST))
				.setIndicator(Lang.get(Lang.LIST), res.getDrawable(R.drawable.ic_menu_sort_by_size))
				.setContent(intent));

		intent = new Intent(this, SendItemsActivity.class);
		tabHost.addTab(tabHost
				.newTabSpec(Lang.get(Lang.SEND))
				.setIndicator(Lang.get(Lang.SEND), res.getDrawable(R.drawable.ic_menu_send))
				.setContent(intent));

		intent = new Intent(this, HelpActivity.class);
		tabHost.addTab(tabHost
				.newTabSpec(Lang.get(Lang.HELP))
				.setIndicator(Lang.get(Lang.HELP), res.getDrawable(R.drawable.ic_menu_help))
				.setContent(intent));
		

		
		tabHost.setCurrentTab(0);

		
		// Set tabs Colors
		tabHost.setBackgroundColor(Color.BLACK);
		tabHost.getTabWidget().setBackgroundColor(Color.BLACK);
		
		
	    ItemListSingleton ils = ItemListSingleton.getInstance();
   
	    tabHost.setOnTabChangedListener(new OnTabChangeListener()
        {
        public void onTabChanged(String tabId)
            {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tabHost.getApplicationWindowToken(), 0);
            }
        });
	    
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, Lang.get(Lang.HELP));
		// menu.add(0, 1, 0, Lang.get(Lang.DELETE_RANGE));
		return super.onCreateOptionsMenu(menu);

	}

}
