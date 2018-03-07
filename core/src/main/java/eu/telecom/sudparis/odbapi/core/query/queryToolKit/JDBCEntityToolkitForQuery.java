package eu.telecom.sudparis.odbapi.core.query.queryToolKit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.EntityToolKit;
import eu.telecom.sudparis.odbapi.core.toolkit.impl.ExecutionManifestParserImpl;

import eu.telecom.sudparis.odbapi.core.entity.toolkit.impl.FromResultSetToJSON;

public class JDBCEntityToolkitForQuery {

	private JSONObject response;

    public JDBCEntityToolkitForQuery() {
	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/MySQL");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }
	public JSONObject executeQuery(String db_name, String es_name,
			 Vector<String> selectList, Vector<String> fromtList, Vector<String>wheretList) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    ExecutionManifestParserImpl param= new ExecutionManifestParserImpl("database/MySQL"); 
		    String URL = param.getURL() + param.getHost() + ":" + param.getPort() + "/" + db_name;
	 	    Connection con = DriverManager.getConnection( URL, param.getLogin() , param.getPassword());
		    Statement stmt = con.createStatement();
			
		    String selectQuery = "select ";
		    for(int i=0;i<selectList.size()-1;i++){
		    	selectQuery=selectQuery+selectList.get(i)+",";
		    }
		    selectQuery=selectQuery + selectList.get(selectList.size()-1);
		    
		    selectQuery= selectQuery + " from " + fromtList.get(0);
		    
		    if (wheretList.size() > 1 || wheretList.get(0) != "")
		    {
			    selectQuery= selectQuery + " where ";
			    for(int i=0;i<wheretList.size()-1;i++){
			    	selectQuery=selectQuery+wheretList.get(i)+" and ";
			    }
			    selectQuery=selectQuery + wheretList.get(wheretList.size()-1);
		    }
		    
		    System.out.println(selectQuery);
		    ResultSet rs = stmt.executeQuery(selectQuery);
		    JSONArray json = FromResultSetToJSON.convertToJSON(rs);
		    con.close();

			response.put("data", json);
		    return response;
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
		    e.printStackTrace();
		} catch (JSONException e) {
		    e.printStackTrace();
		} catch (Exception e) {
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

}
