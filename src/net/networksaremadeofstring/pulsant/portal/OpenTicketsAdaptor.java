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
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class OpenTicketsAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;
    private String SessionID;
    private List<OpenTickets> listOpenTickets;

    public OpenTicketsAdaptor(Context context, List<OpenTickets> listOpenTickets, String SessionID) 
    {
        this.context = context;
        this.listOpenTickets = listOpenTickets;
        this.SessionID = SessionID;
    }

    public int getCount() {
        return listOpenTickets.size();
    }

    public Object getItem(int position) {
        return listOpenTickets.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	OpenTickets entry = listOpenTickets.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tickets_list, null);
        }
        TextView SubjectTextView = (TextView) convertView.findViewById(R.id.TicketSubject);
        SubjectTextView.setText(entry.getsubject());

        TextView UpdatedTextView = (TextView) convertView.findViewById(R.id.LastUpdated);
        UpdatedTextView.setText("( " + DateFormat.format("MM/dd/yy h:mmaa",new Date((long) entry.getlastupdate() * 1000)) + " )");
        
        TextView StatusTextView = (TextView) convertView.findViewById(R.id.TicketStatus);
        StatusTextView.setText(entry.getstatus());
        
        TextView CreatedTextView = (TextView) convertView.findViewById(R.id.TicketCreatedDate);
        CreatedTextView.setText(Integer.toString(entry.getcreatedat()));
        
        TextView IDTextView = (TextView) convertView.findViewById(R.id.TicketID);
        IDTextView.setText(Integer.toString(entry.getid()));
        
        //SubjectTextView.setOnClickListener(this); //legacy - let people click the entire thing rather than just the ticket title
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
    	
    	RelativeLayout RL = (RelativeLayout) view;
    	TextView TicketID = (TextView) RL.getChildAt(5);
    	String ID = TicketID.getText().toString();
    	((TicketLanding)context).ReadTicket(ID, this.SessionID);
    }

}
