package eu.telecom.sudparis.odbapi.core.server;

import org.restlet.Component;
import org.restlet.data.Protocol;

import eu.telecom.sudparis.odbapi.core.all.resource.impl.AllEntityResourceImpl;
import eu.telecom.sudparis.odbapi.core.all.resource.impl.AllEntitySetResourceImpl;
import eu.telecom.sudparis.odbapi.core.entity.resource.impl.EntityResourceImpl;
import eu.telecom.sudparis.odbapi.core.entityset.resource.impl.EntitySetResourceImpl;
import eu.telecom.sudparis.odbapi.core.metadata.resource.AccessRightResource;
import eu.telecom.sudparis.odbapi.core.metadata.resource.MetadataDBResource;
import eu.telecom.sudparis.odbapi.core.metadata.resource.MetadataESResource;
import eu.telecom.sudparis.odbapi.core.metadata.resource.MetadataResource;
import eu.telecom.sudparis.odbapi.core.query.queryResource.impl.QueryImpl;

/**
 * Main class (allows to root a query to the appropriate resource)
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public class MainClass {
    public static void main(String[] args) {
	try {
	    Component component = new Component();

	    // Add a new HTTP server listening on port 8182.
	    component.getServers().add(Protocol.HTTP, 8182);

	    // Attach the sample application.
	    component.getDefaultHost().attach("/odbapi/metadata",
		    MetadataResource.class);
	    component.getDefaultHost().attach("/odbapi/accessright",
		    AccessRightResource.class);
	    component.getDefaultHost().attach(
		    "/obdapi/database/{db_name}/metadata",
		    MetadataDBResource.class);
	    component.getDefaultHost().attach(
		    "/odbapi/entityset/{es_name}/metadata",
		    MetadataESResource.class);

	    component.getDefaultHost().attach("/odbapi/entityset/{esName}/",
		    EntitySetResourceImpl.class);
	    component.getDefaultHost().attach("/odbapi/entityset/{esName}",
		    EntitySetResourceImpl.class);

	    component.getDefaultHost().attach(
		    "/odbapi/entityset/{esName}/entity/{entityId}/",
		    EntityResourceImpl.class);
	    component.getDefaultHost().attach(
		    "/odbapi/entityset/{esName}/entity/{entityId}",
		    EntityResourceImpl.class);

	    component.getDefaultHost().attach("/odbapi/entityset",
		    AllEntitySetResourceImpl.class);
	    component.getDefaultHost().attach(
		    "/odbapi/entityset/{esName}/entity",
		    AllEntityResourceImpl.class);

      component.getDefaultHost().attach("/odbapi/entityset/{esName}/query",
                QueryImpl.class);
	    // Start the component.

	    component.start();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
