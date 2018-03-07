package eu.telecom.sudparis.odbapi.core.entityset.toolkit.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.telecom.sudparis.odbapi.core.entityset.toolkit.EntitySetToolkit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

/**
 * Class representing a toolkit to manage Entity Sets in relational databases
 * (MySQL)
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class JDBCEntitySetToolkit implements EntitySetToolkit {
    private JSONObject response;

    public JDBCEntitySetToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/MySQL");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Create a table in a MySQL database
     * 
     * @param db_name
     *            the name of the database
     * @param es_name
     *            the name of the entity set (table)
     */
    public JSONObject create(String db_name, String es_name,
	    JSONObject jsonEntity) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();

	    JSONArray jsonTest = jsonEntity.getJSONArray("table");

	    String createQuery = "CREATE TABLE " + es_name + " (";
	    for (int i = 0; i < jsonTest.length(); i++) {
		String colName = jsonTest.getJSONObject(i).get("colName")
			.toString();
		String coltype = jsonTest.getJSONObject(i).get("coltype")
			.toString();
		String colConstraint = jsonTest.getJSONObject(i)
			.get("colConstraint").toString();

		createQuery = createQuery + " " + colName + " " + coltype + " "
			+ colConstraint + ",";
	    }

	    createQuery = createQuery.substring(0, createQuery.length() - 1)
		    + ")";
	    stmt.executeUpdate(createQuery);

	    response.put("success", "true");
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

    /**
     * Retrieve a table in a relational database
     * 
     * @param db_name
     *            the name of the database
     * @param es_name
     *            the name of the entity set (table)
     */
    public JSONObject retrieve(String db_name, String es_name) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    DatabaseMetaData md = con.getMetaData();

	    ResultSet resultCol = md.getColumns(null, null, es_name, null);
	    String tableName = "\"tableName\":\"" + es_name + "\"";
	    String columnName = "\"colNames\": [";
	    String columnType = "\"coltype\": [";
	    String columnNullable = "\"colConstraints\": [";
	    String pk = "\"PK\": [";

	    while (resultCol.next()) {
		columnName = columnName + "\""
			+ resultCol.getString("COLUMN_NAME") + "\",";
		columnType = columnType + "\""
			+ resultCol.getString("TYPE_NAME") + "\",";
		columnNullable = columnNullable + "\""
			+ resultCol.getString("NULLABLE") + "\",";
	    }

	    columnName = columnName.substring(0, columnName.length() - 1) + "]";
	    columnType = columnType.substring(0, columnType.length() - 1) + "]";
	    columnNullable = columnNullable.substring(0,
		    columnNullable.length() - 1)
		    + "]";

	    ResultSet resultPK = md.getPrimaryKeys(null, null, es_name);
	    while (resultPK.next()) {
		pk = pk + "\"" + resultPK.getString(4) + "\",";
	    }
	    pk = pk.substring(0, pk.length() - 1) + "]";

	    String metadata = "{ \"metadata\" : { " + tableName + ","
		    + columnName + "," + columnType + "," + columnNullable
		    + "," + pk + "}}";

	    response.put("data", metadata);
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

    /**
     * Delete a table in a relational database
     * 
     * @param db_name
     *            the name of the database
     * @param es_name
     *            the name of the entity set (table)
     */
    public JSONObject delete(String db_name, String es_name) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();
	    String deleteQuery = "DROP TABLE " + es_name;
	    stmt.executeUpdate(deleteQuery);

	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String es_name) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }
}
