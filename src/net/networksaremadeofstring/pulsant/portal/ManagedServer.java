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

public class ManagedServer {
	private String ip; 
	private String servercode;
	private String description;
	private String facility;
	private int bandwidth;
	private String bandwidthTotal;
	private boolean monitored;
	private int transferlimit;
	private String software;
	private String state;
	private boolean managedbackupenabled;
	
	 // Constructor for the Ticket class
    public ManagedServer(String ip, String servercode, String description, String facility, int bandwidth, String bandwidthTotal, boolean monitored, int transferlimit, String software, String state, boolean managedbackupenabled) 
    {
            super();
            this.ip = ip;
            this.servercode = servercode;
            this.description = description;
            this.facility = facility;
            this.bandwidth = bandwidth;
            this.bandwidthTotal = bandwidthTotal;
            this.monitored = monitored;
            this.transferlimit = transferlimit;
            this.software = software;
            this.state = state;
            this.managedbackupenabled = managedbackupenabled;
    }
    
    public String getip() 
    {
        return this.ip;
    }
    
    public String getservercode() 
    {
        return this.servercode;
    }
    
    public String getdescription() 
    {
        return this.description;
    }
    
    public int getbandwidth() 
    {
        return this.bandwidth;
    }
    
    public String getfacility() 
    {
        return this.facility;
    }

    public String getbandwidthTotal() 
    {
        return this.bandwidthTotal;
    }
    
    public boolean getmonitored() 
    {
        return this.monitored;
    }
    
    public int gettransferlimit() 
    {
        return this.transferlimit;
    }
    
    public String getsoftware() 
    {
        return this.software;
    }
    
    public String getstate() 
    {
        return this.state;
    }
    
    public boolean getmanagedbackupenabled() 
    {
        return this.managedbackupenabled;
    }
    
    

}
