package entity.impl;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import entity.RetrieveEntity;

/**
 * Class to retrieve Entities implementing the interface RetrieveEntity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class RetrieveEntityImpl implements RetrieveEntity{

	/**
	 * Retrieve a NoSQL entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 */
	public void retrieveEntity(String url, String databaseType) {

		try {
			ClientResource cr = new ClientResource(url);
			Form headers = (Form) cr.getRequestAttributes().get(
					"org.restlet.http.headers");

			if (headers == null) {
				headers = new Form();
				headers.add("Database-Type", databaseType);
				cr.getRequestAttributes().put("org.restlet.http.headers", headers);
			}
			cr.get().write(System.out);
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	/**
	 * retrieve a relational entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 * @param databaseName
	 * 			  the database name
	 */
	public void retrieveEntity(String url, String databaseType,
			String databaseName) {
		try {
			ClientResource cr = new ClientResource(url);
			Form headers = (Form) cr.getRequestAttributes().get(
					"org.restlet.http.headers");

			if (headers == null) {
				headers = new Form();
				headers.add("Database-Type", databaseType);
				headers.add("Database-Name", databaseName);
				cr.getRequestAttributes().put("org.restlet.http.headers", headers);
			}
			
			cr.get().write(System.out);
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
