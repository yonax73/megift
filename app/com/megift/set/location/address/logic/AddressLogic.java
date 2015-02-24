package com.megift.set.location.address.logic;

import com.megift.set.location.address.dao.AddressDao;
import com.megift.set.location.address.entity.Address;

/** 
 * @class        : AddressLogic.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 24, 2015<br/> 
 * @update date  : Feb 24, 2015<br/> 
 * @update by    : Feb 24, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class AddressLogic {

    /**
     * @param address
     * @return
     */
    public static boolean save(Address address) {
        boolean result = false;
        if (address.exists())
            result = AddressDao.update(address);
        else
            result = AddressDao.create(address);
        return result;
    }

}
