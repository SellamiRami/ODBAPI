package entitySet.impl;

import java.io.IOException;

import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import entitySet.CreateEntitySet;

/**
 * Class to create Entity Sets implementing the interface CreateEntitySet
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class CreateEntitySetImpl implements CreateEntitySet{

	/**
	 * Create a NoSQL entity Set
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 */
	public void createEntitySet(String url, String databaseType) {
		try {
			ClientResource cr = new ClientResource(url);
			Form headers = (Form) cr.getRequestAttributes().get(
					"org.restlet.http.headers");

			if (headers == null) {
				headers = new Form();
				headers.add("Database-Type", databaseType);
				cr.getRequestAttributes().put("org.restlet.http.headers", headers);
			}

			
			cr.put(null).write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a relational entity Set
	 * 
	 * @param url
	 *            the resource url
	 * @param databaseType
	 *            the database type
	 * @param databaseName
	 *            the database name
	 * @param jsonEntity
	 *            the entity set to insert (columns names, domains, PK, etc.)
	 */
	public void createEntitySet(String url, String databaseType,
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

			
			cr.put(new JsonRepresentation(jsonEntity), MediaType.APPLICATION_JSON).write(System.out);
			
		} catch (ResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
