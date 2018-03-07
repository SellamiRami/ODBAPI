package eu.telecom.sudparis.odbapi.core.all.toolkit.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.telecom.sudparis.odbapi.core.all.toolkit.GetAllES;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

public class JDBCGetAllES implements GetAllES {
    private JSONObject response;

    public JDBCGetAllES() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/MySQL");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    public JSONObject retrieve() {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String db_name) {

	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    DatabaseMetaData md = con.getMetaData();
	    ResultSet rs = md.getTables(null, null, "%", null);

	    JSONArray array = new JSONArray();

	    while (rs.next()) {
		array.put(rs.getString(3));
	    }

	    JSONObject object = new JSONObject();
	    object.put("nameES", array);

	    response.put("data", object);
	    return response;
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (JSONException e) {
	    e.printStackTrace();
	}

	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

}
