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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.CookieSpecBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import net.networksaremadeofstring.pulsant.portal.R;

public class PortalAPI 
{
	String SessionID = "";
	Cookie sessionCookie;
	CookieSpecBase cookieSpecBase = new BrowserCompatSpec();
	
	public String Login(List<NameValuePair> Credentials) 
	{  
		// Create a new HttpClient and Post Header  
		HttpClient httpclient = new DefaultHttpClient();  
		HttpPost httppost = new HttpPost("https://portal.pulsant.com/api.php?format=json&action=login");  
		HttpResponse response = null;
		JSONObject json;
		String Success;
		
		try 
		{  
			httppost.setEntity(new UrlEncodedFormEntity(Credentials));  
			response = httpclient.execute(httppost);  
		} 
		catch (ClientProtocolException e) 
		{  
			return "Client Protocol Exception";
		} 
		catch (IOException e) 
		{  
			e.printStackTrace();
			return "Remote API Timed Out"; 
		}  
		
		try 
		{
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			json = new JSONObject(new String(outstream.toByteArray()));
		} 
		catch (JSONException e) 
		{
			//Log.e("APIFuncs",e.getMessage());
			return "JSON Parse Failure";
		}
		catch (IOException e) 
		{  
			return "JSON Parse IO Exception"; 
		} 
		
		try 
		{
			Success = json.getString("success");
		} 
		catch (JSONException e) 
		{
			return "Success Confirmation Failed";
		}
		
		if(Success == "true")
		{
			try 
			{
				SessionID = json.getString("sessionid");
				return "true";
			} 
			catch (JSONException e) 
			{
				return "SessionID Confirmation Failed";
			}
		}
		else
		{
			try 
			{
				return json.getString("msg");
			} 
			catch (JSONException e) 
			{
				return "Failure Message Failed";
			}
		}
	}  
	
	public void UpdateColoDescription(String ServerCode,String Description) throws JSONException
	{
		PortalQueryHack("UpdateColoDesc",ServerCode,Description);
	}

	public JSONObject PortalQueryHack(String Action, String Key, String Payload) throws JSONException 
	{  
		// Create a new HttpClient and Post Header  
		HttpParams params = new BasicHttpParams();
		HttpClient httpclient = new DefaultHttpClient(params); 
		HttpPost httppost = null;
		HttpGet httpget = null;
		HttpResponse response = null;
		JSONObject json = new JSONObject();
		String Panel = "";
		String Page = "";
		
		
		if(Action.equals("UpdateDesc"))
		{
			//?panel=managedservices&page=serverdescription&extra=DEDXXXXXX&newdesc=New%20Description&_dc=1315602914100
			Panel = "managedservices";
			Page = "serverdescription";
			Payload = "&newdesc=" + Payload;
		}
		else if(Action.equals("UpdateColoDesc"))
		{
			Panel = "colo";
			Page = "serverdescription";
			Payload = "&newdesc=" + Payload;
		}
		else if (Action.equals("reboot"))
		{
			//?panel=managedservices&page=rebootServer&extra=' + servercode,
			Panel = "managedservices";
			Page = "rebootServer";
			Payload = "";
		}
		else if (Action.equals("invoices"))
		{
			//?panel=accounts&page=accounts&extra=Array&_dc=1315604614823
			Panel = "accounts";
			Page = "accounts";
			Key = "Array";
			Payload = "";
		}
		else if (Action.equals("invoice"))
		{
			//?panel=accounts&page=invoice&extra=58897
			Panel = "accounts";
			Page = "invoice";
			Payload = "";
		}

		try 
		{  
			//Log.i("APIFuncs","Adding PHPSESSID=" + this.SessionID + " to the Cookie");
			if(Action.equals("UpdateDesc"))
			{
				//Log.i("APIFuncs","Performing GET");
				//https://portal.pulsant.com/json/getViewData.php?panel=managedservices&page=serverdescription&extra=DED2681&newdesc=Test26&_dc=1315754918296
				httpget = new HttpGet("https://portal.pulsant.com/json/getViewData.php?panel=" + Panel + "&page=" + Page + "&extra=" + Key + Payload);
				httpget.setHeader("Cookie","PHPSESSID=" + this.SessionID);
				response = httpclient.execute(httpget);
			}
			else
			{
				//Log.i("APIFuncs","Performing POST");
				httppost = new HttpPost("https://portal.pulsant.com/json/getViewData.php?panel=" + Panel + "&page=" + Page + "&extra=" + Key + Payload);
				httppost.setHeader("Cookie","PHPSESSID=" + this.SessionID);
				response = httpclient.execute(httppost);
				
			}
			
			//Log.i("APIFuncs","Done");
		} 
		catch (ClientProtocolException e) 
		{  
			json.put("success", "false");
			json.put("msg", "Client protocol exception");
			return json;
		} 
		catch (IOException e) 
		{  
			json.put("success", "false");
			json.put("msg", "Generic IO exception");
			return json;
		}  
		
		try 
		{
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			json.put("success", "true");
			json.put("hackReturn", new JSONObject(new String(outstream.toByteArray())));
			//Log.i("APIFuncs","Added " + new String(outstream.toByteArray()));
		} 
		catch (IOException e) 
		{  
			//Log.i("APIFuncs","There was a problem");
			json.put("success", "false");
			json.put("msg", "JSON Parse IO Exception");
			return json;
		} 
		
		return json;
	} 
	
