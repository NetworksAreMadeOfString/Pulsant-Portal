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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.networksaremadeofstring.pulsant.portal.R;

public class ManagedServerLanding extends SherlockActivity
{

	PortalAPI API = new PortalAPI();
	JSONObject Servers = null;
	JSONArray ManagedServers = null;
	String Success = "false";
	JSONObject CurrentServer = null;
	ListView list = null;
    List<ManagedServer> listOfManagedServers = new ArrayList<ManagedServer>();
    String ErrorMessage = "";
    ActionMode mActionMode;
    
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.managedserverlanding);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Managed Servers");
		
		//temp
		list = (ListView)findViewById(R.id.ManagedServerList);
		final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				
    				ManagedServerAdaptor adapter = new ManagedServerAdaptor(ManagedServerLanding.this, listOfManagedServers, API.SessionID);
    		        list.setAdapter(adapter);
    		        /*list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    		        list.setOnLongClickListener(new View.OnLongClickListener() {
				    	public boolean onLongClick(View view) 
				    	{
				            if (mActionMode != null) 
				            {
				            	Log.i("onLongClick","Nuhuh");
				                return false;
				            }
				
				            // Start the CAB using the ActionMode.Callback defined above
				            mActionMode = startActionMode(mActionModeCallback);
				            view.setSelected(true);
				            return true;
				        }
				
				    });*/
    			}
    			else
    			{
    				UpdateErrorMessage(ErrorMessage);
    			}
    		}
    	};
    	
    	Thread dataPreload = new Thread() 
    	{  
    		int ServerCount = 0;
    		public void run() 
    		{
    			try 
    		    {
    				Servers = API.PortalQuery("servers", "none");
    				Success = Servers.getString("success");
    			} 
    		    catch (JSONException e) 
    		    {
    		    	//UpdateErrorMessage("An unrecoverable JSON Exception occured.");
    		    	ErrorMessage = "An unrecoverable JSON Exception occured.";
    			}
    		    
    		    if(Success.equals("false"))
    		    {
    		    	try 
    		    	{
    					//UpdateErrorMessage(Servers.getString("msg"));
    		    		ErrorMessage = Servers.getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		//UpdateErrorMessage("An unrecoverable JSON Exception occured.");
    		    		ErrorMessage = "An unrecoverable JSON Exception occured.";
    				}
    		    }
    		    else
    		    {
    		    	Log.i("APIFuncs",Servers.toString());
    		    	try
    		 	    {
    		    		ManagedServers = Servers.getJSONArray("servers");
    		 	    	//UpdateErrorMessage("");
    		    		ErrorMessage = "";
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	//UpdateErrorMessage("There are no Managed servers in your account.");
    		 	    	ErrorMessage = "There are no Managed servers in your account.";
    		 	    }
    		 	    
    		 	    try
    		 	    {
    		 	    	ServerCount = ManagedServers.length();
    		 	    }
    		 	    catch (Exception e) 
    		 	    {
    		 	    	ServerCount = 0;
    		 	    }
    		        
    		        if(ServerCount == 0)
    		        {
    		        	ErrorMessage = "There are no Managed servers for your account.";
    		        	handler.sendEmptyMessage(0);
    		        	return;
    		        }
    		        
    		        for(int i = 0; i < ServerCount; i++)
    		        {
    		        	
    					try 
    					{
    						CurrentServer = ManagedServers.getJSONObject(i).getJSONObject("Server");
    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}

    	        		//Log.e("APIFuncs",CurrentServer.toString(3));
    	        		listOfManagedServers.add(new ManagedServer(GetString("ip"),
    	        				GetString("servercode"),
    	        				GetString("description"),
    	        				GetString("facility"),
    	        				GetInt("bandwidth"),
    	        				GetString("bandwidthTotal"),
    	        				GetBool("monitored"),
    	        				GetInt("transferlimit"),
    	        				GetString("software"),
    	        				GetString("state"),
    	        				GetBool("managedbackupenabled")));
    		        }
    		    }
    		    handler.sendEmptyMessage(0);
    		}
    	};
    		
    	
    	dataPreload.start();
	   
	}
	
	public String GetString(String requiredString)
	{
		try 
    	{
			return CurrentServer.getString(requiredString);
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
			return CurrentServer.getInt(requiredString);
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
			return CurrentServer.getBoolean(requiredString);
    	}
		catch (JSONException e) 
    	{
			return false;
    	}
	}
	
	public void UpdateErrorMessage(String MessageText)
	{
		TextView ErrorMessage = (TextView) findViewById(R.id.ErrorMessageText);
		ErrorMessage.setTextColor(-65536);
		ErrorMessage.setText(MessageText);
	}
	
	public void ViewServerDetails(String DEDCode, String SessionID)
	{
		Boolean found = false;
		for(int i = 0; i < listOfManagedServers.size(); i++)
		{
			if(listOfManagedServers.get(i).getservercode().equals(DEDCode) == true)
			{
				Intent ViewServerIntent = new Intent(ManagedServerLanding.this, ViewServer.class);
				ViewServerIntent.putExtra("ip", listOfManagedServers.get(i).getip());
				ViewServerIntent.putExtra("servercode", listOfManagedServers.get(i).getservercode());
				ViewServerIntent.putExtra("description", listOfManagedServers.get(i).getdescription());
				ViewServerIntent.putExtra("facility", listOfManagedServers.get(i).getfacility());
				ViewServerIntent.putExtra("bandwidthTotal", listOfManagedServers.get(i).getbandwidthTotal());
				ViewServerIntent.putExtra("monitored", listOfManagedServers.get(i).getmonitored());
				ViewServerIntent.putExtra("software", listOfManagedServers.get(i).getsoftware());
				ViewServerIntent.putExtra("state", listOfManagedServers.get(i).getstate());
				ViewServerIntent.putExtra("managedbackupenabled", listOfManagedServers.get(i).getmanagedbackupenabled());
				ViewServerIntent.putExtra("sessionid", getIntent().getStringExtra("sessionid"));
				ManagedServerLanding.this.startActivity(ViewServerIntent);
				found = true;
			}
		}
		if(found == false)
			Toast.makeText(ManagedServerLanding.this, "Somehow we were unable to find that server in the Portal...", Toast.LENGTH_SHORT).show();
		
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	finish();
	            return(true);
	    }

	    return(super.onOptionsItemSelected(item));
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.server_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) 
            {
                case R.id.change:
                    //shareCurrentItem();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };
}
