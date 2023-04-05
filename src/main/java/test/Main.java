package test;

import com.schoolproject.javafxmoviesapp.Services.GmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MessagingException, GeneralSecurityException, IOException {
       /* String jsonString = """
               ["Browse Films", "Create Film", "Update Film", "Delete Film", "Browse Genres", "Create Genre", "Update Genre", "Delete Genre", "Browse Countries", "Create Country", "Update Country", "Delete Country", "Browse Roles", "Create Role", "Update Role", "Delete Role", "Set Role"]""";
//       String jsonString = "[]";
        JSONArray jsonArray = (JSONArray) JSONValue.parse(jsonString);
        List<String> permissions = (ArrayList<String>) jsonArray;
        System.out.println(permissions.toString());*/

        List<String> emails = new ArrayList<>();
        emails.add("quocthinh.phim@gmail.com");
//        emails.add("majquocthjnh@gmail.com");
//        emails.add("6251071096@st.utc2.edu.vn");

//        GmailService.sendMessage("6251071025@st.utc2.edu.vn", "Hello", """
//                Hello bro
//                Hello and bye!
//                """);

        GmailService.sendManyHtmlMessage(emails, "Hello", """
                <div style="font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2">
                  <div style="margin:50px auto;width:70%;padding:20px 0">
                    <div style="border-bottom:1px solid #eee">
                      <a href="" style="font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600">Your Brand</a>
                    </div>
                    <p style="font-size:1.1em">Hi,</p>
                    <p>Thank you for choosing Your Brand. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>
                    <h2 style="background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;">324457</h2>
                    <p style="font-size:0.9em;">Regards,<br />Your Brand</p>
                    <hr style="border:none;border-top:1px solid #eee" />
                    <div style="float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300">
                      <p>Your Brand Inc</p>
                      <p>1600 Amphitheatre Parkway</p>
                      <p>California</p>
                    </div>
                  </div>
                </div>
                """);
    }
}
