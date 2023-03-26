package com.schoolproject.javafxmoviesapp.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {
    public static boolean isURL(String url) {
        Pattern pattern = Pattern.compile("^(https?:\\/\\/)?([\\da-z.-]+)\\.([a-z.]{2,6})([\\/\\w .-]*)*\\/?");
        return pattern.matcher(url).matches();
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})");
        return pattern.matcher(email).matches();
    }

}
