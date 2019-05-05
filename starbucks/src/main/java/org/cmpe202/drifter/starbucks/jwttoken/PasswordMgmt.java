package org.cmpe202.drifter.starbucks.jwttoken;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author Drifter Group
 *          Paramdeep Saini, Julian Simon, Viswa, Sandhya
 *         
 */


public class PasswordMgmt {
    public static String encryptPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }
}
