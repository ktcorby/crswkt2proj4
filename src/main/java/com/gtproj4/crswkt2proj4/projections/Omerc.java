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
 * Time: 11:58 AM
 */
public class Omerc extends ProjectedCommonParams {
    private double k;
    private double alpha;
    private double lonc;
    private double lat_0;

    public Omerc() {
        super();
        setType(ProjType.OMERC);
    }

    public Omerc(ProjectedCommonParams projectedCommonParams,
                 double lat_0,
                 double lonc,
                 double k,
                 double alpha) {
        super(projectedCommonParams);
        setType(ProjType.OMERC);
        this.lat_0 = lat_0;
        this.lonc = lonc;
        this.k = k;
        this.alpha = alpha;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getLonc() {
        return lonc;
    }

    public void setLonc(double lonc) {
        this.lonc = lonc;
    }

    public double getLat_0() {
        return lat_0;
    }

    public void setLat_0(double lat_0) {
        this.lat_0 = lat_0;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + DoubleVal.format(getLat_0());
        rv = rv + " +lonc=" + DoubleVal.format(getLonc());
        rv = rv + " +alpha=" + DoubleVal.format(alpha);
        rv = rv + " +k=" + DoubleVal.format(k);
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        lat_0 = Double.parseDouble(proj4StringVals.get("lat_0"));
        lonc = Double.parseDouble(proj4StringVals.get("lonc"));
        alpha = Double.parseDouble(proj4StringVals.get("alpha"));
        k = Double.parseDouble(proj4StringVals.get("k"));
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt, convFromBase);
        k =  convFromBase.getParameterValues().parameter("scale_factor").doubleValue();
        lat_0 =  convFromBase.getParameterValues().parameter("latitude_of_center").doubleValue();
        lonc =  convFromBase.getParameterValues().parameter("longitude_of_center").doubleValue();
        alpha =  convFromBase.getParameterValues().parameter("azimuth").doubleValue();
    }
}
