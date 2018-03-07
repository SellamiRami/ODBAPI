package eu.telecom.sudparis.odbapi.core.query.queryToolKit;

import java.net.UnknownHostException;
import java.util.Vector;

import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBEntityToolkitForQuery {

	private JSONObject response;
	private static MongoClient client;

    public MongoDBEntityToolkitForQuery() {
	if (client == null) {
	    try {
		client = new MongoClient("localhost");
	    } catch (UnknownHostException e) {
		e.printStackTrace();
	    }
	}

	response = new JSONObject();
	try {
	    response.put("Database-Type", "database/mongoDB");
	} catch (JSONException e) {
	    e.printStackTrace();
	}
    }
    
	public JSONObject executeQuery(String db_name, String es_name,
			 Vector<String> selectList, Vector<String> fromtList, Vector<String>wheretList) {
		DB d = client.getDB(db_name);
		DBCollection eCollection = d.getCollection(fromtList.get(0));

		DBObject obj = new BasicDBObject();
		System.out.println(wheretList.size());
		for(int i=0;i<wheretList.size();i++){
			ToolkitForQuery toolkit = new ToolkitForQuery();
			Vector v= toolkit.getFilterMongoDB(wheretList.get(i));
			if (v.size()==2){
				obj.put(v.get(0).toString(), v.get(1).toString());
			}
			else if (v.size()==3){
				DBObject objInt = new BasicDBObject();
				objInt.put( v.get(1).toString(), v.get(2).toString());
				obj.put(v.get(0).toString(), objInt);
			}
			System.out.println(obj);
	    }
		
		System.out.println("****************" + obj);
		
		DBObject obj1 = new BasicDBObject();
		for(int i=0;i<selectList.size();i++){
			obj1.put( selectList.get(i).toString(), 1 );
	    }
		
		System.out.println("****************" + obj1);
		
		DBCursor cursor = eCollection.find(obj ,obj1);
		
		JSONArray array = new JSONArray();
		try {
		    while (cursor.hasNext()) {
			array.add(cursor.next());
			}
		} finally {
		    cursor.close();
		}

		try {
		    response.put("data", array);
		} catch (JSONException e) {
		    e.printStackTrace();
		}
		return response;
	}

}
