package org.cmpe202.drifter.starbucks.service;



import java.util.ArrayList;
import starbucks.*;

/**
* @author Drifter Group
*          Paramdeep Saini, Julian Simon, Viswa, Sandhya
*         
*/

public class StarbucksService {

	private static starbucks.AppAuthProxy app;
	
	public StarbucksService () {
		
	}
		
	public static AppAuthProxy getInstance() {
		if ( app == null) {
			app = new starbucks.AppAuthProxy();	
		}
		return app;
	}

	public String getScreenName() {
		if ( app == null) {
			return "Failed";	
		}
		return app.screen();
	}
	
}
