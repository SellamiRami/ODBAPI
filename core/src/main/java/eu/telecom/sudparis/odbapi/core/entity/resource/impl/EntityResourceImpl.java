package eu.telecom.sudparis.odbapi.core.entity.resource.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;

import eu.telecom.sudparis.odbapi.core.entity.resource.EntityResource;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.CouchDBEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.JDBCEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.MongoDBEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.RiakEntityToolkit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.CORSpropertiesParserImpl;

/**
 * Class for managing resources of type Entity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class EntityResourceImpl extends ServerResource implements
	EntityResource {

    /**
     * Create an Entity
     * 
     * @param entity
     *            the name of the resource to create
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    @Post("json")
    public JSONObject setEntity(Representation entity) throws IOException,
	    JSONException {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");

	// Extract the id of an Entity
	String entity_id = (String) getRequestAttributes().get("entityId");

	// Parse the given representation and retrieve data
	JsonRepresentation represent = new JsonRepresentation(entity);
	JSONObject jsonEntity = represent.getJsonObject();
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
	// Create an Entity according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntityToolkit().create(es_name, entity_id,
		    jsonEntity);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntityToolkit().create(db_name, es_name, entity_id,
		    jsonEntity);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntityToolkit().create(es_name, entity_id,
		    jsonEntity);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntityToolkit().create(db_name, es_name,
		    entity_id, jsonEntity);
	} else {
	    return null;
	}
    }

    /**
     * Retrieve an Entity by its entityID
     * 
     * @param entity
     *            the name of the resource to retrieve
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */

    @Get("json")
    public JSONObject getEntityById(Representation entity) throws SQLException,
	    ClassNotFoundException, IOException {
	
        Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");

	// Extract the id of an Entity
	String entity_id = (String) getRequestAttributes().get("entityId");
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
	// Retrieve an Entity according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntityToolkit().retrieve(es_name, entity_id);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntityToolkit()
		    .retrieve(db_name, es_name, entity_id);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntityToolkit().retrieve(es_name, entity_id);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntityToolkit().retrieve(db_name, es_name,
		    entity_id);
	} else {
	    return null;
	}
    }

    /**
     * Update an Entity
     * 
     * @param entity
     *            the name of the resource to update
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    @Put("json")
    public JSONObject updateEntity(Representation entity) throws IOException,
	    JSONException {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");

	// Extract the id of an Entity
	String entity_id = (String) getRequestAttributes().get("entityId");

	// Parse the given representation and retrieve data
	JsonRepresentation represent = new JsonRepresentation(entity);
	JSONObject jsonEntity = represent.getJsonObject();
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
	// Update an Entity according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntityToolkit().update(es_name, entity_id,
		    jsonEntity);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntityToolkit().update(db_name, es_name, entity_id,
		    jsonEntity);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntityToolkit().update(es_name, entity_id,
		    jsonEntity);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntityToolkit().update(db_name, es_name,
		    entity_id, jsonEntity);
	} else {
	    return null;
	}
    }

    /**
     * Delete an Entity
     * 
     * @throws JSONException
     */
    @Delete
    public JSONObject deleteEntity() throws IOException, JSONException {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");
	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");

	// Extract the id of an Entity
	String entity_id = (String) getRequestAttributes().get("entityId");
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
	// Delete an Entity according to the type of the database
	if ("database/couchDB".equals(db_type)) {
	    return new CouchDBEntityToolkit().delete(es_name, entity_id);
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCEntityToolkit().delete(db_name, es_name, entity_id);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakEntityToolkit().delete(es_name, entity_id);
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBEntityToolkit().delete(db_name, es_name,
		    entity_id);
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
