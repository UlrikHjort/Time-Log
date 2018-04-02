/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                           GenericAlertMessage.java                          *
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

public class GenericAlertMessage {
	
	private GenericAlertMessage() {}
	
	public static void alert(Context context, String alertMessage) {
		
        // Create the alert box
        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        // Set the message to display
        alertbox.setMessage(alertMessage);

        // Add a neutral button to the alert box and assign a click listener
        alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

            // Click listener on the neutral button of alert box
            public void onClick(DialogInterface arg0, int arg1) {

             }
        });

         // show the alert box
        alertbox.show();
		
		
	}

}
