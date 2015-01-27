package com.megift.resources.email;

import java.net.MalformedURLException;
import java.net.URL;

public class Resource {
	
    private URL url;
    private String name;
    
    public Resource(URL url, String name) {
      this.url = url;
      this.name = name;
    }
    
    public Resource(String url, String name) throws MalformedURLException {
      this(new URL(url), name);
    }
    
    public URL getUrl() {
      return url;
    }
    
    public void setUrl(URL url) {
      this.url = url;
    }
    
    public String getName() {
      return name;
    }
    
    public void setName(String name) {
      this.name = name;
    }
}