	public JSONObject PortalQuery(String Action, String ActionDetails) throws JSONException 
	{  
		// Create a new HttpClient and Post Header  
		HttpParams params = new BasicHttpParams();
		HttpClient httpclient = new DefaultHttpClient(params); 
		HttpPost httppost = null;
		HttpResponse response = null;
		JSONObject json = new JSONObject();
		if(Action.equals("updates"))
		{
			Log.i("APIFuncs","Calling https://portal.pulsant.com/api.php?format=json&action=" + Action +"&ticketid=" + ActionDetails);
			httppost = new HttpPost("https://portal.pulsant.com/api.php?format=json&action=" + Action +"&ticketid=" + ActionDetails);
		}
		else
		{
			httppost = new HttpPost("https://portal.pulsant.com/api.php?format=json&action=" + Action);  
		}
		
		try 
		{  
			Log.i("APIFuncs","Adding PHPSESSID=" + this.SessionID + " to the Cookie");
			httppost.setHeader("Cookie","PHPSESSID=" + this.SessionID);
			response = httpclient.execute(httppost);
			//Log.i("APIFuncs","Done");
		} 
		catch (ClientProtocolException e) 
		{  
			json.put("success", false);
			json.put("msg", "Client protocol exception");
			return json;
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "Generic IO exception");
			return json;
		}  
		
		try 
		{
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			json = new JSONObject(new String(outstream.toByteArray()));
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "JSON Parse IO Exception");
			return json;
		} 
		
		return json;
	}  
	
	public int CreateTicket(String Subject, String Message)throws JSONException
	{
		// Create a new HttpClient and Post Header  
		HttpParams params = new BasicHttpParams();
		HttpClient httpclient = new DefaultHttpClient(params); 
		HttpPost httppost = null;
		HttpResponse response = null;
		JSONObject json = new JSONObject();

		httppost = new HttpPost("https://portal.pulsant.com/api.php?format=json&action=addticket");  
		
		try 
		{  
			//Log.i("APIFuncs","Creating TicketDetails NameValuePair to the POST");
			List<NameValuePair> TicketDetails = new ArrayList<NameValuePair>(2);  
			TicketDetails.add(new BasicNameValuePair("subject", Subject));
			TicketDetails.add(new BasicNameValuePair("message", Message));
			httppost.setEntity(new UrlEncodedFormEntity(TicketDetails));  
			
			//Log.i("APIFuncs","Adding PHPSESSID=" + this.SessionID + " to the Cookie");
			httppost.setHeader("Cookie","PHPSESSID=" + this.SessionID);
			response = httpclient.execute(httppost);
			//Log.i("APIFuncs","Done");
		} 
		catch (ClientProtocolException e) 
		{  
			json.put("success", false);
			json.put("msg", "Client protocol exception");
			return 0;
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "Generic IO exception");
			return 0;
		}  
		
		try 
		{
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			json = new JSONObject(new String(outstream.toByteArray()));
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "JSON Parse IO Exception");
			return 0;
		} 
		
		return 1;
	}
	
	public int ReplyToTicket(String ticketid, String Message)throws JSONException
	{
		// Create a new HttpClient and Post Header  
		HttpParams params = new BasicHttpParams();
		HttpClient httpclient = new DefaultHttpClient(params); 
		HttpPost httppost = null;
		HttpResponse response = null;
		JSONObject json = new JSONObject();

		httppost = new HttpPost("https://portal.pulsant.com/api.php?format=json&action=replytoticket");  
		
		try 
		{  
			//Log.i("APIFuncs","Creating TicketDetails NameValuePair to the POST");
			List<NameValuePair> TicketDetails = new ArrayList<NameValuePair>(2);  
			TicketDetails.add(new BasicNameValuePair("ticketid", ticketid));
			TicketDetails.add(new BasicNameValuePair("message", Message));
			httppost.setEntity(new UrlEncodedFormEntity(TicketDetails));  
			
			//Log.i("APIFuncs","Adding PHPSESSID=" + this.SessionID + " to the Cookie");
			httppost.setHeader("Cookie","PHPSESSID=" + this.SessionID);
			response = httpclient.execute(httppost);
			//Log.i("APIFuncs","Done");
		} 
		catch (ClientProtocolException e) 
		{  
			json.put("success", false);
			json.put("msg", "Client protocol exception");
			return 0;
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "Generic IO exception");
			return 0;
		}  
		
		try 
		{
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			response.getEntity().writeTo(outstream);
			json = new JSONObject(new String(outstream.toByteArray()));
		} 
		catch (IOException e) 
		{  
			json.put("success", false);
			json.put("msg", "JSON Parse IO Exception");
			return 0;
		} 
		
		return 1;
	}
}
