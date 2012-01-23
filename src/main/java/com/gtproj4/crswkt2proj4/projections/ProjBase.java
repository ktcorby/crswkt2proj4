package com.gtproj4.crswkt2proj4.projections;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 8:34 PM
 */
public abstract class ProjBase implements Proj {
    private ProjType type;

    protected ProjBase() {
    }

    protected ProjBase(ProjType type) {
        this.type = type;
    }

    protected ProjBase(Proj toCopy) {
        this(toCopy.getType());
    }

    protected void buildProj4String() {
    }

    public void initFromProj4String(Map<String, String> proj4StringVals) {
    }

    public ProjType getType() {
        return type;
    }

    public void setType(ProjType type) {
        this.type = type;
    }

    public String toProj4String() {
        return "+proj=" + type.toString() + getProj4StringRest() + " +no_defs";
    }

    protected String getProj4StringRest() {
        return "";
    }

    public int getPriority() {
        return 1;
    }

    public boolean isCandidate(CoordinateReferenceSystem crs) {
        return false;
    }
}
