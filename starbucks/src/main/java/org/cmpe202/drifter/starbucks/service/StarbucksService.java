package org.cmpe202.drifter.starbucks.service;



import java.util.ArrayList;
import org.cmpe202.drifter.starbucks.model.StarbucksModel;
import starbucks.*;


public class StarbucksService {

	private static AppAuthProxy app ;
		
	public static AppAuthProxy getInstance() {
		if ( app == null) {
			app = new AppAuthProxy();	
		}
		
		
		return app;
	}
}
