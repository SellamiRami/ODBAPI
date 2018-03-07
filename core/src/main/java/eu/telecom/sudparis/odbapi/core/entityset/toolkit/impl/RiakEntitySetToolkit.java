package eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl;

import org.json.JSONException;
import org.json.JSONObject;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;

import eu.telecom.sudparis.odbapi.core.entityset.toolkit.EntitySetToolkit;

/**
 * Class representing a toolkit to manage Entity Sets in Riak databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class RiakEntitySetToolkit implements EntitySetToolkit {
    private JSONObject response;

    public RiakEntitySetToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/Riak");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create a bucket in a Riak database
     * 
     * @param es_name
     *            the name of the entity set (bucket)
     * @return if OK return a message and 200 status code else return a message
     *         and error status code
     */
    public JSONObject create(String es_name) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.createBucket(es_name).execute();
	    riakClient.shutdown();

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
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

    /**
     * Retrieve a bucket in a Riak database
     * 
     * @param es_name
     *            the name of the entity set (bucket)
     * @return if OK return the requested bucket and 200 status code else return
     *         null and error status code
     */
    public JSONObject retrieve(String es_name) {
	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
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
     * Delete a bucket in a Riak database
     * 
     * @param es_name
     *            the name of the entity set (bucket)
     */
    public JSONObject delete(String es_name) {

	try {
	    IRiakClient riakClient = RiakFactory.httpClient();
	    Bucket myBucket = riakClient.fetchBucket(es_name).execute();
	    riakClient.shutdown();

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
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
