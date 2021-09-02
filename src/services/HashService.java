package services;

import java.math.BigInteger;
import java.security.MessageDigest;

import utils.ApplicationUtilities;

public class HashService {
  public static String hash(String text) {
    try {
        return new BigInteger( 1, MessageDigest.getInstance( "MD5" ).digest( text.getBytes( "UTF-8" ) ) ).toString( 16 );
    } catch ( Exception e ) {
        ApplicationUtilities.getInstance().handleException(e);
    }

    return "(FAIL)";
}
}
