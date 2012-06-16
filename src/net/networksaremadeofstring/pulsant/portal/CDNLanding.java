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
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class CDNLanding extends SherlockActivity
{
	PortalAPI API = new PortalAPI();
	JSONObject CDNs = null;
	JSONArray CDNNodes = null;
	String Success = "false";
	JSONObject CurrentCDN = null;
	String ErrorMessage = "";
	//Temp
	List<CDN> listOfCDNs = new ArrayList<CDN>();
	ListView list = null;
	//Temp
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    //setContentView(R.layout.cdnlanding);
		setContentView(R.layout.cdnlanding_holding);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Managed Networks");
	    //Progress test
	    /*list = (ListView)findViewById(R.id.CDNList);
	    final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				CDNAdaptor adapter = new CDNAdaptor(CDNLanding.this, listOfCDNs);
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
    		    	CDNs = API.PortalQuery("cdn", "none");
    				Success = CDNs.getString("success");	
    			} 
    		    catch (JSONException e) 
    		    {
    		    	ErrorMessage = "An unrecoverable JSON Exception occured.";
    		    	//UpdateErrorMessage("An unrecoverable JSON Exception occured.");
    			}

    		    if(Success.equals("false"))
    		    {
    		    	try 
    		    	{
    					//UpdateErrorMessage(CDNs.getString("msg"));
    		    		ErrorMessage = CDNs.getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		//UpdateErrorMessage("A JSON parsing error prevented an exact error message to be determined.");
    		    		ErrorMessage = "A JSON parsing error prevented an exact error message to be determined.";
    				}
    		    }
    		    else
    		    {
    		    	try
    		 	    {
    		    		CDNNodes = CDNs.getJSONArray("cdns");
    		 	    	//UpdateErrorMessage("");
    		    		ErrorMessage = "";
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	//UpdateErrorMessage("There are no CDN nodes in your account.");
    		 	    	ErrorMessage = "There are no CDN nodes in your account.";
    		 	    }
    		        int CDNCount = CDNNodes.length();
    		        
    		        if(CDNCount == 0)
    		        {
    		        	//UpdateErrorMessage("There are no CDN nodes for your account.");
    		        	ErrorMessage = "There are no CDN nodes for your account.";
    		        	Success = "false";
    		        	handler.sendEmptyMessage(0);
    		        }
    		        
    		        for(int i = 0; i < CDNCount; i++)
    		        {
    					try 
    					{
    						CurrentCDN = CDNNodes.getJSONObject(i);
    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}

    		        		listOfCDNs.add(new CDN(GetString("name"),
    		        				GetString("backend"),
    		        				GetInt("bandwidthemea"),
    		        				GetInt("bandwidthapac"),
    		        				GetInt("bandwidthamericas"),
    		        				GetBool("loadbalanced")));
    		        }
    		    }
    		    handler.sendEmptyMessage(0);
    		}
    	};

    	dataPreload.start();*/
	    
    	//Progress test
    	
	    /*try 
	    {
	    	CDNs = API.PortalQuery("cdn", "none");
			Success = CDNs.getString("success");	
		} 
	    catch (JSONException e) 
	    {
	    	UpdateErrorMessage("An unrecoverable JSON Exception occured.");
	    	//Toast.makeText(ColoLanding.this, "An unrecoverable JSON Exception occured.", Toast.LENGTH_LONG).show();
		}

	    if(Success == "false")
	    {
	    	try 
	    	{
				UpdateErrorMessage(CDNs.getString("msg"));
			} 
	    	catch (JSONException e) 
	    	{
	    		UpdateErrorMessage("A JSON parsing error prevented an exact error message to be determined.");
			}
	    }
	    else
	    {
	    	Log.i("APIFuncs",CDNs.toString());
	    	try
	 	    {
	    		CDNNodes = CDNs.getJSONArray("cdns");
	 	    	UpdateErrorMessage("");
	 	    }
	 	    catch (JSONException e) 
	 	    {
	 	    	UpdateErrorMessage("There are no CDN nodes in your account.");
	 	    }
	 	    
	 	    //OK lets actually do something useful
	 	    ListView list = (ListView)findViewById(R.id.CDNList);
	        List<CDN> listOfCDNs = new ArrayList<CDN>();
	        int CDNCount = CDNNodes.length();
	        
	        if(CDNCount == 0)
	        {
	        	UpdateErrorMessage("There are no CDN nodes for your account.");
	        	return;
	        }
	        
	        for(int i = 0; i < CDNCount; i++)
	        {
				try 
				{
					CurrentCDN = CDNNodes.getJSONObject(i);
				} 
				catch (JSONException e1) 
				{
					Log.e("APIFuncs",e1.getMessage());
				}

	        		listOfCDNs.add(new CDN(GetString("name"),
	        				GetString("backend"),
	        				GetInt("bandwidthemea"),
	        				GetInt("bandwidthapac"),
	        				GetInt("bandwidthamericas"),
	        				GetBool("loadbalanced")));
	        }
   
	        CDNAdaptor adapter = new CDNAdaptor(this, listOfCDNs);
	        
	        list.setAdapter(adapter);
	    }*/
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
	
	public String GetString(String requiredString)
	{
		try 
    	{
			return CurrentCDN.getString(requiredString);
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
			return CurrentCDN.getInt(requiredString);
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
			return CurrentCDN.getBoolean(requiredString);
    	}
		catch (JSONException e) 
    	{
			return false;
    	}
	}
}
