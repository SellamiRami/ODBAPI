package query;

import org.json.JSONObject;

/**
 * Interface to send complex queries
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public interface Query {
	void sendQuery(String url, String databaseType, JSONObject jsonEntity);
	void sendQuery(String url, String databaseType, String databaseName, JSONObject jsonEntity);
}
