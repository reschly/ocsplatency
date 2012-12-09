package com.reschly.ocsplatency;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class ResultsReporter
{
	private static final String postURL = "http://localhost/ocsplatency";
	private static final String HOST_KEY = "host";
	private static final String RESPONDER_KEY = "responder";
	private static final String TOTAL_KEY = "totalMS";
	private static final String DNS_KEY = "dnsMS";
	private static final String TCP_KEY = "tcpMS";
	private static final String OCSP_KEY = "ocspMS";
	private static final String NETWORK_TYPE_KEY = "networkType";
	private static final String NETWORK_SUBTYPE_KEY = "networkSubtype";
	
	private String responder;
	private String host;
	private long totalLatency;
	private long dnsLatency;
	private long tcpLatency;
	private long ocspLatency;
	private String type;
	private String subType;
	
	
	public ResultsReporter(OCSPResponder responder, OCSPTimer timer, NetworkMetrics metrics)
	{
		this.responder = responder.getDisplayName();
		host = responder.getHost();
		totalLatency = timer.getTotalLatency();
		dnsLatency = timer.getDnsLatency();
		tcpLatency = timer.getTCPLatency();
		ocspLatency = timer.getOCSPLatency();
		type = metrics.getTypeName();
		subType = metrics.getSubTypeName();
	}
	
	
	public void report()
	{
		HttpPost post = new HttpPost(postURL);
		List<NameValuePair> pairs = new ArrayList<NameValuePair>(8);
		try
		{
			pairs.add(new BasicNameValuePair(HOST_KEY, host));
			pairs.add(new BasicNameValuePair(RESPONDER_KEY, responder));
			pairs.add(new BasicNameValuePair(TOTAL_KEY, Long.toString(totalLatency)));
			pairs.add(new BasicNameValuePair(DNS_KEY, Long.toString(dnsLatency)));
			pairs.add(new BasicNameValuePair(TCP_KEY, Long.toString(tcpLatency)));
			pairs.add(new BasicNameValuePair(OCSP_KEY, Long.toString(ocspLatency)));
			pairs.add(new BasicNameValuePair(NETWORK_TYPE_KEY, type));
			pairs.add(new BasicNameValuePair(NETWORK_SUBTYPE_KEY, subType));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			new DefaultHttpClient().execute(post);
		}
		catch (Exception e)
		{
			/* Intentionally ignoring reporting errors */
		}
	}
}
