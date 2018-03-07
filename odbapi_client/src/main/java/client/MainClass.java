package client;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import net.sf.json.JSONArray;

import entity.impl.CreateEntityImpl;
import entity.impl.RetrieveEntityImpl;
import entity.impl.DeleteEntityImpl;
import entitySet.impl.CreateEntitySetImpl;
import entitySet.impl.DeleteEntitySetImpl;
import entitySet.impl.RetrieveEntitySetImpl;
import query.impl.QueryImpl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import java.net.UnknownHostException;

import java.util.List;
import java.util.Set;

public class MainClass {
	
		public static void main(String[] args) throws JSONException {

		// TODO Auto-generated method stub
		try{	

			/**********************************/
			/***** 			MySQL 		  *****/
			/**********************************/
			
			/**********************************/
			/***** An entity set creation *****/
			/**********************************/
			CreateEntitySetImpl ces = new CreateEntitySetImpl();
			InputStream is1 = new FileInputStream("classes/Files/EntitySetMySQL.json");
			InputStreamReader isr1 =new InputStreamReader(is1);
			JSONTokener tokener1 = new JSONTokener(isr1);
			JSONObject jsonEntity1 = new JSONObject(tokener1);
			ces.createEntitySet("http://localhost:8182/odbapi/entityset/testMySQL",
					"database/MySQL", "ODBAPI_MySQL_Test", jsonEntity1);
			
			/**********************************/
			/****** An entity  creation *******/
			/**********************************/
			CreateEntityImpl ce = new CreateEntityImpl();
			InputStream is = new FileInputStream("classes/Files/EntityMySQL.json");
			InputStreamReader isr=new InputStreamReader(is);
			JSONTokener tokener = new JSONTokener(isr);
			JSONObject jsonEntity = new JSONObject(tokener);
			ce.createEntity("http://localhost:8182/odbapi/entityset/testMySQL/entity/1",
					"database/MySQL", "ODBAPI_MySQL_Test", jsonEntity);

			/**********************************/
			/******* Retrieve an entity *******/
			/**********************************/
			RetrieveEntityImpl re = new RetrieveEntityImpl();
			re.retrieveEntity("http://localhost:8182/odbapi/entityset/testMySQL/entity/1",
					"database/MySQL", "ODBAPI_MySQL_Test");
			
			/**********************************/
			/***** An entity set deletion *****/
			/**********************************/
			DeleteEntitySetImpl des = new DeleteEntitySetImpl();
			des.deleteEntitySet("http://localhost:8182/odbapi/entityset/testMySQL","database/MySQL", "ODBAPI_MySQL_Test");
			
			/**********************************/
			/***** 			MongoDB 	  *****/
			/**********************************/
			
			/**********************************/
			/***** An entity set creation *****/
			/**********************************/
			CreateEntitySetImpl ces1 = new CreateEntitySetImpl();
			ces1.createEntitySet("http://localhost:8182/odbapi/entityset/testMongoDB",
					"database/mongoDB", "ODBAPI_MongoDB_Test", null);
			
			/**********************************/
			/****** An entity  creation *******/
			/**********************************/
			CreateEntityImpl ce2 = new CreateEntityImpl();
			InputStream is2 = new FileInputStream("classes/Files/EntityMongoDB.json");
			InputStreamReader isr2=new InputStreamReader(is2);
			JSONTokener tokener2 = new JSONTokener(isr2);
			JSONObject jsonEntity2 = new JSONObject(tokener2);
			ce2.createEntity("http://localhost:8182/odbapi/entityset/testMongoDB/entity/1",
					"database/mongoDB", "ODBAPI_MongoDB_Test", jsonEntity1);
			
			/**********************************/
			/******* Retrieve an entity *******/
			/**********************************/
			RetrieveEntityImpl re1 = new RetrieveEntityImpl();
			re1.retrieveEntity("http://localhost:8182/odbapi/entityset/testMongoDB/entity/1",
					"database/mongoDB", "ODBAPI_MongoDB_Test");
		}
		catch(Exception e){
			System.out.println(e);
		}	
	}

}
