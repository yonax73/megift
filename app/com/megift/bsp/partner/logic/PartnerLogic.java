package com.megift.bsp.partner.logic;

import com.megift.bsp.partner.dao.PartnerDao;
import com.megift.bsp.partner.entity.Partner;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * date : Feb 19, 2015<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class PartnerLogic {

	/**
	 * @param partner
	 * @return
	 */
	public static boolean create(Partner partner) {
		boolean created = false;
		if (!partner.isEmpty()) {
			created = PartnerDao.create(partner);
		}
		return created;
	}

	public static boolean loadPartner(Partner partner) {
		return PartnerDao.loadPartner(partner);
	}

	public static boolean update(Partner partner) {
		boolean saved = false;
		if (!partner.isEmpty()) {
			if (partner.exists())
				saved = PartnerDao.update(partner);
		}
		return saved;
	}

	/**
	 * @param legalRepresentative
	 * @return
	 */
	public static boolean save(Partner partner) {
		boolean saved = false;
		if (!partner.isEmpty()) {
			if (partner.exists()) {
				saved = PartnerDao.update(partner);
			} else {
				saved = PartnerDao.create(partner);
			}
		}
		return saved;
	}

}
