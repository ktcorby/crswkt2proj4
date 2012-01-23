package com.gtproj4.crswkt2proj4.params;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 2:39 PM
 */
public enum UnitType {
    METER("m", "m"),
    FOOT("ft", "ft"),
    US_FOOT("foot_survey_us", "us-ft");

    private String code;
    private String proj4String;
    protected static final Map<String,UnitType> typesByCode = new HashMap<String,UnitType>();
    protected static final Map<String,UnitType> typesByProj4 = new HashMap<String,UnitType>();

    static {
          for(UnitType pt : EnumSet.allOf(UnitType.class)) {
              typesByCode.put(pt.code, pt);
              typesByProj4.put(pt.proj4String, pt);
          }

    }

    public static UnitType findByCode(String code) {
        return typesByCode.get(code);
    }

    public static UnitType findByProj4String(String units) {
        return typesByProj4.get(units);
    }

    UnitType(String code, String proj4) {
        this.code = code;
        this.proj4String = proj4;
    }

    public String getCode() {
        return code;
    }

    public String getProj4String() {
        return proj4String;
    }

}
