package eu.telecom.sudparis.odbapi.core.all.resource.impl;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;
import org.restlet.ext.json.JsonRepresentation;

import eu.telecom.sudparis.odbapi.core.all.resource.AllEntityResource;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.CouchDBGetAllE;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.JDBCGetAllE;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.MongoDBGetAllE;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.RiakGetAllE;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.CORSpropertiesParserImpl;

import net.sf.json.JSONObject;
/**
 * Class for getting all resources of type entity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class AllEntityResourceImpl extends ServerResource implements
	AllEntityResource {

    private static final String HEADERS_KEY = "org.restlet.http.headers";

    @Get("json")
    public JsonRepresentation getAllEntities(Representation entity) {
	Form requestHeaders = (Form) getRequest().getAttributes().get("org.restlet.http.headers");
	
	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");

	// Extract the name of an Entity Set
	String es_name = (String) getRequestAttributes().get("esName");
	// Retrieve an Entity according to the type of the database
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
	if ("database/couchDB".equals(db_type)) {
	    //return new CouchDBGetAllE().retrieve(es_name);
		return null;
	} else if ("database/MySQL".equals(db_type)) {
            	return new JsonRepresentation(new JDBCGetAllE().retrieve(db_name, es_name));
	} else if ("database/Riak".equals(db_type)) {
	    return null;//return new RiakGetAllE().retrieve(es_name);
	} else if ("database/mongoDB".equals(db_type)) {
	  	return new JsonRepresentation(new MongoDBGetAllE().retrieve(db_name, es_name));
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
