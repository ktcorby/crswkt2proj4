package com.gtproj4.crswkt2proj4.projections;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:01 PM
 */
public enum ProjType {
    LAEA("laea", "Lambert_Azimuthal_Equal_Area", Lon0FromCentralMeridian.class),
    STEREA("sterea", "Oblique_Stereographic", Sterea.class),
    AEA("aea", "Albers_Conic_Equal_Area", Conic.class),
    CASS("cass", "Cassini_Soldner", Lon0FromCentralMeridian.class),
    EQC("eqc", "Plate_Carree", Eqc.class),
    UTM("utm", "Transverse_Mercator", Utm.class),
    LCC("lcc", "Lambert_Conformal_Conic", Lcc.class),
    TMERC("tmerc", "Transverse_Mercator", Tmerc.class),
    LONGLAT("longlat", "N/A", LongLat.class),
    POLY("poly", "Polyconic", Lon0FromCentralMeridian.class),
    STERE("stere", "Polar_Stereographic", Stere.class),
    OMERC("omerc", "Hotine_Oblique_Mercator", Omerc.class),
    MERC("merc", "Mercator_1SP", Merc.class),
    NZMG("nzmg", "New_Zealand_Map_Grid", Lon0FromCentralMeridian.class),
    Krovak("krovak", "Krovak", Krovak.class),
    SOMERC("somerc", "Hotine_Oblique_Mercator", Somerc.class);


    private String code;
    private String methodCode;
    private Class<? extends ProjBase> clazz;
    protected static final Map<String,ProjType> typesByCode = new HashMap<String,ProjType>();

    static {
          for(ProjType pt : EnumSet.allOf(ProjType.class)) {
              typesByCode.put(pt.code, pt);
          }

    }

    public static ProjType findByCode(String code) {
        return typesByCode.get(code);
    }

    ProjType(String code, String methodCode, Class<? extends ProjBase> clazz) {
        this.code = code;
        this.methodCode = methodCode;
        this.clazz = clazz;
    }

    public String getCode() {
        return code;
    }

    public String getMethodCode() {
        return methodCode;
    }

    @Override
    public String toString() {
        return code;
    }

    public ProjBase getInstance() {
        try {
            ProjBase rv = clazz.newInstance();
            rv.setType(this);
            return rv;
        } catch (InstantiationException ignore) {
        } catch (IllegalAccessException ignore) {
        }
        return null;
    }
}
