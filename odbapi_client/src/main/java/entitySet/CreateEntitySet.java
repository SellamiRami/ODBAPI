package entitySet;

import org.json.JSONObject;

/**
 * Interface to create Entity Sets
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface CreateEntitySet {

	void createEntitySet(String url, String databaseType);
	void createEntitySet(String url, String databaseType, String databaseName, JSONObject jsonEntity);
}
