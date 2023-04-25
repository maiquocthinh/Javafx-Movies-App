package com.schoolproject.javafxmoviesapp.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {
    /**
     * @param url A url video of Youtube
     * @return A url embed of Youtube with format: https://www.youtube.com/embed/{IdVideo}
     */
    public static String convertToYoutubeEmbedLink(String url) {
        String regex = "^.*((youtu.be\\/)|(v\\/)|(\\/u\\/\\w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#&?]*).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches() && matcher.group(7) != null) {
            String videoId = matcher.group(7);
            return "https://www.youtube.com/embed/" + videoId;
        }
        return null;
    }
}
