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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class TicketLanding extends Activity
{
	PortalAPI API = new PortalAPI();
	JSONObject TicketAPI = null;
	JSONArray Tickets = null;
	String Success = "false";
	String ErrorMessage = "";
	List<OpenTickets> listOfTickets = new ArrayList<OpenTickets>();
	ListView list = null;
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.ticketlanding);
	    final ListView list = (ListView)findViewById(R.id.TicketList);
	    
	    final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				OpenTicketsAdaptor adapter = new OpenTicketsAdaptor(TicketLanding.this, listOfTickets, API.SessionID);
        	        list.setAdapter(adapter);
    			}
    			else
    			{
    				UpdateErrorMessage(ErrorMessage);
    			}
    		}
    	};
    	
    	Thread dataPreload = new Thread() 
    	{  
    		public void run() 
    		{
    			try 
    		    {
    		    	TicketAPI = API.PortalQuery("tickets", "none");
    				Success = TicketAPI.getString("success");	
    			} 
    		    catch (JSONException e) 
    		    {
    		    	ErrorMessage = "An unrecoverable JSON Exception occured.";
    		    	Success = "false";
    			}
    		    
    		    if(Success.equals("false"))
    		    {
    		    	try 
    		    	{
    		    		ErrorMessage = TicketAPI.getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		ErrorMessage = "A JSON parsing error prevented an exact error message to be determined.";
    				}
    		    }
    		    else
    		    {
    		    	Log.i("APIFuncs",TicketAPI.toString());
    		    	try
    		 	    {
    		    		Tickets = TicketAPI.getJSONArray("tickets");
    		    		ErrorMessage = "";
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	ErrorMessage = "There are no open tickets for your account.";
    		 	    }
    		 	    
    		 	    //OK lets actually do something useful
    		 	    //ListView list = (ListView)findViewById(R.id.TicketList);
    		        //List<OpenTickets> listOfTickets = new ArrayList<OpenTickets>();
    		        int TicketCount = Tickets.length();
    		        
    		        if(TicketCount == 0)
    		        {
    		        	ErrorMessage = "There are no open tickets for your account.";
    		        	handler.sendEmptyMessage(0);
    		        	return;
    		        }
    		        
    		        for(int i = 0; i < TicketCount; i++)
    		        {
    		        	JSONObject CurrentTicket = null;
    					try 
    					{
    						CurrentTicket = Tickets.getJSONObject(i);
    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}
    		        	try 
    		        	{
    		        		listOfTickets.add(new OpenTickets(CurrentTicket.getString("status"),
    		        				CurrentTicket.getInt("id"),
    		        				CurrentTicket.getString("server"), 
    		        				CurrentTicket.getString("email"), 
    		        				CurrentTicket.getString("subject"), 
    		        				CurrentTicket.getInt("createdat"),
    		        				CurrentTicket.getInt("lastupdate"), 
    		        				false));
    		        				//CurrentTicket.getBoolean("subscriber")));
    					} 
    		        	catch (JSONException e) 
    		        	{
    		        		Log.e("APIFuncs",e.getMessage());
    					}
    		        }
    		    }
    		        
    			handler.sendEmptyMessage(0);
    		}
    	};
    	
    	dataPreload.start();
	}
	
	public void UpdateErrorMessage(String MessageText)
	{
		TextView ErrorMessage = (TextView) findViewById(R.id.ErrorMessageText);
		if(MessageText.length() == 0)
		{
			ErrorMessage.setHeight(0);
		}
		else
		{
			ErrorMessage.setHeight(24);
			ErrorMessage.setTextColor(-65536);
			ErrorMessage.setText(MessageText);
		}
	}
	
	public void ReadTicket(String TicketID, String SessionID)
	{
		Intent TicketIntent = new Intent(TicketLanding.this, ViewTicket.class);
    	TicketIntent.putExtra("sessionid", API.SessionID);
    	TicketIntent.putExtra("ticketID", TicketID);
    	TicketLanding.this.startActivity(TicketIntent);
	}
}
