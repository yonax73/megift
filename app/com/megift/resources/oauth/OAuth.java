package com.megift.resources.oauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;

import play.Logger;

public class OAuth {    
	
	private String id;
	private SocialAuthManager manager; 
	private String authURL;
	private Profile profile;
	private List<Contact> contactsList;
	private AuthProvider provider;
	

	private static final String VALUE_GOOGLE_KEY = "243898693327-170qb4voajlndqucubdsm3bghtugld92.apps.googleusercontent.com";
	private static final String VALUE_GOOGLE_SECRET = "QW8J06FFVTM8935mK6zErdm6";
	private static final String KEY_GOOGLE_KEY = "www.google.com.consumer_key";
	private static final String KEY_GOOGLE_SECRET = "www.google.com.consumer_secret";
	private static final String VALUE_HOTMAIL_KEY = "000000004813DD99";
	private static final String VALUE_HOTMAIL_SECRET = "QZz6X2v7Bes1ujH3PXDaA0kWKzpjlvG1";
	private static final String KEY_HOTMAIL_KEY = "consent.live.com.consumer_key";
	private static final String KEY_HOTMAIL_SECRET = "consent.live.com.consumer_secret";
	private static final String VALUE_YAHOO_KEY = "dj0yJmk9bXlzZlFaWFdjVnFqJmQ9WVdrOWNuWlRSek54TXpBbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD04Ng--";
	private static final String VALUE_YAHOO_SECRET = "5e2d3f08d64967069704c0affac97cf160f3597e";
	private static final String KEY_YAHOO_KEY = "api.login.yahoo.com.consumer_key";
	private static final String KEY_YAHOO_SECRET = "api.login.yahoo.com.consumer_secret";
	private static final String AUTHENTICATION_URL ="http://megift.co/successAuth";
	private static final String ID_GOOGLE ="google";
	private static final String ID_HOTMAIL ="hotmail";
	private static final String ID_YAHOO ="yahoo";	

	public OAuth(String id) {
		this.id = id;
		contactsList = new ArrayList<Contact>();
	}
	
	public boolean authenticate(){
		boolean auth = false;
    	try {
           if(id!= null && !id.isEmpty()){
	       		Properties properties = System.getProperties();    	
	       		switch (id) {
	   			case ID_GOOGLE:
	   	    		properties.put(KEY_GOOGLE_KEY,VALUE_GOOGLE_KEY);
	   	    		properties.put(KEY_GOOGLE_SECRET,VALUE_GOOGLE_SECRET);  	   	    		
	   				break;
	   			case ID_HOTMAIL:
	   	    		properties.put(KEY_HOTMAIL_KEY,VALUE_HOTMAIL_KEY);
	   	    		properties.put(KEY_HOTMAIL_SECRET,VALUE_HOTMAIL_SECRET);	   	    	
	   				break;
	   			case ID_YAHOO:
	   	    		properties.put(KEY_YAHOO_KEY,VALUE_YAHOO_KEY);
	   	    		properties.put(KEY_YAHOO_SECRET,VALUE_YAHOO_SECRET);	   	    	
	   				break;
	   			}		
	       		SocialAuthConfig config = new SocialAuthConfig();
	       		config.load(properties); 
	       		manager = new SocialAuthManager(); 
	       		manager.setSocialAuthConfig(config); 	      
	       		authURL =  manager.getAuthenticationUrl(id, AUTHENTICATION_URL);    		
	       		auth = true;
           }
		} catch (Exception e) {
			Logger.error("Error tryning authenticate for "+id+" \n"+e.getMessage());			
		}
		return auth;
	}
	
	public boolean loadParamsMap(Set<Map.Entry<String,String[]>> entries){
		   boolean loaded = false;           
		   try {
			  if(entries!=null){
				   final Map<String, String> paramsMap = new HashMap<String,String>(); 	       
		 	        for (Map.Entry<String,String[]> entry : entries) {
		 	            final String key = entry.getKey();  
		 	            String values[] = entry.getValue();
		 	            paramsMap.put(key, values[0].toString());
		 	        }    	        
					provider = manager.connect(paramsMap);
					profile = provider.getUserProfile();			
					contactsList = provider.getContactList();
					 loaded = true;
			  }
		} catch (Exception e) {
			Logger.error("Error tryning load params map \n"+e.getMessage());
		}			
		return loaded;
	}
	
	public String getId() {
		return id;
	}

	public String getAuthURL() {
		return authURL;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public List<Contact> getContactsList() {
		return contactsList;
	}


	
	
	
}
