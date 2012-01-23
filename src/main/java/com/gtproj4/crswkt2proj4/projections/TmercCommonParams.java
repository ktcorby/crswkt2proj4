package com.gtproj4.crswkt2proj4.projections;

import com.gtproj4.crswkt2proj4.ConversionException;
import com.gtproj4.crswkt2proj4.etc.DoubleVal;
import com.gtproj4.crswkt2proj4.params.UnitType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.crs.ProjectedCRS;
import org.opengis.referencing.operation.Projection;

import javax.measure.converter.MultiplyConverter;
import javax.measure.unit.TransformedUnit;
import javax.measure.unit.Unit;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/11/12
 * Time: 12:56 PM
 */
public class TmercCommonParams extends CommonProjParams {
    private double meterFactor = -1.0;
    private UnitType unitType = UnitType.METER;

    public TmercCommonParams() {
        super();
    }

    public TmercCommonParams(CommonProjParams commonParams,
                             double meterFactor,
                             UnitType ut) {
        super(commonParams);
        this.meterFactor = meterFactor;
        this.unitType = ut;
    }

    public TmercCommonParams(TmercCommonParams toCopy) {
        this(toCopy, toCopy.meterFactor, toCopy.getUnitType());
    }

    public double getMeterFactor() {
        return meterFactor;
    }

    public void setMeterFactor(double meterFactor) {
        this.meterFactor = meterFactor;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public int getPriority() {
        return 2;
    }

    protected String getMethodCode(CoordinateReferenceSystem crs) {
        ProjectedCRS pcrs = (ProjectedCRS) crs;
        Projection fromBase = pcrs.getConversionFromBase();
        return fromBase.getMethod().getName().getCode();
    }

    protected void initFromCrs(ProjectedCRS crs, String wkt, Projection convFromBase) throws ConversionException {
        super.initFromCrs(crs, wkt);
        if (crs.getName().getCode().equals("RT90 2.5 gon V")) {
            setToWgs84String("414.1,41.3,603.1,-0.855,2.141,-7.023,0");
        }
        meterFactor = -1.0;
        Unit<?> unit = crs.getCoordinateSystem().getAxis(1).getUnit();
        unitType = UnitType.findByCode(unit.toString());
        if (unitType == null) {
            unitType = UnitType.METER;
            if (TransformedUnit.class.isAssignableFrom(unit.getClass())) {
                TransformedUnit<?> tu = (TransformedUnit<?>) unit;
                if (MultiplyConverter.class.isAssignableFrom(tu.toParentUnit().getClass())) {
                    MultiplyConverter mc = (MultiplyConverter) tu.toParentUnit();
                    meterFactor = mc.getFactor();
                }
                else {
                    throw new ConversionException("Found a unit conversion we can't handle: " + tu);
                }
            }
            else {
                throw new ConversionException("Unidentified unit: " + unit);
            }
        }
    }

    @Override
    public void initFromCrs(CoordinateReferenceSystem crs, String wkt) throws ConversionException {
        ProjectedCRS pcrs = (ProjectedCRS) crs;
        Projection fromBase = pcrs.getConversionFromBase();
        initFromCrs(pcrs, wkt, fromBase);
    }

    @Override
    public boolean isCandidate(CoordinateReferenceSystem crs) {
        return ProjectedCRS.class.isAssignableFrom(crs.getClass()) && getMethodCode(crs).startsWith(getType().getMethodCode());
    }

    protected String getProj4StringRest() {
        String rv = super.getProj4StringRest();
        if (meterFactor > 0.0) {
            rv = rv + " +to_meter=" + DoubleVal.format(meterFactor);
        }
        else if (unitType != null) {
            rv = rv + " +units=" + unitType.getProj4String();
        }
        if (getToWgs84String() != null && getToWgs84String().length() > 0 && getToWgs84String().startsWith("414.1,")) {
            rv = rv + " +towgs84=" + getToWgs84String();
        }
        return rv;
    }

    public void initFromProj4String(Map<String, String> proj4StringVals) {
        super.initFromProj4String(proj4StringVals);
        if (proj4StringVals.containsKey("to_meter")) {
            meterFactor = Double.parseDouble(proj4StringVals.get("to_meter"));
        }
        if (proj4StringVals.containsKey("units")) {
            unitType = UnitType.findByProj4String(proj4StringVals.get("units"));
        }
    }
}
