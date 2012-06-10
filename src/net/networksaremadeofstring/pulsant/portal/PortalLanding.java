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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class PortalLanding extends Activity
{
	PortalAPI API = new PortalAPI();
	// Called when the activity is first created.
	public void onCreate(Bundle savedInstanceState) 
	{
		//Your code here
		API.SessionID = getIntent().getStringExtra("sessionid");
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.portallanding_iclone);
	    
	    //---------------------------------- IClone View--------------------------------------
	    //Create Ticket
	    TextView CreateTicketText = (TextView) findViewById(R.id.CreateTicketLabel);
	    CreateTicketText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	//This doesn't exist yet!
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
        });

	}

}
