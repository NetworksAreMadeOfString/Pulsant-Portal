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

public class Invoices 
{
	private String reference; 
	private double amount;
	private String details;
	private String date;
	
	public Invoices(String reference, double amount, String details, String date) 
    {
            super();
            this.reference = reference;
            this.amount = amount;
            this.details = details;
            this.date = date;
    }
	
	public String getreference() 
    {
        return this.reference;
    }
	
	public String getdetails() 
    {
        return this.details;
    }
	
	public String getdate() 
    {
        return this.date;
    }
	
	public double getamount() 
    {
        return this.amount;
    }
	    
}
