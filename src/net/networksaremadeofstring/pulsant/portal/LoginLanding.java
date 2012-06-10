package net.networksaremadeofstring.pulsant.portal;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginLanding extends Activity
{
	PortalAPI API = new PortalAPI();
	ProgressDialog dialog;
	String APIResponse = "";
	String AcctCode, Email, Password = "";
	private SharedPreferences settings = null;
	Thread peformLogin;
	Handler handler;
	List<NameValuePair> Credentials = new ArrayList<NameValuePair>(3);  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);
        
		dialog = new ProgressDialog(this);
		dialog.setMessage("Logging in.....");
		
        //Get our settings
        settings = getSharedPreferences("Pulsant", 0);
        ManageSettings();

        handler = new Handler() 
    	{
    		public void handleMessage(Message msg) 
    		{
    			dialog.dismiss();
    			//Apparantly stopping threads is no longer supported
    			//peformLogin.stop();
    			
    			if(APIResponse == "true")
    			{
    				//Tell the user
            		Toast.makeText(LoginLanding.this, "Login Successful", Toast.LENGTH_SHORT).show();
            		
            		//Check the "remember details checkbox"
            		CheckBox Remember = (CheckBox) findViewById(R.id.RememberDetailsCheckBox);
            		
            		SharedPreferences.Editor editor = settings.edit();
            		if(Remember.isChecked() == true)
            		{
                        editor.putString("AcctCode", AcctCode);
                        editor.putString("Email", Email);
                        editor.putString("Password", Password);
                        editor.putBoolean("Remember", true);
                        editor.commit();
            		}
            		else
            		{
                        editor.putString("AcctCode", "");
                        editor.putString("Email", "");
                        editor.putString("Password", "");
                        editor.putBoolean("Remember", false);
                        editor.commit();
            		}
            		
            		//Do extent stuff
            		Intent LauncherIntent = new Intent(LoginLanding.this, PortalLanding.class);
            		LauncherIntent.putExtra("sessionid", API.SessionID);
            		LoginLanding.this.startActivity(LauncherIntent);
            		finish();
    			}
    			else
    			{
    				Toast.makeText(LoginLanding.this, "Login Failed: " + APIResponse, Toast.LENGTH_SHORT).show();    				
    			}
    		}
    	};
    	
        Button LoginButton = (Button) findViewById(R.id.LoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	dialog.show();
            	EditText AcctCodeEditText = (EditText) findViewById(R.id.AcctCodeInput);
                EditText EmailEditText = (EditText) findViewById(R.id.EmailInput);
                EditText PasswordEditText = (EditText) findViewById(R.id.PasswordInput);
                AcctCode = AcctCodeEditText.getText().toString();
                Email = EmailEditText.getText().toString();
                Password = PasswordEditText.getText().toString();
                
                Credentials.add(new BasicNameValuePair("account", AcctCode));  
                Credentials.add(new BasicNameValuePair("username", Email));
                Credentials.add(new BasicNameValuePair("password", Password));
                CreateThread();
                peformLogin.start();
            }
        });
    }
    
    public void CreateThread()
    {
    	peformLogin = new Thread() 
    	{  
    		public void run() 
    		{
    			APIResponse = API.Login(Credentials);

            	handler.sendEmptyMessage(0);
            	return;
    		}
    	};
    	
    }
    public void ManageSettings()
    {
        EditText AcctCodeEditText = (EditText) findViewById(R.id.AcctCodeInput);
        EditText EmailEditText = (EditText) findViewById(R.id.EmailInput);
        EditText PasswordEditText = (EditText) findViewById(R.id.PasswordInput);
        CheckBox Remember = (CheckBox) findViewById(R.id.RememberDetailsCheckBox);
        
        AcctCodeEditText.setText(settings.getString("AcctCode", ""));
        EmailEditText.setText(settings.getString("Email", ""));
        PasswordEditText.setText(settings.getString("Password", ""));
        Remember.setChecked(settings.getBoolean("Remember", false));
    }
}