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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class ViewServer extends Activity
{
	PortalAPI API = new PortalAPI();
	
	public void onCreate(Bundle savedInstanceState) 
	{
		//We need the API key if we make any changes
		API.SessionID = getIntent().getStringExtra("sessionid");
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.viewserver);
	    
	    TextView DEDCode = (TextView) findViewById(R.id.DEDCode);
	    DEDCode.setText(getIntent().getStringExtra("servercode"));
	    
	    TextView IP = (TextView) findViewById(R.id.IPTV);
	    IP.setText(getIntent().getStringExtra("ip"));
		
	    TextView Description = (TextView) findViewById(R.id.DescTV);
	    Description.setText(getIntent().getStringExtra("description"));
	    
	    TextView Facility = (TextView) findViewById(R.id.FacilityTV);
	    Facility.setText(getIntent().getStringExtra("facility"));
	    
	    TextView Bandwidth = (TextView) findViewById(R.id.BandwidthTV);
	    Bandwidth.setText(getIntent().getStringExtra("bandwidthTotal"));
	    
	    TextView Status = (TextView) findViewById(R.id.StatusTV);
	    if(getIntent().getStringExtra("state").equals("up") == false)
		{
	    	Status.setText("DOWN");
	    	Status.setTextColor(-65536);
		}
	    
	    TextView Monitored = (TextView) findViewById(R.id.MonitorEnabledTV);
	    if(getIntent().getBooleanExtra("monitored",false) == false)
	    {
	    	Monitored.setText("NO");
	    	Monitored.setTextColor(-65536);
	    }
	    
	    if(getIntent().getStringExtra("software").equals("windows"))
        {
        	ImageView imgView = (ImageView) findViewById(R.id.SoftwareImage);
			imgView.setImageResource(R.drawable.hd_windows);
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.server_menu, menu);
	    return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
	        case R.id.change:
	        {
	        	//Toast.makeText(this, "Changes", Toast.LENGTH_SHORT).show();
	        	Intent EditServerIntent = new Intent(ViewServer.this, EditServer.class);
	        	
	        	EditServerIntent.putExtra("servercode", getIntent().getStringExtra("servercode"));
	        	EditServerIntent.putExtra("description", getIntent().getStringExtra("description"));
	        	EditServerIntent.putExtra("software", getIntent().getStringExtra("software"));
	        	EditServerIntent.putExtra("sessionid", getIntent().getStringExtra("sessionid"));
	        	ViewServer.this.startActivity(EditServerIntent);
	            return true;
	        }
        }
        return false;
    }
}
