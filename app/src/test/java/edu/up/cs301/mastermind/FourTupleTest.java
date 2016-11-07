package edu.up.cs301.mastermind;

import org.junit.Test;
import static org.junit.Assert.*;

/* JUnit test-class for FourTuple
 * 
 */
public class FourTupleTest {
	
	// illegal strings for constructor: wrong length
	private static String badLengthStrings[] = {
		"", "rgb", "r", "by", "brrbg", "yprrbg",
	};
	
	// try applying constructor on bad strings: check that exception
	// is thrown
	@Test
	public void testBadLength() {
		boolean gotException;
		for (int i = 0; i < badLengthStrings.length; i++) {
			
			gotException = false; // has exception been thrown?
			
			// attempt the construction
			try {
				FourTuple ft = new FourTuple(badLengthStrings[i]);
			}
			catch (FourTupleException ftx) {
				gotException = true;
			}
			
			// assert that exception was thrown
			assertTrue("Expected exception not thrown: "+
					badLengthStrings[i],
					gotException);
		}
	}
	
	// some good strings
	private static String goodStrings[] = {
		"rgyp", "gypw", "ypwb", "pwbr", "wbrg", "brgy",
	};
	
	// some good characters
	private static String goodChars = "rgbypwRGBYPW";
	
	// attempt to invoke constructor with some bad characters
	@Test
	public void testBadChars() {
		boolean gotException; // did we get exception?
		
		// go through all characters in ASCII-range 0..255; insert each
		// into a"good" string in each position
		
		// loop over each character to insert
		for (char insertChar = 0; insertChar < 256; insertChar++) {
			
			// do we expect an exception (i.e., is this a good character
			boolean exceptionExpected = goodChars.indexOf(insertChar) < 0;
			
			// loop over character positions
			for (int pos = 0; pos < 4; pos++) {
				
				// loop over each of our good strings
				for (String str : goodStrings) {
					
					// insert the character
					String thisString =
						str.substring(0, pos) + insertChar +
						str.substring(pos+1);
					
					gotException = false; // no exception so far
					
					// attempt to invoke the constructor
					try {
						FourTuple ft = new FourTuple(thisString);
					}
					catch (FourTupleException ftx) {
						gotException = true;
					}
					
					// invoke appropriate assertion
					if (exceptionExpected) {
						assertTrue("Expected exception not thrown: "+
									thisString,
								gotException);
					}
					else {
						assertTrue("Unexpected exception thrown: "+str,
								!gotException);
					}
				}
			}
		}
	}
	
	// test the 'toString' method
	@Test
	public void testToString() throws FourTupleException {
		
		// loop over all combinations of good characters, testing to make
		// sure that string-result is as expected
		for (int x1 = 0; x1 < goodChars.length(); x1++) {
			for (int x2 = 0; x2 < goodChars.length(); x2++) {
				for (int x3 = 0; x3 < goodChars.length(); x3++) {
					for (int x4 = 0; x4 < goodChars.length(); x4++) {
						
						// create string
						String str = ""+
							goodChars.charAt(x1)+
							goodChars.charAt(x2)+
							goodChars.charAt(x3)+
							goodChars.charAt(x4);
						
						// create object
						FourTuple ft = new FourTuple(str);
						
						// make sure that we get an appropriate string
						assertTrue("string-conversion problem: "+
								str,
								ft.toString().equalsIgnoreCase(str));
					}
				}
			}
			
		}
		
	}
	
	
	// numbers used to generate some "random" good strings
	private static int[] eqTests = {
		0, 1, 2, 5, 8, 27, 53, 85, 223, 235, 245, 321, 322,
		537, 673, 773, 846, 939, 1073,		
	};
	
	// test the equals method
	@Test
	public void testEquals() throws FourTupleException {
		for (int i = 0; i < eqTests.length; i++) { // all strings in list
			for (int ii = 0; ii < 0xf; ii++) { // upper/lower combos
				for (int j = 0; j < eqTests.length; j++) { // all strings
					for (int jj = 0; jj < 0xf; jj++) { // combos, again
						FourTuple ft1 = // first tuple
							new FourTuple(numTo4String(i, ii));
						FourTuple ft2 = // second tuple
							new FourTuple(numTo4String(j, jj));
						boolean result = ft1.equals(ft2); // test equality
						boolean expected = i == j;
						
						// do we get expected result
						assertTrue("Equality mismatch: "+
								ft1+"/"+ft2,
								result == expected);
					}
				}
			}
		}
	}
	
