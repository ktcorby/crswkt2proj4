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
 * Time: 5:07 PM
 */
public class Eqc extends ProjectedCommonParams {
    private double lon0 = 0.0;

    public Eqc() {
        super();
    }

    public Eqc(ProjectedCommonParams params,
                                         double lon0) {
        super(params);
        this.lon0 = lon0;
    }

    public Eqc(Eqc toCopy) {
        this(toCopy, toCopy.getLon0());
    }

    public double getLon0() {
        return lon0;
    }

    public void setLon0(double lon0) {
        this.lon0 = lon0;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        lon0 = Double.parseDouble(proj4StringVals.get("lon_0"));
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt, convFromBase);
        try {
            lon0 = convFromBase.getParameterValues().parameter("central_meridian").doubleValue();
        }
        catch (Exception ignore) {
        }
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_ts=0 +lat_0=0 +lon_0=" + DoubleVal.format(lon0);
        return rv + super.getProj4StringRest();
    }
}
