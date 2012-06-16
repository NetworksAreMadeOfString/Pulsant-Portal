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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class PortalLanding extends SherlockActivity
{
	PortalAPI API = new PortalAPI();
	final String CATEGORIES[] = { "Portal", "Managed Hosting", "Colocation", "Cloud", "Managed Networks", "Tickets", "Account" };
	OnNavigationListener navHandler;
	
	// Called when the activity is first created.
	public void onCreate(Bundle savedInstanceState) 
	{   
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.portallanding);
	    
	    
	    CreateHandlers();
	    
	    ActionBar sAb = getSupportActionBar();
	    sAb.setTitle("");
	    sAb.setNavigationMode(sAb.NAVIGATION_MODE_LIST);//android.app.ActionBar.NAVIGATION_MODE_LIST
        SpinnerAdapter adap = new ArrayAdapter(this, R.layout.sherlock_spinner_dropdown_item, CATEGORIES);
        sAb.setListNavigationCallbacks(adap, navHandler);
        sAb.setSelectedNavigationItem(0);

        ((ImageView) findViewById(R.id.managedImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	GotoManagedLanding();
            }
        });
        
        ((ImageView) findViewById(R.id.coloImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	GotoColoLanding();
            }
        });
        
        ((ImageView) findViewById(R.id.cloudImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Toast.makeText(v.getContext(), "Ask your Account manager to add Cloud functionality to the API", Toast.LENGTH_LONG).show();
            }
        });
        
        ((ImageView) findViewById(R.id.networksImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	GotoNetworksLanding();
            }
        });
        
        ((ImageView) findViewById(R.id.ticketsImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	GotoTicketsLanding();
            }
        });
        
        ((ImageView) findViewById(R.id.invoicesImage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	GotoAccountLanding();
            }
        });
        
	    //Create Ticket
	    /*TextView CreateTicketText = (TextView) findViewById(R.id.CreateTicketLabel);
	    CreateTicketText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent CreateTicketIntent = new Intent(PortalLanding.this, CreateTicket.class);
            	CreateTicketIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(CreateTicketIntent);
            }
        });
	    
	    ImageView CreateTicketImage = (ImageView) findViewById(R.id.CreateTicketArrow);
	    CreateTicketImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent CreateTicketIntent = new Intent(PortalLanding.this, CreateTicket.class);
            	CreateTicketIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(CreateTicketIntent);
            }
        });
	    
	    
	    //View Ticket -----------------------------------------
	    TextView ViewTicketText = (TextView) findViewById(R.id.ViewTicketLabel);
	    ViewTicketText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent TicketIntent = new Intent(PortalLanding.this, TicketLanding.class);
            	TicketIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(TicketIntent);
            }
        });
	    
	    ImageView ViewTicketImage = (ImageView) findViewById(R.id.ViewTicketsArrow);
	    ViewTicketImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent TicketIntent = new Intent(PortalLanding.this, TicketLanding.class);
            	TicketIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(TicketIntent);
            }
        });
	    
	    //List Managed -----------------------------------------
	    TextView ListManagedText = (TextView) findViewById(R.id.ListManagedLabel);
	    ListManagedText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent ManagedServerIntent = new Intent(PortalLanding.this, ManagedServerLanding.class);
            	ManagedServerIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(ManagedServerIntent);
            }
        });
	    
	    ImageView ViewManagedImage = (ImageView) findViewById(R.id.ViewServersArrow);
	    ViewManagedImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent ManagedServerIntent = new Intent(PortalLanding.this, ManagedServerLanding.class);
            	ManagedServerIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(ManagedServerIntent);
            }
        });
	    
	    //List Colo -----------------------------------------
	    TextView ListColoText = (TextView) findViewById(R.id.ListColoLabel);
	    ListColoText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent ColoIntent = new Intent(PortalLanding.this, ColoLanding.class);
            	ColoIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(ColoIntent);
            }
        });
	    
	    ImageView ViewColoImage = (ImageView) findViewById(R.id.ViewCabsArrow);
	    ViewColoImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent ColoIntent = new Intent(PortalLanding.this, ColoLanding.class);
            	ColoIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(ColoIntent);
            }
        });
	    
	    //List CDN -----------------------------------------
	    TextView ListCDNText = (TextView) findViewById(R.id.ListCDNLabel);
	    ListCDNText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent CDNIntent = new Intent(PortalLanding.this, CDNLanding.class);
            	CDNIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(CDNIntent);
            }
        });
	    
	    ImageView ViewCDNImage = (ImageView) findViewById(R.id.ViewCDNArrow);
	    ViewCDNImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent CDNIntent = new Intent(PortalLanding.this, CDNLanding.class);
            	CDNIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(CDNIntent);
            }
        });
	    
	  //List Invoices -----------------------------------------
	    TextView ListInvoiceText = (TextView) findViewById(R.id.viewInvoiceLabel);
	    ListInvoiceText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent InvoiceIntent = new Intent(PortalLanding.this, InvoiceLanding.class);
            	InvoiceIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(InvoiceIntent);
            }
        });
	    
	    ImageView ViewInvoiceImage = (ImageView) findViewById(R.id.ViewInvoiceArrow);
	    ViewInvoiceImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Intent InvoiceIntent = new Intent(PortalLanding.this, InvoiceLanding.class);
            	InvoiceIntent.putExtra("sessionid", API.SessionID);
        		PortalLanding.this.startActivity(InvoiceIntent);
            }
        });*/

	}
	
	public void CreateHandlers()
	{
		navHandler = new OnNavigationListener() 
    	{
			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) 
			{
				getSupportActionBar().setSelectedNavigationItem(0);
				// { "Goto ....", "Managed Hosting", "Colocation", "Cloud", "Managed Networks", "Tickets", "Account" };
				switch(itemPosition)
				{
					case 0:
					{
						return false;
					}
					
					case 1:
					{
						GotoManagedLanding();
					}
					break;
					
					case 2:
					{
						GotoColoLanding();
					}
					break;
					
					case 3:
					{
						Toast.makeText(PortalLanding.this, "Ask your Account manager to add Cloud functionality to the API", Toast.LENGTH_LONG).show();
					}
					break;
					
					case 4:
					{
						GotoNetworksLanding();
					}
					break;
					
					case 5:
					{
						GotoTicketsLanding();
					}
					break;
					
					
					case 6:
					{
						GotoAccountLanding();
					}
					break;
				}
				return false;
			}
    		
    	};
	}

	public void GotoColoLanding()
	{
		Intent ColoIntent = new Intent(PortalLanding.this, ColoLanding.class);
    	ColoIntent.putExtra("sessionid", API.SessionID);
		PortalLanding.this.startActivity(ColoIntent);
	}
	
	public void GotoAccountLanding()
	{
		Intent InvoiceIntent = new Intent(PortalLanding.this, InvoiceLanding.class);
    	InvoiceIntent.putExtra("sessionid", API.SessionID);
		PortalLanding.this.startActivity(InvoiceIntent);
	}
	
	public void GotoNetworksLanding()
	{
		Intent CDNIntent = new Intent(PortalLanding.this, CDNLanding.class);
    	CDNIntent.putExtra("sessionid", API.SessionID);
		PortalLanding.this.startActivity(CDNIntent);
	}
	
	public void GotoManagedLanding()
	{
		Intent ManagedServerIntent = new Intent(PortalLanding.this, ManagedServerLanding.class);
    	ManagedServerIntent.putExtra("sessionid", API.SessionID);
		PortalLanding.this.startActivity(ManagedServerIntent);
	}
	
	public void GotoTicketsLanding()
	{
		Intent TicketIntent = new Intent(PortalLanding.this, TicketLanding.class);
    	TicketIntent.putExtra("sessionid", API.SessionID);
		PortalLanding.this.startActivity(TicketIntent);
	}
}
