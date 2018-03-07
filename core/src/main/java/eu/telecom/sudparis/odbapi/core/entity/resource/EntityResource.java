package eu.telecom.sudparis.odbapi.core.entity.resource;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;

/**
 * interface for managing resources of type Entity
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface EntityResource {

    public JSONObject setEntity(Representation entity) throws IOException,
	    JSONException;

    public JSONObject getEntityById(Representation entity) throws SQLException,
	    ClassNotFoundException, IOException;

    public JSONObject updateEntity(Representation entity) throws IOException,
	    JSONException;

    public JSONObject deleteEntity() throws IOException, JSONException;

}
