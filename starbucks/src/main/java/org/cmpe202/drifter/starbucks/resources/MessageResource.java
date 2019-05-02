package org.cmpe202.drifter.starbucks.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.cmpe202.drifter.starbucks.model.StarbucksModel;
import org.cmpe202.drifter.starbucks.service.StarbucksService;
import starbucks.*;


@Path("/messages")

public class MessageResource {
	
	StarbucksService starbuckSvc = new StarbucksService();
	private AppAuthProxy app;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessages() {
	       String[] lines ;
	        app = starbuckSvc.getInstance();
	        app.touch(1,5) ;  // 1
	        app.touch(2,5) ;  // 2
	        app.touch(3,5) ;  // 3
	        app.touch(1,6) ;  // 4	              
	        app.display() ;
	        app.touch(2,4) ;
	        return app.screen();	        
	}

}
