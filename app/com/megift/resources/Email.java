package com.megift.resources;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import java.net.MalformedURLException;
import java.net.URL;

public class Email {
         
	  private String host;
	  private  String username = null;
	  private  String password = null;

	  
	  public Email(String host, String username, String password) {
	    this.host = host;
	    this.username = username;
	    this.password = password;

	  }
	  
	  public void sendHTMLMail(String fromEmail,String fromName,String[] toEmail,String[] toName,String subject,String html,String alternateText,Resource[] resources) throws EmailException {
	    HtmlEmail email = new HtmlEmail();
	    email.setHostName(host);
	    email.setSmtpPort(587);
	    if (username != null) {
		      email.setAuthentication(username, password);
		     
		    }	
	    email.setSSLOnConnect(true);
	    email.setFrom(fromEmail, fromName);
	    for (int i = 0; i < toEmail.length; i++) {
	      email.addTo(toEmail[i], toName[i]);
	    }

	    if (resources != null) {
	        int i = 0;
	        for (Resource resource : resources) {
	          String id = email.embed(resource.getUrl(), resource.getName());
	          html = html.replaceAll("\\$\\{" + i++ + "\\}", "cid:" + id);
	        }
	    email.setSubject(subject);
	    email.setHtmlMsg(html);
	    email.setTextMsg(alternateText);
	    email.send();
	  }
    }
  public static class Resource {
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
}

	  

