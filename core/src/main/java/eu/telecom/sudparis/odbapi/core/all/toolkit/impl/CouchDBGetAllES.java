package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.fourspaces.couchdb.Session;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllES;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class CouchDBGetAllES implements GetAllES {
    private JSONObject response;

    public CouchDBGetAllES() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/couchDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    public JSONObject retrieve() {
	try {
	    /* Creating a session with couch db running in 5984 port */
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/couchDB"); 
	    Session odbapiSession = new Session(param.getHost(), Integer.parseInt(param.getPort()));

	    /* Extracting the database name from the URL */

	    List<String> ESNames = odbapiSession.getDatabaseNames();
	    Iterator i = ESNames.iterator();

	    JSONObject json = new JSONObject();
	    JSONArray array = new JSONArray();

	    while (i.hasNext()) {
		array.add(i.next());
	    }
	    json.put("nameES", array);
	    response.put("data", json);

	    return response;
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return null;
    }

    public JSONObject retrieve(String db_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

}
