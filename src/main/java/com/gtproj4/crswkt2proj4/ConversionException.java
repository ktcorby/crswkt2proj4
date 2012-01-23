package com.gtproj4.crswkt2proj4;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 11:33 PM
 */
public class ConversionException extends Exception {
    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
