package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:07 PM
 */
public class Tmerc extends Lon0FromCentralMeridianParams {
    private double k;

    public Tmerc() {
        super();
        setType(ProjType.TMERC);
    }

    public Tmerc(Lon0FromCentralMeridianParams projectedCommonParams,
                 double k) {
        super(projectedCommonParams);
        setType(ProjType.TMERC);
        this.k = k;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public int getPriority() {
        return 1;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        rv = rv + " +k=" + DoubleVal.format(k);
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        k = Double.parseDouble(proj4StringVals.get("k"));
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt, convFromBase);
        k =  convFromBase.getParameterValues().parameter("scale_factor").doubleValue();
    }
}
