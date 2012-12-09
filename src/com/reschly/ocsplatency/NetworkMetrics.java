package com.reschly.ocsplatency;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkMetrics
{
	private NetworkInfo netInfo;
	private String typeName;
	private String subTypeName;
	
	public NetworkMetrics(ConnectivityManager cm)
	{
		netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null)
		{
			typeName = netInfo.getTypeName();
			subTypeName = netInfo.getSubtypeName();
		}

		if (typeName == null)
			typeName = "";
		if (subTypeName == null)
			subTypeName = "";
	}

	public String getTypeName()
	{
		return typeName;
	}

	public String getSubTypeName()
	{
		return subTypeName;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Type: " + typeName + "\n");
		if (subTypeName.length() > 0)
			sb.append("Subtype: " + subTypeName + "\n");		
		return sb.toString();
	}
}
