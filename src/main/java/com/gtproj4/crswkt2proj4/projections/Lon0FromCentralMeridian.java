package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.etc.DoubleVal;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/12/12
 * Time: 2:39 PM
 */
public class Lon0FromCentralMeridian extends Lon0FromCentralMeridianParams {
    public Lon0FromCentralMeridian() {
        super();
    }

    public Lon0FromCentralMeridian(Lon0FromCentralMeridianParams projectedCommonParams) {
        super(projectedCommonParams);
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        rv = rv + super.getProj4StringRest();
        return rv;
    }

}
