package com.schoolproject.javafxmoviesapp.Utils;

import com.schoolproject.javafxmoviesapp.Entity.Country;
import com.schoolproject.javafxmoviesapp.Entity.Genre;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SQLQueryUtil {
    public static void setGenreAndCountryForFilm(int filmId, List<Genre> genres, List<Country> countries) throws SQLException, IOException {
        StringBuffer genresSB = new StringBuffer();
        for (Genre genre : genres) genresSB.append(genre.getId() + ",");
        genresSB.deleteCharAt(genresSB.length() - 1);

        StringBuffer counreiesSB = new StringBuffer();
        for (Country country : countries) counreiesSB.append(country.getId() + ",");
        counreiesSB.deleteCharAt(counreiesSB.length() - 1);

        String sql = "SET @film_id := ?;"
                + "DELETE FROM `film_genre` WHERE `filmId`=@film_id; "
                + "DELETE FROM `film_country` WHERE `filmId`=@film_id; "
                + "INSERT INTO `film_genre` (`filmId`, `genreId`) SELECT @film_id, `id` FROM `genres` WHERE `id` IN (" + genresSB.toString() + ");"
                + "INSERT INTO `film_country` (`filmId`, `countryId`) SELECT @film_id, `id` FROM `countries` WHERE `id` IN (" + counreiesSB.toString() + ");";
        Connection connection = JDBCUtil.getConnecttion();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, filmId);

        preparedStatement.executeUpdate();

        connection.close();
    }



}
