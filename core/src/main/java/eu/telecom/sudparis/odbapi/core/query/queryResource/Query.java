package eu.telecom.sudparis.odbapi.core.query.queryResource;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;

public interface Query {
	public JSONObject executeQuery(Representation entity) throws IOException,
	JSONException;
}
