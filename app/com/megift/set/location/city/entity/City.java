package com.megift.set.location.city.entity;

import com.megift.resources.base.Entity;

/** 
 * @class        : City.java<br/>
 * @company      : Megift S.A<br/>
 * @user         : YQ<br/> 
 * @date         : Feb 23, 2015<br/> 
 * @update date  : Feb 23, 2015<br/> 
 * @update by    : Feb 23, 2015<br/> 
 * @version      : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 *
 */
public class City extends Entity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String city;
    private String country;

    /**
     * @param id
     */
    public City(int id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.megift.resources.base.Entity#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

}
