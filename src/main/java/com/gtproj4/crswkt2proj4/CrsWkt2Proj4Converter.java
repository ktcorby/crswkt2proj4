package com.gtproj4.crswkt2proj4;

import com.gtproj4.crswkt2proj4.projections.*;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CRSFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 8:23 PM
 */
public class CrsWkt2Proj4Converter {
    private CRSFactory factory;

    public void init() {
        this.factory = ReferencingFactoryFinder.getCRSFactory(null);
    }

    public Proj convert(String wkt) throws ConversionException {
        try {
            CoordinateReferenceSystem crs = factory.createFromWKT(wkt);
            Proj candidate = null;
            int priority = Integer.MIN_VALUE;
            for (ProjType pt : EnumSet.allOf(ProjType.class)) {
                Proj instance = pt.getInstance();
                if (instance.getPriority() > priority && instance.isCandidate(crs)) {
                    priority = instance.getPriority();
                    candidate = instance;
                }
            }
            if (candidate != null) {
                candidate.initFromCrs(crs, wkt);
                return candidate;
            }
            return null;
        } catch (FactoryException e) {
            throw new ConversionException("Unable to convert wkt to proj4", e);
        }
    }
}
