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

public class ColoRacks {
	private String ip;
	private String servercode;
	private String facility;
	private int bandwidth;
	private String bandwidthTotal;
	private int transferlimit;
	private String state;
	
	 // Constructor for the Colo class
    public ColoRacks(String ip, String servercode, String facility, int bandwidth, String bandwidthTotal, int transferlimit, String state) 
    {
            super();
            this.ip = ip;
            this.servercode = servercode;
            this.facility = facility;
            this.bandwidth = bandwidth;
            this.bandwidthTotal = bandwidthTotal;
            this.transferlimit = transferlimit;
            this.state = state;
    }
    
    public String getip() 
    {
        return this.ip;
    }
    
    public String getservercode() 
    {
        return this.servercode;
    }
    
    public String getfacility() 
    {
        return this.facility;
    }

    public int getbandwidth() 
    {
        return this.bandwidth;
    }
    
    public String getbandwidthTotal() 
    {
        return this.bandwidthTotal;
    }
    
    public int gettransferlimit() 
    {
        return this.transferlimit;
    }
    
    public String getstate() 
    {
        return this.state;
    }
}
