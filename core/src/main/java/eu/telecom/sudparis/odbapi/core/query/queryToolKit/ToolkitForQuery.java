package eu.telecom.sudparis.odbapi.core.query.queryToolKit;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ToolkitForQuery {
	public Vector<String> getSelect(JSONObject jsonEntity) throws JSONException{
		JSONArray select = jsonEntity.getJSONArray("select");
		Vector<String> selectList = new Vector<String>();
		if (select != null) { 
			   int len = select.length();
			   for (int i=0;i<len;i++){ 
				   selectList.add(select.get(i).toString());
			   } 
			}
		return selectList;
	}
	
	public Vector<String> getFrom(JSONObject jsonEntity) throws JSONException{
		JSONArray from = jsonEntity.getJSONArray("from");
		Vector<String> fromList = new Vector<String>();
		if (from != null) { 
			   int len = from.length();
			   
			   for (int i=0;i<len;i++){ 
				   fromList.add(from.get(i).toString());
			   } 
		}
		return fromList;
	}
	
	public Vector<String> getWhere(JSONObject jsonEntity) throws JSONException{
		JSONArray where = jsonEntity.getJSONArray("where");
		Vector<String> whereList = new Vector<String>();
		if (where != null) { 
			   int len = where.length();
			   for (int i=0;i<len;i++){ 
				   whereList.add(where.get(i).toString());
			   } 
		}
		return whereList;
	}

	public Vector getFilterMongoDB(String where){
		Vector v= new Vector();
		
		if(where.contains("=")){
			int index= where.indexOf('=');
			v.add(where.substring(0, index));
			v.add(where.substring(index+1));
		}else if (where.contains("<")){
			int index= where.indexOf('<');
			v.add(where.substring(0, index));
			v.add("$lt");
			v.add(where.substring(index+1));	
		}else if (where.contains(">")){
			int index= where.indexOf('>');
			v.add(where.substring(0, index));
			v.add("$gt");
			v.add(where.substring(index+1));
		}
		
		return v; 
	}
}
