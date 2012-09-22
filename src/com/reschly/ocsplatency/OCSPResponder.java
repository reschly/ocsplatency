package com.reschly.ocsplatency;


public class OCSPResponder
{
	private byte[] request;
	private String host;
	private int port;
	private String displayName;
	
	private static final String digicertEVRequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038330d0a0d0a3051304f304d304b3049300906052b0e03021a05000414b8a299f09d061dd5c1588f76cc89ff57092b94dd04144c58cb25f0414f52f428c881439ba6a8a0e692e50210044dffee75e453ec29bfe58a767ac1c7";
	private static final String digicertEVHost = "ocsp.digicert.com";
	private static final int digicertEVPort = 80;
	private static final String digicertEVDisplayName = "DigiCert High Assurance EV CA-1";
	
	private static final String entrustL1ERequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2037310d0a0d0a304530433041303f303d300906052b0e03021a05000414a26dbff1bfd96987ba020bdf578e9a965eee1c8e04145b418ab2c443c1bdbfc85441559de096adffb9a102044c202995";
	private static final String entrustL1EHost = "ocsp.entrust.net";
	private static final int entrustL1EPort = 80;
	private static final String entrustL1EDisplayName = "Entrust Certification Authority - L1E";
	
	private static final String globalsignRequestString = "504f5354202f6773657874656e6476616c673220485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038350d0a0d0a30533051304f304d304b300906052b0e03021a05000414a0720ea06a7c620254f2a8f59dd27ba4f3b72fa40414b0b04afd1c7528f81c61aa13f6fac1903d6b16a3021211210629afba98660632feca36b6d5425671";
	private static final String globalsignHost = "ocsp2.globalsign.com";
	private static final int globalsignPort = 80;
	private static final String globalsignDisplayName = "GlobalSign Extended Validation CA - G2";
	
	private static final String godaddyRequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2037340d0a0d0a30483046304430423040300906052b0e03021a0500041470292276537f1abc8fd53c9484e914cb762a052a0414fdac6132936c45d6e2ee855f9abae7769968cce702072b23e711fe41fd";
	private static final String godaddyHost = "ocsp.godaddy.com";
	private static final int godaddyPort = 80;
	private static final String godaddyDisplayName = "Go Daddy Secure Certification Authority";
	
	private static final String startcomClass1RequestString = "504f5354202f7375622f636c617373312f7365727665722f636120485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2037300d0a0d0a304430423040303e303c300906052b0e03021a050004146568874f40750f016a3475625e1f5c93e5a26d580414eb4234d098b0ab9ff41b6b08f7cc642eef0e2c45020305facc";
	private static final String startcomClass1Host = "ocsp.startssl.com";
	private static final int startcomClass1Port = 80;
	private static final String startcomClass1DisplayName = "StartCom Class 1 Primary Intermediate Server CA";
	
	private static final String startcomClass2RequestString = "504f5354202f7375622f636c617373322f7365727665722f636120485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2037300d0a0d0a304430423040303e303c300906052b0e03021a05000414b9b2d56db021b36e42f627245806c4a9a6979aeb04144e0bef1aa4405ba517698730ca346843d041aef202030092a9";
	private static final String startcomClass2Host = "ocsp.startssl.com";
	private static final int startcomClass2Port = 80;
	private static final String startcomClass2DisplayName = "StartCom Class 2 Primary Intermediate Server CA";
	
	private static final String thawteRequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038330d0a0d0a3051304f304d304b3049300906052b0e03021a050004141e9209aa713c794bca1e931a0a61ad3fd0ba608304143b349a709173b28a1b0cf4e937cdb370329e185402104f9d96d966b0992b54c2957cb4157d4d";
	private static final String thawteHost = "ocsp.thawte.com";
	private static final int thawtePort = 80;
	private static final String thawteDisplayName = "Thawte SGC CA";
	
	private static final String verisignClass3EVRequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038330d0a0d0a3051304f304d304b3049300906052b0e03021a0500041445a7d4d407751a5fbded1e191d4a8dec52d241550414fc8a50ba9eb9255a7b55854f9500638fe9586b4302101727dd643e4236f87f79bb046bc19802";
	private static final String verisignClass3EVHost = "EVSecure-ocsp.verisign.com";
	private static final int verisignClass3EVPort = 80;
	private static final String verisignClass3EVDisplayName = "VeriSign Class 3 Extended Validation SSL CA";
	
