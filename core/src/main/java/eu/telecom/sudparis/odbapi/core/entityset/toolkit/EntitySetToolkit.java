package eu.telecom.sudparis.odbapi.core.entityset.toolkit;

import org.json.JSONObject;

/**
 * Interface representing a toolkit to manage Entity Sets in any type of
 * databases
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface EntitySetToolkit {

    public JSONObject create(String es_name);

    public JSONObject create(String db_name, String es_name,
	    JSONObject jsonEntity);

    public JSONObject retrieve(String es_name);

    public JSONObject retrieve(String db_name, String es_name);

    public JSONObject delete(String es_name);

    public JSONObject delete(String db_name, String es_name);
}
