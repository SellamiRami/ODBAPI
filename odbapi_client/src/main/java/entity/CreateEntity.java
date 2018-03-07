package entity;

import org.json.JSONObject;

/**
 * Interface to create Entities
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public interface CreateEntity {
	void createEntity(String url, String databaseType, JSONObject jsonEntity);
	void createEntity(String url, String databaseType, String databaseName, JSONObject jsonEntity);
}
