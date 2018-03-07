package entitySet;

/**
 * Interface to retrieve Entity Sets
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface RetrieveEntitySet {

	void retrieveEntitySet(String url, String databaseType);
	void retrieveEntitySet(String url, String databaseType, String databaseName);
}
