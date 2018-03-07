package eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl;

import java.net.UnknownHostException;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import eu.telecom.sudparis.odbapi.core.entityset.toolkit.EntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

/**
 * This class exposes basic CRUD operations for entity sets.
 * 
 * @author Michel Vedrine
 * 
 */
public class MongoDBEntitySetToolkit implements EntitySetToolkit {
    private JSONObject response;
    private static MongoClient client;

    public MongoDBEntitySetToolkit() {
	if (client == null) {
	    try {
		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
	        client = new MongoClient(param.getHost());
	    } catch (UnknownHostException e) {
		e.printStackTrace();
	    }
	}

	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/mongoDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create an entityset.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     * @param jsonEntity
     *            Data to construct the entityset (MySQL only, always null in
     *            this case).
     */
    public JSONObject create(String db_name, String es_name,
	    JSONObject jsonEntity) {
	DB d = client.getDB(db_name);
	d.createCollection(es_name, new BasicDBObject("capped", false));

	try {
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Retrive an entityset.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     */
    public JSONObject retrieve(String db_name, String es_name) {
	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	JSONArray array = new JSONArray();

	DBCursor cursor = eCollection.find();
	try {
	    while (cursor.hasNext()) {
		array.add(cursor.next());
	    }
	} finally {
	    cursor.close();
	}

	try {
	    response.put("data", array);
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Delete an entityset.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     */
    public JSONObject delete(String db_name, String es_name) {
	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	eCollection.drop();

	try {
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }
}
