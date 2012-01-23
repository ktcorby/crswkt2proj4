package com.gtproj4.crswkt2proj4;

import com.gtproj4.crswkt2proj4.projections.Proj;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.junit.Test;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CRSFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 8:24 PM
 */
public class CrsWkt2Proj4ConverterTest {
    @Test
    public void testConverter() throws Throwable {
        CrsWkt2Proj4Converter cut = new CrsWkt2Proj4Converter();
        Proj4TextParser parser = new Proj4TextParser();
        cut.init();
        BufferedReader is = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/testproj.csv")));
        try {
            Set<String> priorityProjections = new HashSet<String>(Arrays.asList("longlat", "lcc", "utm", "tmerc", "cass", "aea", "sterea", "omerc", "merc", "laea",
                    "somerc", "poly", "stere", "eqc", "cea", "nzmg", "krovak"));
            Assert.assertEquals(17, priorityProjections.size());
            String line;
            int numDone = 0;
            int numSkipped = 0;
            CRSFactory factory = ReferencingFactoryFinder.getCRSFactory(null);
            while ((line = is.readLine()) != null) {
                String[] parts = line.trim().split(";");
                String wkt = parts[0];
                if (parts.length != 2) {
                    continue;
                }
                String proj4actual = parts[1];
                Proj fromParser = parser.parse(proj4actual);
                String proj4abbr = proj4actual.split("\\s+")[0] + " +nodefs";
                String proj4type = proj4abbr.replaceAll("\\+proj=", "").replaceAll(" \\+nodefs", "");
                if (priorityProjections.contains(proj4type)) {
                    try {
                        wkt = wkt.replaceAll("AXIS\\[\"Easting\",UNKNOWN\\],AXIS\\[\"Northing\",UNKNOWN\\]", "AXIS[\"Easting\",EAST],AXIS[\"Northing\",NORTH]");
                        wkt = wkt.replaceAll("AXIS\\[\"X\",UNKNOWN\\],AXIS\\[\"Y\",UNKNOWN\\]", "AXIS[\"X\",EAST],AXIS[\"Y\",NORTH]");
                        // If geotools can't parse it, we can't either
                        factory.createFromWKT(wkt);
                        Proj proj4gen = cut.convert(wkt);
                        Assert.assertEquals(Proj4Cleaner.clean(proj4actual), Proj4Cleaner.clean(proj4gen.toProj4String()));
                        Assert.assertEquals(Proj4Cleaner.clean(fromParser.toProj4String()), Proj4Cleaner.clean(proj4gen.toProj4String()));
                        numDone++;
                    }
                    catch (FactoryException e) {
                        numSkipped++;
                    }
                }
            }
            Assert.assertEquals(3716, numDone);
            Assert.assertEquals(3, numSkipped);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }
}
