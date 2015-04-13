/**
 * 
 */
package com.megift.set.setting.entity;

import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Mar 22, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Mar 22, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Setting extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int RESULTS_BY_GIFTS = 29;
	public static final int RESULTS_BY_ACTIONS = 30;
	public static final int RESULTS_BY_POS = 31;
	public static final int RESULTS_BY_BUSINESS = 32;

	private MasterValue searchSetting;

	public Setting(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public MasterValue getSearchSetting() {
		return searchSetting;
	}

	public void setSearchSetting(MasterValue searchSetting) {
		this.searchSetting = searchSetting;
	}

}
