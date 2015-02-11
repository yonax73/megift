package com.megift.resources.email;

public class Templates {
 
	public final static String RESOURCE_HEADER_EMAIL_IMAGE ="https://cdn.rawgit.com/yonaxTics/megift/master/public/images/templates/headerMail.png";
	
	public static String inviteEmailContacts(String userName,String contactName){		
		return "<!DOCTYPE html><html><body><div style=\"background-color: rgb(243, 243, 244); display: flex; height: auto;\"><div style=\"margin: 0px auto; max-width: 600px; min-width: 200px; background-color: rgb(255, 255, 255);\"><div ><img src=\"${0}\" height=\"100%\" width=\"100%\"></div><div style=\"font-family: Helvetica,arial,sans-serif;font-size: 14px;color: #686970;text-align: left;line-height: 20px;padding:15px;\"><p >Hola "+contactName+", <br><br>He tenido la suerte de encontrar enmi celular Megift, una aplicaci\u00F3n que me da regalos por hacer cosasdivertidas como salir a comer, comprar ropa, consentirme, y muchasm\u00E1s.<br><br>Creo que te podr\u00EDa interesar explorarla, desc\u00E1rgala en elsiguiente link: <br><br><a style=\"text-decoration: none;background-color: #eb4646; display: block;font-size: 16px;font-family: Helvetica,arial,sans-serif;text-align: center;color: #ffffff;font-weight: 300;padding:10px\" href=\"http://www.megift.co\" target=\"_blank\">DESCUBRIR MEGIFT</a><br><br>Para conocerla un poco m\u00E1s puedes visitar sus redes sociales.<br><ul style=\"font-family: Helvetica,arial,sans-serif;font-size: 14px;color: #686970;\"> <li> <a href=\"http://www.megift.co\" target=\"_blank\">megift</a></li><li> <a href=\"https://www.facebook.com/megiftcolombia\" target=\"_blank\">Facebook</a></li><li> <a href=\"https://www.twitter.com/megiftcolombia\" target=\"_blank\">Twitter</a></li><li> <a href=\"https://instagram.com/megiftcolombia\" target=\"_blank\">Instagram</a></li><li> <a href=\"https://www.google.com/+megiftcolombia\" target=\"_blank\">Google plus</a></li><li> <a href=\"https://www.youtube.com/channel/UC0N0S0-7QFeiHFafE-rdvLg\" target=\"_blank\">Youtube</a></li></ul> <br>"+userName+".<br></p></div></div></div></body></html>";
	}
	
	public static String successRegister(String userName){
		return "<!DOCTYPE html><html><body><div> <table> <tbody> <tr> <td><img src=\"${0}\" style=\"width: 30%;\"></td></tr><tr> <td> Bienvenido a megift.<br><br>Hola "+userName+",<br><br>\u00A1Felicitaciones! Tu solicitud ha sido recibida.<br><br>Estamos trabajando muy duro, para poderte dar muchos regalos y alegrar tu vida todos los d\u00EDas, porque sabemos que eres alguien especial.<br><br>Con Megift podr\u00E1s recibir regalos por: <ul> <li>Salir a comer</li><li>Comprar ropa</li><li>Consentirte</li><li>Y mucho m\u00E1s...</li></ul> Te pedimos un poco de paciencia, valdr\u00E1 la pena esperar.<br><br>Pronto te enviaremos un email con el link de descarga.<br><br>\u00A1Disfruta el regalo de la vida!<br><br>Equipo de Megift </td></tr></tbody> </table></div></body></html>";
	}
	   
	
	
}
