package com.schoolproject.javafxmoviesapp.Utils;

import com.schoolproject.javafxmoviesapp.Services.GmailService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class OTPUtil {
    private static OTPUtil instance = null;

    public static OTPUtil getInstance() {
        if (instance == null) instance = new OTPUtil();
        return instance;
    }

    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    private boolean compareOTP(String hashedOTP, String OTP) {
        return DigestUtils.sha256Hex(OTP).equals(hashedOTP);
    }

    public void generateAndSendEmailOTP(String email) throws SQLException, IOException, MessagingException, GeneralSecurityException {
        // generate otp
        String OTPCode = generateOTP();
        String OTPCodeHashed = DigestUtils.sha256Hex(OTPCode);
        // save otp to db
        {
            // get connection
            Connection connection = JDBCUtil.getConnecttion();
            // create Statement
            String sql = "INSERT INTO `otps` (`email`, `otp_code_hashed`) VALUE (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, OTPCodeHashed);
            // execute sql
            preparedStatement.executeUpdate();
            // close connection
            preparedStatement.close();
            connection.close();
        }
        // send email with otp
        {
            GmailService.sendMessage(email, "Verification email", "Your OTP Code: " + OTPCode);
        }
    }

    public boolean checkOTP(String email, String OTPCode) throws SQLException, IOException {
        Boolean isVerified = false;
        String OTPCodeHashed = "";

        // get connection
        Connection connection = JDBCUtil.getConnecttion();

        // create Statement
        String sql = "SELECT `otp_code_hashed` FROM `otps` WHERE `email` = ? AND `expiry_time` > NOW() ORDER BY `expiry_time` DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);

        // execute sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // get data
        if (resultSet.next()) OTPCodeHashed = resultSet.getString("otp_code_hashed");

        // compare OTPCode param with OTPCode in db
        if (!OTPCodeHashed.isEmpty()) {
            isVerified = compareOTP(OTPCodeHashed, OTPCode);
        }

        // close connection
        preparedStatement.close();
        connection.close();

        return isVerified;
    }


}

