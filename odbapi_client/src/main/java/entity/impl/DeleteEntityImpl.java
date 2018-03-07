package entity.impl;

import java.io.IOException;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import entity.DeleteEntity;

/**
 * Class to delete Entities implementing the interface DeleteEntity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class DeleteEntityImpl implements DeleteEntity{

	/**
	 * Delete a NoSQL entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 */
	public void deleteEntity(String url, String databaseType) {
		try {
			ClientResource cr = new ClientResource(url);
			Form headers = (Form) cr.getRequestAttributes().get(
					"org.restlet.http.headers");

			if (headers == null) {
				headers = new Form();
				headers.add("Database-Type", databaseType);
				cr.getRequestAttributes().put("org.restlet.http.headers", headers);
			}

			
			cr.delete().write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Delete a relational entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 * @param databaseName
	 * 			  the database name
	 */
	public void deleteEntity(String url, String databaseType,
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
			cr.delete().write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
