package com.megift.bsp.partner.entity;

import static com.megift.resources.utils.Constants.DATE_FORMATTER;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.megift.resources.base.Person;
import com.megift.sec.login.entity.Login;
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
public class Partner extends Person {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private LocalDate birthday;


	/**
	 * @param id
	 */
	public Partner(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public int getAge() {
        if (birthday != null) {
            Period p = Period.between(birthday, LocalDate.now());
            return p.getYears();
        } else {
            return 0;
        }
	}

    public String getFormatBirthday() {
        if (birthday != null) {
            return birthday.format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
        } else {
            return "";
        }
	}

	/**
	 * @param name
	 */
	public Partner(String name) {
		super(0);
		this.name = name;
	}

	public Partner(Login login) {
		super(0);
		this.login = login;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

}
