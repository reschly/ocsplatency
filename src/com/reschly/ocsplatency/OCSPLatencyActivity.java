package com.reschly.ocsplatency;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OCSPLatencyActivity extends Activity implements OnClickListener {
	
	private Button button;
	private Spinner menu;
	private EditText resultsBox;
	private Toast toast;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        menu = (Spinner) findViewById(R.id.responder_menu);
        button = (Button) findViewById(R.id.submit_buton);
        resultsBox = (EditText) findViewById(R.id.results_box);
        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, OCSPResponder.getDisplayNames() );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter);
        
        button.setOnClickListener(this);
        
        resultsBox.setEnabled(false);        
    }

    
    
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		toast.setText("Checking " + String.valueOf(menu.getSelectedItem()));
		toast.show();
		
		int id = v.getId();
		switch(id)
		{
			case R.id.submit_buton:
				String selected = String.valueOf(menu.getSelectedItem());
				button.setEnabled(false);
				menu.setEnabled(false);
				toast.setText("Checking " + selected);
				toast.show();
				
				new OCSPLatencyCheck(selected).execute();
								
				break;
			default:
				
		}	
	}
	
	
	private class OCSPLatencyCheck extends AsyncTask<Void, String, String> 
	{
		OCSPResponder responder;
		String latencyResults;
		
		public OCSPLatencyCheck(String displayName)
		{
			super();
			responder = OCSPResponder.findByDisplayname(displayName);
		}
		

		@Override
		protected String doInBackground(Void... args)
		{
			String result = "Finished";
			
			try
			{				
				latencyResults = OCSPLatency.checkLatency(responder.getRequest(), responder.getHost(), responder.getPort()).toString();
			}
			catch (UnknownHostException e)
			{
				result = "Error";
				latencyResults = "DNS Error: " + e.getMessage();
			}
			catch (SocketTimeoutException e)
			{
				result = "Error";
				latencyResults = "Timeout: No response after " + OCSPLatency.timeout + " milliseconds";
			}
			catch (Exception e)
			{
				result = "Error";
				latencyResults = "Error: " + e.getMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			toast.setText(result);
			toast.show();
			resultsBox.setText("Responder: " + responder.getDisplayName() + "\n" + "Host: " + responder.getHost() + "\n" + latencyResults);
			menu.setEnabled(true);
			button.setEnabled(true);
		}
		
	}
    
    
    
}