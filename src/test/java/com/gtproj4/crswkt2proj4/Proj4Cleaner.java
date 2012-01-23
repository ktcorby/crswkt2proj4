package com.gtproj4.crswkt2proj4;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 1:08 PM
 */
public class Proj4Cleaner {
    /* Mostly deals with precision issues in converting floats to strings */
    public static String clean(String proj4text)  {
        proj4text = proj4text.replaceAll("(\\.[0-9]{6})[0-9]+ ", "$1 ");
        proj4text = proj4text.replaceAll("(\\.[0-9]*[1-9])0+ ", "$1 ");
        proj4text = proj4text.replaceAll("(\\.[0-9]+)49+ ", "$1ZZZZ ").replaceAll("ZZZZ", "5");
        proj4text = proj4text.replaceAll("(\\.[0-9]+)89+ ", "$1ZZZZ ").replaceAll("ZZZZ", "9");
        proj4text = proj4text.replaceAll("(\\.[0-9]+)89+ ", "$1ZZZZ ").replaceAll("ZZZZ", "9");
        proj4text = proj4text.replaceAll("(\\.[0-9]+)74+ ", "$1ZZZZ ").replaceAll("ZZZZ", "75");
        proj4text = proj4text.replaceAll("(\\.[0-9]*)0999+ ", "$1ZZZZ ").replaceAll("ZZZZ", "1");
        proj4text = proj4text.replaceAll(" \\+pm=2.337229 ", " +pm=paris ");
        proj4text = proj4text.replaceAll("\\.0+ ", " ");
        return proj4text;
    }
}
