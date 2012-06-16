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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ColoLanding extends SherlockActivity
{
	PortalAPI API = new PortalAPI();
	JSONObject Colo = null;
	JSONArray ColoCabs = null;
	String Success = "false";
	ListView list = null;
    List<ColoRacks> listOfColoRacks = new ArrayList<ColoRacks>();
    String ErrorMessage = "";
    ActionMode mActionMode;
    ColoRacksAdaptor adapter;
    int childID = 0;
    Dialog detailsdialog;
    AlertDialog editDialog;
    Handler EditHandler;
    Thread submitEdit;
    String EditText = "";
    
    @Override
    public void onStop()
    {
    	if(editDialog != null)
    		editDialog.dismiss();
    	
    	if(detailsdialog != null)
    		detailsdialog.dismiss();
    	
    	super.onStop();
    }
    
	public void onCreate(Bundle savedInstanceState) 
	{
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.cololanding);
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Colocation");
		
		detailsdialog = new Dialog(this);
		
		EditHandler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			if(msg.what == 0)
    			{
    				Toast.makeText(ColoLanding.this, "Description updated successfully!", Toast.LENGTH_SHORT).show();
    			}
    			else
    			{
    				Toast.makeText(ColoLanding.this, "There was an error updating that description", Toast.LENGTH_SHORT).show();
    			}
    		}
    	};
		
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
    				adapter = new ColoRacksAdaptor(ColoLanding.this, listOfColoRacks);
    		        list.setAdapter(adapter);
    		        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
    														  CurrentColo.getString("state"),
    														  CurrentColo.getString("description")));
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
	
	 private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	        // Called when the action mode is created; startActionMode() was called
	        @Override
	        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	            // Inflate a menu resource providing context menu items
	            MenuInflater inflater = mode.getMenuInflater();
	            inflater.inflate(R.menu.cab_colo, menu);
	            return true;
	        }

	        // Called each time the action mode is shown. Always called after onCreateActionMode, but
	        // may be called multiple times if the mode is invalidated.
	        @Override
	        public boolean onPrepareActionMode(ActionMode mode, Menu menu) 
	        {
	            return false; // Return false if nothing is done
	        }

	        // Called when the user selects a contextual menu item
	        @Override
	        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	            switch (item.getItemId()) 
	            {
	                case R.id.Details:
	                {
	                	//Log.i("Details",listOfColoRacks.get(childID).getservercode());
	
	                	detailsdialog.setContentView(R.layout.colo_details);
	                	detailsdialog.setTitle(listOfColoRacks.get(childID).getservercode() + " Details");

	                	//((TextView) detailsdialog.findViewById(R.id.coloServerCode)).setText(listOfColoRacks.get(childID).getservercode());
	                    ((TextView) detailsdialog.findViewById(R.id.coloServerCode)).setText("( " + listOfColoRacks.get(childID).getstate().toUpperCase() + " )");
	                    ((TextView) detailsdialog.findViewById(R.id.coloFacility)).setText(listOfColoRacks.get(childID).getfacility());
	                    ((TextView) detailsdialog.findViewById(R.id.coloBandwidth)).setText(listOfColoRacks.get(childID).getbandwidthTotal());
	                    ((TextView) detailsdialog.findViewById(R.id.coloLockCombo)).setText("Unknown");
	                    ((TextView) detailsdialog.findViewById(R.id.coloIP)).setText(listOfColoRacks.get(childID).getip());
	                    
	                    if(listOfColoRacks.get(childID).getstate().equals("up"))
	                    {
	                    	ImageView imgView = (ImageView) detailsdialog.findViewById(R.id.ColoStateIcon);
	            			imgView.setImageResource(R.drawable.up);
	                    }
	                    
	                	detailsdialog.show();
	                	
	                    mode.finish(); // Action picked, so close the CAB
	                    listOfColoRacks.get(childID).SetSelected(false);
	                    childID = 0;
	                    adapter.notifyDataSetChanged();
	                    return true;
	                }
	                
	                case R.id.Edit:
	                {
	                	detailsdialog.setContentView(R.layout.colo_edit);
	                	detailsdialog.setTitle("Edit " +listOfColoRacks.get(childID).getservercode() + "'s Description");
	                	((EditText) detailsdialog.findViewById(R.id.coloDescEditText)).setText(listOfColoRacks.get(childID).getDesc());
	                	
	                	((Button) detailsdialog.findViewById(R.id.coloEditButton)).setOnClickListener( new OnClickListener(){
							@Override
							public void onClick(View arg0) 
							{
								EditText = ((EditText) detailsdialog.findViewById(R.id.coloDescEditText)).getText().toString();
								submitEdit = new Thread()
						    	{  
						    		public void run() 
						    		{
						    			try 
						            	{
						    				//sleep(6000);
						    				//Log.i(listOfColoRacks.get(childID).getservercode(), EditText);
						    				API.UpdateColoDescription(listOfColoRacks.get(childID).getservercode(), EditText);
						    				EditHandler.sendEmptyMessage(0);
						    			} 
						            	catch (Exception e) 
						            	{
						            		EditHandler.sendEmptyMessage(1);
						    			}
						    		}
						    	};
						    	
						    	listOfColoRacks.get(childID).setDescription(((EditText) detailsdialog.findViewById(R.id.coloDescEditText)).getText().toString());
								listOfColoRacks.get(childID).SetSelected(false);
			                    childID = 0;
			                    adapter.notifyDataSetChanged();
			                    detailsdialog.hide();
			                    submitEdit.start();
							}});
	                	
	                	detailsdialog.show();
	                	mode.finish(); // Action picked, so close the CAB
	                	return true;
	                }
	                
	                default:
	                	listOfColoRacks.get(childID).SetSelected(false);
	                	childID = 0;
	                    adapter.notifyDataSetChanged();
	                    return false;
	            }
	        }

	        // Called when the user exits the action mode
	        @Override
	        public void onDestroyActionMode(ActionMode mode) 
	        {
	        	listOfColoRacks.get(childID).SetSelected(false);
	        	childID = 0;
	            mActionMode = null;
	            adapter.notifyDataSetChanged();
	        }
	    };
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		        case android.R.id.home:
		        	finish();
		            return(true);
		    }

		    return(super.onOptionsItemSelected(item));
		}
	    
	    public void ColoLongPress(int id)
		{
	    	mActionMode = startActionMode(mActionModeCallback);
	    	childID = id;
	    	listOfColoRacks.get(childID).SetSelected(true);
            adapter.notifyDataSetChanged();
		}
}
