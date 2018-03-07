package eu.telecom.sudparis.odbapi.core.entityset.resource;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.representation.Representation;

/**
 * Interface for managing resources of type Entity Set
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface EntitySetResource {

    public JSONObject setEntitySet(Representation entity) throws IOException;

    public Object getEntitySetByName(Representation entity) throws IOException;

    public JSONObject deleteEntitySET() throws IOException, JSONException;
}
