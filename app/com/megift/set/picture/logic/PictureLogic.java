package com.megift.set.picture.logic;

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
public class PictureLogic {
    /**
     * @param picture
     * @return
     */
    public static boolean save(Partner partner) {
        boolean result = false;
        if (partner.getPicture().exists())
            result = PictureDao.update(partner.getPicture());
        else if (partner.exists())
            result = PictureDao.create(partner);
        return result;
    }

}
