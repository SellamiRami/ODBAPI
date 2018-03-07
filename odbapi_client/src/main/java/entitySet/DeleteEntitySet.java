package entitySet;

/**
 * Interface to delete Entity Sets
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface DeleteEntitySet {

	void deleteEntitySet(String url, String databaseType);
	void deleteEntitySet(String url, String databaseType, String databaseName);
}
