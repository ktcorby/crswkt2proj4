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
 * Time: 12:31 PM
 */
public class Merc extends ProjectedCommonParams {
    private double k;
    private double lon_0;
    private Double lat_ts;
    private boolean spherical;

    public Merc() {
        super();
        setType(ProjType.MERC);
    }

    public Merc(ProjectedCommonParams projectedCommonParams,
                 double lon_0,
                 double k,
                 boolean isSpherical,
                 Double lat_ts) {
        super(projectedCommonParams);
        setType(ProjType.MERC);
        this.lon_0 = lon_0;
        this.k = k;
        this.spherical = isSpherical;
        this.lat_ts = lat_ts;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getLon_0() {
        return lon_0;
    }

    public void setLon_0(double lon_0) {
        this.lon_0 = lon_0;
    }

    public Double getLat_ts() {
        return lat_ts;
    }

    public void setLat_ts(Double lat_ts) {
        this.lat_ts = lat_ts;
    }

    public boolean isSpherical() {
        return spherical;
    }

    public void setSpherical(boolean spherical) {
        this.spherical = spherical;
    }

    @Override
    protected String getProj4StringRest() {
        if (!isSpherical()) {
            String rv = " +lon_0=" + DoubleVal.format(getLon_0());
            rv = rv + " +k=" + DoubleVal.format(k);
            rv = rv + super.getProj4StringRest();
            return rv;
        }
        else {
            StringBuilder rv = new StringBuilder();
            rv.append(" +a=").append(geteAxes().getSemiMajorString())
                .append(" +b=").append(geteAxes().getSemiMinorString())
                .append(" +lat_ts=").append(DoubleVal.format(getLat_ts()))
                .append(" +lon_0=").append(DoubleVal.format(getLon_0()))
                .append(" +x_0=").append(DoubleVal.format(getX0()))
                .append(" +y_0=").append(DoubleVal.format(getY0()))
                .append(" +units=").append(getUnitType().getProj4String())
                .append(" +k=").append(DoubleVal.format(getK()))
                .append(" +nadgrids=@null");
            return rv.toString();
        }
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        if (proj4StringVals.containsKey("nadgrids") && proj4StringVals.get("nadgrids").equals("@null")) {
            spherical = true;
        }
        k = Double.parseDouble(proj4StringVals.get("k"));
        lon_0 = Double.parseDouble(proj4StringVals.get("lon_0"));
        if (spherical) {
            lat_ts = Double.parseDouble(proj4StringVals.get("lat_ts"));
        }
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        if (crs.getDatum().getEllipsoid().isSphere()) {
            spherical = true;
        }
        super.initFromCrs(crs, wkt, convFromBase);
        k =  convFromBase.getParameterValues().parameter("scale_factor").doubleValue();
        if (isSpherical()) {
            lon_0 =  convFromBase.getParameterValues().parameter("central_meridian").doubleValue();
            lat_ts =  convFromBase.getParameterValues().parameter("latitude_of_origin").doubleValue();
        }
        else {
            lon_0 =  convFromBase.getParameterValues().parameter("longitude_of_center").doubleValue();
        }
    }
}
