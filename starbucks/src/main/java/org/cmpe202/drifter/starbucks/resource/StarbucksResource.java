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
	StarbucksModel starbucksmodel;
	
	private AppAuthProxy app;
	
	public StarbucksResource() {
		app = starbuckSvc.getInstance();
	    starbucksmodel = new StarbucksModel(app);
	}
	
	@GET
	public String starbucks() {
	        return app.screenContents();               
	}

	@GET
	@Path("/screenname")
	public String screenname() {	  
		    app = starbuckSvc.getInstance();
	        return app.screenContents();
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
            	  
            	   System.out.println("Password " + password);
            	   starbucksmodel.enterInput(password);
               }
               if(app.screen().contentEquals("MyCards")) {

            	   starbucksmodel.populateCards(username);
            	   return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
               }
               else {
            	   return Response.status(UNAUTHORIZED).build();
               }
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
        	returnStr = "Error occurred in card addition, please login with pin to add the card!";
        } else {
        	starbucksmodel.AddCard(cardNum, cardCode);
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
	
	@POST
	@Path("/orderCoffee")    
    @IJWTTokenMgmt
	public String orderCoffee(@QueryParam("coffeeName") String param1) {
		String coffeeName, addon, status, returnStr;		
		coffeeName=param1;
		
		System.out.println("Token Validation went fine");

        returnStr ="Order being processed"; 

        returnStr=starbucksmodel.processOrder(coffeeName);

        status=starbucksmodel.orderCoffeeStatus(coffeeName, returnStr);

		return status;     	
	}
	
	@GET
	@Path("/menuItems")    
    @IJWTTokenMgmt
	public String menuItems() {
		String returnStr;		
		
		System.out.println("Token Validation went fine");

        returnStr ="dripcoffee   $1.50\n"; 
        returnStr +="fancycoffee $2.50\n"; 

		return returnStr;     	
	}

	@GET
	@Path("/orderStatus")    
    @IJWTTokenMgmt
	public String orderStatus() {
		String returnStr;		
		
		System.out.println("Token Validation went fine");

        returnStr ="Order being processed"; 

        returnStr=starbucksmodel.getCurrentOrders();

		return returnStr;     	
	}
	@POST
	@Path("/payment")    
    @IJWTTokenMgmt
	public String payment(@QueryParam("cardnumber") String param)
	{
		String scrName;
		String retStatus;
		String status;
		String cardNum = param;
		scrName = starbucksmodel.MyCardsState(cardNum);
		if(scrName == "MyCardsPay")
			System.out.println("Card was added succesfully");
		else
			retStatus = "Error In Adding Card Details";
		retStatus = starbucksmodel.ScanCard(cardNum);
		double balance = starbucksmodel.getbalance(cardNum);
		String cardbalance = "$" + String.valueOf(balance);
		status = starbucksmodel.cardBalanceStatus(cardNum, cardbalance, retStatus);
		return status;
	}
	
}

