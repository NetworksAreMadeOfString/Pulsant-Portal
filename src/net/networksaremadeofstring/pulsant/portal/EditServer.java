package net.networksaremadeofstring.pulsant.portal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import net.networksaremadeofstring.pulsant.portal.R;

public class EditServer extends Activity
{
	PortalAPI API = new PortalAPI();
	ProgressDialog dialog;
	JSONObject APIResponse = null;
	String Server = "";
	Thread peformUpdate;
	Handler handler;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		//We need the API key if we make any changes
		API.SessionID = getIntent().getStringExtra("sessionid");
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Updating the portal.....");
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.editserver);
	    
	    if(getIntent().getStringExtra("software").equals("windows"))
        {
        	ImageView imgView = (ImageView) findViewById(R.id.SoftwareImage);
			imgView.setImageResource(R.drawable.hd_windows);
        }
	    
	    TextView DEDCode = (TextView) findViewById(R.id.DEDCode);
	    Server = getIntent().getStringExtra("servercode");
	    DEDCode.setText(Server);
	    
	    EditText Description = (EditText) findViewById(R.id.ServerDescEditText);
	    Description.setText(getIntent().getStringExtra("description"));
	    
	    handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			String Success = "false";
				try 
				{
					Success = APIResponse.getString("success");
				} 
				catch (JSONException e) 
				{
					Toast.makeText(EditServer.this, "Error Occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
					finish();
				}
    			
    			if(Success.equals("true"))
    			{
    				//Tell the user
            		Toast.makeText(EditServer.this, "Updated!", Toast.LENGTH_SHORT).show();
            		finish();
    			}
    			else
    			{
    				Toast.makeText(EditServer.this, "Update failed.", Toast.LENGTH_SHORT).show();
    			}
    		}
    	};
	    
	    Button LoginButton = (Button) findViewById(R.id.SaveDetails);
	    LoginButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) 
	        {
	        	dialog.show();
	        	CreateThread();
	        	peformUpdate.start();
	        }});
	}
	
	public void CreateThread()
    {
		peformUpdate = new Thread() 
    	{  
    		public void run() 
    		{
    			EditText Description = (EditText) findViewById(R.id.ServerDescEditText);
    			
    			try 
    			{
					APIResponse = API.PortalQueryHack("UpdateDesc", Server, Description.getText().toString());
				} 
    			catch (JSONException e) 
    			{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            	handler.sendEmptyMessage(0);
            	return;
    		}
    	};
    	
    }
	
	
}
