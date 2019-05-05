package org.cmpe202.drifter.starbucks.jwttoken;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author Drifter Group
 *          Paramdeep Saini, Julian Simon, Viswa, Sandhya
 *         
 */

public class GenerateSecret implements IGenereateSecret {

	@Override
	public Key generateKey() {
	       String keyString = "starbucks";
	        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
	        return key;
	}
}