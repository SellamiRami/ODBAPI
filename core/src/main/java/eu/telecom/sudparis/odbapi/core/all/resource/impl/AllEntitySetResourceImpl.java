package eu.telecom.sudparis.odbapi.core.all.resource.impl;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.ServerResource;

import eu.telecom.sudparis.odbapi.core.all.resource.AllEntitySetResource;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.CouchDBGetAllES;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.JDBCGetAllES;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.MongoDBGetAllES;
import eu.telecom.sudparis.odbapi.core.all.toolkit.impl.RiakGetAllES;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.CORSpropertiesParserImpl;

/**
 * Class for getting all resources of type entity set
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class AllEntitySetResourceImpl extends ServerResource implements
	AllEntitySetResource {

    @Get("json")
    public Object getAllEntitySets(Representation entity) {
	Form requestHeaders = (Form) getRequest().getAttributes().get(
		"org.restlet.http.headers");

	// Extract the type and name of the target database
	String db_name = requestHeaders.getFirstValue("Database-Name");
	String db_type = requestHeaders.getFirstValue("Database-Type");
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
	    return new CouchDBGetAllES().retrieve();
	} else if ("database/MySQL".equals(db_type)) {
	    return new JDBCGetAllES().retrieve(db_name);
	} else if ("database/Riak".equals(db_type)) {
	    return new RiakGetAllES().retrieve();
	} else if ("database/mongoDB".equals(db_type)) {
	    return new MongoDBGetAllES().retrieve(db_name);
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
