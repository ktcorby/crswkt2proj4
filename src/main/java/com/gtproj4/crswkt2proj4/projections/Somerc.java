package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

import static com.gtproj4.crswkt2proj4.etc.DoubleVal.eq;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/13/12
 * Time: 3:05 PM
 */
public class Somerc extends ProjectedCommonParams {
    private double lat0;
    private double lon0;

    public Somerc() {
        super();
        setType(ProjType.SOMERC);
    }

    public Somerc(ProjectedCommonParams projectedCommonParams,
                 double lat0,
                 double lon0) {
        super(projectedCommonParams);
        setType(ProjType.SOMERC);
        this.lat0 = lat0;
        this.lon0 = lon0;
    }

    public double getLat0() {
        return lat0;
    }

    public void setLat0(double lat0) {
        this.lat0 = lat0;
    }

    public double getLon0() {
        return lon0;
    }

    public void setLon0(double lon0) {
        this.lon0 = lon0;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean isCandidate(CoordinateReferenceSystem crs) {
        try {
            return super.isCandidate(crs)
                    && eq(90.0, ((ProjectedCRS) crs).getConversionFromBase().getParameterValues().parameter("azimuth").doubleValue());
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        lat0 = Double.parseDouble(proj4StringVals.get("lat_0"));
        lon0 = Double.parseDouble(proj4StringVals.get("lon_0"));
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt, convFromBase);
        lat0 =  convFromBase.getParameterValues().parameter("latitude_of_center").doubleValue();
        lon0 =  convFromBase.getParameterValues().parameter("longitude_of_center").doubleValue();
    }
}
