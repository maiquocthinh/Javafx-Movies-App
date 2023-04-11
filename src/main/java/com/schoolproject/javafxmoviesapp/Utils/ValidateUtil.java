package com.schoolproject.javafxmoviesapp.Utils;


import java.util.regex.Pattern;

public class ValidateUtil {
    public  static  boolean isEmail(String email){
        String regex = "^([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,})";
        Pattern pattern = Pattern.compile(regex);
        return  pattern.matcher(email).matches();
    }
}
