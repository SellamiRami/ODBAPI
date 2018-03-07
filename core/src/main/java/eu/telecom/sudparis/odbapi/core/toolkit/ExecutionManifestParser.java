package eu.telecom.sudparis.odbapi.core.toolkit;

/**
 * Interface representing a toolkit to parse an execution manifest
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface ExecutionManifestParser {
	public String getURL();
	public String getHost();
	public String getPort();
	public String getLogin();
	public String getPassword();
}
