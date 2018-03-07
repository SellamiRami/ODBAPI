package eu.telecom.sudparis.odbapi.core.entity.toolkit;

import org.json.JSONObject;

/**
 * Interface representing a toolkit to manage Entities in any type of databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface EntityToolKit {

    public JSONObject create(String es_name, String e_id, JSONObject jsonEntity);

    public JSONObject create(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity);

    public JSONObject retrieve(String es_name, String e_id);

    public JSONObject retrieve(String db_name, String es_name, String e_id);

    public JSONObject update(String es_name, String e_id, JSONObject jsonEntity);

    public JSONObject update(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity);

    public JSONObject delete(String es_name, String e_id);

    public JSONObject delete(String db_name, String es_name, String e_id);

}
