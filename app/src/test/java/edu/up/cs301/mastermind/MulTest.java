package edu.up.cs301.mastermind;

import org.junit.Test;
import static junit.framework.Assert.assertTrue;
public class MulTest {
    @Test
    public void test1() {
        int result = 3 * 5;
        assertTrue("bad multiply", result == 15);
    }

    private static final int[] testNums =
            {-23,-1, 0, 1, 33, 10398};
    @Test
    public void test2() {
        for (int val : testNums) {
            Integer state = new Integer(val);
            assertTrue(
                    "bad counter value after constructor invoked",
                    state.intValue() == val);
        }
    }
}