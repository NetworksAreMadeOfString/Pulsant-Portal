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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import net.networksaremadeofstring.pulsant.portal.R;

public class CDNAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<CDN> listCDNs;

    public CDNAdaptor(Context context, List<CDN> listCDNs) 
    {
        this.context = context;
        this.listCDNs = listCDNs;
    }

    public int getCount() {
        return listCDNs.size();
    }

    public Object getItem(int position) {
        return listCDNs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	CDN entry = listCDNs.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cdn_list, null);
        }
        TextView CDNNameTextView = (TextView) convertView.findViewById(R.id.CDNName);
        CDNNameTextView.setText(entry.getname());

        TextView EMEATextView = (TextView) convertView.findViewById(R.id.EMEAUsage);
        EMEATextView.setText(entry.getbandwidthemea_asGb());
        
        TextView USTextView = (TextView) convertView.findViewById(R.id.USUsage);
        USTextView.setText(entry.getbandwidthamericas_asGb());

        TextView HKTextView = (TextView) convertView.findViewById(R.id.AsiaUsage);
        HKTextView.setText(entry.getbandwidthapac_asGb());
        
        convertView.setOnClickListener(this);
        
        return convertView;
    }

    public void onClick(View view) 
    {
    	Toast.makeText(view.getContext(), "Detailed region breakdowns will be developed when the API supports it.", Toast.LENGTH_SHORT).show();
    	//TextView CDNDetailsTextView = (TextView) view.findViewById(R.id.CDNDetails);
    	//CDNDetailsTextView.setText("Coming soon......");
    }

}
