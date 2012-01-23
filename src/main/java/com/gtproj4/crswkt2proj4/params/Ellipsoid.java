package com.gtproj4.crswkt2proj4.params;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 11:11 PM
 */
public enum Ellipsoid {
    Average_Terrestrial_System_1977("Average Terrestrial System 1977", ""),
    Clarke_1880_Arc("Clarke 1880 (Arc)", ""),
    Clarke_1880_IGN("Clarke 1880 (IGN)", ""),
    Everest_1830_1937_Adjustment("Everest 1830 (1937 Adjustment)", ""),
    Everest_1830_1962_Definition("Everest 1830 (1962 Definition)", ""),
    Everest_1830_1975_Definition("Everest 1830 (1975 Definition)", ""),
    Everest_1830_Modified("Everest 1830 Modified", ""),
    Indonesian_National_Spheroid("Indonesian National Spheroid", ""),
    Australian_National_Spheroid("Australian National Spheroid", "aust_SA"),
    Bessel_1841("Bessel 1841", "bessel"),
    Bessel_Namibia_GLM("Bessel Namibia (GLM)", "bess_nam"),
    Clarke_1866("Clarke 1866", "clrk66"),
    Clarke_1880_RGS("Clarke 1880 (RGS)", "clrk80"),
    Everest_1830_1967_Definition("Everest 1830 (1967 Definition)", "evrstSS"),
    GRS_1967("GRS 1967", "GRS67"),
    GRS_1967_Modified("GRS 1967 Modified", "aust_SA"),
    GRS_1980("GRS 1980", "GRS80"),
    Helmert_1906("Helmert 1906", "helmert"),
    International_1924("International 1924", "intl"),
    Krassowsky_1940("Krassowsky 1940", "krass"),
    WGS_72("WGS 72", "WGS72"),
    WGS_84("WGS 84", "WGS84"),
    WGS_66("NWL 9D", "WGS66"),
    GEM_10C("GEM 10C", "WGS84"),
    Airy_1830("Airy 1830", "airy");

    private String code;
    private String proj4code;

    protected static final Map<String,Ellipsoid> ellpsByCode = new HashMap<String,Ellipsoid>();
    protected static final Map<String,Ellipsoid> ellpsByProj4 = new HashMap<String,Ellipsoid>();

    static {
          for(Ellipsoid ell : EnumSet.allOf(Ellipsoid.class)) {
              ellpsByCode.put(ell.code, ell);
              ellpsByProj4.put(ell.proj4code, ell);
          }

    }

    public static Ellipsoid findByCode(String code) {
        return ellpsByCode.get(code);
    }

    public static Ellipsoid findByProj4Code(String code) {
        return ellpsByProj4.get(code);
    }

    public String getProj4code() {
        return proj4code;
    }

    Ellipsoid(String code, String proj4code) {
        this.code = code;
        this.proj4code = proj4code;
    }

}
