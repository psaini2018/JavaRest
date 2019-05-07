package org.cmpe202.drifter.starbucks.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.json.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.cmpe202.drifter.starbucks.service.*;
import org.cmpe202.drifter.starbucks.jwttoken.IJWTTokenMgmt;
import org.cmpe202.drifter.starbucks.model.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import starbucks.*;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
* @author Drifter Group
*          Paramdeep Saini, Julian Simon, Viswa, Sandhya
*         
*/

@Path("/starbucks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StarbucksResource {
	
	StarbucksService starbuckSvc = new StarbucksService();
	StarbucksModel starbucksmodel = new StarbucksModel();
	
	private AppAuthProxy app;
	
	public StarbucksResource() {
		app = starbuckSvc.getInstance();
	}
	
	@GET
	public String starbucks() {
		   return "Welcome to Starbucks!";
		/*		   
	       String[] lines ;
	        app = starbuckSvc.getInstance();
	        app.touch(1,5) ;  // 1
	        app.touch(2,5) ;  // 2
	        app.touch(3,5) ;  // 3
	        app.touch(1,6) ;  // 4	              
	        app.display() ;
	        app.touch(2,4) ;
	        return app.screen();	
	        */        
	}

	@GET
	@Path("/screenname")
	public String screenname() {	  
		    app = starbuckSvc.getInstance();
	        return app.screen();	        
	}
	
    @POST
    @Path("/login")
    public Response authenticateUser(@QueryParam("username") String username,
    		@QueryParam("password") String password) {
    	String token;	
        try {
            if (starbucksmodel.checkLogin(username, password)) {
            	 System.out.println("User Login Check Passed");
               token = starbucksmodel.getToken(username);
               if ( app.screen().equals("PinScreen")) {
               app.touch(1,5) ;
               app.touch(2,5) ;
               app.touch(3,5) ;
               app.touch(1,6) ;               
               app.display();
               }
               return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } else  
        {
        	 System.out.println("Invalid Username and password");
        }
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
        return Response.status(UNAUTHORIZED).build();        
    }  
	
    @POST
    @Path("/register")
    public String registerUser(@QueryParam("username") String username,
    		@QueryParam("password") String password,
	        @QueryParam("mobile_num") String mobile_num,
            @QueryParam("email_id") String email_id) throws SQLException   {
    	String returnStr=starbucksmodel.createUser(username,password,mobile_num,email_id);
        return returnStr;
    }
    
    @GET
    @Path("/users")
    public String users()   {
    	String returnStr=starbucksmodel.getUsers();
        return returnStr;
    }
    
	@POST
	@Path("/addcard")    
    @IJWTTokenMgmt
	public String addCard(@QueryParam("cardnumber") String param1,
            @QueryParam("cardcode") String param2) {
		String cardNum, cardCode, scrName, returnStr, status;		
		cardNum=param1;
		cardCode=param2;	
		
		System.out.println("Token Validation went fine");
        if ( ( ! starbucksmodel.isDigit(cardNum) ) && (starbucksmodel.strLength(cardNum) != 9)) {
        	returnStr= "cardNumber must be of length 9 digits";
        	status=starbucksmodel.addCardStatus(cardNum, cardCode, returnStr);
        	return status;
        }
        if ( ( ! starbucksmodel.isDigit(cardCode) ) && (starbucksmodel.strLength(cardNum) != 3)) {
        	returnStr= "cardCode must be of length 3 digits";
        	status=starbucksmodel.addCardStatus(cardNum, cardCode, returnStr);
        	return status;
        }      
        
        scrName=app.screen();
        System.out.println("scrName :" + scrName);
        if ( scrName.equals("PinScreen")) {
            app.touch(1,5) ;
            app.touch(2,5) ;
            app.touch(3,5) ;
            app.touch(1,6) ;
            System.out.println("Test3");
            System.out.println("test4");
            app.display();
            app.execute("E");            
            app.touch(1,1) ; // Add New Card
            System.out.println("Test1");
            System.out.println("test2");
            // Card Id digits
            app.touch(1,5); // 1
            app.touch(2,5); // 2
            app.touch(3,5); // 3
            app.touch(1,6); // 4
            app.touch(2,6); // 5
            app.touch(3,6); // 6
            app.touch(1,7); // 7
            app.touch(2,7); // 8
            app.touch(3,7); // 9
            app.touch(2,3); // focus on card code
            // Card Code digits
            app.touch(3,7); // 9
            app.touch(3,7); // 9
            app.touch(3,7); // 9  
            app.next();
            app.display();
       	
        } else {
            System.out.println("Test1");
            System.out.println("test2");
            app.display();
        	System.out.println(scrName);
        	app.execute("E") ; // Settings Page
            app.touch(1,1) ; // Add New Card
            // Card Id digits
            app.touch(1,5); // 1
            app.touch(2,5); // 2
            app.touch(3,5); // 3
            app.touch(1,6); // 4
            app.touch(2,6); // 5
            app.touch(3,6); // 6
            app.touch(1,7); // 7
            app.touch(2,7); // 8
            app.touch(3,7); // 9
            app.touch(2,3); // focus on card code
            // Card Code digits
            app.touch(3,7); // 9
            app.touch(3,7); // 9
            app.touch(3,7); // 9
            app.next();
            app.display();
        }
        
        scrName=app.screen();
        if ( scrName.equals("MyCards")) {
        	returnStr ="Sucessfully Added the card"; 
        } else if ( scrName == "AddCard" ) {
        	returnStr = "Card Already Exist";
        } else {
        	returnStr = "Error occurred in card addition, please contact application administrator!";
        }
        	
        status=starbucksmodel.addCardStatus(cardNum, cardCode, returnStr);
		return status;     	
	}
	
}
