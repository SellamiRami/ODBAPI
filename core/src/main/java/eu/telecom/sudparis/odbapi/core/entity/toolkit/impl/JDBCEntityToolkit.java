package eu.telecom.sudparis.odbapi.core.entity.toolkit.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.EntityToolKit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;
/**
 * Class representing a toolkit to manage Entities in relational databases
 * (MySQL)
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public class JDBCEntityToolkit implements EntityToolKit {
    private JSONObject response;

    public JDBCEntityToolkit() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/MySQL");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }

    /**
     * insert a tuple in a relational table
     * 
     * @param db_name
     *            the database name
     * @param es_name
     *            the name of the entity set (table) that contains the entity
     *            entityID (tuple)
     * @param jsonEntity
     *            the tuple to insert
     */
    public JSONObject create(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
	try {
	    
            Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
 	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();

	    JSONArray jsonTest = jsonEntity.getJSONArray("values");
	    String insertQuery = "INSERT INTO " + es_name + " VALUES (\""
		    + e_id + "\",";
	    for (int i = 0; i < jsonTest.length(); i++) {
		String value = jsonTest.getJSONObject(i).get("value")
			.toString();
		insertQuery = insertQuery + "\"" + value + "\",";
	    }
	    insertQuery = insertQuery.substring(0, insertQuery.length() - 1)
		    + ")";
	    System.out.println(insertQuery);
	    stmt.executeUpdate(insertQuery);
	    con.close();
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
     * retrieve a tuple in a relational table
     * 
     * @param db_name
     *            the database name
     * @param es_name
     *            the name of the entity set (table) that contains the entity
     *            entityID (tuple)
     * @param e_id
     *            the id of the tuple to retrieve
     * 
     * @return if OK return the result set in JSON format and 200 status code
     *         else return an error status code
     */
    public JSONObject retrieve(String db_name, String es_name, String e_id) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL");
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
 	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();
	    String retreiveQuery = "SELECT * FROM " + es_name + " where id=\""
		    + e_id + "\"";
	    ResultSet rs = stmt.executeQuery(retreiveQuery);
	    JSONArray json = FromResultSetToJSON.convertToJSON(rs);
	    con.close();
	    try {
		response.put("data", json);
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	   
	    return response;

	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	} catch (SQLException e) {
	    e.printStackTrace();
	} catch (Exception e) {
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
     * update a tuple in a relational table
     * 
     * @param db_name
     *            the database name
     * @param es_name
     *            the name of the entity set (table) that contains the entity
     *            entityID (tuple)
     * @param jsonEntity
     *            the new content of a tuple
     * @param entityId
     *            the id of the tuple to update
     */
    public JSONObject update(String db_name, String es_name, String e_id,
	    JSONObject jsonEntity) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
 	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();

	    ResultSet rs = stmt.executeQuery("SELECT * FROM " + es_name);
	    ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();

	    String updateQuery = "UPDATE " + es_name + " SET ";
	    JSONArray jsonTest = jsonEntity.getJSONArray("values");
	    for (int i = 1; i < jsonTest.length(); i++) {
		String value = jsonTest.getJSONObject(i).get("value")
			.toString();
		if (!value.equals(""))
		    updateQuery = updateQuery + rsmd.getColumnName(i + 1)
			    + "=\"" + value + "\",";
	    }
	    updateQuery = updateQuery.substring(0, updateQuery.length() - 1)
		    + " WHERE id =\"" + e_id + "\"";
	    System.out.println(updateQuery);
	    stmt.executeUpdate(updateQuery);
	    con.close();
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
     * delete a tuple in a relational table
     * 
     * @param db_name
     *            the database name
     * @param es_name
     *            the name of the entity set (table) that contains the entity
     *            entityID (tuple)
     * @param e_id
     *            the id of the tuple to delete
     */
    public JSONObject delete(String db_name, String es_name, String e_id) {
	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
	    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
 	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
	    Statement stmt = con.createStatement();
	    String deleteQuery = "DELETE FROM " + es_name + " WHERE id =\""
		    + e_id + "\"";
	    stmt.executeUpdate(deleteQuery);
	    con.close();
	    try {
		response.put("success", "true");
	    } catch (JSONException e) {
		e.printStackTrace();
	    }
	    return response;
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject retrieve(String es_name, String e_id) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject update(String es_name, String e_id, JSONObject jsonEntity) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject delete(String es_name, String e_id) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

    public JSONObject create(String es_name, String e_id, JSONObject jsonEntity) {
	try {
	    response.put("success", "false");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
	return response;
    }

}
