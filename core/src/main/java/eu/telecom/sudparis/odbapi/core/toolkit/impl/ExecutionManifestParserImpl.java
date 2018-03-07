package eu.telecom.sudparis.odbapi.core.toolkit.impl;

/**
 * Class representing a toolkit to parse an execution manifest
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

import eu.telecom.sudparis.odbapi.core.toolkit.ExecutionManifestParser;
public class ExecutionManifestParserImpl implements ExecutionManifestParser {
	private File ExecutionManifest;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
        private String DBType="";
	
	public ExecutionManifestParserImpl(String type){
		try{		
			ExecutionManifest = new File("classes/Files/ExecutionManifest.xml");	
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(ExecutionManifest);
			doc.getDocumentElement().normalize();
			DBType = DBType + type;
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}	
	}
	public String getURL(){
		try{	
			int i=0;
			boolean OK=false;
			NodeList nList = doc.getElementsByTagName("endpoint");
			while((i<nList.getLength()) && (OK==false)){
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("Database-Type").equals(DBType)){
					OK=true;
					return eElement.getElementsByTagName("url").item(0).getTextContent();
				}
				i++;		
			}
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getHost(){
		try{
			int i=0;
			boolean OK=false;
			NodeList nList = doc.getElementsByTagName("endpoint");
			while((i<nList.getLength()) && (OK==false)){
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("Database-Type").equals(DBType)){
					OK=true;
					return eElement.getElementsByTagName("host").item(0).getTextContent();
				}
				i++;		
			}
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getPort(){
		try{
			int i=0;
			boolean OK=false;
			NodeList nList = doc.getElementsByTagName("endpoint");
			while((i<nList.getLength()) && (OK==false)){
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("Database-Type").equals(DBType)){
					OK=true;
					return eElement.getElementsByTagName("port").item(0).getTextContent();
				}
				i++;		
			}
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getLogin(){
		try{
			int i=0;
			boolean OK=false;
			NodeList nList = doc.getElementsByTagName("endpoint");
			while((i<nList.getLength()) && (OK==false)){
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("Database-Type").equals(DBType)){
					OK=true;
					return eElement.getElementsByTagName("login").item(0).getTextContent();
				}
				i++;		
			}
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
	public String getPassword(){
		try{
			int i=0;
			boolean OK=false;
			NodeList nList = doc.getElementsByTagName("endpoint");
			while((i<nList.getLength()) && (OK==false)){
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				if(eElement.getAttribute("Database-Type").equals(DBType)){
					OK=true;
					return eElement.getElementsByTagName("password").item(0).getTextContent();
				}
				i++;		
			}
		}
    		catch (Exception e) {
			e.printStackTrace();
    		}
		return "";	
	}
}
