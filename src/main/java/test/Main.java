package test;

import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;
import com.schoolproject.javafxmoviesapp.Utils.ValidateUtil;


public class Main {
    public static void main(String[] args) {
        String url = "chat.openai.com/sdasa";
        String email  = "majquocthjnh@gmail.com";

        System.out.println(ValidateUtil.isURL(url));
        System.out.println(ValidateUtil.isEmail(email));

    }
}
