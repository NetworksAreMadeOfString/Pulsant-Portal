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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class ColoLanding extends Activity
{
	PortalAPI API = new PortalAPI();
	JSONObject Colo = null;
	JSONArray ColoCabs = null;
	String Success = "false";
	ListView list = null;
    List<ColoRacks> listOfColoRacks = new ArrayList<ColoRacks>();
    String ErrorMessage = "";
    
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.cololanding_iclone);
	    //Temp
	    list = (ListView)findViewById(R.id.ColoCabList);
	    final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				ColoRacksAdaptor adapter = new ColoRacksAdaptor(ColoLanding.this, listOfColoRacks);
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
    		    	Colo = API.PortalQuery("colocation", "none");
    				Success = Colo.getString("success");	
    			} 
    		    catch (JSONException e) 
    		    {
    		    	ErrorMessage = "An unrecoverable JSON Exception occurred.";
    		    	//UpdateErrorMessage("An unrecoverable JSON Exception occurred.");
    			}

    		    if(Success.equals("false"))
    		    {
    		    	try 
    		    	{
    					//UpdateErrorMessage(Colo.getString("msg"));
    		    		ErrorMessage = Colo.getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		ErrorMessage = "An unrecoverable JSON Exception occured.";
    		    		//UpdateErrorMessage("An unrecoverable JSON Exception occured.");
    				}
    		    }
    		    else
    		    {
    		    	Log.i("APIFuncs",Colo.toString());
    		    	try
    		 	    {
    		 	    	ColoCabs = Colo.getJSONArray("colocation");
    		 	    	ErrorMessage = "";
    		 	    	//UpdateErrorMessage("");
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	ErrorMessage = "There are no Co-Location Cabinets in your account.";
    		 	    	//UpdateErrorMessage("There are no Co-Location Cabinets in your account.");
    		 	    }
    		 	    
    		        int ColoCount = ColoCabs.length();
    		        
    		        if(ColoCount == 0)
    		        {
    		        	//UpdateErrorMessage("There are no colocation cabinets for your account.");
    		        	ErrorMessage = "There are no colocation cabinets for your account.";
    		        	handler.sendEmptyMessage(0);
    		        	return;
    		        }
    		        
    		        for(int i = 0; i < ColoCount; i++)
    		        {
    		        	JSONObject CurrentColo = null;
    					try 
    					{
    						CurrentColo = ColoCabs.getJSONObject(i);
    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}
    					
    		        	try 
    		        	{
    						listOfColoRacks.add(new ColoRacks(CurrentColo.getString("ip"),
    														  CurrentColo.getString("servercode"),
    														  CurrentColo.getString("facility"), 
    														  CurrentColo.getInt("bandwidth"), 
    														  CurrentColo.getString("bandwidthTotal"), 
    														  CurrentColo.getInt("transferlimit"), 
    														  CurrentColo.getString("state")));
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
	    //temp
    	
    	
	    /*try 
	    {
	    	Colo = API.PortalQuery("colocation", "none");
			Success = Colo.getString("success");	
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
				UpdateErrorMessage(Colo.getString("msg"));
			} 
	    	catch (JSONException e) 
	    	{
	    		UpdateErrorMessage("An unrecoverable JSON Exception occured.");
			}
	    }
	    else
	    {
	    	Log.i("APIFuncs",Colo.toString());
	    	try
	 	    {
	 	    	ColoCabs = Colo.getJSONArray("colocation");
	 	    	UpdateErrorMessage("");
	 	    }
	 	    catch (JSONException e) 
	 	    {
	 	    	UpdateErrorMessage("There are no Co-Location Cabinets in your account.");
	 	    }
	 	    
	 	    //OK lets actually do something useful
	 	    ListView list = (ListView)findViewById(R.id.ColoCabList);
	        List<ColoRacks> listOfColoRacks = new ArrayList<ColoRacks>();
	        int ColoCount = ColoCabs.length();
	        
	        if(ColoCount == 0)
	        {
	        	UpdateErrorMessage("There are no colocation cabinets for your account.");
	        	return;
	        }
	        
	        for(int i = 0; i < ColoCount; i++)
	        {
	        	JSONObject CurrentColo = null;
				try 
				{
					CurrentColo = ColoCabs.getJSONObject(i);
				} 
				catch (JSONException e1) 
				{
					Log.e("APIFuncs",e1.getMessage());
				}
	        	try 
	        	{
					listOfColoRacks.add(new ColoRacks(CurrentColo.getString("ip"),
													  CurrentColo.getString("servercode"),
													  CurrentColo.getString("facility"), 
													  CurrentColo.getInt("bandwidth"), 
													  CurrentColo.getString("bandwidthTotal"), 
													  CurrentColo.getInt("transferlimit"), 
													  CurrentColo.getString("state")));
				} 
	        	catch (JSONException e) 
	        	{
	        		Log.e("APIFuncs",e.getMessage());
				}
	        }
	        
	        ColoRacksAdaptor adapter = new ColoRacksAdaptor(this, listOfColoRacks);
	        
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
			ErrorMessage.setHeight(24);
			ErrorMessage.setTextColor(-65536);
			ErrorMessage.setText(MessageText);
		}
	}
}
