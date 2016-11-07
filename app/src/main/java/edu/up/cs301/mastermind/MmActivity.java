package edu.up.cs301.mastermind;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.annotation.Inherited;

public class MmActivity extends AppCompatActivity implements View.OnClickListener {

    // GUI codes for the answer buttons
    private static int[] answerButtonCodes = {
            R.id.answer_button1,
            R.id.answer_button2,
            R.id.answer_button3,
            R.id.answer_button4,
    };

    // GUI codes for the guess buttons
    private static int[] guessButtonCodes = {
            R.id.guess_button1,
            R.id.guess_button2,
            R.id.guess_button3,
            R.id.guess_button4,
    };

    // GUI codes for the clue buttons
    private static int[] clueButtonCodes = {
            R.id.clue_button1,
            R.id.clue_button2,
            R.id.clue_button3,
            R.id.clue_button4,
    };

    // our GUI's buttons
    private Button[] answerButtons;
    private Button[] guessButtons;
    private Button[] clueButtons;
    private Button getClueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set up initial layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm);

        // initialize button arrays; set up listeners
        answerButtons = mapToButtons(answerButtonCodes, true);
        guessButtons = mapToButtons(guessButtonCodes, true);
        clueButtons = mapToButtons(clueButtonCodes, false);
        getClueButton = (Button)findViewById(R.id.get_clue_button);
        getClueButton.setOnClickListener(this);
    }

    /**
     * helper method for initialization. Takes an array of button-codes creates a corresponding
     * array of buttons. If 'setListener' is true, also sets the listener foreach of the buttons
     * to be the calling activity.
     * @param codes the array of button-codes
     * @param setListener whether or not to set the buttons as listeners
     * @return the array of GUI buttons
     */
    private Button[] mapToButtons(int[] codes, boolean setListener) {
        // create the array
        Button[] rtnVal = new Button[codes.length];

        // initialize the array by calling findViewById on each code; set the listener if
        // specified
        for (int i = 0; i < codes.length; i++) {
            rtnVal[i] = (Button)findViewById(codes[i]);
            if (setListener) {
                rtnVal[i].setOnClickListener(this);
            }
        }

        // return the array
        return rtnVal;
    }

    /**
     * callback method--button is clicked
     * @param v the button
     */
    @Override
    public void onClick(View v) {
        // if it's the 'get clue' button, update the clue buttons; otherwise, toggle the color
        // on the button that was pressed
        if (v == getClueButton) {
            updateClue();
        }
        else {
            toggleColor((Button) v);
        }
    }

    // a constant to denot the color purple
    private static final int PURPLE = 0xffa000c0;

    // the colors through which we will toggle the buttons. First and last are the same in order
    // to make the loop a bit simpler
    private int[] buttonColors = {
            Color.BLACK, Color.RED, PURPLE, Color.GREEN, Color.WHITE, Color.YELLOW,
            Color.BLACK,
    };

    // names of the buttons, as known by the 'FourTuple' class. These are in the same order as
    // in the buttonColor array
    private String[] buttonNames = { "b", "r", "p", "g", "w", "y", "b"};


    /**
     * toggles the color of a button. Rotates amond the colors black, red, purple, green, white
     * and yelloe
     * @param b the button to be recolored
     */
    private void toggleColor(Button b) {
        // current color of the button
        int color = getButtonColor(b);

        // the new color--default of black is use if the current color is unexpected
        int newColor = Color.BLACK;

        // loop through the colors; set the new color appropriately if a match is found with the
        // current color
        for (int i = 1; i < buttonColors.length; i++) {
            if (color == buttonColors[i-1 ]) {
                newColor = buttonColors[i];
                break;
            }
        }

        // update the button's color
        b.setBackgroundColor(newColor);
    }

    /**
     * helper-method to map a color to a name, as known by the FourTuple class
     * @param color the color to be mapped
     * @return
     */
    private String mapColorToName(int color) {
        // go through the color array; if a match is found, return the corresponding letter
        for (int i = 0; i < buttonNames.length; i++) {
            if (buttonColors[i] == color) {
                return buttonNames[i];
            }
        }

        // no match found: return a question mark
        return "?";
    }

    /**
     * get the background color of a button
     * @param b the button
     * @return the button's background color, or white if it's not a simple color
     */
    private int getButtonColor(Button b) {
        // get the button's drawable
        Drawable background = b.getBackground();

        // if it's a simple (one-color) background, return the color; if it's more complex
        // (e.g., an image), return white as a default
        if (background instanceof ColorDrawable) {
            return ((ColorDrawable)background).getColor();
        }
        else {
            return Color.WHITE;
        }
    }

    /**
     * update the clue buttons based on the answer and guess buttons
     */
    private void updateClue() {
        // get a FourTuple-compatible string containing the colors of the answer and guess buttons
        String answerString = mapButtonArrayToColorNames(answerButtons);
        String guessString = mapButtonArrayToColorNames(guessButtons);

        // set default values for number of black and white clue-pegs, and the background color
        int numBlacks = 0;
        int numWhites = 0;
        int defaultColor = Color.LTGRAY;

        try {
            // create FourTuple objects corresponding to the answer and guess buttons
            FourTuple answerFt = new FourTuple(answerString);
            FourTuple guessFt = new FourTuple(guessString);

            // get the number of black and white pegs by creating an MMAnswer objectx
            MMAnswer answer = new MMAnswer(answerFt, guessFt);
            numBlacks = answer.getBlacks();
            numWhites = answer.getWhites();
        }
        catch (FourTupleException ftx) {
            // If something went wrong and the MMAnswer constructor threw an exception,
            // make the default clue-button color red
            defaultColor = Color.RED;
        }

        // set the appropriate number of black and white clue buttons, and then set the rest
        // of them to the default color
        for (Button b : clueButtons) {
            if (numBlacks > 0) {
                numBlacks--;
                b.setBackgroundColor(Color.BLACK);
            }
            else if (numWhites > 0) {
                numWhites--;
                b.setBackgroundColor(Color.WHITE);
            }
            else {
                b.setBackgroundColor(defaultColor);
            }
        }
    }

    /**
     * create a FourTuple-compatible string from an array of buttons, based on their colors
     * @param arr the array of buttons
     * @return the corresponding string, to be used in the FourTuple constructor
     */
    private String mapButtonArrayToColorNames(Button[] arr) {
        // initialize the string to empty
        String rtnVal = "";

        // append a one-character string based on each button color
        for (Button b : arr) {
            rtnVal += mapColorToName(getButtonColor(b));
        }

        // return the string
        return rtnVal;
    }
}
