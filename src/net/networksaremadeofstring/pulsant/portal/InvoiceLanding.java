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

public class InvoiceLanding extends Activity
{
	PortalAPI API = new PortalAPI();
	JSONObject InvoiceAPIReturn = null;
	JSONArray Invoices = null;
	String Success = "false";
	String ErrorMessage = "";
	List<Invoices> listOfInvoices = new ArrayList<Invoices>();
	ListView list = null;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.invoicelanding);
	    final ListView list = (ListView)findViewById(R.id.InvoiceList);
	    
	    final ProgressDialog dialog = ProgressDialog.show(this, "Pulsant Portal", "Please wait: loading data....", true);
    	final Handler handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			if(Success.equals("true"))
    			{
    				UpdateErrorMessage(ErrorMessage);
    				InvoiceAdaptor adapter = new InvoiceAdaptor(InvoiceLanding.this, listOfInvoices, API.SessionID);
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
    				InvoiceAPIReturn = API.PortalQueryHack("invoices", "", "");
    				Success = InvoiceAPIReturn.getString("success");	
    			} 
    		    catch (JSONException e) 
    		    {
    		    	ErrorMessage = "An unrecoverable JSON Exception occured.";
    		    	Success = "false";
    			}
    		    
    		    if(Success.equals("false"))
    		    {
    		    	Log.i("API","Success was false");
    		    	try 
    		    	{
    		    		ErrorMessage = InvoiceAPIReturn.getJSONObject("hackReturn").getString("msg");
    				} 
    		    	catch (JSONException e) 
    		    	{
    		    		ErrorMessage = "A JSON parsing error prevented an exact error message to be determined.";
    				}
    		    }
    		    else
    		    {
    		    	int InvoiceCount = 0;
    		    	Log.i("APIFuncs",InvoiceAPIReturn.toString());
    		    	try
    		 	    {
    		    		Invoices = InvoiceAPIReturn.getJSONObject("hackReturn").getJSONArray("data");
    		    		InvoiceCount = Invoices.length();
    		    		ErrorMessage = "";
    		 	    }
    		 	    catch (JSONException e) 
    		 	    {
    		 	    	ErrorMessage = "There don't appear to be any invoices for your account.....";
    		 	    	InvoiceCount = 0;
    		 	    	Log.e("API","There was an eror parsing the array");
    		 	    	Log.e("API",e.getMessage());
    		 	    }

    		 	    //OK lets actually do something useful
    		        
    		        if(InvoiceCount == 0)
    		        {
    		        	Success = "false";
    		        	ErrorMessage = "There are no invoices for your account.";
    		        	handler.sendEmptyMessage(0);
    		        	return;
    		        }
    		        
    		        for(int i = 0; i < InvoiceCount; i++)
    		        {
    		        	JSONObject CurrentInvoice = null;
    					try 
    					{
    						CurrentInvoice = Invoices.getJSONObject(i);
    					} 
    					catch (JSONException e1) 
    					{
    						Log.e("APIFuncs",e1.getMessage());
    					}
    		        	try 
    		        	{
    		        		if(CurrentInvoice.getString("type").equals("invoice"))
    		        			listOfInvoices.add(new Invoices(CurrentInvoice.getString("ref"), CurrentInvoice.getDouble("amount"), CurrentInvoice.getString("details"), CurrentInvoice.getString("date")));
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
}
