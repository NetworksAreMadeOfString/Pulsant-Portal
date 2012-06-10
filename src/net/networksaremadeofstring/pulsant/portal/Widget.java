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

import android.content.SharedPreferences;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import net.networksaremadeofstring.pulsant.portal.R;

public class Widget extends AppWidgetProvider 
{
	RemoteViews remoteViews;
	AppWidgetManager appWidgetManager;
	ComponentName thisWidget;
	PortalAPI WidgetAPI = new PortalAPI();
	private SharedPreferences settings = null;
	
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) 
    {    	
    	this.appWidgetManager = appWidgetManager;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		thisWidget = new ComponentName(context, Widget.class);
		
		remoteViews.setTextViewText(R.id.ManagedStatus,"Changed");
    }
    
    @Override
    public void onEnabled(Context context) 
    {
    	super.onEnabled(context);
    	settings = PreferenceManager.getDefaultSharedPreferences(context);
    	
    	if(settings.getBoolean("Remember",false) == false)
    	{
    		remoteViews.setTextViewText(R.id.ManagedStatus,"Unavailable");
			remoteViews.setTextViewText(R.id.ColoStatus,"Unavailable");
			remoteViews.setTextViewText(R.id.BandwidthStatus,"Unavailable");
    	}
    	else
    	{
	    	//this.appWidgetManager = appWidgetManager;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			thisWidget = new ComponentName(context, Widget.class);
			
			remoteViews.setTextViewText(R.id.ManagedStatus,"Waiting...");
			remoteViews.setTextViewText(R.id.ColoStatus,"Waiting...");
			remoteViews.setTextViewText(R.id.BandwidthStatus,"Waiting...");
    	}
    }
}