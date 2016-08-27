package com.gdg.jpq.maltratocero.tools;

import android.widget.EditText;
import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String NAME_REGEX = "^([A-Za-z ñáéíóúÑÁÉÍÓÚüÜ'-]{1,60})$";

    // Error Messages
    private static final String REQUIRED_MSG = "obligatorio";
    private static final String EMAIL_MSG = "email inválido";
    private static final String NAME_MSG = "caracteres inválidos";
    private static final String PHONE_MSG = "###-#######";


    private static final String ORIGINAL_CHARACTERS = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT_CHARACTERS = "AaEeIiOoUuNnUu";

    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check name validation
    public static final boolean isName(EditText editText, boolean required) {
        return isValid(editText, NAME_REGEX, NAME_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
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

    public static String replaceSpecialCharacters(String str){
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL_CHARACTERS.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT_CHARACTERS.charAt(pos);
            }
        }
        return new String(array);
    }
}
