package edu.up.cs301.mastermind;

/**
 * A guess in the game of MasterMind
 * 
 * @author Steven R. Vegdahl
 * @version 7 November 2002
 */
public class FourTuple {

    // string that encodes the order of the colors
    private static String colorMap = "RGBYPW";
    
    //// SYMBOLIC CONSTANTS REPRESENTING OUR COLORS ////
    
    /**
     * A red peg.
     */
    public static final int RED = colorMap.indexOf('R');
    
    /**
     * A blue peg.
     */
    public static final int BLUE = colorMap.indexOf('B');
       
    /**
     * A green peg.
     */
    public static final int GREEN = colorMap.indexOf('G');
       
    /**
     * A white peg.
     */
    public static final int WHITE = colorMap.indexOf('W');
       
    /**
     * A purple peg.
     */
    public static final int PURPLE = colorMap.indexOf('P');
       
    /**
     * A yello peg.
     */
    public static final int YELLOW = colorMap.indexOf('Y');
    
    // four-element array containing the four pegs
    private int[] items;
 
    /**
     * FourTuple Constructor
     * 
     * @param s  a 4-character string containing only strings from
     *     the set "RGBYPW" (or their lower-case counterparts).  Creates
     *     a FourTuple object with a correspondingly colored peg in each
     *     position.  Throws a FourTupleException of the string does not
     *     conform to the above constraints. 
     */
    public FourTuple(String s) throws FourTupleException {
    
        // create the array
        items = new int[4];
        
        // if string is null or has bad length, throw an exception
        if (s == null || s.length() != 4) {
            throw new FourTupleException(s);
        }
        
        // convert each character to the corresponding integer value;
        // initialize the array.  Throw an exception if we get a bad
        // character
        for (int i = 0; i < 4; i++) {
            char thisChar = Character.toUpperCase(s.charAt(i));
            int idx = colorMap.indexOf(thisChar);
            if (idx < 0) throw new FourTupleException(s);
            items[i] = idx;
        }   
    }
    
    /**
     * Produces the object's print-string.
     * 
     * @return  the object converted to string form
     */
    public String toString() {
        // Convert to a four-character string of the corresponding upper-
        // case characters
        String rtnVal = "";
        for (int i = 0; i < items.length; i++) {
            rtnVal += colorMap.charAt(items[i]);
        }
        return rtnVal;
    }
    
    /**
     * Get the peg color at a particular position
     * 
     * @param i  the position of the peg we want to access
     * @return  the int corresponding to that peg's color, or -1 if the
     *     position was an illegal one.
     */    
    public int valueAt(int i) {
    
        // check for out of bounds, returning -1 if so
        if (i < 0 || i >= items.length) return -1;
        // fetch/return the appropriate element
        else return items[i];
    }
    
    /**
     * Determines if two FourTuple objects are equal
     * 
     * @param other  the object we're comparing ourself to
     * @return  true iff the we is "logically" equal to the
     *     other object: in other words, if we have all the same pegs
     *     in all the same places.
     */
    public boolean equals(FourTuple other) {
    
        // check each position, returning false upon detecting a mismatch
        for (int i = 0; i < items.length; i++) {
            if (this.items[i] != other.items[i]) return false;
        }
        
        // if we get here, there was no mismatch, so return true
        return true;
    }
    
    /**
     * returns the object's hash-code
     * 
     * @return  an integer that is guaranteed to be equal to the hash code
     *     of any other FourTuple object that compares equal.
     */
    public int hashCode() {
        // use the hashcode of the String version of the object, since equal
        // FourTuples always print the same.
        return this.toString().hashCode();
    }
}
