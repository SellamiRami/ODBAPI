package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.json.JSONObject;

import org.json.JSONArray;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllE;
import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.FromResultSetToJSON;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class JDBCGetAllE implements GetAllE {
    private JSONObject response;
    private static final String HEADERS_KEY = "org.restlet.http.headers";

    public JDBCGetAllE() {
	response = new JSONObject();
	response.put("Database-Type", "database/MySQL");
    }

    public JSONObject retrieve(String es_name) {
	response.put("success", "false");
	return response;
    }

    public JSONObject retrieve(String db_name, String es_name) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());

	    Statement stmt = con.createStatement();
	    String retreiveQuery = "SELECT * FROM " + es_name;
	    ResultSet rs = stmt.executeQuery(retreiveQuery);
	    JSONArray json = FromResultSetToJSON.convertToJSON(rs);

	    response.put("data", json.toString());
	    return response;

	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}

	response.put("success", "false");
	return response;
    }
}
