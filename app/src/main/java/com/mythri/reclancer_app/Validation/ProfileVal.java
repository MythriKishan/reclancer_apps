package com.mythri.reclancer_app.Validation;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ProfileVal {

        private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        private static final String PASS_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        private static final String Name_REGEX = "^[a-zA-z]+([\\s][a-zA-Z]+)*$";
        private static final String PHONE_REGEX = "^([9]{1})([234789]{1})([0-9]{8})$";
        private static final String ADDRS_REGEX = "^[#.0-9a-zA-Z\\s,-]+$";


        // Error Messages
        private static final String REQUIRED_MSG = "required";
        private static final String EMAIL_MSG = "invalid email";
        private static final String CPASS_MSG = "Password Mismatch";
        private static final String PASS_MSG = "Password must contain atleast one Uppercase letter,lower case letter and Numeral with minimum 8 characters";
        private static final String NAME_MSG = "Only Alphabets and Space Allowed";
        private static final String PHONE_MSG = "########## - 10 digit Number";
        private static final String ADDRS_MSG = "Inavlid Ex : #1, North Street, Bangalore - 11";


        public static boolean isFName(TextView editText, boolean required) {
            return isValid(editText, Name_REGEX, NAME_MSG, required);
        }

        public static boolean isLName(TextView editText, boolean required) {
            return isValid(editText, Name_REGEX, NAME_MSG, required);
        }

        // call this method when you need to check phone number validation
        public static boolean isPhoneNumber(TextView editText, boolean required) {
            return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
        }

        public static boolean isEmailAddress(TextView editText, boolean required) {
            return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
        }

        // call this method when you need to check phone number validation
        public static boolean isPassword(EditText editText, boolean required) {
            return isValid(editText, PASS_REGEX, PASS_MSG, required);
        }

        public static boolean isCpass(EditText editText, boolean required) {
            return isValid(editText, PASS_REGEX, CPASS_MSG, required);
        }

        public static boolean isAddress(TextView editText, boolean required) {
            return isValid(editText, ADDRS_REGEX, ADDRS_MSG, required);
        }


        // return true if the input field is valid, based on the parameter passed
        public static boolean isValid(TextView editText, String regex, String errMsg, boolean required) {

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
        public static boolean hasText(TextView editText) {

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
