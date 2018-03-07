package eu.telecom.sudparis.odbapi.core.all.resource;

import org.restlet.representation.Representation;
import org.restlet.ext.json.JsonRepresentation;

public interface AllEntityResource {

    public JsonRepresentation getAllEntities(Representation entity);
}
