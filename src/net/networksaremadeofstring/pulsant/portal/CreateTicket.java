/*
* Copyright (C) 2011 - Gareth Llewellyn
*
* This file is part of DediPortal - http://blog.NetworksAreMadeOfString.co.uk/DediPortal/
*
* This program is free software: you can redistribute it and/or modify it
* under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License
* for more details.
*
* You should have received a copy of the GNU General Public License along with
* this program. If not, see <http://www.gnu.org/licenses/>
*/
package net.networksaremadeofstring.pulsant.portal;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.networksaremadeofstring.pulsant.portal.R;

public class CreateTicket extends Activity
{
	PortalAPI API = new PortalAPI();
	int TicketID = 0;
	ProgressDialog dialog;
	String Subject, Message = "";
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		dialog = new ProgressDialog(this);
		dialog.setMessage("Loading...");
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.createticket);
	    
	    final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(TicketID == 0)
    			{
    				Toast.makeText(CreateTicket.this, "Ticket Creation Failed. Please try again later or email request@Pulsant.com", Toast.LENGTH_LONG).show();
    			}
    			else
    			{
    				Toast.makeText(CreateTicket.this, "Ticket Created! Returning to main screen...", Toast.LENGTH_SHORT).show();
    				finish();
    			}
    		}
    	};
    	
    	final Thread submitTicket = new Thread() 
    	{  
    		public void run() 
    		{
    			try 
            	{
    				TicketID = API.CreateTicket(Subject , Message);
    			} 
            	catch (JSONException e) 
            	{
            		TicketID = 0;
    			}
            	
            	handler.sendEmptyMessage(0);
    		}
    	};
    	

	    Button CreateTicketButton = (Button) findViewById(R.id.CreateTicketButton);
	    CreateTicketButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	dialog.show();
            	EditText SubjectET = (EditText) findViewById(R.id.Subject);
            	EditText MessageET = (EditText) findViewById(R.id.Message);
            	Subject = SubjectET.getText().toString();
            	Message = MessageET.getText().toString();
            	
            	submitTicket.start();
            	/*EditText Subject = (EditText) findViewById(R.id.Subject);
            	EditText Message = (EditText) findViewById(R.id.Message);

            	try 
            	{
					TicketID = API.CreateTicket(Subject.getText().toString() , Message.getText().toString());
				} 
            	catch (JSONException e) 
            	{
            		Toast.makeText(CreateTicket.this, "Ticket Creation Failed. Please try again later or email request@Pulsant.com", Toast.LENGTH_LONG).show();
				}
            	
            	
            	if(TicketID == 0)
            	{
            		Toast.makeText(CreateTicket.this, "Ticket Creation Failed. Please try again later or email request@Pulsant.com", Toast.LENGTH_LONG).show();
            	}
            	else
            	{
            		Toast.makeText(CreateTicket.this, "Ticket Created! Returning to main screen...", Toast.LENGTH_SHORT).show();
            		finish();
            	}*/
            }
        });
	}
}
