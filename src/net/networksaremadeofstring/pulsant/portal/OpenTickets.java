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

import net.networksaremadeofstring.pulsant.portal.R;

public class OpenTickets {
	private String status; 
	private int id;
	private String server;
	private String email;
	private String subject; 
	private int createdat;
	private int lastupdate; 
	private boolean subscriber;
	
	 // Constructor for the Ticket class
    public OpenTickets(String status, int id, String server, String email, String subject, int createdat, int lastupdate, boolean subscriber) 
    {
            super();
            this.status = status;
            this.id = id;
            this.server = server;
            this.email = email;
            this.subject = subject;
            this.createdat = createdat;
            this.lastupdate = lastupdate;
            this.subscriber = subscriber;
    }
    
    public String getstatus() 
    {
        return this.status;
    }
    
    public int getid() 
    {
        return this.id;
    }
    
    public String getserver() 
    {
        return this.server;
    }

    public String getemail() 
    {
        return this.email;
    }
    
    public String getsubject() 
    {
        return this.subject;
    }
    
    public int getcreatedat() 
    {
        return this.createdat;
    }
    
    public int getlastupdate() 
    {
        return this.lastupdate;
    }
    
    public boolean getsubscriber() 
    {
        return this.subscriber;
    }
}
