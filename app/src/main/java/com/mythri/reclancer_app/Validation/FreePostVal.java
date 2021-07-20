package com.mythri.reclancer_app.Validation;


import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class FreePostVal {

    private static final String Name_REGEX = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
    private static final String PHONE_REGEX = "^([9]{1})([234789]{1})([0-9]{8})$";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PTITLE_REGEX = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
    private static final String ADDRS_REGEX = "^[#.0-9a-zA-Z\\s,-]+$";
    private static final String SKILLS_REGEX = "^[,.0-9a-zA-Z\\s,-]+$";
    private static final String EXP_REGEX = "^[0-9]*$";
    private static final String PRJRTS_REGEX = "^[0-9]*$";
    private static final String PRJREF_REGEX = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";

    private static final String REQUIRED_MSG = "required";
    private static final String NAME_MSG = "Only Alphabets and Space Allowed";
    private static final String MOBILE_MSG = "########## - 10 digit Number";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PTITLE_MSG = "Only Alphabets and Space Allowed";
    private static final String ADDRS_MSG = "Inavlid Ex : #1, North Street, Bangalore - 11";
    private static final String SKILLS_MSG = "Skills sepearated by Comma";
    private static final String STATE_MSG = "Required Field";
    private static final String EXP_MSG = "Required Field.Experience in Years.";
    private static final String PRJRTS_MSG = "Only Numbers are Allowed -  100/hr";
    private static final String PRJREF_MSG = "Only Alphabets and Space Allowed";

    public static boolean isName(EditText editText, boolean required) {
        return isValid(editText, Name_REGEX, NAME_MSG, required);
    }

    public static boolean isMobile(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, MOBILE_MSG, required);
    }

    public static boolean isEmail(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isPtitle(EditText editText, boolean required) {
        return isValid(editText, PTITLE_REGEX, PTITLE_MSG, required);
    }

    public static boolean isAddress(EditText editText, boolean required) {
        return isValid(editText, ADDRS_REGEX, ADDRS_MSG, required);
    }

    public static boolean isPskills(EditText editText, boolean required) {
        return isValid(editText, SKILLS_REGEX, SKILLS_MSG , required);
    }

    public static boolean isExp(EditText editText, boolean required) {
        return isValid(editText, EXP_REGEX, EXP_MSG , required);
    }

    public static boolean isPrjRts(EditText editText, boolean required) {
        return isValid(editText, PRJRTS_REGEX, PRJRTS_MSG, required);
    }

    public static boolean isPrjRef(EditText editText, boolean required) {
        return isValid(editText, PRJREF_REGEX, PRJREF_MSG, required);
    }

    public static boolean isState(Spinner spin, boolean required){
        return isVal(spin, STATE_MSG, required);
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

    public static boolean isVal(Spinner spin, String errMsg, boolean required) {

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
