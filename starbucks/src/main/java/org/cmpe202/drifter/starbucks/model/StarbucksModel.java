package org.cmpe202.drifter.starbucks.model;

import org.cmpe202.drifter.starbucks.jwttoken.*;
import org.json.JSONObject;

import io.jsonwebtoken.SignatureAlgorithm;
import starbucks.UserInfo;
import starbucks.AppAuthProxy;
import starbucks.ICardInputObserver;
import starbucks.Cards;
import io.jsonwebtoken.Jwts;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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


public class StarbucksModel implements ICardInputObserver{

	private static final String mysqlurl = "jdbc:mysql://127.0.0.1:3306/starbucks";
	private static final String mysqluser = "prod";
	private static final String mysqlpassword = "welcome1";

	private static Connection con;
	private AppAuthProxy app;
	private Boolean initInProgress;

	@Context
	private UriInfo uriInfo;

	// @Inject
	private IGenereateSecret generateSecret = new GenerateSecret();

	// @PersistenceContext
	//    private EntityManager em;

	public StarbucksModel (AppAuthProxy app) {
		Cards.getInstance().registerObserver(this);
		this.app = app;
		this.initInProgress = false;
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
	
	public String cardBalanceStatus(String cardNum, double balance, String status)
	{
		String jsonString;
		JSONObject jo = new JSONObject();
		jo.put("cardNumber", cardNum);
		jo.put("Balance", balance);
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

		if ( (status) && (username.equals(usr_name)) ) {

			UserInfo userInfo = (UserInfo) UserInfo.getInstance();

			/*Make sure starbucks.jar contains the right info*/
			userInfo.SetUserName(username);
			userInfo.SetPin(username, password);

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

		UserInfo userInfo = (UserInfo) UserInfo.getInstance();

		userInfo.SetUserName(email_id);
		userInfo.SetPin(email_id, password);
		userInfo.SetPhoneNumber(email_id, mobile_num);
		userInfo.SetEmailId(email_id, email_id);

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

	private String getUserId() {

		UserInfo userInfo = (UserInfo) UserInfo.getInstance();
		String user_id = "";

		String search = "select user_id from users where email_id = ?;";
		String searchString = userInfo.GetUserName().trim();
		System.out.println("query : " + search + searchString);
		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, searchString);
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			if (count !=  1) {
				System.out.println("Return Statement: No user Exist");   	         	      
				return user_id;
			} else {
				user_id = rs.getString("user_id");
				System.out.println("Return Statement: user Exist");   	         	      
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  

		return user_id;
	}


	private String getCardId(String cardNum) {

		String card_id = "";

		String search = "select card_id from cards where card_num = ?;";
		System.out.println("query : " + search + cardNum);
		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, cardNum);
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			if (count !=  1) {
				System.out.println("Return Statement: No Card Exist");   	         	      
				return null;
			} else {
				card_id = rs.getString("card_id");
				System.out.println("Return Statement: Card Exist " + card_id);   	         	      
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  

		return card_id;
	}

	public void enterInput(String numbers) {

		for (int i = 0; i < numbers.length(); i++) {
			switch(numbers.charAt(i) - '0') {

			case 0: app.touch(2, 8); break;
			case 1: app.touch(1, 5); break;
			case 2: app.touch(2, 5); break;
			case 3: app.touch(3, 5); break;
			case 4: app.touch(1, 6); break;
			case 5: app.touch(2, 6); break;
			case 6: app.touch(3, 6); break;
			case 7: app.touch(1, 7); break;
			case 8: app.touch(2, 7); break;
			case 9: app.touch(3, 7); break;
			}
		}     	
	}

	public void AddCard(String cardNum, String cardCode) {
		app.display();
		app.execute("E") ; // Settings Page
		app.display();
		app.touch(1,1) ; // Add New Card
		app.display();
		// Card Id digits
		app.touch(1,2); // focus on card number
		enterInput(cardNum);
		app.touch(2,3); // focus on card code
		// Card Code digits
		enterInput(cardCode);
		app.next(); 
		app.display();
	}
	
	public String MyCardsState(String cardNum ) 
	{
		String retString;
		String scrName = app.screen();
		System.out.println("scrName :" + scrName);
        if ( scrName.equals("MyCards"))
        {
        	app.display();
        	app.touch(3, 3);
        	app.display();
        	return app.screen();
        }
        else
        {
        	retString ="Error occurred in Card addition";
        }
		return retString;
	}
	
	public double getOrderPrice()
	{
		String user_id = getUserId();
		Double retprice = 0.0;
	
		String search = "select item, price from orders where user_id = ? and status = ?;";
		System.out.println("query : " + search + user_id);
		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, user_id);
			preparedStmt.setString (2, "CREATED");
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			rs.first();
			for(int i = 0; i<count; i++){
				String item = rs.getString("item");
				String price = rs.getString("price");
				retprice += Double.parseDouble(price);
				System.out.println("Item : " + item + " price "+ price);
				rs.next();
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  
		
		return retprice;
	}
	
	public void updatePaymentStatus(String status)
	{
		//update the status in DB.
		boolean action;
		String returnStr,pin;

		String user_id = getUserId();
		PreparedStatement preparedStmt;

		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

		try {
			String query = " update starbucks.orders set status = ?, created_on = ? where (user_id = ? AND status = ?);";	    
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString (1, status);
			preparedStmt.setTimestamp(2, timestamp);
			preparedStmt.setString (3, user_id);
			preparedStmt.setString (4, "CREATED");
			int count = preparedStmt.executeUpdate();
			if (count > 0) {
				returnStr="Card Created Sucessfully";
			} else {
				returnStr="Error in User Creation";
			}	            
			System.out.println("Return Statement: " + returnStr);
			con.close();
		} catch (SQLException sqlEx) {
			returnStr="Error in Payment Updation! Please Check StackTrace";
			System.out.println("Return Statement: " + returnStr);
			sqlEx.printStackTrace();
		} 

		return;
	}
	
	//Scans the card
	public double ScanCard(String cardNum)
	{
		System.out.println("CardNum: " + cardNum);
		double curBalance= Cards.getInstance().getCardBalance(cardNum);
		System.out.println("CardNum: " + curBalance);
		double balance = 0;
		System.out.println("Current balance retrieved from DB: " + curBalance);
		app.display();
		//app.touch(2, 2);
		double amount = getOrderPrice();
		if (curBalance > amount)
		{			
			Cards.getInstance().subtractCardBalance(cardNum, amount);
			updatePaymentStatus("PAID");
		}
		else
		{
			System.out.println("current balance: " + curBalance + "is not sufficient");
			//balance = (curBalance);
		}
		
		return Cards.getInstance().getCardBalance(cardNum);
	}
	
	public String getbalance(String cardNum)
	{
		
		String balance = "0.0";

		String search = "select card_id from cards where card_num = ?;";
		System.out.println("query : " + search + cardNum);
		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, cardNum);
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			if (count !=  1) {
				System.out.println("Return Statement: No Card Exist");   	         	      
				return null;
			} else {
				balance = rs.getString("balance");
				System.out.println("Return Statement: Card Exist " + balance);   	         	      
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  

		return balance;
		
		
	}

	@Override
	public void cardDetailUpdate(String number, Double balance, Boolean newCard) {

		boolean action;
		String returnStr,pin;

		if(initInProgress == true) {
			System.out.println("Init inprogress. Returning");
			return;
		}
			
		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
		UUID uuid = UUID.randomUUID();
		String card_id;

		card_id = getCardId(number); 
		 
		if(card_id == null) {
			card_id = uuid.toString();
			newCard = true;
		}
		else {
			newCard = false;
		}
		
		   
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/starbucks?useSSL=false","prod","welcome1");
			if (con != null)
			{ 
				System.out.println("Successfully connected to MySQL database starbucks"); 
			}  
		} 
		catch (SQLException ex) {
			System.out.println("An error occurred while connecting MySQL database"); 
			ex.printStackTrace();
			return;
		}

		String user_id = getUserId();
		PreparedStatement preparedStmt;
	
		System.out.println(number + " " + balance + " " + newCard + "  ");
		try {
			if(newCard) {
				String query = " insert into starbucks.cards(card_id,user_id,card_num,card_code,balance,created_on) values(?,?,?,?,?,?);";	    

				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString (1, card_id);
				preparedStmt.setString (2, user_id);
				preparedStmt.setString (3, number);
				preparedStmt.setString (4, "123");
				preparedStmt.setString (5, balance.toString());
				preparedStmt.setTimestamp (6, timestamp);
			}
			else {
				String query = " update starbucks.cards set balance = ? where (card_id = ?);";	    
				preparedStmt = con.prepareStatement(query);
				preparedStmt.setString (1, balance.toString());
				preparedStmt.setString (2, card_id);
			}	
			int count = preparedStmt.executeUpdate();
			if (count > 0) {
				returnStr="Card Created Sucessfully";
			} else {
				returnStr="Error in User Creation";
			}	            
			System.out.println("Return Statement: " + returnStr);
			con.close();
		} catch (SQLException sqlEx) {
			returnStr="Error in Card Creation! Please Check StackTrace";
			System.out.println("Return Statement: " + returnStr);
			sqlEx.printStackTrace();
		} 

		return;
	}

	@Override
	public void inputCompleteEvent(Boolean status) {
	}


	public void populateCards(String username) {
		String user_id = getUserId();
	
		initInProgress = true;
		System.out.println(" set" + initInProgress);
		
		String search = "select card_num, card_code, balance from cards where user_id = ?;";
		System.out.println("query : " + search + user_id);
		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, user_id);
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			rs.first();
			for(int i = 0; i<count; i++){
				String cardNum = rs.getString("card_num");
				String cardCode = rs.getString("card_code");
				String balance  = rs.getString("balance");
				System.out.println("Card Num: " + cardNum + " Code "+ cardCode + " Balance " + balance);
				Cards.getInstance().setCardNumber(cardNum);
				Cards.getInstance().setCardBalance(cardNum, Double.parseDouble(balance));
				rs.next();
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  

		initInProgress = false;
		System.out.println(" reset" + initInProgress);
	}
	
	
	public String orderCoffeeStatus(String cardNum, String status) {
		String jsonString;
		JSONObject jo = new JSONObject();
		jo.put("coffeeName", cardNum);
		jo.put("Status", status);
		jsonString = jo.toString();	  
		return jsonString;  
	}
        

	public String processOrder(String coffeeName) {
		String returnStr;
		String price;
		String order_status;
		UUID uuid = UUID.randomUUID();
		String order_id = uuid.toString();
		
		if(coffeeName.equals("dripcoffee")) {
			//Add to db the order id	
			returnStr="Drip Coffee is ready. Please pay $1.50";
			price = "1.50";
			order_status = "CREATED";
		}
		else if(coffeeName.equals("fancycoffee")) {
			
			returnStr="Coffee is being prepared. Please pay $2.50 while the coffee is getting ready";

			price = "2.50";
			order_status = "CREATED";
		}
		else {
			
			returnStr="This Coffee option is not being served at this location";

			return returnStr;
		}
		
		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());


		String user_id = getUserId();
		PreparedStatement preparedStmt;
	
		System.out.println(order_id + " " + user_id + " " + price + "  ");
		try {

			con = getDBConnection();
			String query = " insert into starbucks.orders(order_id,user_id,item,status,price,created_on) values(?,?,?,?,?,?);";	    

			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString (1, order_id);
			preparedStmt.setString (2, user_id);
			preparedStmt.setString (3, coffeeName);
			preparedStmt.setString (4, order_status);
			preparedStmt.setString (5, price); 
			preparedStmt.setTimestamp (6, timestamp);

			int count = preparedStmt.executeUpdate();
			if (count > 0) {
				returnStr="Order Created Sucessfully";
			} else {
				returnStr="Error in Order Creation";
			}	            
			System.out.println("Return Statement: " + returnStr);
			con.close();
		} catch (SQLException sqlEx) {
			returnStr="Error in Order Creation! Please Check StackTrace";
			System.out.println("Return Statement: " + returnStr);
			sqlEx.printStackTrace();
		} 

		return returnStr;
	}
	
	public String getCurrentOrders() {
		String user_id = getUserId();
		String returnStr = "";
	
		String search = "select item, price, status, created_on from orders where user_id = ?;";
		System.out.println("query : " + search + user_id);

		Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

		try {
			con = getDBConnection();
			PreparedStatement preparedStmt = con.prepareStatement(search);
			preparedStmt.setString (1, user_id);
			ResultSet rs = preparedStmt.executeQuery();
			rs.last();           
			int count =  rs.getRow();
			System.out.println("Rows Count :" + count);
			rs.first();
			for(int i = 0; i<count; i++){
				String item = rs.getString("item");
				String price = rs.getString("price");
				String status = rs.getString("status");
				Timestamp updated = rs.getTimestamp("created_on");
				System.out.println("Item : " + item + " price "+ price + " " + updated);
				if(status.equals("PAID")) {
					if(updated.compareTo(timestamp) >  2000) {
						status = "DELIVERED"; 
					}
				}
				returnStr += "Item : " + item + " price "+ price + " " + status + "\n";
				System.out.println("Item : " + item + " price "+ price + " " + updated);
				rs.next();
			}
		} catch (SQLException sqlEx) {
			System.out.println("Return Statement: " );
			sqlEx.printStackTrace();
		}  
		
		return returnStr;
	}

}