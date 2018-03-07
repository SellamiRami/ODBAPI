package eu.telecom.sudparis.odbapi.core.entity.toolkit.impl;

import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.EntityToolKit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

/**
 * This class exposes basic CRUD operations for entities.
 * 
 * @author Michel Vedrine
 * 
 */
public class MongoDBEntityToolkit implements EntityToolKit {
    private static MongoClient client;
    private JSONObject response;

    public MongoDBEntityToolkit() {
//	if (client == null) {
//	    try {
//		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
//	        client = new MongoClient(param.getHost());
//	    } catch (UnknownHostException e) {
//		e.printStackTrace();
//	    }
//	}
//
//	response = new JSONObject();
//	try {
//	    response.put("Database-Type", "database/mongoDB");
//	} catch (JSONException e) {
//	    e.printStackTrace();
//	}
    }

    /**
     * Create an entity.
     * 
     * @param es_name
     *            Entity name.
     * @param jsonEntity
     *            Entity data (JSON format).
     */
    public JSONObject create(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
    	try {
    		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
    		 client = new MongoClient(param.getHost());
    	    } catch (UnknownHostException e) {
    		e.printStackTrace();
    	    }
	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	try {
	    jsonEntity.put("_id", e_id);
	} catch (JSONException e) {
	    e.printStackTrace();
	}

	eCollection.insert((DBObject) JSON.parse(jsonEntity.toString()));

	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Retrieve an entity.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     * @param e_id
     *            Entity ID.
     */
    public JSONObject retrieve(String db_name, String es_name, String e_id) {
    	try {
    		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
    		 client = new MongoClient(param.getHost());
    	    } catch (UnknownHostException e) {
    		e.printStackTrace();
    	    }
    	
    	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	BasicDBObject query = new BasicDBObject("_id", e_id);
	DBCursor cursor = eCollection.find(query);

	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    while (cursor.hasNext()) {
		response.put("data", cursor.next());
		return response;
	    }
	} catch (JSONException e) {
	    e.printStackTrace();
	} finally {
	    cursor.close();
	}

	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Retrieve an entity.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     * @param jsonEntity
     *            Entity data (JSON format).
     */
    public JSONObject update(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
    	try {
    		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
   		 client = new MongoClient(param.getHost());
   	    } catch (UnknownHostException e) {
   		e.printStackTrace();
   	    }
	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	BasicDBObject query = new BasicDBObject("_id", e_id);
	eCollection.update(query, (DBObject) JSON.parse(jsonEntity.toString()));

	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    /**
     * Update an entity.
     * 
     * @param db_name
     *            Database name.
     * @param es_name
     *            Entityset name.
     * @param e_id
     *            Entity ID.
     */
    public JSONObject delete(String db_name, String es_name, String e_id) {
	try {
		ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/mongoDB"); 
		 client = new MongoClient(param.getHost());
	    } catch (UnknownHostException e) {
		e.printStackTrace();
	    }
    	DB d = client.getDB(db_name);
	DBCollection eCollection = d.getCollection(es_name);

	BasicDBObject query = new BasicDBObject("_id", e_id);
	eCollection.remove(query);

	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "true");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String es_name, String e_id, JSONObject jsonEntity) {
	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String es_name, String e_id) {
	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject update(String es_name, String e_id, JSONObject jsonEntity) {
	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String es_name, String e_id) {
	try {
		response = new JSONObject();
		response.put("Database-Type", "database/mongoDB");
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }
}
