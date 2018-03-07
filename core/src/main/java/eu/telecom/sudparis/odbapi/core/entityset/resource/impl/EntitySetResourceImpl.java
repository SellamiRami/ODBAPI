package eu.telecom.sudparis.odbapi.core.entityset.resource.impl;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;

import eu.telecom.sudparis.odbapi.core.entityset.resource.EntitySetResource;
import eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl.CouchDBEntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl.JDBCEntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl.MongoDBEntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl.RiakEntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.CORSpropertiesParserImpl;

/**
 * Class for managing resources of type Entity Set
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class EntitySetResourceImpl extends ServerResource implements
	EntitySetResource {

    /**
     * Create an Entity Set
     * 
     * @param entity
     *            the name of the resource to create
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    @Put
    public JSONObject setEntitySet(Representation entity) {
	try {
	    Form requestHeaders = (Form) getRequest().getAttributes().get(
		    "org.restlet.http.headers");

	    // Extract the type and name of the target database
	    String db_name = requestHeaders.getFirstValue("Database-Name");
	    String db_type = requestHeaders.getFirstValue("Database-Type");

	    // Extract the name of an Entity Set
	    String es_name = (String) getRequestAttributes().get("esName");

        Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers"); 
	if (responseHeaders == null) { 
		responseHeaders = new Form(); 
		getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
	}
	CORSpropertiesParserImpl cors= new CORSpropertiesParserImpl(); 
	responseHeaders.add("Access-Control-Allow-Origin", cors.getAccessControlAllowOrigin()); 
	responseHeaders.add("Access-Control-Allow-Methods", cors.getAccessControlAllowMethods());
	responseHeaders.add("Access-Control-Allow-Headers", cors.getAccessControlAllowHeaders()); 
	responseHeaders.add("Access-Control-Allow-Credentials", cors.getAccessControlAllowCredentials()); 
	responseHeaders.add("Access-Control-Max-Age", cors.getAccessControlMaxAge());
	    // Create an Entity Set according to the type of the database
	    if ("database/couchDB".equals(db_type)) {
		return new CouchDBEntitySetToolkit().create(es_name);
	    } else if ("database/Riak".equals(db_type)) {
		return new RiakEntitySetToolkit().create(es_name);
	    } else if ("database/MySQL".equals(db_type)) {
		JsonRepresentation represent = new JsonRepresentation(entity);
		JSONObject jsonToPut = represent.getJsonObject();

		return new JDBCEntitySetToolkit().create(db_name, es_name,
			jsonToPut);
	    } else if ("database/mongoDB".equals(db_type)) {
		return new MongoDBEntitySetToolkit().create(db_name, es_name,
			null);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return null;
    }

    /**
     * Retrieve an Entity Set by its esName
     * 
     * @param entity
     *            the name of the resource
     * @return if OK return the object of the target resource and 200 status
     *         code else return null and error status code
     */
    @Get
    public JSONObject getEntitySetByName(Representation entity)
	    throws IOException {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");
	Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers"); 
	if (responseHeaders == null) { 
		responseHeaders = new Form(); 
		getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
	}
	CORSpropertiesParserImpl cors= new CORSpropertiesParserImpl(); 
	responseHeaders.add("Access-Control-Allow-Origin", cors.getAccessControlAllowOrigin()); 
	responseHeaders.add("Access-Control-Allow-Methods", cors.getAccessControlAllowMethods());
	responseHeaders.add("Access-Control-Allow-Headers", cors.getAccessControlAllowHeaders()); 
	responseHeaders.add("Access-Control-Allow-Credentials", cors.getAccessControlAllowCredentials()); 
	responseHeaders.add("Access-Control-Max-Age", cors.getAccessControlMaxAge());
	// Retrieve an Entity Set according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntitySetToolkit().retrieve(es_name);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntitySetToolkit().retrieve(es_name);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntitySetToolkit().retrieve(db_name, es_name);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntitySetToolkit().retrieve(db_name, es_name);
	} else {
	    return null;
	}
    }

    /**
     * Delete an Entity Set
     * 
     * @throws JSONException
     */
    @Delete
    public JSONObject deleteEntitySET() throws IOException, JSONException {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");
	Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers"); 
	if (responseHeaders == null) { 
		responseHeaders = new Form(); 
		getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
	}
	CORSpropertiesParserImpl cors= new CORSpropertiesParserImpl(); 
	responseHeaders.add("Access-Control-Allow-Origin", cors.getAccessControlAllowOrigin()); 
	responseHeaders.add("Access-Control-Allow-Methods", cors.getAccessControlAllowMethods());
	responseHeaders.add("Access-Control-Allow-Headers", cors.getAccessControlAllowHeaders()); 
	responseHeaders.add("Access-Control-Allow-Credentials", cors.getAccessControlAllowCredentials()); 
	responseHeaders.add("Access-Control-Max-Age", cors.getAccessControlMaxAge());
	// Delete an Entity Set according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntitySetToolkit().delete(es_name);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntitySetToolkit().delete(es_name);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntitySetToolkit().delete(db_name, es_name);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntitySetToolkit().delete(db_name, es_name);
	} else {
	    return null;
	}
    }

	@Options
	public void doOptions(Representation entity) {
	    	Form responseHeaders = (Form) getResponse().getAttributes().get("org.restlet.http.headers"); 
	    	if (responseHeaders == null) { 
			responseHeaders = new Form(); 
			getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders); 
	    	}
	    	CORSpropertiesParserImpl cors= new CORSpropertiesParserImpl(); 
		responseHeaders.add("Access-Control-Allow-Origin", cors.getAccessControlAllowOrigin()); 
		responseHeaders.add("Access-Control-Allow-Methods", cors.getAccessControlAllowMethods());
		responseHeaders.add("Access-Control-Allow-Headers", cors.getAccessControlAllowHeaders()); 
		responseHeaders.add("Access-Control-Allow-Credentials", cors.getAccessControlAllowCredentials()); 
		responseHeaders.add("Access-Control-Max-Age", cors.getAccessControlMaxAge()); 
	}
}
