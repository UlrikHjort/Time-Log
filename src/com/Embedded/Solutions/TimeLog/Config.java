/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                Config.java                                  *
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

import java.util.Locale;

import android.content.Context;
import android.os.Vibrator;

public class Config {
	
	private final static int DEMO = 0;
	private final static int FULL = 1;
	
	// Config Setup Begin:
	private final static int VERSION = FULL;	
	public static int LANG = Lang.DK;
	// Config Setup End.

	public static String getVersion() {
		return (VERSION == FULL) ? Lang.get(Lang.FULL) : Lang.get(Lang.DEMO); 
	}
	
	public static Boolean isFullVersion() {
		return true;
	}
	
	public static void setLanguage() {
		String language = Locale.getDefault().getDisplayLanguage();
		
		if (language.equals("dansk"))
			LANG = Lang.DK;
		else 
			LANG = Lang.ENG;
	}
	
}
