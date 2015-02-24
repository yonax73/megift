package com.megift.set.location.logic;

import com.megift.set.location.dao.LocationDao;
import com.megift.set.location.entity.Location;

public class LocationLogic {

    /**
     * @param location
     * @return
     */
    public static boolean save(Location location) {
        boolean result = false;
        if (location.exists())
            result = LocationDao.update(location);
        else
            result = LocationDao.create(location);
        return result;
    }

}
