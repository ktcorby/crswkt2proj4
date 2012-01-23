package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/13/12
 * Time: 5:23 PM
 */
public class Krovak extends ProjectedCommonParams {
    private double alpha;
    private double k;
    private double lat0;
    private double lon0;

    public Krovak() {
        super();
        setType(ProjType.Krovak);
    }

    public Krovak(Lon0FromCentralMeridianParams projectedCommonParams,
                  double lat0, double lon0, double k, double alpha) {
        super(projectedCommonParams);
        setType(ProjType.Krovak);
        this.lat0 = lat0;
        this.lon0 = lon0;
        this.k = k;
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
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
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        rv = rv + " +alpha=" + DoubleVal.format(alpha);
        rv = rv + " +k=" + DoubleVal.format(k);
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        super.initFromProj4String(proj4StringVals);
        lat0 = Double.parseDouble(proj4StringVals.get("lat_0"));
        lon0 = Double.parseDouble(proj4StringVals.get("lon_0"));
        k = Double.parseDouble(proj4StringVals.get("k"));
        alpha = Double.parseDouble(proj4StringVals.get("alpha"));
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String origText, Projection pr) throws ConversionException {
        super.initFromCrs(crs, origText, pr);
        lat0 = pr.getParameterValues().parameter("latitude_of_center").doubleValue();
        lon0 = pr.getParameterValues().parameter("longitude_of_center").doubleValue();
        k = pr.getParameterValues().parameter("scale_factor").doubleValue();
        alpha = pr.getParameterValues().parameter("azimuth").doubleValue();
    }
}
