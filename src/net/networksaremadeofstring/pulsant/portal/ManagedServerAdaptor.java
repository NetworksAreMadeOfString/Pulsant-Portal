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

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class ManagedServerAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;
    private String SessionID;
    private List<ManagedServer> ManagedServer;

    public ManagedServerAdaptor(Context context, List<ManagedServer> ManagedServer, String SessionID) 
    {
        this.context = context;
        this.ManagedServer = ManagedServer;
        this.SessionID = SessionID;
    }

    public int getCount() {
        return ManagedServer.size();
    }

    public Object getItem(int position) {
        return ManagedServer.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	ManagedServer Server = ManagedServer.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.managed_list, null);
            convertView = inflater.inflate(R.layout.managed_list, null);
        }
        TextView DEDCode = (TextView) convertView.findViewById(R.id.DEDCode);
        DEDCode.setText(Server.getservercode());
        
        TextView Description = (TextView) convertView.findViewById(R.id.Desc);
        Description.setText(Server.getdescription());
       
        TextView Facility = (TextView) convertView.findViewById(R.id.Facility);
        Facility.setText(Server.getfacility());
        
        TextView Bandwidth = (TextView) convertView.findViewById(R.id.ManagedBandwidth);
        Bandwidth.setText(Server.getbandwidthTotal());
        
        if(Server.getmanagedbackupenabled() == true)
        {
        	TextView BackupStatus = (TextView) convertView.findViewById(R.id.BackupStatus);
        	BackupStatus.setText("Enabled");
        }
        
        
        if(Server.getstate().equals("up"))
        {
        	ImageView imgView = (ImageView) convertView.findViewById(R.id.StatusIcon);
			imgView.setImageResource(R.drawable.up);
        }
        
        if(Server.getmonitored() == true)
        {
        	TextView MonitorStatus = (TextView) convertView.findViewById(R.id.MonitorStatus);
        	MonitorStatus.setText("Enabled");
        	/*ImageView imgView2 = (ImageView) convertView.findViewById(R.id.StateIcon);
        	imgView2.setImageResource(R.drawable.up);*/
        }
        
        if(Server.getsoftware().equals("windows"))
        {
        	ImageView imgView3 = (ImageView) convertView.findViewById(R.id.SoftwareIcon);
			imgView3.setImageResource(R.drawable.software_windows);
        }
        
        convertView.setTag(Server.getservercode());
        convertView.setOnClickListener(this);
        return convertView;
    }

    public void onClick(View view) 
    {
    	/*RelativeLayout RL = (RelativeLayout) view;
    	TextView DEDCode = (TextView) RL.getChildAt(4);
    	String ID = DEDCode.getText().toString();*/
    	((ManagedServerLanding)context).ViewServerDetails((String)view.getTag(), this.SessionID);
    }
}