	private static final String verisignClass3G2RequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038330d0a0d0a3051304f304d304b3049300906052b0e03021a050004146c2bc55aaf8d96bf60adf81d023f23b48a0059c20414a5ef0b11cec04103a34a659048b21ce0572d7d47021025f5d12d5e6f0bd4eaf2a2c966f3b4ce";
	private static final String verisignClass3G2Host = "ocsp.verisign.com";
	private static final int verisignClass3G2Port = 80;
	private static final String verisignClass3G2DisplayName = "VeriSign Class 3 Secure Server CA - G2";
	
	private static final String verisignInternationalClass3RequestString = "504f5354202f20485454502f312e300d0a436f6e74656e742d547970653a206170706c69636174696f6e2f6f6373702d726571756573740d0a436f6e74656e742d4c656e6774683a2038330d0a0d0a3051304f304d304b3049300906052b0e03021a05000414c0fe0278fc99188891b3f212e9c7e1b21ab7bfc004140dfc1df0a9e0f01ce7f2b213177e6f8d157cd4f602103c08cfeebe9febc42bb13ee03d620bdf";
	private static final String verisignInternationalClass3Host = "ocsp.verisign.com";
	private static final int verisignInternationalClass3Port = 80;
	private static final String verisignInternationalClass3DisplayName = "VeriSign International Server CA - Class 3";
	
	private static final OCSPResponder[] responders = new OCSPResponder[]
	{
			new OCSPResponder(hexStringToByteArray(digicertEVRequestString), digicertEVHost, digicertEVPort, digicertEVDisplayName),
			new OCSPResponder(hexStringToByteArray(entrustL1ERequestString), entrustL1EHost, entrustL1EPort, entrustL1EDisplayName),
			new OCSPResponder(hexStringToByteArray(globalsignRequestString), globalsignHost, globalsignPort, globalsignDisplayName),
			new OCSPResponder(hexStringToByteArray(godaddyRequestString), godaddyHost, godaddyPort, godaddyDisplayName),
			new OCSPResponder(hexStringToByteArray(startcomClass1RequestString), startcomClass1Host, startcomClass1Port, startcomClass1DisplayName),
			new OCSPResponder(hexStringToByteArray(startcomClass2RequestString), startcomClass2Host, startcomClass2Port, startcomClass2DisplayName),
			new OCSPResponder(hexStringToByteArray(thawteRequestString), thawteHost, thawtePort, thawteDisplayName),
			new OCSPResponder(hexStringToByteArray(verisignClass3EVRequestString), verisignClass3EVHost, verisignClass3EVPort, verisignClass3EVDisplayName),
			new OCSPResponder(hexStringToByteArray(verisignClass3G2RequestString), verisignClass3G2Host, verisignClass3G2Port, verisignClass3G2DisplayName),
			new OCSPResponder(hexStringToByteArray(verisignInternationalClass3RequestString), verisignInternationalClass3Host, verisignInternationalClass3Port, verisignInternationalClass3DisplayName)
	};

	
	public OCSPResponder(byte[] request, String host, int port, String displayName)
	{
		this.request = request;
		this.host = host;
		this.port = port;
		this.displayName = displayName;
	}

	
	public byte[] getRequest()
	{
		return request;
	}


	public String getHost()
	{
		return host;
	}


	public int getPort()
	{
		return port;
	}


	public String getDisplayName()
	{
		return displayName;
	}


	public static OCSPResponder[] getResponders()
	{
		return responders;
	}
	
	public static CharSequence[] getDisplayNames()
	{
		CharSequence result[] = new CharSequence[responders.length];
		
		for (int i = 0; i < responders.length; i++)
			result[i] = responders[i].getDisplayName();
		
		return result;
	}
	
	
	public static byte[] hexStringToByteArray(String s) 
	{
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}


	public static OCSPResponder findByDisplayname(String displayName)
	{
		for (OCSPResponder responder : responders)
		{
			if (responder.displayName.equals(displayName))
				return responder;
		}
		return null;
	}

}
