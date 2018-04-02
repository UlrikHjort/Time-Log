/*******************************************************************************
 *                                                                             *
 *                                  Time Log                                   *
 *                                                                             *
 *                            EdititemActivity.java                            *
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
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class EditItemActivity  extends Activity implements OnClickListener {

	
	private RadioButton m_radioPrivate;
	private RadioButton m_radioBusiness;

	private EditText m_descriptionField;
	private EditText m_tripStartField;
	private EditText m_tripStopField;

	private TextView m_descriptionFieldLabel;
	private TextView m_tripStartFieldLabel;
	private TextView m_tripStopFieldLabel;
	
	
	private Button m_updateButton;
	
	private int m_id;

	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_item_layout);
		
		Bundle extras = getIntent().getExtras();
		
		long id = extras.getLong("ItemNo");
		
		
		m_radioPrivate = (RadioButton) findViewById(R.id.RadioPrivateEdit);
		m_radioPrivate.setText(Lang.get(Lang.PRIVATE));
		m_radioPrivate.setTextColor(Color.WHITE);

		
		m_radioBusiness = (RadioButton) findViewById(R.id.RadioBusinessEdit);
		m_radioBusiness.setText(Lang.get(Lang.BUSINESS));
		m_radioBusiness.setTextColor(Color.BLUE);


		
		m_descriptionField = (EditText) findViewById(R.id.DescriptionEdit);
		m_descriptionFieldLabel = (TextView) findViewById(R.id.DescriptionEditLabel);
		m_descriptionFieldLabel.setText(Lang.get(Lang.DESCRIPTION));
		
		m_tripStartField = (EditText) findViewById(R.id.TripStartEdit);
		m_tripStartFieldLabel = (TextView) findViewById(R.id.TripStartEditLabel);
		m_tripStartFieldLabel.setText(Lang.get(Lang.TRIP_START));
		
		m_tripStopField = (EditText) findViewById(R.id.TripStopEdit);
		m_tripStopFieldLabel = (TextView) findViewById(R.id.TripStopEditLabel);
		m_tripStopFieldLabel.setText(Lang.get(Lang.TRIP_STOP));

		m_updateButton = (Button) findViewById(R.id.Update);
		m_updateButton.setText(Lang.get(Lang.UPDATE));
		m_updateButton.setOnClickListener(EditItemActivity.this);

		
		
		DBAdapter ds = new DBAdapter(this);

		ds.open();
        Cursor c = ds.getRunning(id);
        c.moveToFirst();
        
        m_id = c.getInt(c.getColumnIndex(Const.KEY_ROWID));
        
        if (c.getString(c.getColumnIndex(Const.KEY_TYPE)).equals(""+Lang.PRIVATE)) {
        	m_radioPrivate.setChecked(true);
        	m_radioBusiness.setChecked(false);
        } else {
        	m_radioPrivate.setChecked(false);
        	m_radioBusiness.setChecked(true);     	
        }
        
        m_descriptionField.setText(c.getString(c.getColumnIndex(Const.KEY_MESSAGE)));
        m_tripStartField.setText(""+c.getLong(c.getColumnIndex(Const.KEY_TRIP_START)));
        m_tripStopField.setText(""+c.getLong(c.getColumnIndex(Const.KEY_TRIP_END)));
        
        
        
        
		ds.close();
		



	}



	@Override
	public void onClick(View v) {
       if (v == m_updateButton) {
    	   
			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long milliseconds = 50;
			vib.vibrate(milliseconds);

    	   
    	    if ((m_tripStartField.getText().toString().length() == 0) || (m_tripStopField.getText().toString().length() == 0))  {
    	        GenericAlertMessage.alert(this, Lang.get(Lang.NO_EMPTY_ODOMETER_FIELDS));
    	        return;
    	    }
    	    
			long tripStart = Long.parseLong(m_tripStartField.getText().toString());
			long tripEnd = Long.parseLong(m_tripStopField.getText().toString());

			if (tripEnd < tripStart) {
				GenericAlertMessage.alert(this, Lang
						.get(Lang.TRIP_STOP_GT_TRIP_START));
				return;
			}
			String type = m_radioPrivate.isChecked() ? ""+Lang.PRIVATE : ""+Lang.BUSINESS;
			

			String description = m_descriptionField.getText().toString().replace("\n", " ");
			
			DBAdapter ds = new DBAdapter(this);

			ds.open();

			ds.updateRecord(m_id, type, description, tripStart, tripEnd);

			ds.close();
            this.finish();
       }
	}
	
	

}
