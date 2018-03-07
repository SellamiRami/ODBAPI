package eu.telecom.sudparis.odbapi.core.entity.toolkit.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.EntityToolKit;

/**
 * Class representing a toolkit to manage Entities in Riak databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class RiakEntityToolkit implements EntityToolKit {
    private JSONObject response;

    public RiakEntityToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/couchDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * insert a value
     * 
     * @param es_name
     *            the name of the entity set (bucket) that contains the entity
     *            entityID (value)
     * @param e_id
     *            the entityID of the value to insert
     * @param jsonEntity
     *            the value to insert
     */
    public JSONObject create(String es_name, String e_id, JSONObject jsonEntity) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
	    String mydata = jsonEntity.getString("value");
	    myBucket.store(e_id, mydata).execute();
	    riakClient.shutdown();

	    response.put("success", "true");
	    return response;
	} catch (RiakException e) {
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
     * retrieve a value
     * 
     * @param es_name
     *            the name of the entity set (bucket) that contains the entity
     *            entityID (value)
     * @param e_id
     *            the entityID of the value to retrieve
     * @return if OK return the value and 200 status code else return an error
     *         status code
     */
    public JSONObject retrieve(String es_name, String e_id) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
	    IRiakObject myObject = myBucket.fetch(e_id).execute();
	    // note that getValueAsString() will return null here if there's no
	    // value in Riak
	    String value = myObject.getValueAsString();
	    riakClient.shutdown();

	    response.put("data", myBucket.toString());
	    return response;
	} catch (RiakException e) {
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
     * update a value
     * 
     * @param es_name
     *            the name of the entity set (bucket) that contains the entity
     *            entityID (value)
     * @param e_id
     *            the entityID of the value to update
     * @param jsonEntity
     *            the new values
     */
    public JSONObject update(String es_name, String e_id, JSONObject jsonEntity) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
	    String mydata = jsonEntity.getString("value");
	    myBucket.store(e_id, mydata).execute();
	    riakClient.shutdown();

	    response.put("success", "true");
	    return response;
	} catch (RiakException e) {
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
     * delete a value
     * 
     * @param es_name
     *            the name of the entity set (bucket) that contains the entity
     *            entityID (value)
     * @param e_id
     *            the entityID of the value to delete
     */
    public JSONObject delete(String es_name, String e_id) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
	    myBucket.delete(e_id);
	    riakClient.shutdown();
	} catch (RiakException e) {
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
