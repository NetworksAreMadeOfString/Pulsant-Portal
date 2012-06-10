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
import java.text.DecimalFormat;

public class CDN 
{
	private String name;
	private String backend;
	private int bandwidthemea;
	private int bandwidthapac;
	private int bandwidthamericas;
	private boolean loadbalanced;
	private DecimalFormat numberFormat = new DecimalFormat();
	
	 // Constructor for the CDN class
    public CDN(String name, String backend, int bandwidthemea, int bandwidthapac, int bandwidthamericas, boolean loadbalanced) 
    {
            super();
            this.name = name;
            this.backend = backend;
            this.bandwidthemea = bandwidthemea;
            this.bandwidthapac = bandwidthapac;
            this.bandwidthamericas = bandwidthamericas;
            this.loadbalanced = loadbalanced;
            
        	numberFormat.setMaximumFractionDigits(2); 
        	numberFormat.setMinimumFractionDigits(2);
    }
    
    public String getname() 
    {
        return this.name;
    }
    
    public String getbackend() 
    {
        return this.backend;
    }
    
    public int getbandwidthemea() 
    {
        return this.bandwidthemea;
    }

    public int getbandwidthapac() 
    {
        return this.bandwidthapac;
    }
    
    public int getbandwidthamericas() 
    {
        return this.bandwidthamericas;
    }
    
    public boolean getloadbalanced() 
    {
        return this.loadbalanced;
    }
    
    public String getbandwidthemea_asGb() 
    {
    	double emea = (double) this.bandwidthemea / 1048576;
        return numberFormat.format(emea) +"Gb";
    	//return Float.toString((float)this.bandwidthemea / 1048576) +"Gb";
    }

    public String getbandwidthapac_asGb() 
    {
    	double apac = (double) this.bandwidthapac / 1048576;
        return numberFormat.format(apac) +"Gb";
    	//return Float.toString((float)this.bandwidthapac / 1048576) +"Gb";
    }
    
    public String getbandwidthamericas_asGb() 
    {
    	double usa = (double) this.bandwidthamericas / 1048576;
        return numberFormat.format(usa) +"Gb";
    	//return Float.toString((float)this.bandwidthamericas / 1048576) +"Gb";
    }
    
}
