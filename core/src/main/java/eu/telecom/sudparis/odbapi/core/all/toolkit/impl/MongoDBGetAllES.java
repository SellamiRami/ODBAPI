package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import java.net.UnknownHostException;
import java.util.Set;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllES;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class MongoDBGetAllES implements GetAllES {
    private JSONObject response;

    public MongoDBGetAllES() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/mongoDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    public JSONObject retrieve() {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String db_name) {
	try {
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
	    MongoClient client = new MongoClient(param.getHost());

	    DB d = client.getDB(db_name);

	    Set<String> esNames = d.getCollectionNames();

	    client.close();

	    JSONObject json = new JSONObject();
	    JSONArray array = new JSONArray();

	    for (String s : esNames) {
		array.add(s);
	    }

	    json.put("nameES", array);
	    response.put("data", json);
	    return response;
	} catch (UnknownHostException e) {
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

}
