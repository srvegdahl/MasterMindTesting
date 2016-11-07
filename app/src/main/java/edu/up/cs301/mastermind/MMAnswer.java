package edu.up.cs301.mastermind;

/**
 * A clue (# black pegs + # white pegs) in a MasterMind game.
 * 
 * **** We think this class is still buggy****
 * 
 * @author Steven R. Vegdahl
 * @version 7 November 2002
 */
public class MMAnswer {
    
    // the respective number of black and white pegs
    private int numBlacks;
    private int numWhites;
    
    /**
     * Constructor.  Disallows negative peg-counts or peg-counts whose
     *     sum is greater then 4.
     * 
     * @param b  the number of black pegs.  Zero is used if negative; four
     *     is used if greater than four.
     * @param w  the number of white pegs.  Zero is used if negative; if
     *     the number of white and black pegs would exceed 4, the number
     *     is used that would make this sum exactly 4.
     */
    public MMAnswer(int b, int w) {
        // initialize our instance variables, enforcing our bounds
        numBlacks = Math.min(4, Math.max(0, b));
        numWhites = Math.min(4-b, Math.max(0, w));
    }
    
    /**
     * Constructor.  Creates a MMAnswer object based on two FourTuple
     *     objects, which are treated as the guess and the answer.  This
     *     will necessarily be an answer whose peg-sum is no greater than
     *     four.
     *     
     * @param guess  the guess made by the player
     * @param answer  the actual answer
     */
    public MMAnswer(FourTuple guess, FourTuple answer) {
    
        // keep track of which positions we've already used, initially
        // no position being marked as used.
        boolean[] used = new boolean[4];
        for (int i = 0; i < used.length; i++) {
            used[i] = false;
        }
        
        // initialize instance variables, subject to later incrementing
        numBlacks = 0;
        numWhites = 0;
        
        // for each position with a match, increment the number of black
        // pegs and mark the position as used
        for (int i = 0; i < used.length; i++) {
            if (guess.valueAt(i) == answer.valueAt(i)) {
                used[i] = true;
                numBlacks++;
            }
        }
        
        // for each combination of positions between the guess and answer
        // where the answer-position is unused, if we get a match:
        //   - increment the number of white pegs
        //   - mark that position in the answer as "used"
        //   - break out of the loop so that we don't reuse that guess-peg
        for (int i = 0; i < used.length; i++) {
            for (int j = 0; j < used.length; j++) {
                if (!used[j]) {
                    if (guess.valueAt(i) == answer.valueAt(j)) {
                        numWhites++;
                        used[j] = true;
                        break;
                    }
                }
            }
        }
    }

    /**
     * the number of white pegs
     * 
     * @return the number of white pegs
     */
    public int getWhites() {
        return numWhites;
    }
    
    /**
     * the number of black pegs
     * 
     * @return the number of black pegs
     */
    public int getBlacks() {
        return numBlacks;
    }
    
    /**
     * converts the object to string form
     * 
     * @return  the string version of the object
     */
    public String toString() {
        // return string with black and white counts, separated by '/'
        return numBlacks+"/"+numWhites;
    }
    
    /**
     * tells whether two MMAnswer objects are "logically" equal
     * 
     * @param other  the object we're comparing ourselves to
     */
    public boolean equals(MMAnswer other) {
        return this.numWhites == other.numWhites &&
            this.numBlacks == other.numBlacks;
    }
    
    /**
     * returns the object's hash-code
     * 
     * @return  an integer that is guaranteed to be equal to the hash code
     *     of any other MMAnswer object that compares equal.
     */ 
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    /**
     * a main program that does some simple exercizing of the class; prints
     * some results.
     * 
     * @param args  the arguments from the command line.
     */
    public static void main(String[] args) {
    
        // the tests we'll use
        String[] tempTests = {
            "RRRR", "RRGB", "YWPG", "YYBR",
        };
       
        // create an answer for each pair of test-inputs
        for (int i = 0; i < tempTests.length; i++) {
            for (int j = 0; j < tempTests.length; j++) {
                try {
                
                    // create the two four-tuples
                    FourTuple ft1 = new FourTuple(tempTests[i]);
                    FourTuple ft2 = new FourTuple(tempTests[j]);
                    
                    // if we get here, the FourTuples have been created
                    // successfully.  Create and print the MMAnswer object.
                    System.out.println(ft1+"/"+ft2+" => "+
                        new MMAnswer(ft1, ft2));
                }
                catch (FourTupleException ftx) {
                
                    // if we get here, an error occured creating an object.
                    System.out.println("Error creating: "+ftx.getString());
                }
            }
        }
    }
}
