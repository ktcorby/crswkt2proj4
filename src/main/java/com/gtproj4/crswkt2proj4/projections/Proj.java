package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/12/12
 * Time: 2:49 PM
 */
public interface Proj {
    void initFromProj4String(Map<String, String> proj4StringVals);
    void initFromCrs(CoordinateReferenceSystem crs, String wkt) throws ConversionException;
    ProjType getType();
    String toProj4String();
    boolean isCandidate(CoordinateReferenceSystem crs);
    int getPriority();
}
