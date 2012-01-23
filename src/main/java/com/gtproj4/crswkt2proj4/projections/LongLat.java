package com.gtproj4.crswkt2proj4.projections;

import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:08 PM
 */
public class LongLat extends CommonProjParams {
    public LongLat() {
        super();
        setType(ProjType.LONGLAT);
    }

    public LongLat(CommonProjParams commonParams) {
        super(commonParams);
        setType(ProjType.LONGLAT);
    }

    @Override
    public boolean isCandidate(CoordinateReferenceSystem crs) {
        return DefaultGeographicCRS.class.isAssignableFrom(crs.getClass());
    }
}
