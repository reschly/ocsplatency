package com.reschly.ocsplatency;

public class OCSPTimer
{
	private long startTime = 0;
	private long dnsTime = 0;
	private long tcpTime = 0;
	private long ocspTime = 0;
	
	public void start()
	{
		startTime = System.currentTimeMillis();
	}
	public void dnsFinished()
	{
		dnsTime = System.currentTimeMillis();
	}
	public void tcpFinished()
	{
		tcpTime = System.currentTimeMillis();
	}
	public void ocspFinished()
	{
		ocspTime = System.currentTimeMillis();
	}
	
	public long getDnsLatency()
	{
		return dnsTime - startTime;
	}
	public long getTCPLatency()
	{
		return tcpTime - dnsTime;
	}
	public long getOCSPLatency()
	{
		return ocspTime - tcpTime;
	}
	public long getTotalLatency()
	{
		return ocspTime - startTime;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(256);
		sb.append("Total Latency: " + getTotalLatency() + " milliseconds\n");
		sb.append("DNS: " + getDnsLatency() + " milliseconds\n");
		sb.append("TCP Handshake: " + getTCPLatency() + " milliseconds\n");
		sb.append("OCSP: " + getOCSPLatency() + " milliseconds\n");
		
		return sb.toString();
	}

}
