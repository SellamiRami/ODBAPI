package eu.telecom.sudparis.odbapi.core.all.toolkit;

import net.sf.json.JSONObject;

public interface GetAllE {
    public JSONObject retrieve(String es_name);

    public JSONObject retrieve(String db_name, String es_name);
}
