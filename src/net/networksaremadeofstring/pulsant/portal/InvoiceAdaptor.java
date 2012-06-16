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
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.networksaremadeofstring.pulsant.portal.R;

public class InvoiceAdaptor extends BaseAdapter implements OnClickListener {
    private Context context;
    private String SessionID;
    private List<Invoices> listInvoices;

    public InvoiceAdaptor(Context context, List<Invoices> listInvoices, String SessionID) 
    {
        this.context = context;
        this.listInvoices = listInvoices;
        this.SessionID = SessionID;
    }

    public int getCount() {
        return listInvoices.size();
    }

    public Object getItem(int position) {
        return listInvoices.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) 
    {
    	Invoices entry = listInvoices.get(position);
        if (convertView == null) 
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.invoices_list, null);
        }
        
        TextView ReferenceTextView = (TextView) convertView.findViewById(R.id.InvoiceReference);
        ReferenceTextView.setText(entry.getreference());

        TextView DateTextView = (TextView) convertView.findViewById(R.id.InvoiceDate);
        //DateTextView.setText("( " + DateFormat.format("MM/dd/yy h:mmaa",new Date((long) entry.getlastupdate() * 1000)) + " )");
        DateTextView.setText( entry.getdate());
        
        TextView InvoiceAmountTextView = (TextView) convertView.findViewById(R.id.InvoiceAmount);
        String amount = Double.toString(entry.getamount());
        if(amount.indexOf(".") > (amount.length() - 3))
        	amount = amount + "0";
        
        /*int digits = 10 - amount.length();
        
        for (int i = 0; i < digits; i++) 
        {
        	amount = " " + amount;
        }*/
        
        InvoiceAmountTextView.setText("£" + amount);
        
        TextView InvoiceDetailsTextView = (TextView) convertView.findViewById(R.id.InvoiceDetails);
       
        if(entry.getdetails().equals("Unallocated"))
        {
        	InvoiceDetailsTextView.setText("[ UNPAID ]");
        	InvoiceDetailsTextView.setTextColor(Color.RED);
        	InvoiceAmountTextView.setTextColor(Color.RED);
        }
        else
        {
        	InvoiceDetailsTextView.setText(entry.getdetails());
        	InvoiceDetailsTextView.setTextColor(Color.rgb(80, 150, 50));
        	InvoiceAmountTextView.setTextColor(Color.rgb(80, 150, 50));
        }
        
        
        //convertView.setOnClickListener(this);
        return convertView;
    }

    public void onClick(View view) 
    {
    	/*RelativeLayout RL = (RelativeLayout) view;
    	TextView TicketID = (TextView) RL.getChildAt(5);
    	String ID = TicketID.getText().toString();
    	((TicketLanding)context).ReadTicket(ID, this.SessionID);*/
    }

}
