package eu.telecom.sudparis.odbapi.core.entity.toolkit.impl;

import java.io.IOException;

import net.sf.json.JSONSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.EntityToolKit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

/**
 * Class representing a toolkit to manage Entities in CouchDB databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public class CouchDBEntityToolkit implements EntityToolKit {
    private JSONObject response;

    public CouchDBEntityToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/couchDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create a document in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection) that contains the
     *            entity entityID (document)
     * @param e_id
     *            the entityID of the document to insert
     * @param jsonEntity
     *            the document to insert
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    public JSONObject create(String es_name, String e_id, JSONObject jsonEntity) {
	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	    /* Extracting the database name from the URL */
	    Database odbapiCouchDb = odbapiSession.getDatabase(es_name);

	    /* Creating a new Document */
	    System.out.println(jsonEntity.toString());
	    net.sf.json.JSONObject jsonToSave = (net.sf.json.JSONObject) JSONSerializer
		    .toJSON(jsonEntity.toString());

	    Document newdoc = new Document(jsonToSave);

	    /* Saving the new document in the database */

	    odbapiCouchDb.saveDocument(newdoc, e_id);
	    System.out.println("Document" + newdoc.getId()
		    + " has been created successfully...");

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
	} catch (IOException e) {
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
     * retrieve a document in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection) that contains the
     *            entity entityID (document)
     * @param e_id
     *            the entityID of the document to retrieve
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    public JSONObject retrieve(String es_name, String e_id) {
	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	    /* Extracting the database name from the URL */

	    Database odbapiCouchDb = odbapiSession.getDatabase(es_name);
	    /* Extracting the document id from the URL */

	    Document newdoc = odbapiCouchDb.getDocument(e_id);

	    response.put("data", newdoc);
	    return response;
	} catch (IOException e) {
	    e.printStackTrace();
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
     * update a document in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection) that contains the
     *            entity entityID (document)
     * @param e_id
     *            the entityID of the document to update
     * @param jsonEntity
     *            the entityID of the new document to insert
     */
    public JSONObject update(String es_name, String e_id, JSONObject jsonEntity) {

	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	    /* Databse access */
	    Database odbapiCouchDb = odbapiSession.getDatabase(es_name);

	    /* Get the document to update */
	    Document docToUpdate;
	    docToUpdate = odbapiCouchDb.getDocument(e_id);
	    String rev = docToUpdate.getRev();
	    /* Updating the document */
	    System.out.println(jsonEntity.toString());
	    net.sf.json.JSONObject jsonToUpdate = (net.sf.json.JSONObject) JSONSerializer
		    .toJSON(jsonEntity.toString());

	    jsonToUpdate.put("_rev", rev);
	    Document newdoc = new Document(jsonToUpdate);
	    odbapiCouchDb.saveDocument(newdoc, e_id);

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
	} catch (IOException e) {
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
     * delete a document in a couchDB database
     * 
     * @param es_name
     *            the name of the entity set (collection) that contains the
     *            entity entityID (document)
     * @param e_id
     *            the entityID of the document to delete
     */
    public JSONObject delete(String es_name, String e_id) {

	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	    /* Extracting the database name from the URL */
	    Database odbapiCouchDb = odbapiSession.getDatabase(es_name);
	    /* Extracting the document id from the URL */
	    Document newdoc = odbapiCouchDb.getDocument(e_id);
	    odbapiCouchDb.deleteDocument(newdoc);

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
	} catch (IOException e) {
	    e.printStackTrace();
	}

	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String db_name, String es_name, String e_id) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject update(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String db_name, String es_name, String e_id) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

}
