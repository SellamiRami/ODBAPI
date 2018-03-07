package entity;

import org.json.JSONObject;

/**
 * Interface to update Entities
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public interface UpdateEntity {
	void updateEntity(String url, String databaseType, JSONObject jsonEntity);
	void updateEntity(String url, String databaseType, String databaseName, JSONObject jsonEntity);
}
