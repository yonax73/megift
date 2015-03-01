package com.megift.set.document.entity;

import java.time.LocalDate;

import com.megift.resources.base.Entity;
import com.megift.set.master.entity.MasterValue;

/**
 * @class : Document.java<br/>
 * @company : Megift S.A<br/>
 * @user : YQ<br/>
 * @date : Feb 26, 2015<br/>
 * @update date : Feb 26, 2015<br/>
 * @update by : Feb 26, 2015<br/>
 * @version : 0.1 <br/>
 * @author Yonatan Alexis Quintero Rodriguez
 * 
 */
public class Document extends Entity {

	/**
	 * @param id
	 */
	public Document(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private MasterValue type;
	private String document;
	private String placeOfIssue;
	private LocalDate dateOfIssue;

	@Override
	public boolean isEmpty() {

		return document == null;
	}

	public MasterValue getType() {
		return type;
	}

	public void setType(MasterValue type) {
		this.type = type;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public LocalDate getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(LocalDate dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

}
