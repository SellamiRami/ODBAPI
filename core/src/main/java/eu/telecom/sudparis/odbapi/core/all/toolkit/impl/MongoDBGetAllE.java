package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import java.net.UnknownHostException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllE;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class MongoDBGetAllE implements GetAllE {
    private JSONObject response;

    public MongoDBGetAllE() {
	response = new JSONObject();
	response.put("Database-Type", "database/mongoDB");
    }

    public JSONObject retrieve(String es_name) {
	response.put("success", "false");
	return response;
    }

    public JSONObject retrieve(String db_name, String es_name) {
	try {
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
	    MongoClient client = new MongoClient(param.getHost());

	    DB d = client.getDB(db_name);

	    DBCollection eCollection = d.getCollection(es_name);

	    JSONArray json = new JSONArray();

	    DBCursor cursor = eCollection.find();
	    try {
		while (cursor.hasNext()) {
		    json.add(cursor.next());
		}
	    } finally {
		cursor.close();
	    }
	    response.put("data", json);

	    client.close();

	    return response;

	} catch (UnknownHostException e) {
	    e.printStackTrace();
	}

	response.put("success", "false");
	return response;
    }

}
