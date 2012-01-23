package com.gtproj4.crswkt2proj4;

import com.gtproj4.crswkt2proj4.projections.Proj;
import com.gtproj4.crswkt2proj4.projections.ProjType;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 1/10/12
 * Time: 10:23 PM
 */
public class Proj4TextParser {
    public Proj parse(String proj4text) throws ParseException {
        String[] pieces = proj4text.split("\\s+");
        String projCode = pieces[0].replaceAll("\\+proj=", "");
        ProjType pt = ProjType.findByCode(projCode);
        if (pt == null) return null;
        Proj rv = pt.getInstance();
        Map<String,String> proj4Pieces = new HashMap<String, String>();
        for (String p : pieces) {
            if (!p.startsWith("+")) throw new ParseException("Base proj4 string", proj4text.indexOf(p));
            p = p.substring(1);
            String[] subPieces = p.split("=");
            if (subPieces.length == 2) {
                proj4Pieces.put(subPieces[0], subPieces[1]);
            }
            else {
                proj4Pieces.put(subPieces[0], "");
            }
        }
        rv.initFromProj4String(proj4Pieces);
        return rv;
    }
}
