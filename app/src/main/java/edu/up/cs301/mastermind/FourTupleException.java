package edu.up.cs301.mastermind;
/**
 * An exception that occurs during the creation of a FourTuple object.
 * Contains the string that was used in attempting to create the object.
 * 
 * @author Steven R. Vegdahl
 * @version 7 November 2002
 */
public class FourTupleException extends Exception {
    
    // the string used in attempting to create the object
    private String badOne;
    
    /**
     * Constructor.
     * 
     * @param str the string used in the attempt to create the object
     *
     */
    public FourTupleException(String str) {
        badOne = str;
    }
    
    /**
     * the string that was used in the failed object-creation attempt
     * 
     * @return the string that was used in attempting to create the
     *     object
     */
    public String getString() {
        return badOne;
    }
}
