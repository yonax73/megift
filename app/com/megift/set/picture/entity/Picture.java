package com.megift.set.picture.entity;

import java.io.File;

import com.megift.resources.base.Entity;

/**
 * company : Megift S.A<br/>
 * user : YQ<br/>
 * update date : Feb 19, 2015<br/>
 * update by : Yonatan Alexis Quintero Rodriguez<br/>
 * 
 * @version : 0.1 <br/>
 * @created : Feb 19, 2015<br/>
 * @author Yonatan Alexis Quintero Rodriguez
 */
public class Picture extends Entity {

	/**
     * 
     */
	public static String BASE64_CODING = "base64";
	private static final long serialVersionUID = 1L;
	private String mime;
	private String src;
	private File file;
	private String coding;
	private boolean main;

	/**
	 * @param id
	 */
	public Picture(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param file2
	 */
	public Picture(int id, File file) {
		super(id);
		this.file = file;
	}

	@Override
	public boolean isEmpty() {
		return file == null;
	}

	public String getDataURI() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("data:");
		buffer.append(mime);
		buffer.append(";");
		buffer.append(coding);
		buffer.append(",");
		buffer.append(src);
		return buffer.toString();
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the coding
	 */
	public String getCoding() {
		return coding;
	}

	/**
	 * @param coding
	 *            the coding to set
	 */
	public void setCoding(String coding) {
		this.coding = coding;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

}
