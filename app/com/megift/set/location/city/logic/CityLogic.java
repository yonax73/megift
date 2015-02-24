package com.megift.set.location.city.logic;

import com.megift.set.location.city.dao.CityDao;
import com.megift.set.location.city.entity.City;
public class CityLogic {


    /**
     * @param city
     * @return
     */
    public static boolean save(City city) {
        boolean result = false;
        if (!city.isEmpty()) {
            if (city.exists())
                result = CityDao.update(city);
            else
                result = CityDao.create(city);
        }
        return result;
    }

}
