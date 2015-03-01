/**
 * 
 */
package com.megift.set.document.logic;

import com.megift.set.document.dao.DocumentDao;
import com.megift.set.document.entity.Document;

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
public class DocumentLogic {

	/**
	 * @param doc
	 * @return
	 */
	public static boolean save(Document doc) {
		boolean result = false;
		if (!doc.isEmpty()) {
			if (doc.exists()) {
				result = DocumentDao.update(doc);
			} else {
				result = DocumentDao.create(doc);
			}
		}
		return result;
	}

}
