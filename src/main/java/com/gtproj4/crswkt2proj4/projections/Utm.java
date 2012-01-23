package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import org.geotools.referencing.operation.projection.TransverseMercator;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:00 PM
 */
public class Utm extends TmercCommonParams {
    private int zone = -1;
    private boolean north = true;

    public Utm() {
        super();
        setType(ProjType.UTM);
    }

    public Utm(TmercCommonParams tmercParams, int zone,
               boolean north) {
        super(tmercParams);
        setType(ProjType.UTM);
        this.zone = zone;
        this.north = north;
    }

    @Override
    public boolean isCandidate(CoordinateReferenceSystem crs) {
        if (super.isCandidate(crs)) {
            ProjectedCRS pcrs = (ProjectedCRS) crs;
            Projection fromBase = pcrs.getConversionFromBase();
            int centralMeridian = fromBase.getParameterValues().parameter("central_meridian").intValue();
            int falseEasting = fromBase.getParameterValues().parameter("false_easting").intValue();
            String scale_factor = fromBase.getParameterValues().parameter("scale_factor").toString().trim();
            return super.isCandidate(crs) && scale_factor.equals("scale_factor = 0.9996")
                    && (centralMeridian + 177) % 6 == 0
                    &&  falseEasting == 500000
                    && TransverseMercator.class.isAssignableFrom(fromBase.getMathTransform().getClass());
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    protected String getProj4StringRest() {
        String rv = " +zone=" + zone;
        if (!north) {
            rv = rv + " +south";
        }
        rv = rv + super.getProj4StringRest();
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        this.zone = Integer.parseInt(proj4StringVals.get("zone"));
        north = !proj4StringVals.containsKey("south");
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        TransverseMercator tm = (TransverseMercator) convFromBase.getMathTransform();
        super.initFromCrs(crs, wkt, convFromBase);
        zone = tm.getZone();
        north = (0 == convFromBase.getParameterValues().parameter("false_northing").intValue());
    }
}
