package org.nag.translator.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import org.springframework.stereotype.Component;


/**
 * Implementation for loading content from specified URL.<br/>
 * Valid paths to load are http://someurl.com, https://secureurl.com, ftp://frpurl.com etc.
 */
@Component
public class URLSourceProvider implements SourceProvider {
	private InetSocketAddress iaddr; 
	private Proxy proxy; 
	
	public URLSourceProvider() {
		try {
			iaddr = new InetSocketAddress("ws-062", 8080);	
			proxy = new Proxy(Proxy.Type.HTTP, iaddr);
		} catch(Exception e) {
			System.out.println("Wrong proxy-server addres.");
		}	
	}

    public boolean isAllowed(String pathToSource) {
     
        try {
            URLConnection urlc = (new URL(pathToSource)).openConnection(proxy);
            System.out.println("Connecting through proxy...");
            if (urlc != null) return true;
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    public String load(String pathToSource) throws IOException {
    	System.out.println("\nConnecting...");

    	try(BufferedReader reader = new BufferedReader(
    				    new InputStreamReader(
    					  (new URL(pathToSource)).openConnection(proxy).getInputStream(), "UTF-8"
    				    )
    				)
    		)
    	{
    	    System.out.println("Connection OK.\n");
    	    int next;
    	    StringBuffer transl = new StringBuffer();

		    while((next = reader.read()) != -1) {
		    	transl = transl.append((char) next);
		    }

		    return transl.toString();

	    } catch (IOException e) {
	        System.out.println("Cannot read from URL: " + e);
	    }
	        
	    return null;
    }
}
