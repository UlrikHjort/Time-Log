/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                                LogModule.java                               *
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
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;

public class LogModule {

	private static String makeString(Exception exception) {
		String RMT_Version = "1.0";
		/*******
		 * String Java_Class_Version = System.getProperty("java.class.version");
		 * String VM_Vendor = System.getProperty("java.vendor"); String
		 * VM_Libraries_Version =
		 * System.getProperty("java.specification.version"); String
		 * VM_Libraries_Vendor =
		 * System.getProperty("java.specification.vendor"); String
		 * VM_Libraries_Name = System.getProperty("java.specification.name");
		 * 
		 * String VM_Implementation_Version =
		 * System.getProperty("java.vm.version"); String
		 * VM_Implementation_Vendor = System.getProperty("java.vm.vendor");
		 * String VM_Implementation_Name = System.getProperty("java.vm.name");
		 * String VM_Specification_Version =
		 * System.getProperty("java.vm.specification.version"); String
		 * VM_Specification_Vendor =
		 * System.getProperty("java.vm.specification.vendor"); String
		 * VM_Specification_Name =
		 * System.getProperty("java.vm.specification.name"); String
		 * OS_Architecture = System.getProperty("os.arch	OS architecture");
		 * String OS_Name = System.getProperty("os.name"); String OS_Version =
		 * System.getProperty("os.version");
		 ********/

		String buildInfo = "MANUFACTURER: " + Build.MANUFACTURER + "\nMODEL: "
				+ Build.MODEL + "\nCPU: " + Build.CPU_ABI + "\n";

		String systemInfo = "java.class.version: "
				+ System.getProperty("java.class.version") + "\n"
				+ "java.vendor: " + System.getProperty("java.vendor") + "\n"
				+ "java.specification.version: "
				+ System.getProperty("java.specification.version") + "\n"
				+ "java.specification.vendor: "
				+ System.getProperty("java.specification.vendor") + "\n"
				+ "java.specification.name: "
				+ System.getProperty("java.specification.name") + "\n"
				+ "java.vm.version: " + System.getProperty("java.vm.version")
				+ "\n" + "java.vm.vendor: "
				+ System.getProperty("java.vm.vendor") + "\n"
				+ "java.vm.name: " + System.getProperty("java.vm.name") + "\n"
				+ "java.vm.specification.version: "
				+ System.getProperty("java.vm.specification.version") + "\n"
				+ "java.vm.specification.vendor: "
				+ System.getProperty("java.vm.specification.vendor") + "\n"
				+ "ava.vm.specification.name: "
				+ System.getProperty("java.vm.specification.name") + "\n"
				+ "os.arch	OS architecture: "
				+ System.getProperty("os.arch	OS architecture") + "\n"
				+ "os.name: " + System.getProperty("os.name") + "\n"
				+ "os.version: " + System.getProperty("os.version") + "\n"
				+ "Android SDK: " + android.os.Build.VERSION.SDK_INT + "\n";

		String timeStamp = "-";
		try {
			long timestamp = System.currentTimeMillis();

			java.util.Date today = new java.util.Date(timestamp);

			java.text.DateFormat df = new java.text.SimpleDateFormat();

			timeStamp = df.format(today).toString() + "\n";

		} catch (Exception e1) {
		}

		String stackTrace = "Exception: " + exception.getMessage() + "\n";
		StackTraceElement[] stackTraceList = exception.getStackTrace();

		for (StackTraceElement stackElement : stackTraceList) {
			stackTrace += (stackElement.getFileName() + ":::"
					+ stackElement.getClassName() + ":::"
					+ stackElement.getMethodName() + ":"
					+ stackElement.getLineNumber() + "\n");

		}

		return timeStamp + buildInfo + systemInfo + stackTrace;

	}

	public static void write(final Context ctx, Exception exception) {

		final String exceptionInfo = makeString(exception);

		new AlertDialog.Builder(ctx)
				.setIcon(android.R.drawable.ic_dialog_alert).setTitle(
						Lang.get(Lang.DELETE_ALL) + "?").setMessage("")
				.setPositiveButton(Lang.get(Lang.OK),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								File_IO.writeErrorMessage(ctx, exceptionInfo);

							}
						}).setNegativeButton(Lang.get(Lang.CANCEL),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();

	}
}
