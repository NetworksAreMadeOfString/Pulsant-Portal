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

public class TicketUpdates {
	private String type; 
	private String name;
	private int time;
	private String content;
	
	 // Constructor for the Ticket class
    public TicketUpdates(String type, String name, int time, String content) 
    {
            super();
            this.type = type;
            this.name = name;
            this.time = time;
            this.content = content;
    }
    
    public String gettype() 
    {
        return this.type;
    }
    
    public String getname() 
    {
        return this.name;
    }
    
    public String getcontent() 
    {
        return this.content;
    }

    public int gettime() 
    {
        return this.time;
    }

}
