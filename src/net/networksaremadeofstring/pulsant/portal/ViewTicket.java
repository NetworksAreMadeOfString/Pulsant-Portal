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
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class ViewTicket extends Activity implements Runnable
{
	PortalAPI API = new PortalAPI();
	JSONObject TicketAPI = null;
	JSONArray Updates = null;
	String Success = "false";
	String ErrorMessage = "";
	JSONObject CurrentTicket = null;
	ProgressDialog dialog = null;
	ListView list = null;
	//List<TicketUpdates> listOfUpdates = null;
	List<TicketUpdates> listOfUpdates = new ArrayList<TicketUpdates>();
	
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.viewticket);
	    list = (ListView)findViewById(R.id.TicketUpdatesList);

	    final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				TicketUpdatesAdaptor adapter = new TicketUpdatesAdaptor(ViewTicket.this, listOfUpdates, API.SessionID);
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
    		    	TicketAPI = API.PortalQuery("updates", getIntent().getStringExtra("ticketID"));
    				Success = TicketAPI.getString("success");	
    			} 
    		    catch (JSONException e) 
    		    {
    		    	ErrorMessage = "An unrecoverable JSON Exception occured. Error Code: 1";
    		    	Log.e("APIFuncs",e.getMessage());
    			}
    		    
    		    if(Success.equals("false"))
    		    {
    		    	try 
    		    	{
    		    		Log.i("APIFuncs",TicketAPI.toString(3));
    		    		ErrorMessage = TicketAPI.getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		ErrorMessage = e.getMessage();
    		    		Log.e("APIFuncs",e.getMessage());
    				}
    		    	handler.sendEmptyMessage(0);
    		    }
    		    else
    		    {
    		    	Log.i("APIFuncs",TicketAPI.toString());
    		    	try
    		 	    {
    		    		Updates = TicketAPI.getJSONArray("updates");
    		    		ErrorMessage = "";
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	ErrorMessage = "There are no updates for this ticket. (Strange!)";
    		 	    }

    		        int TicketCount = Updates.length();
    		        
    		        if(TicketCount == 0)
    		        {
    		        	ErrorMessage = "There are no updates for this ticket.";
    		        	return;
    		        }
    		        
    		        for(int i = 0; i < TicketCount; i++)
    		        {
    					try 
    					{
    						CurrentTicket = Updates.getJSONObject(i);

    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}
    					
    					if(CurrentTicket != null)
    					{
    						listOfUpdates.add(new TicketUpdates(GetString("type"),
    	        				GetString("name"), 
    	        				GetInt("time"), 
    	        				GetString("content")));
    					}
    		        }
    		        handler.sendEmptyMessage(0);
    		    }
    		    
    		}
    	};
    	
    	dataPreload.start();
    	
    	Button AddReply = (Button) findViewById(R.id.AddReplyButton);
    	AddReply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent CreateTicketReplyIntent = new Intent(ViewTicket.this, AddTicketReply.class);
            	CreateTicketReplyIntent.putExtra("sessionid", API.SessionID);
            	CreateTicketReplyIntent.putExtra("ticketid", getIntent().getStringExtra("ticketID"));
            	ViewTicket.this.startActivity(CreateTicketReplyIntent);
            }
        });
	    
	    /*
	    try 
	    {
	    	TicketAPI = API.PortalQuery("updates", getIntent().getStringExtra("ticketID"));
			Success = TicketAPI.getString("success");	
		} 
	    catch (JSONException e) 
	    {
	    	UpdateErrorMessage("An unrecoverable JSON Exception occured. Error Code: 1");
	    	Log.e("APIFuncs",e.getMessage());
	    	//Toast.makeText(ColoLanding.this, "An unrecoverable JSON Exception occured.", Toast.LENGTH_LONG).show();
		}

	    if(Success == "false")
	    {
	    	try 
	    	{
	    		Log.i("APIFuncs",TicketAPI.toString(3));
				UpdateErrorMessage(TicketAPI.getString("msg"));
			} 
	    	catch (JSONException e) 
	    	{
	    		Log.e("APIFuncs",e.getMessage());
	    		UpdateErrorMessage("An unrecoverable JSON Exception occured.  Error Code: 2");
			}
	    }
	    else
	    {
	    	Log.i("APIFuncs",TicketAPI.toString());
	    	try
	 	    {
	    		Updates = TicketAPI.getJSONArray("updates");
	 	    	UpdateErrorMessage("");
	 	    }
	 	    catch (JSONException e) 
	 	    {
	 	    	UpdateErrorMessage("There are no updates for this ticket. (Strange!)");
	 	    }
	 	    
	 	    //OK lets actually do something useful
	        List<TicketUpdates> listOfUpdates = new ArrayList<TicketUpdates>();
	        int TicketCount = Updates.length();
	        
	        if(TicketCount == 0)
	        {
	        	UpdateErrorMessage("There are no updates for this ticket.");
	        	return;
	        }
	        
	        for(int i = 0; i < TicketCount; i++)
	        {
				try 
				{
					CurrentTicket = Updates.getJSONObject(i);

				} 
				catch (JSONException e1) 
				{
					Log.e("APIFuncs",e1.getMessage());
				}
				
				if(CurrentTicket != null)
				{
					listOfUpdates.add(new TicketUpdates(GetString("type"),
        				GetString("name"), 
        				GetInt("time"), 
        				GetString("content")));
				}
	        }
	        TicketUpdatesAdaptor adapter = new TicketUpdatesAdaptor(this, listOfUpdates, API.SessionID);
	        
	        list.setAdapter(adapter);
	    }*/
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
			
			ErrorMessage.setTextColor(-65536);
			ErrorMessage.setText(MessageText);
		}
	}
	
	public String GetString(String requiredString)
	{
		try 
    	{
			return CurrentTicket.getString(requiredString);
    	}
		catch (JSONException e) 
    	{
			return "Unknown";
    	}
	}
	
	public int GetInt(String requiredString)
	{
		try 
    	{
			return CurrentTicket.getInt(requiredString);
    	}
		catch (JSONException e) 
    	{
			return 0;
    	}
	}
	
	public boolean GetBool(String requiredString)
	{
		try 
    	{
			return CurrentTicket.getBoolean(requiredString);
    	}
		catch (JSONException e) 
    	{
			return false;
    	}
	}
	
	public void run() 
	{

		
	}
	

	/*private Handler handler = new Handler() 
	{
		public void handleMessage(Message msg) 
		{
			dialog.dismiss();
			TicketUpdatesAdaptor adapter = new TicketUpdatesAdaptor(ViewTicket.this, listOfUpdates, API.SessionID);
	        list.setAdapter(adapter);
		}
	};*/
	
	
}
