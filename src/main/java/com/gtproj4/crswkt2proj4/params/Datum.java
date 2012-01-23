package com.gtproj4.crswkt2proj4.params;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 12:05 AM
 */
public enum Datum {
    WGS_1984("WGS84"),
    North_American_Datum_1927("NAD27"),
    North_American_Datum_1983("NAD83"),
    New_Zealand_Geodetic_Datum_1949("nzgd49"),
    OSGB_1936("OSGB36"),
    Deutsches_Hauptdreiecksnetz("potsdam");

    private String proj4code;
    protected static final Map<String,Datum> datumsByCode = new HashMap<String,Datum>();

    static {
          for(Datum pt : EnumSet.allOf(Datum.class)) {
              datumsByCode.put(pt.proj4code, pt);
          }

    }

    public static Datum findByProj4Code(String code) {
        return datumsByCode.get(code);
    }

    Datum(String proj4code) {
        this.proj4code = proj4code;
    }

    public String getProj4code() {
        return proj4code;
    }
}
