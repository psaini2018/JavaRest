package org.cmpe202.drifter.starbucks.jwttoken;


import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Drifter Group
 *          Paramdeep Saini, Julian Simon, Viswa, Sandhya
 *         
 */

@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})

public @interface IJWTTokenMgmt {

}
