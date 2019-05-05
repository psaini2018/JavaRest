package org.cmpe202.drifter.starbucks.jwttoken;

import java.security.Key;

/**
 * @author Drifter Group
 *          Paramdeep Saini, Julian Simon, Viswa, Sandhya
 *         
 */

public interface IGenereateSecret {

	Key generateKey();
}
