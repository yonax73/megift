/**
 * 
 */
package com.megift.bsp.business.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.db.DB;

import com.megift.bsp.business.entity.Business;
import com.megift.bsp.partner.entity.Partner;
import com.megift.resources.base.Dao;
import com.megift.sec.login.entity.Login;
import com.megift.set.document.entity.Document;
import com.megift.set.location.address.entity.Address;
import com.megift.set.location.entity.Location;
import com.megift.set.location.phone.entity.Phone;
import com.megift.set.master.entity.MasterValue;

/**
 * company : Megift S.A<br/>
 * user : yonatan<br/>
 * update date : Feb 28, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @created : Feb 28, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class BusinessDao extends Dao {

	/**
	 * @param business
	 * @return
	 */
	public static boolean update(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("CALL sp_bsp_businesses_UPDATE(?,?,?,?,?,?,?,?)");
			cst.setInt(1, business.getId());
			cst.setInt(2, business.getLegalRepresentative().getId());
			cst.setInt(3, business.getContact().getId());
			cst.setInt(4, business.getType().getId());
			cst.setString(5, business.getOtherType());
			cst.setString(6, business.getNIT());
			cst.setString(7, business.getLegalName());
			cst.setString(8, business.getTradeName());
			result = cst.executeUpdate() > 0;
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static boolean create(Business business) {
		boolean result = false;
		CallableStatement cst = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			String sql = "CALL sp_bsp_businesses_CREATE(?,?,?,?,?,?,?,?);";
			cst = conn.prepareCall(sql);
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setInt(2, business.getLegalRepresentative().getId());
			cst.setInt(3, business.getContact().getId());
			cst.setInt(4, business.getType().getId());
			cst.setString(5, business.getOtherType());
			cst.setString(6, business.getNIT());
			cst.setString(7, business.getLegalName());
			cst.setString(8, business.getTradeName());
			result = cst.executeUpdate() > 0;
			if (result)
				business.setId(cst.getInt(1));
		} catch (Exception e) {
			Logger.error(e.getMessage());
		} finally {
			close(cst, conn);
		}
		return result;
	}

	/**
	 * @param business
	 * @return
	 */
	public static Business load(Business business) {
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_businesses_LOAD(?)}");
			cst.setInt(1, business.getContact().getLogin().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				business.setId(rs.getInt(1));
				Partner legalRep = new Partner(rs.getInt(2));
				Document doc = new Document(rs.getInt(3));
				doc.setDocument(rs.getString(4));
				doc.setType(new MasterValue(rs.getInt(5)));
				doc.setPlaceOfIssue(rs.getString(6));
				legalRep.setName(rs.getString(7));
				legalRep.setDocument(doc);
				business.setLegalRepresentative(legalRep);
				Partner contact = business.getContact();
				contact.setId(rs.getInt(8));
				contact.getLogin().setEmail(rs.getString(9));
				Location loc = new Location(rs.getInt(10));
				Phone phone = new Phone(rs.getInt(11));
				phone.setNumber(rs.getString(12));
				phone.setExtension(rs.getString(13));
				phone.setMobile(rs.getString(14));
				Address address = new Address(rs.getInt(15));
				address.setAddress(rs.getString(16));
				loc.setAddress(address);
				loc.setPhone(phone);
				contact.setName(rs.getString(17));
				contact.setLocation(loc);
				business.setContact(contact);
				business.setType(new MasterValue(rs.getInt(18)));
				if (business.isOtherType())
					business.setOtherType(rs.getString(19));
				business.setNIT(rs.getString(20));
				business.setLegalName(rs.getString(21));
				business.setTradeName(rs.getString(22));
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the business.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return business;
	}

	/**
	 * @param business
	 * @return
	 */
	public static Business loadById(Business business) {
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_businesses_LOAD_BY_ID(?)}");
			cst.setInt(1, business.getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				Partner legalRep = new Partner(rs.getInt(1));
				Document doc = new Document(rs.getInt(2));
				doc.setDocument(rs.getString(3));
				doc.setType(new MasterValue(rs.getInt(4)));
				doc.setPlaceOfIssue(rs.getString(5));
				legalRep.setName(rs.getString(6));
				legalRep.setDocument(doc);
				business.setLegalRepresentative(legalRep);
				Partner contact = business.getContact() == null ? new Partner(0) : business.getContact();
				contact.setId(rs.getInt(7));
				if (contact.getLogin() == null) {
					contact.setLogin(new Login(0));
				}
				contact.getLogin().setEmail(rs.getString(8));
				Location loc = new Location(rs.getInt(9));
				Phone phone = new Phone(rs.getInt(10));
				phone.setNumber(rs.getString(11));
				phone.setExtension(rs.getString(12));
				phone.setMobile(rs.getString(13));
				Address address = new Address(rs.getInt(14));
				address.setAddress(rs.getString(15));
				loc.setAddress(address);
				loc.setPhone(phone);
				contact.setName(rs.getString(16));
				contact.setLocation(loc);
				business.setContact(contact);
				business.setType(new MasterValue(rs.getInt(17)));
				if (business.isOtherType())
					business.setOtherType(rs.getString(18));
				business.setNIT(rs.getString(19));
				business.setLegalName(rs.getString(20));
				business.setTradeName(rs.getString(21));
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred tryning loading the business.\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return business;
	}

	public static List<Business> search(Business business) {
		List<Business> businesses = null;
		CallableStatement cst = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = DB.getConnection();
			cst = conn.prepareCall("{CALL sp_bsp_businesses_SEARCH(?)}");
			cst.setInt(1, business.getType() == null ? 0 : business.getType().getId());
			rs = cst.executeQuery();
			if (rs.next()) {
				businesses = new ArrayList<Business>();
				do {
					Business b = new Business(rs.getInt(1));
					b.setTradeName(rs.getString(2));
					b.setGiftCount(rs.getInt(3));
					businesses.add(b);
				} while (rs.next());
			}
		} catch (Exception e) {
			Logger.error("An error has been occurred trying to search the business .\n" + e.getMessage(), e);
		} finally {
			close(rs, cst, conn);
		}
		return businesses;
	}
}
