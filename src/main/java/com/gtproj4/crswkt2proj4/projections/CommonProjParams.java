package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.params.Datum;
import com.gtproj4.crswkt2proj4.params.Ellipsoid;
import com.gtproj4.crswkt2proj4.params.EllipsoidAxes;
import com.gtproj4.crswkt2proj4.params.Pm;
import org.geotools.referencing.crs.AbstractSingleCRS;
import org.geotools.referencing.datum.BursaWolfParameters;
import org.geotools.referencing.datum.DefaultGeodeticDatum;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.datum.GeodeticDatum;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 4:44 PM
 */
public class CommonProjParams extends ProjBase {
    private Datum datum = null;
    private Ellipsoid ellipsoid = null;
    private EllipsoidAxes eAxes = null;
    private Pm primeMerdian = Pm.Greenwich;
    private String toWgs84String = "";

    public CommonProjParams() {
        super();
    }

    public CommonProjParams(Datum datum,
                            Ellipsoid ellipsoid,
                            String toWgs84String,
                            EllipsoidAxes eAxes,
                            Pm pm) {
        super();
        this.datum = datum;
        this.ellipsoid = ellipsoid;
        this.eAxes = eAxes;
        this.primeMerdian = pm;
        this.toWgs84String = toWgs84String;
    }

    public CommonProjParams(CommonProjParams toCopy) {
        this(toCopy.getDatum(), toCopy.getEllipsoid(),
                toCopy.getToWgs84String(), toCopy.geteAxes(),
                toCopy.getPrimeMerdian());
        setType(toCopy.getType());
    }

    public Datum getDatum() {
        return datum;
    }

    public Ellipsoid getEllipsoid() {
        return ellipsoid;
    }

    public EllipsoidAxes geteAxes() {
        return eAxes;
    }

    public Pm getPrimeMerdian() {
        return primeMerdian;
    }

    public String getToWgs84String() {
        return toWgs84String;
    }

    public void setToWgs84String(String toWgs84String) {
        this.toWgs84String = toWgs84String;
    }

    public void setDatum(Datum datum) {
        this.datum = datum;
    }

    public void setEllipsoid(Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    public void seteAxes(EllipsoidAxes eAxes) {
        this.eAxes = eAxes;
    }

    public void setPrimeMerdian(Pm primeMerdian) {
        this.primeMerdian = primeMerdian;
    }

    protected String getProj4StringRest() {
        String rv = "";
        if (ellipsoid != null && ellipsoid.getProj4code().length() > 0) {
            rv = rv + " +ellps=" + ellipsoid.getProj4code();
        }
        if (datum != null && datum.getProj4code().length() > 0) {
            rv = rv + " +datum=" + datum.getProj4code();
        }
        if (eAxes != null) {
            rv = rv + " +a=" + eAxes.getSemiMajorString() + " +b=" + eAxes.getSemiMinorString();
        }
        if (toWgs84String != null && toWgs84String.length() > 0 && !toWgs84String.startsWith("414.1,")) {
            rv = rv + " +towgs84=" + toWgs84String;
        }
        if (primeMerdian != null && primeMerdian.getProj4String().length() > 0) {
            rv = rv + " +pm=" + primeMerdian.getProj4String();
        }
        return rv;
    }

    public void initFromProj4String(Map<String, String> proj4StringVals) {
        if (proj4StringVals.containsKey("ellps")) {
            ellipsoid = Ellipsoid.findByProj4Code(proj4StringVals.get("ellps"));
        }
        if (proj4StringVals.containsKey("datum")) {
            datum = Datum.findByProj4Code(proj4StringVals.get("datum"));
        }
        if (proj4StringVals.containsKey("a") && proj4StringVals.containsKey("b")) {
            eAxes = new EllipsoidAxes(proj4StringVals.get("a"), proj4StringVals.get("b"));
        }
        if (proj4StringVals.containsKey("pm")) {
            primeMerdian = Pm.findByProj4StringOrValue(proj4StringVals.get("pm"));
        }
        if (proj4StringVals.containsKey("towgs84")) {
            toWgs84String = proj4StringVals.get("towgs84");
        }
    }

    public boolean isCandidate(CoordinateReferenceSystem crs) {
        return AbstractSingleCRS.class.isAssignableFrom(crs.getClass())
                && DefaultGeodeticDatum.class.isAssignableFrom(((AbstractSingleCRS)crs).getDatum().getClass());
    }

    public void initFromCrs(CoordinateReferenceSystem crsIn, String wkt) throws ConversionException {
        AbstractSingleCRS crs = (AbstractSingleCRS) crsIn;
        DefaultGeodeticDatum geodeticDatum = (DefaultGeodeticDatum) crs.getDatum();
        String ellipseCode = geodeticDatum.getEllipsoid().getName().getCode();
        ellipsoid = Ellipsoid.findByCode(ellipseCode);
        eAxes = null;
        if (ellipsoid == null || ellipsoid.getProj4code().equals("")) {
            eAxes = new EllipsoidAxes(geodeticDatum.getEllipsoid().getSemiMajorAxis(),
                    geodeticDatum.getEllipsoid().getSemiMinorAxis());
        }
        String datumCode = geodeticDatum.getName().getCode();
        datum = null;
        try {
            datum = Datum.valueOf(datumCode);
        }
        catch (Exception ignore) {
        }
        String pmCode = geodeticDatum.getPrimeMeridian().getName().getCode();
        try {
            primeMerdian = Pm.valueOf(pmCode);
        }
        catch (IllegalArgumentException ea) {
            throw new ConversionException("Unknowm prime meridian: " + pmCode);
        }

        figureOutBursaWolfParams(crs,geodeticDatum);
    }

    private void figureOutBursaWolfParams(AbstractSingleCRS crs, GeodeticDatum geodeticDatum) {
        toWgs84String = "";
        if (datum == null || datum.getProj4code().length() == 0) {
            if (DefaultGeodeticDatum.class.isAssignableFrom(geodeticDatum.getClass())) {
                DefaultGeodeticDatum gdd = (DefaultGeodeticDatum) geodeticDatum;
                if (gdd.getBursaWolfParameters() != null && gdd.getBursaWolfParameters().length > 0) {
                    for (BursaWolfParameters bwp : gdd.getBursaWolfParameters()) {
                        if (bwp.targetDatum.getName().getCode().equals("WGS84")) {
                            toWgs84String = bwp.toString().replaceAll("TOWGS84\\[", "")
                                    .replaceAll("]", "").replaceAll("\\s+", "").replaceAll("\\.0,", ",")
                                    .replaceAll(".0$", "");
                            break;
                        }
                    }
                }
            }
        }
        if (toWgs84String.length() == 0) {
            if (crs.getName().getCode().equals("Amersfoort / RD New")) {
                setToWgs84String("565.237,50.0087,465.658,-0.406857,0.350733,-1.87035,4.0812");
            }
        }
    }
}
