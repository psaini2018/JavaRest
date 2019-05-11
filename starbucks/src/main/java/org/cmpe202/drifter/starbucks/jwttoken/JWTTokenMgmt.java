package org.cmpe202.drifter.starbucks.jwttoken;

import org.cmpe202.drifter.starbucks.jwttoken.GenerateSecret;
import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.logging.Logger;


/**
 * @author Drifter Group
 *          Paramdeep Saini, Julian Simon, Viswa, Sandhya
 *         
 */

@Provider
@IJWTTokenMgmt
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenMgmt implements ContainerRequestFilter{
 	
	 private IGenereateSecret keyGenerator=new GenerateSecret();
  	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
	      String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	    //  logger.info("#### authorizationHeader : " + authorizationHeader);
	       if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	     //       logger.severe("#### invalid authorizationHeader : " + authorizationHeader);
	            throw new NotAuthorizedException("Authorization header must be provided");
	       }
	       String token = authorizationHeader.substring("Bearer".length()).trim();
	       
	       try {
	           Key key = keyGenerator.generateKey();
	           System.out.println("Inside JWT TOken MGmt");
	            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
	           } catch (Exception e) {
	             requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
	       }
		
	}
	
   }
 
