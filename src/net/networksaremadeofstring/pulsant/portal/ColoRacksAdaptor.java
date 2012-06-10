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
import android.widget.ImageView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class ColoRacksAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<ColoRacks> listColoRacks;

    public ColoRacksAdaptor(Context context, List<ColoRacks> listColoRacks) {
        this.context = context;
        this.listColoRacks = listColoRacks;
    }

    public int getCount() {
        return listColoRacks.size();
    }

    public Object getItem(int position) {
        return listColoRacks.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
    	ColoRacks entry = listColoRacks.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.colo_list_iclone, null);
        }
        TextView servercodeTextView = (TextView) convertView.findViewById(R.id.coloServerCode);
        servercodeTextView.setText(entry.getservercode());

        /*TextView stateTextView = (TextView) convertView.findViewById(R.id.coloState);
        stateTextView.setText("( " + entry.getstate() + " )");
        
        TextView facilityTextView = (TextView) convertView.findViewById(R.id.coloFacility);
        facilityTextView.setText(entry.getfacility());*/

        TextView bandwidthTextView = (TextView) convertView.findViewById(R.id.coloBandwidth);
        bandwidthTextView.setText(entry.getbandwidthTotal());

        if(entry.getstate().equals("up"))
        {
        	ImageView imgView = (ImageView) convertView.findViewById(R.id.ColoStateIcon);
			imgView.setImageResource(R.drawable.up);
        }
        
        // Set the onClick Listener on this button
        /*Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);
        // Set the entry, so that you can capture which item was clicked and
        // then remove it
        // As an alternative, you can use the id/position of the item to capture
        // the item
        // that was clicked.
        btnRemove.setTag(entry);

        // btnRemove.setId(position);*/

        return convertView;
    }

    public void onClick(View view) {
    	ColoRacks entry = (ColoRacks) view.getTag();
    	listColoRacks.remove(entry);
        // listPhonebook.remove(view.getId());
        notifyDataSetChanged();

    }

}