	// test the hashCode method, making sure that any objects that are
	// "equal" hash to the same value
	@Test
	public void testHashCode() throws FourTupleException {
		for (int i = 0; i < eqTests.length; i++) { // the sample strings
			for (int ii = 0; ii < 0xf; ii++) { // upper/lower case combos
				for (int j = 0; j < eqTests.length; j++) { // sample str's
					for (int jj = 0; jj < 0xf; jj++) { // case combos
						
						// create the two objects
						FourTuple ft1 =
							new FourTuple(numTo4String(i, ii));
						FourTuple ft2 =
							new FourTuple(numTo4String(j, jj));
						
						// check that they are either not equal or
						// that they hash the same
						boolean result =
							ft1.hashCode()==ft2.hashCode();
						assertTrue("Hashcode mismatch: "+
								ft1+"/"+ft2,
								result || i != j);
					}
				}
			}
		}
	}
	
	// our "good" characters
	private static String goodCharsLower = "rgbypw";
	
	// private helper-method to generate strings
	// num: the number of the string (e.g., 0 => "rrrr", 1 => "rrrg")
	// upLowMask: low four bits tell whether each char should be
	//   upper or lower case
	private String numTo4String(int num, int upLowMask) {
		// figure out the chars (based on position in goodCharsLower array)
		// for each position
		int len = goodCharsLower.length();
		int idx1 = Math.abs(num % len);
		int idx2 = Math.abs((num/len)%len);
		int idx3 = Math.abs((num/len/len)%len);
		int idx4 = Math.abs((num/len/len/len)%len);
		
		// compute the characters, including proper case
		char ch1 = goodCharsLower.charAt(idx1);
		if ((upLowMask & 0x1) == 0) {
			ch1 = Character.toUpperCase(ch1);
		}
		char ch2 = goodCharsLower.charAt(idx2);
		if ((upLowMask & 0x2) == 0) {
			ch2 = Character.toUpperCase(ch2);
		}
		char ch3 = goodCharsLower.charAt(idx3);
		if ((upLowMask & 0x4) == 0) {
			ch3 = Character.toUpperCase(ch3);
		}
		char ch4 = goodCharsLower.charAt(idx4);
		if ((upLowMask & 0x8) == 0) {
			ch4 = Character.toUpperCase(ch4);
		}
		
		// return them, appended together
		return ""+ch1+ch2+ch3+ch4;
	}
	
	// an array of our encoded values
	private static int[] values = {
		FourTuple.RED, FourTuple.BLUE, FourTuple.GREEN, 
		FourTuple.PURPLE, FourTuple.WHITE, FourTuple.YELLOW, 
	};
	
	// our good chars, both upper and lower case, in same order as 'values'
	// array
	private static String stringValues = "RBGPWYrbgpwy";
	
	// test valueAt method
	@Test
	public void testValueAt() throws FourTupleException {
		// "double" string to avoid out-of-bounds problems
		String myStringValues = stringValues+stringValues;
		
		// loop over set of strings so that each char is in each
		// position once; create object and test each position
		for (int i = 0; i < stringValues.length(); i++) {
			// our string
			String thisString = myStringValues.substring(i, i+4);
			// create object
			FourTuple ft = new FourTuple(thisString);
			
			// try indexing it in all positions from -2 to 9
			for (int j = -2; j < 10; j++) {
				// expected value is -1 if out of range, otherwise, based
				// on proper array element
				int expected =
					j < 0 || j >=4 ? -1 : values[(i+j)%values.length];
					
				// test whether value matches expected
				int actual = ft.valueAt(j);
				assertTrue("bad result for valueAt: "+ft+"/"+j,
						actual == expected);
			}
		}
	}

}
