package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/13/12
 * Time: 12:06 PM
 */
public class Lon0FromCentralMeridianParams extends ProjectedCommonParams {
    private double lat0;
    private double lon0;

    public Lon0FromCentralMeridianParams() {
        super();
    }

    public Lon0FromCentralMeridianParams(ProjectedCommonParams params,
                                         double lat0,
                                         double lon0) {
        super(params);
        this.lat0 = lat0;
        this.lon0 = lon0;
    }

    public Lon0FromCentralMeridianParams(Lon0FromCentralMeridianParams toCopy) {
        this(toCopy, toCopy.getLat0(), toCopy.getLon0());
    }

    public double getLon0() {
        return lon0;
    }

    public void setLon0(double lon0) {
        this.lon0 = lon0;
    }

    public double getLat0() {
        return lat0;
    }

    public void setLat0(double lat0) {
        this.lat0 = lat0;
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
        lat0 = convFromBase.getParameterValues().parameter("latitude_of_origin").doubleValue();
        lon0 = convFromBase.getParameterValues().parameter("central_meridian").doubleValue();
    }
}
