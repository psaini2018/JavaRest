package org.cmpe202.drifter.starbucks.model;

import org.cmpe202.drifter.starbucks.jwttoken.*;
import org.json.JSONObject;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.sql.*;

/**
* @author Drifter Group
*          Paramdeep Saini, Julian Simon, Viswa, Sandhya
*         
*/


public class StarbucksModel {
	
	private static final String mysqlurl = "jdbc:mysql://127.0.0.1:3306/starbucks";
	private static final String mysqluser = "prod";
	private static final String mysqlpassword = "welcome1";

	private static Connection con;

	@Context
    private UriInfo uriInfo;
	
	// @Inject
	    private IGenereateSecret generateSecret = new GenerateSecret();
	 
	// @PersistenceContext
	//    private EntityManager em;
	 
	public StarbucksModel() {
	
	}
	
   public boolean isDigit(String str ) {
	   boolean flag;
	   if (str.matches("[0-9]+")) {
			  flag=true;	  
		  } else {
			  flag=false;
		  }	     
	   return flag;
   }
   
  public int strLength(String str) {
	  return str.length();
  }
  
  public String addCardStatus(String cardNum, String cardCode, String status) {
	  String jsonString;
      JSONObject jo = new JSONObject();
	  jo.put("cardNumber", cardNum);
	  jo.put("cardCode", cardCode);
	  jo.put("Status", status);
	  jsonString = jo.toString();	  
	  return jsonString;  
  }
  
  public String ConvertToJsonStr(String status) {
	  String jsonString;
      JSONObject jo = new JSONObject();
	  jo.put("Status", status);
	  jsonString = jo.toString();	  
	  return jsonString;  
  }
  
  public boolean checkLogin(String username, String password) throws Exception {
	     String usr_name="none", passwd="none",returnStr;
	     boolean status=false;
	     Connection con = null;
	     PreparedStatement preparedStmt = null;

       String query = "select email_id, pin from users where email_id = ?;";
      String searchString = username.toLowerCase().trim();
      System.out.println("query : " + query + searchString);
      try {
          con = getDBConnection();
          preparedStmt = con.prepareStatement(query);
 	      preparedStmt.setString (1, searchString);
          ResultSet rs = preparedStmt.executeQuery();
          rs.last();           
          int count =  rs.getRow();
          System.out.println("Rows Count :" + count);
          if (count !=  1) {
   	    	  status=false;   
              System.out.println("Return Statement: No user Exist");   	         	      
   	      } else {
   	    	  status=true;
	    	  usr_name = rs.getString("email_id");
   	    	  passwd = rs.getString("pin");
              System.out.println("Return Statement: user Exist");   	         	      
   	      }
       } catch (SQLException sqlEx) {
       	returnStr="Error Occurred during User Search in the database! Please check with you application administrator";
       	System.out.println("Return Statement: " + returnStr);
       	status=false;
        sqlEx.printStackTrace();
       } finally {
			if (preparedStmt != null) {
				preparedStmt.close();
			}
			if (con != null) {
				con.close();
			}
       }	

      
       if ( (status) && (username.equals(usr_name)) && passwd.equals(PasswordMgmt.encryptPassword(password))) {
    	
    	   status=true;    	  
       }        
       System.out.println("User Login Check Status is being returned");
   return status;
  }

  public String getToken(String username) {
      Key key = generateSecret.generateKey();
      System.out.println("Inside Token generation");
      String jwtToken = Jwts.builder()
    		     .setSubject(username)
    		     .setIssuer("http://localhost.com")
                 .setIssuedAt(new Date())
                 .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                 .signWith(SignatureAlgorithm.HS512, key)
                 .compact();
      //      .setIssuer(uriInfo.getAbsolutePath().toString())
            System.out.println("Returning Token " + jwtToken);
             return jwtToken;
  }   
  
  
  private Date toDate(LocalDateTime localDateTime) {
      return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
  
  public String createUser(String username,String password,String mobile_num,String email_id) throws SQLException {
	    boolean action;
	    String returnStr,pin;
	    
	    Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        UUID uuid = UUID.randomUUID();
        String user_id = uuid.toString();
        
        if ( ( ! isDigit(password) ) && (strLength(password) != 4)) {
        	returnStr= "Password length must be of 4 digits";
        	String status= ConvertToJsonStr(returnStr);
        	return status;
        }     
        try {
        	 DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/starbucks?useSSL=false","prod","welcome1");
        	 if (con != null)
        	 { 
        		 System.out.println("Successfully connected to MySQL database starbucks"); 
        	 }  
        	} catch (SQLException ex) 
        	{ System.out.println("An error occurred while connecting MySQL database"); 
        		ex.printStackTrace();
        		return "An error occurred while connecting MySQL database";
        	}


        	
        String query = " insert into starbucks.users(user_id,user_name,created_on,mobile_num,email_id,pin) values(?,?,?,?,?,?);";	    
	       try {
	    	    PreparedStatement preparedStmt = con.prepareStatement(query);
	    	      preparedStmt.setString (1, user_id);
	    	      preparedStmt.setString (2, username);
	    	      preparedStmt.setTimestamp  (3, timestamp);
	    	      preparedStmt.setString (4, mobile_num);
	    	      preparedStmt.setString (5, email_id);
	    	      preparedStmt.setString (6, PasswordMgmt.encryptPassword(password));
	    	      int count = preparedStmt.executeUpdate();
	    	      if (count > 0) {
	    	    	  returnStr="User Created Sucessfully";
	    	      } else {
	    	    	  returnStr="Error in User Creation";
	    	      }	            
	    	      System.out.println("Return Statement: " + returnStr);
	    	      con.close();
	        } catch (SQLException sqlEx) {
	        	returnStr="Error in User Creation! Please Check StackTrace";
	        	System.out.println("Return Statement: " + returnStr);
	            sqlEx.printStackTrace();
	        } 
	       return returnStr;
	}
  
  
  
  public String getUsers() {
	    String returnStr;
	     returnStr="Checking users";
	    return returnStr;
  }
  
  private static Connection getDBConnection() throws SQLException {
		Connection dbConnection = null;
		
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			dbConnection=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/starbucks?autoReconnect=true&useSSL=false","prod","welcome1");
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}