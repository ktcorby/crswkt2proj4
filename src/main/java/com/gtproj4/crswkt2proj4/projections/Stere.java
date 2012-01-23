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
 * Time: 3:40 PM
 */
public class Stere extends Lon0FromCentralMeridianParams {
    private Double k;

    public Stere() {
        super();
        setType(ProjType.STERE);
    }

    public Stere(Lon0FromCentralMeridianParams projectedCommonParams,
                  Double k) {
        super(projectedCommonParams);
        setType(ProjType.STERE);
        this.k = k;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +lat_0=" + (getLat0() > 0 ? "90" : "-90");
        rv = rv + " +lat_ts=" + DoubleVal.format(getLat0());
        rv = rv + " +lon_0=" + DoubleVal.format(getLon0());
        if (k != null) rv = rv + " +k=" + DoubleVal.format(k);
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        super.initFromProj4String(proj4StringVals);
        if (proj4StringVals.containsKey("lat_ts")) {
            setLat0(Double.parseDouble(proj4StringVals.get("lat_ts")));
        }
        if (proj4StringVals.containsKey("k")) {
            k = Double.parseDouble(proj4StringVals.get("k"));
        }
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String origText, Projection pr) throws ConversionException {
        super.initFromCrs(crs, origText, pr);
        k = null;
        try {
            k = pr.getParameterValues().parameter("scale_factor").doubleValue();
        }
        catch (Exception ignore) {
        }
        setLat0(Double.parseDouble(origText.replaceAll(".*\\[\"latitude_of_origin\",([0-9-.]*)\\].*", "$1")));
    }
}
