package com.gtproj4.crswkt2proj4.params;

import com.gtproj4.crswkt2proj4.etc.DoubleVal;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 12:40 AM
 */
public class EllipsoidAxes {
    private String semiMajorString;
    private String semiMinorString;

    public EllipsoidAxes() {
    }

    public EllipsoidAxes(double semiMajor, double semiMinor) {
        this.semiMajorString = format(semiMajor);
        this.semiMinorString = format(semiMinor);
    }

    public EllipsoidAxes(String semiMajorString, String semiMinorString) {
        this.semiMajorString = semiMajorString;
        this.semiMinorString = semiMinorString;
    }

    public String getSemiMajorString() {
        return semiMajorString;
    }

    public String getSemiMinorString() {
        return semiMinorString;
    }

    private String format(double x) {
        return DoubleVal.format(x);
    }

}
