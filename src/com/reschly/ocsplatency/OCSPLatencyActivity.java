package com.reschly.ocsplatency;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.ConnectivityManager;
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
	
	public static final String FINISHED = "Finished";
	public static final String ERROR = "Error";
	
	private Button button;
	private Button allButton;
	private Spinner menu;
	private EditText resultsBox;
	private Toast toast;

	
    /** Called when the activity is first created. */
    @SuppressLint("ShowToast")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        menu = (Spinner) findViewById(R.id.responder_menu);
        button = (Button) findViewById(R.id.submit_buton);
        allButton = (Button) findViewById(R.id.submit_all_button);
        resultsBox = (EditText) findViewById(R.id.results_box);
        toast = Toast.makeText(this, null, Toast.LENGTH_LONG);


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, OCSPResponder.getDisplayNames() );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter);
        
        button.setOnClickListener(this);
        allButton.setOnClickListener(this);
        
        resultsBox.setEnabled(false);        
    }

    
    
	@Override
	public void onClick(View v)
	{	
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
			
			case R.id.submit_all_button:
				toast.setText("Checking All (reporting only)");
				toast.show();
		
				allButton.setEnabled(false);
				new OCSPLatencyCheckAll().execute();
				
				break;
			
			default:
				
		}	
	}
	
	
	private class OCSPLatencyCheck extends AsyncTask<Void, String, String> 
	{
		OCSPResponder responder;
		OCSPTimer timer;
		String latencyResults;
		
		public OCSPLatencyCheck(String displayName)
		{
			super();
			responder = OCSPResponder.findByDisplayname(displayName);
			
		}
		

		@Override
		protected String doInBackground(Void... args)
		{
			String result = FINISHED;
			
			try
			{				
				timer = OCSPLatency.checkLatency(responder.getRequest(), responder.getHost(), responder.getPort());
				latencyResults = timer.toString();
			}
			catch (UnknownHostException e)
			{
				result = ERROR;
				latencyResults = "DNS Error: " + e.getMessage();
			}
			catch (SocketTimeoutException e)
			{
				result = "Error";
				latencyResults = "Timeout: No response after " + OCSPLatency.timeout + " milliseconds";
			}
			catch (Exception e)
			{
				result = ERROR;
				latencyResults = "Error: " + e.getMessage();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			toast.setText(result);
			toast.show();
			StringBuilder sb = new StringBuilder();
			sb.append("Responder: " + responder.getDisplayName() + "\n" + "Host: " + responder.getHost() + "\n" + latencyResults);
			NetworkMetrics metrics = null;
			if (result.equals(FINISHED))
			{
				metrics = new NetworkMetrics((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
				sb.append(metrics);
			}
			resultsBox.setText(sb);
			if (result.equals(FINISHED))
			{
				new ResultsReporter(responder, timer, metrics).report();
			}
			menu.setEnabled(true);
			button.setEnabled(true);
		}
		
	}
    
	private class OCSPLatencyCheckAll extends AsyncTask<Void, String, String> 
	{
		OCSPTimer timer;
		NetworkMetrics metrics;
		
		public OCSPLatencyCheckAll()
		{
			super();			
		}
		

		@Override
		protected String doInBackground(Void... args)
		{
			String result = "Finished with all";
			
			for (OCSPResponder responder : OCSPResponder.getResponders())
			{
				try
				{				
					timer = OCSPLatency.checkLatency(responder.getRequest(), responder.getHost(), responder.getPort());
					metrics = new NetworkMetrics((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
					new ResultsReporter(responder, timer, metrics).report();
				}
				catch (UnknownHostException e)
				{
					// Ignore
				}
				catch (SocketTimeoutException e)
				{
					// Ignore
				}
				catch (Exception e)
				{
					// Ignore
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result)
		{
			toast.setText(result);
			toast.show();
			allButton.setEnabled(true);
		}    
    
	}
}