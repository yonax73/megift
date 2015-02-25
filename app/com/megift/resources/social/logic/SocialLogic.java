package com.megift.resources.social.logic;

import static com.megift.resources.email.Email.INFO_EMAIL;
import static com.megift.resources.email.Email.INFO_EMAIL_PASSWORD;
import static com.megift.resources.email.Email.SERVER_NAME;
import static com.megift.resources.email.Email.SERVER_SECURITY_TYPE_SSL;
import static com.megift.resources.email.Email.SUPPORT_EMAIL;
import static com.megift.resources.email.Templates.RESOURCE_HEADER_EMAIL_IMAGE;
import static com.megift.resources.email.Templates.changePassword;
import static com.megift.resources.email.Templates.inviteEmailContacts;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;

import play.Logger;

import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.email.Email;
import com.megift.resources.email.Resource;
import com.megift.sec.login.entity.Login;

public class SocialLogic {

	public static boolean inviteFriendsByEmail(Partner partner, String to[]) {
		boolean result = false;
		Email email = new Email(SERVER_NAME, INFO_EMAIL, INFO_EMAIL_PASSWORD, SERVER_SECURITY_TYPE_SSL);
		try {
            email.sendHTMLMail(INFO_EMAIL, partner.getName(), to, to, "Invitación a Megift Colombia", inviteEmailContacts(partner.getName()), new Resource(
			        RESOURCE_HEADER_EMAIL_IMAGE, "Megift"));
			result = true;
		} catch (EmailException e) {
			Logger.error("Error tryning send register email \n" + e.getMessage());

		} catch (MalformedURLException e) {
			Logger.error("Error tryning create the resources for send register email \n" + e.getMessage());
		}
		return result;
	}

    /**
     * @param login
     * @return
     */
    public static boolean sendPasswordChangeRequest(Login login) {
        boolean result = false;
        Email email = new Email(SERVER_NAME, INFO_EMAIL, INFO_EMAIL_PASSWORD, SERVER_SECURITY_TYPE_SSL);
        try {
            String strEmail = login.getEmail();
            StringBuffer bufferLink = new StringBuffer();
            bufferLink.append("http://www.megift.co/passwordChange?login=");
            bufferLink.append(login.getId());
            bufferLink.append("&code=");
            bufferLink.append(login.getCodeRequest());
            email.sendHTMLMail(SUPPORT_EMAIL, "Soporte megift", strEmail, strEmail, "Solicitud cambio de contraseña", changePassword(bufferLink.toString()), new Resource(
                    RESOURCE_HEADER_EMAIL_IMAGE, "Megift"));
            result = true;
        } catch (EmailException e) {
            Logger.error("Error tryning send register email \n" + e.getMessage());

        } catch (MalformedURLException e) {
            Logger.error("Error tryning create the resources for send register email \n" + e.getMessage());
        }
        return result;
    }

}
