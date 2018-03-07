package entity;

import org.json.JSONObject;

/**
 * Interface to delete Entities
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public interface DeleteEntity {
	void deleteEntity(String url, String databaseType);
	void deleteEntity(String url, String databaseType, String databaseName);

}
