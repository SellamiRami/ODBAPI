package entity;

/**
 * Interface to retrieve Entities
 * 
 * @author Rami Sellami
 * @version 1.0
 */

public interface RetrieveEntity {

	void retrieveEntity(String url, String databaseType);
	void retrieveEntity(String url, String databaseType, String databaseName);
}
