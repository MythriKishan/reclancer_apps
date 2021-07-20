package com.mythri.reclancer_app.Validation;

import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class RecRegVal {

    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //10 digit Mobile Number//
    private static final String MOB_REGEX = "^[0-9]{10}$";
    private static final String PASS_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";


    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String MOB_MSG = "invalid Mobile";
    private static final String CPASS_MSG = "Password Mismatch";
    private static final String PASS_MSG = "Password must contain atleast one Uppercase letter,lower case letter and Numeral with minimum 8 characters";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isMobileNum(EditText editText, boolean required) {
        return isValid(editText, MOB_REGEX, MOB_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPassword(EditText editText, boolean required) {
        return isValid(editText, PASS_REGEX, PASS_MSG, required);
    }

    public static boolean isCpass(EditText editText, boolean required) {
        return isValid(editText, PASS_REGEX, CPASS_MSG, required);
    }


    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        };

        return true;
    }

    public static boolean isVal(Spinner spin, String regex, String errMsg, boolean required) {

        int test = spin.getSelectedItemPosition();

        if(test == 0)
        {
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

}
