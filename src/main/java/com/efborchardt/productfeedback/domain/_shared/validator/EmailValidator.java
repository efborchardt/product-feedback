package com.efborchardt.productfeedback.domain._shared.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    private EmailValidator() {}

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
