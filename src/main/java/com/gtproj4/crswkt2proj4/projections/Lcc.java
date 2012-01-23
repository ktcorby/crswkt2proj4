package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import java.util.Map;

import static com.gtproj4.crswkt2proj4.etc.DoubleVal.eq;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:06 PM
 */
public class Lcc extends Conic {
    private Double k_0;

    public Lcc() {
        super();
        setType(ProjType.LCC);
    }

    public Lcc(Conic toCopy,
               Double k_0) {
        super(toCopy);
        setType(ProjType.LCC);
        this.k_0 = k_0;
    }

    public Double getK_0() {
        return k_0;
    }

    public void setK_0(Double k_0) {
        this.k_0 = k_0;
    }

    @Override
    protected String getProj4StringRestConic() {
        String rv = super.getProj4StringRestConic();
        if (k_0 != null) rv = rv + " +k_0=" + DoubleVal.format(k_0);
        return rv;
    }

    @Override
    public void initFromProj4String(Map<String, String> proj4StringVals) {
        if (proj4StringVals.containsKey("k_0")) {
            k_0 = Double.parseDouble(proj4StringVals.get("k_0"));
        }
        super.initFromProj4String(proj4StringVals);
    }

    @Override
    protected void initFromCrs(ProjectedCRS crs, String origText, Projection pr) throws ConversionException {
        super.initFromCrs(crs, origText, pr);
        k_0 = null;
        try {
            k_0 = pr.getParameterValues().parameter("scale_factor").doubleValue();
            if (eq(k_0, 1.0) && !origText.contains("scale_factor")) {
                k_0 = null;
            }
        }
        catch (Exception ignore) {
        }
    }
}
