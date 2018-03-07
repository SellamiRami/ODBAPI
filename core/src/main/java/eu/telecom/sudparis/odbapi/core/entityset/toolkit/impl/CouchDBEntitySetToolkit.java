package eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Session;

import eu.telecom.sudparis.odbapi.core.entityset.toolkit.EntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

/**
 * Class representing a toolkit to manage Entity Sets in CouchDB databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class CouchDBEntitySetToolkit implements EntitySetToolkit {
    private JSONObject response;

    public CouchDBEntitySetToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/couchDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create a collection in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection)
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    public JSONObject create(String es_name) {
	/* Creating a session with couch db running in 5984 port */
	ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	/* Extracting the database name from the URL */
	Database odbapiCouchDb = odbapiSession.createDatabase(es_name);

	if (odbapiCouchDb != null) {
	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	} else {
	    try {
		response.put("success", "false");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	}
	return response;
    }

    /**
     * Retrieve a collection in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection)
     * @return if OK return the requested collection and 200 status code else
     *         return null and error status code
     */
    public JSONObject retrieve(String es_name) {
	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));
	    Database odbapiCouchDb = odbapiSession.getDatabase(es_name);

	    response.put("data", odbapiCouchDb.toString());
	    return response;
	} catch (JSONException e) {
	    e.printStackTrace();
	}

	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Delete a collection in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection)
     * @return if OK return true and 200 status code else return false and error
     *         status code
     */
    public JSONObject delete(String es_name) {
	/* Creating a session with couch db running in 5984 port */
	ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	/* Deleting the database */
	odbapiSession.deleteDatabase(es_name);

	try {
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String db_name, String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String db_name, String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String db_name, String es_name,
	    JSONObject jsonEntity) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }
}
