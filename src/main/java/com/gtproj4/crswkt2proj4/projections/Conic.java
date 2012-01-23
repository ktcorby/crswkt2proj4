package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

import static com.gtproj4.crswkt2proj4.etc.DoubleVal.eq;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/12/12
 * Time: 4:13 PM
 */
public class Conic extends Lon0FromCentralMeridianParams {
    private double lat_1;
    private Double lat_2 = null;

    public Conic() {
        super();
    }

    public Conic(Lon0FromCentralMeridianParams toCopy,
               double lat_1, Double lat_2) {
        super(toCopy);
        this.lat_1 = lat_1;
        this.lat_2 = lat_2;
    }

    public Conic(Conic toCopy) {
        this(toCopy, toCopy.getLat_1(), toCopy.getLat_2());
    }

    public Double getLat_2() {
        return lat_2;
    }

    public void setLat_2(Double lat_2) {
        this.lat_2 = lat_2;
    }

    public double getLat_1() {
        return lat_1;
    }

    public void setLat_1(double lat_1) {
        this.lat_1 = lat_1;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = getProj4StringRestConic();
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    protected String getProj4StringRestConic() {
        String rv = " +lat_1=" + DoubleVal.format(getLat_1());
        if (lat_2 != null) rv = rv + " +lat_2=" + DoubleVal.format(getLat_2());
        rv = rv + " +lat_0=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        lat_1 = Double.parseDouble(proj4StringVals.get("lat_1"));
        if (proj4StringVals.containsKey("lat_2")) {
            lat_2 = Double.parseDouble(proj4StringVals.get("lat_2"));
        }
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String origText, Projection pr) throws ConversionException {
        super.initFromCrs(crs, origText, pr);
        try {
            lat_1 = pr.getParameterValues().parameter("standard_parallel_1").doubleValue();
        }
        catch (Exception ignore) {
            lat_1 = pr.getParameterValues().parameter("latitude_of_origin").doubleValue();
        }
        lat_2 = null;
        try {
            lat_2 = pr.getParameterValues().parameter("standard_parallel_2").doubleValue();
        }
        catch (Exception ignore) {
        }
        if (lat_2 != null) {
            try {
                Double actualLat2Val = Double.valueOf(origText.replaceAll(".*standard_parallel_2\",([^\\]]+)\\].*", "$1"));
                if (!eq(actualLat2Val, lat_2)) {
                    double swp = lat_2;
                    lat_2 = lat_1;
                    lat_1 = swp;
                }
            }
            catch (Exception ignore) {
            }
        }
    }
}
