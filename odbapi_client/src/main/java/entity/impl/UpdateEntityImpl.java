package entity.impl;

import java.io.IOException;

import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import entity.UpdateEntity;

/**
 * Class to update Entities implementing the interface UpdateEntity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class UpdateEntityImpl implements UpdateEntity{

	/**
	 * Update a NoSQL entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 * @param jsonEntity
	 *            the entity to update
	 */
	public void updateEntity(String url, String databaseType,
			JSONObject jsonEntity) {
		try {
			ClientResource cr = new ClientResource(url);
			Form headers = (Form) cr.getRequestAttributes().get(
					"org.restlet.http.headers");

			if (headers == null) {
				headers = new Form();
				headers.add("Database-Type", databaseType);
				cr.getRequestAttributes().put("org.restlet.http.headers", headers);
			}

			
			cr.post(new JsonRepresentation(jsonEntity), MediaType.APPLICATION_JSON).write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Update a relational entity 
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 * @param databaseName
	 *            the database name
	 * @param jsonEntity
	 *            the entity to update
	 */
	public void updateEntity(String url, String databaseType,
			String databaseName, JSONObject jsonEntity) {
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

			
			cr.post(new JsonRepresentation(jsonEntity), MediaType.APPLICATION_JSON).write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
