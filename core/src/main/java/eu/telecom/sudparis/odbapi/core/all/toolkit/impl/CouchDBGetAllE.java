package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import net.sf.json.JSONObject;

import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.ViewResults;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllE;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class CouchDBGetAllE implements GetAllE {
    private JSONObject response;

    public CouchDBGetAllE() {
	response = new JSONObject();
	response.put("Database-Type", "database/couchDB");
    }

    public JSONObject retrieve(String es_name) {
	/* Creating a session with couch db running in 5984 port */
	ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	/* Extracting the database name from the URL */

	Database odbapiCouchDb = odbapiSession.getDatabase(es_name);
	/* Extracting the document id from the URL */

	ViewResults allDocs = odbapiCouchDb.getAllDocuments();

	response.put("data", allDocs.getJSONObject());
	return response;
    }

    public JSONObject retrieve(String db_name, String es_name) {
	response.put("success", "false");
	return response;
    }

}
