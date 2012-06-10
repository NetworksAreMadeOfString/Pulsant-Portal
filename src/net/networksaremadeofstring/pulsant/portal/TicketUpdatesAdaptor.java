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

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class TicketUpdatesAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;
    //private String SessionID;
    private List<TicketUpdates> TicketUpdates;

    public TicketUpdatesAdaptor(Context context, List<TicketUpdates> TicketUpdates, String SessionID) 
    {
        this.context = context;
        this.TicketUpdates = TicketUpdates;
       // this.SessionID = SessionID;
    }

    public int getCount() {
        return TicketUpdates.size();
    }

    public Object getItem(int position) {
        return TicketUpdates.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	TicketUpdates entry = TicketUpdates.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.updates_list, null);
        }
        TextView UpdaterName = (TextView) convertView.findViewById(R.id.UpdaterName);
        UpdaterName.setText(entry.getname());

        TextView UpdatedTextView = (TextView) convertView.findViewById(R.id.updateTime);
        UpdatedTextView.setText("( " + DateFormat.format("MM/dd/yy h:mmaa",new Date((long) entry.gettime() * 1000)) + " )");
        
        TextView UpdateText = (TextView) convertView.findViewById(R.id.UpdateContent);
        UpdateText.setText(entry.getcontent());
       
        if(entry.gettype().equals("dedipower"))
        {
        	ImageView imgView = (ImageView) convertView.findViewById(R.id.UpdaterIcon);
			imgView.setImageResource(R.drawable.dedipower_update);
        }
        else
        {
        	UpdaterName.setText("You");
        }
		
        convertView.setOnClickListener(this);
        return convertView;
    }

    public void onClick(View view) 
    {
    	/*RelativeLayout RL = (RelativeLayout) view.getParent();
    	TextView TicketID = (TextView) RL.getChildAt(5);
    	String ID = TicketID.getText().toString();
    	Log.e("APIFuncs","TicketID: " + ID + " / Session: " + this.SessionID);
    	//((TicketLanding)activity).ReadTicket(ID, this.SessionID);
    	((TicketLanding)context).ReadTicket(ID, this.SessionID);*/
    	
    	/*RelativeLayout RL = (RelativeLayout) view;
    	TextView TicketID = (TextView) RL.getChildAt(5);
    	String ID = TicketID.getText().toString();
    	((TicketLanding)context).ReadTicket(ID, this.SessionID);*/
    }

}
