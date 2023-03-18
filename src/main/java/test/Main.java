package test;

import com.schoolproject.javafxmoviesapp.Utils.JDBCUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Connection connection = JDBCUtil.getConnecttion();
        JDBCUtil.printInfo(connection);
    }
}
