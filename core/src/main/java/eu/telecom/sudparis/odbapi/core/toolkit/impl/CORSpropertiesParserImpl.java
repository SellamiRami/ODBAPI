package eu.telecom.sudparis.odbapi.core.toolkit.impl;

/**
 * Class representing a toolkit to parse a CORS properties file
 * (MySQL)
 * 
 * @author Rami Sellami
 * @version 1.0
 */

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import eu.telecom.sudparis.odbapi.core.toolkit.CORSpropertiesParser;
public class CORSpropertiesParserImpl implements CORSpropertiesParser {
	private File ExecutionManifest;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	public CORSpropertiesParserImpl(){
		try{		
			ExecutionManifest = new File("classes/Files/CORSproperties.xml");	
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(ExecutionManifest);
			doc.getDocumentElement().normalize();
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}	
	}

	public String getAccessControlAllowOrigin(){
		try{	
			NodeList nList = doc.getElementsByTagName("CORS-headers");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			return eElement.getElementsByTagName("Access-Control-Allow-Origin").item(0).getTextContent();
				
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getAccessControlAllowMethods()
	{
		try{	
			NodeList nList = doc.getElementsByTagName("CORS-headers");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			return eElement.getElementsByTagName("Access-Control-Allow-Methods").item(0).getTextContent();
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";		
	}
	public String getAccessControlAllowHeaders(){
		try{	
			NodeList nList = doc.getElementsByTagName("CORS-headers");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			return eElement.getElementsByTagName("Access-Control-Allow-Headers").item(0).getTextContent();
			
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";		
	}
	public String getAccessControlAllowCredentials(){
		try{	
			NodeList nList = doc.getElementsByTagName("CORS-headers");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			return eElement.getElementsByTagName("Access-Control-Allow-Credentials").item(0).getTextContent();
			
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getAccessControlMaxAge(){
		try{	
			NodeList nList = doc.getElementsByTagName("CORS-headers");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			return eElement.getElementsByTagName("Access-Control-Max-Age").item(0).getTextContent();
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
}
