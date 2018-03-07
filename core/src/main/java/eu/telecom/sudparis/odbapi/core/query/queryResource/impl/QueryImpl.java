package eu.telecom.sudparis.odbapi.core.query.queryResource.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.CouchDBEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.JDBCEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.MongoDBEntityToolkit;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.RiakEntityToolkit;
import eu.telecom.sudparis.odbapi.core.query.queryResource.Query;
import eu.telecom.sudparis.odbapi.core.query.queryToolKit.CouchDBEntityToolkitForQuery;
import eu.telecom.sudparis.odbapi.core.query.queryToolKit.JDBCEntityToolkitForQuery;
import eu.telecom.sudparis.odbapi.core.query.queryToolKit.MongoDBEntityToolkitForQuery;
import eu.telecom.sudparis.odbapi.core.query.queryToolKit.RiakEntityToolkitForQuery;
import eu.telecom.sudparis.odbapi.core.query.queryToolKit.ToolkitForQuery;

public class QueryImpl extends ServerResource implements Query {

	@Post
	public JSONObject executeQuery(Representation entity) throws IOException,
			JSONException {
		
		JsonRepresentation represent = new JsonRepresentation(entity);
		JSONObject jsonEntity = represent.getJsonObject();
		
		Form requestHeaders = (Form) getRequest().getAttributes().get(
				"org.restlet.http.headers");
		// Extract the name of an Entity Set
		String es_name = (String) getRequestAttributes().get("esName");
		String db_type = requestHeaders.getFirstValue("Database-Type");
		
        ToolkitForQuery tkfq = new ToolkitForQuery();
		
		Vector<String> selectList = tkfq.getSelect(jsonEntity);
		
		Vector<String> fromtList = tkfq.getFrom(jsonEntity);
		
		Vector<String> wheretList = tkfq.getWhere(jsonEntity);
		
		if ("database/couchDB".equals(db_type)) {
		    return new CouchDBEntityToolkitForQuery().executeQuery(es_name, selectList, fromtList, wheretList);
		} else if ("database/MySQL".equals(db_type)) {
			String db_name = requestHeaders.getFirstValue("Database-Name");
			return new JDBCEntityToolkitForQuery().executeQuery(db_name, es_name, selectList, fromtList, wheretList);
		} else if ("database/Riak".equals(db_type)) {
		    return new RiakEntityToolkitForQuery().executeQuery(es_name, selectList, fromtList, wheretList);
		} else if ("database/mongoDB".equals(db_type)) {
			String db_name = requestHeaders.getFirstValue("Database-Name");
		    return new MongoDBEntityToolkitForQuery().executeQuery(db_name, es_name, selectList, fromtList, wheretList);
		} else {
		    return null;
		}
	}

}
