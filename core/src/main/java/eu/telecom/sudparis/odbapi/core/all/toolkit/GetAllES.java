package eu.telecom.sudparis.odbapi.core.all.toolkit;

import org.json.JSONObject;

public interface GetAllES {
    public JSONObject retrieve();

    public JSONObject retrieve(String db_name);
}
