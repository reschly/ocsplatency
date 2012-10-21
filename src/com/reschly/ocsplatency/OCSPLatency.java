package com.reschly.ocsplatency;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;




public class OCSPLatency
{	
	public static final int timeout = 5000;
	
	public static OCSPTimer checkLatency(byte[] request, String host, int port) throws UnknownHostException, SocketException, IOException
	{
		OCSPTimer timer = new OCSPTimer();
		byte[] resp = new byte[8192];
		Socket sock = new Socket();
		sock.setSoTimeout(timeout);
		InetAddress responder;

		timer.start();
		responder = InetAddress.getByName(host);
		timer.dnsFinished();
		sock.connect(new InetSocketAddress(responder, port), timeout);
		timer.tcpFinished();
		sock.getOutputStream().write(request);
		sock.getInputStream().read(resp);
		timer.ocspFinished();
				
		return timer;
	}	

}
