package com.megift.resources.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import static com.megift.resources.utils.Constants.EMAIL_SERVER_SECURITY_TYPE_SSL;
import static com.megift.resources.utils.Constants.EMAIL_SERVER_SECURITY_TYPE_TLS;

public class Email {
	  private String host;
	  private String username = null;
	  private String password = null;
	  private int smtpPort;

	  
	  public Email(String host, String username, String password,int smtpPort) {
	    this.host = host;
	    this.username = username;
	    this.password = password;
	    this.smtpPort = smtpPort;
	  }
	  
	  public void sendHTMLMail(String fromEmail,String fromName,String[] toEmail,String[] toName,String subject,String html,Resource[] resources) throws EmailException {
	    HtmlEmail email = new HtmlEmail();
	    email.setHostName(host);
	    email.setSmtpPort(smtpPort);
	    email.setSSLOnConnect(isSecurityTypeSSL());
	    email.setAuthentication(username, password);	    
	    email.setFrom(fromEmail, fromName);
	    int n = toEmail.length;
	    for (int i = 0; i < n ; i++) {
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
	    email.send();
	  }
	  }
	  
	  public boolean isSecurityTypeSSL(){
		  return smtpPort == EMAIL_SERVER_SECURITY_TYPE_SSL;
	  }
	  
	  public boolean isSecurityTypeTLS(){
		  return smtpPort == EMAIL_SERVER_SECURITY_TYPE_TLS;
	  }

}
