package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/12/12
 * Time: 10:46 AM
 */
public class ProjectedCommonParams extends TmercCommonParams {
    private double x0;
    private double y0;

    public ProjectedCommonParams() {
        super();
    }

    public ProjectedCommonParams(TmercCommonParams params,
                                 double x0,
                                 double y0) {
        super(params);
        this.x0 = x0;
        this.y0 = y0;
    }

    public ProjectedCommonParams(ProjectedCommonParams toCopy) {
        this(toCopy, toCopy.getX0(), toCopy.getY0());
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +x_0=" + DoubleVal.format(x0);
        rv = rv + " +y_0=" + DoubleVal.format(y0);
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        x0 = Double.parseDouble(proj4StringVals.get("x_0"));
        y0 = Double.parseDouble(proj4StringVals.get("y_0"));
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt, convFromBase);
        x0 = convFromBase.getParameterValues().parameter("false_easting").doubleValue();
        y0 = convFromBase.getParameterValues().parameter("false_northing").doubleValue();
    }
}
