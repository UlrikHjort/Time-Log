/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                  Test.java                                  *
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

import android.content.Context;

public class Test {
	
	public static void fillDB(Context ctx) {
		DBAdapter ds = new DBAdapter(ctx);

		ds.open();
		ds.insertItem("Firma", DateTimeFormat.dateToLongTest("17-12-2009 12:15"),
				"test 1", 123);
		ds.stopRunningTest(456, DateTimeFormat.dateToLongTest("17-12-2009 15:15"));

		
		
		ds.insertItem("Privat", DateTimeFormat.dateToLongTest("31-12-2009 23:00"),
				"test 123", 1);
		ds.stopRunningTest(456, DateTimeFormat.dateToLongTest("01-01-2010 00:01"));

		
		ds.insertItem("Privat", DateTimeFormat.dateToLongTest("23-02-2011 15:00"),
				"test 123", 1);
		ds.stopRunningTest(456, DateTimeFormat.dateToLongTest("23-02-2011 16:01"));
		
		ds.close();

	}

}
