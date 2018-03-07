package eu.telecom.sudparis.odbapi.core.toolkit;

/**
 * Interface representing a toolkit to parse an execution manifest
 * 
 * @author Rami Sellami
 * @version 1.0
 */
public interface CORSpropertiesParser {
	public String getAccessControlAllowOrigin();
	public String getAccessControlAllowMethods();
	public String getAccessControlAllowHeaders();
	public String getAccessControlAllowCredentials();
	public String getAccessControlMaxAge();
}

