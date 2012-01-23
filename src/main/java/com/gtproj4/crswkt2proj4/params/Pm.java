package com.gtproj4.crswkt2proj4.params;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.gtproj4.crswkt2proj4.etc.DoubleVal.eq;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 4:06 PM
 */
public enum Pm {
    Greenwich(""),
    Oslo("oslo"),
    Rome("rome"),
    Lisbon("lisbon"),
    Ferro("ferro"),
    Bern("bern"),
    Bogota("bogota"),
    Jakarta("jakarta"),
    Paris("paris", 2.3372291),
    paris("paris"),
    Brussels("brussels"),
    Stockholm("stockholm"),
    Athens("athens"),
    Madrid("madrid");

    private double val;
    private String proj4String;
    protected static final Map<String,Pm> pmsByProj4 = new HashMap<String,Pm>();

    static {
          for(Pm pt : EnumSet.allOf(Pm.class)) {
              pmsByProj4.put(pt.proj4String, pt);
          }

    }

    public static Pm findByProj4String(String units) {
        return pmsByProj4.get(units);
    }

    public static Pm findByValue(double v) {
        for (Pm pm : EnumSet.allOf(Pm.class)) {
            if (!eq(pm.val, -1.0) && eq(pm.val, v)) {
                return pm;
            }
        }
        return null;
    }

    public static Pm findByProj4StringOrValue(String s) {
        Pm rv = findByProj4String(s);
        if (rv == null) {
            try {
                rv = findByValue(Double.parseDouble(s));
            }
            catch (Exception ignore) {
            }
        }
        return rv;
    }

    Pm(String proj4String) {
        this(proj4String, -1.0);
    }

    Pm(String proj4String, double val) {
        this.proj4String = proj4String;
        this.val = val;
    }

    public String getProj4String() {
        return proj4String;
    }
}
