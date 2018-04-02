/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                             DateTimeFormat.java                             *
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

import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.ParseException;
import android.text.format.DateFormat;
import android.util.Log;

public class DateTimeFormat {

	
	public static long dateToLong(String strDate) {
		   try {
//			      String str_date="11-June-07";
			      SimpleDateFormat formatter ; 
			      Date date; 
			      formatter = new SimpleDateFormat("dd-MM-yyyy");
			      date = (Date)formatter.parse(strDate); 
			      return date.getTime();
			    
			    } catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return -1;
		
	}

	public static long dateToLongTest(String strDate) {
		   try {
//			      String str_date="11-June-07";
			      SimpleDateFormat formatter ; 
			      Date date; 
			      formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			      date = (Date)formatter.parse(strDate); 
			      return date.getTime();
			    
			    } catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return -1;
		
	}
	
	
	public static String getDate(long timestamp) {
		try {
			java.util.Date today = new java.util.Date(timestamp);

			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"dd-MM-yyyy");

			return df.format(today).toString();

		} catch (Exception e) {
			return e.toString();
		}
	}

	
	public static String getDateTime(long timestamp) {
		try {
			java.util.Date today = new java.util.Date(timestamp);

			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"MM/dd/yyyy HH:mm:ss");

			return df.format(today).toString();

		} catch (Exception e) {
			return e.toString();
		}
	}
	
	
	
	public static String getTime(long timestamp) {

		try {
			java.util.Date today = new java.util.Date(timestamp);

			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"dd MMM yyyy\nHH:mm");

			return df.format(today).toString();

		} catch (Exception e) {
			return e.toString();
		}

	}

}
