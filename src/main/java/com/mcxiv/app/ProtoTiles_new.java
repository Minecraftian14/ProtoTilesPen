package com.mcxiv.app;

import com.mcxiv.app.util.AppUtil;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Color;
import com.mcxiv.app.util.functions.FloatFunction;

public class ProtoTiles_new {
    public static Color[][] getTopFlat(Color[][] raw) {
        return ArrUtil.clonePixels(raw);
    }

    public static Color[][] getTopCap(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w = raw.length;
        int h = raw[0].length;
        Color[][] upperTriangle = TileDissector.getTopTri(fade, fadeLength, raw, interpolator, true, true);
        Color[][] upperLeftTriangle = TileDissector.getTopLefPoly(w / 2, h / 2, fade, fadeLength, raw, interpolator, true, true);
        Color[][] upperRightTriangle = TileDissector.getTopRigPoly(w / 2, h / 2, fade, fadeLength, raw, interpolator, true, true);

        return ArrUtil.deNull(ArrUtil.mix(
                ArrUtil.rot270(upperLeftTriangle),
                upperTriangle,
                ArrUtil.rot90(upperRightTriangle)
        ), Color.BLACK);
    }

    public static Color[][] getTopLefCor(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w = raw.length;
        Color[][] upperLeftTriangle = TileDissector.getTopLefTri(w, fade, fadeLength, raw, interpolator, true, true);
        Color[][] upperRightTriangle = TileDissector.getTopRigTri(w, fade, fadeLength, raw, interpolator, true, true);
        return ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), upperRightTriangle), Color.BLACK);
    }


    public static Color[][] getTopLefInnerCor(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w = raw.length;
        Color[][] lowerLeftTriangle = TileDissector.getBotLefTri(w, fade, fadeLength, raw, interpolator, true, true);
        Color[][] lowerRightTriangle = TileDissector.getBotRigTri(w, fade, fadeLength, raw, interpolator, true, true);
        return ArrUtil.deNull(ArrUtil.mix(lowerLeftTriangle, ArrUtil.rot270(lowerRightTriangle)), Color.BLACK);
    }

    public static Color[][] getTopLefDoubleCor(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        double w = raw.length;
        int w2 = (int) (raw.length / 2.);
        Color[][] buf = getTopLefCor(fade, fadeLength, raw, interpolator);
        Color[][] segmentA = TileDissector.getTopRigPoly(w2, w2, fade, fadeLength, buf, interpolator, true, true);
        Color[][] segmentB = TileDissector.getLefBotPoly(w2, w2, fade, fadeLength, buf, interpolator, true, true);
        buf = getTopLefInnerCor(fade, fadeLength, raw, interpolator);
        Color[][] segmentC = TileDissector.getTopLefRect(w2, w2, fade, fadeLength, buf, interpolator, true, true);

        return ArrUtil.deNull(ArrUtil.mix(
                segmentA,
                segmentB,
                ArrUtil.rot180(segmentC)
        ), Color.BLACK);
    }

    public static Color[][] getTopFlatRigInnerCorner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        Color[][] segmentA = TileDissector.getTopRect(w2, fade, fadeLength, raw, interpolator, true, true);
        raw = getTopLefInnerCor(fade, fadeLength, raw, interpolator);
        Color[][] segmentB = TileDissector.getTopRect(w2, fade, fadeLength, raw, interpolator, true, true);

        return ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot180(segmentB)), Color.BLACK);
    }

    public static Color[][] getTopFlatLefInnerCorner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        Color[][] segmentA = TileDissector.getTopRect(w2, fade, fadeLength, raw, interpolator, true, true);
        raw = getTopLefInnerCor(fade, fadeLength, raw, interpolator);
        Color[][] segmentB = TileDissector.getLefRect(w2, fade, fadeLength, raw, interpolator, true, true);
        return ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot270(segmentB)), Color.BLACK);
    }


    public static Color[][] getTopFlatOppoInner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        Color[][] segmentA = TileDissector.getTopRect(w2, fade, fadeLength, raw, interpolator, true, true);
        Color[][] buf = getTopFlatLefInnerCorner(fade, fadeLength, raw, interpolator);
        Color[][] segmentB = TileDissector.getBotLefRect(w2, w2, fade, fadeLength, buf, interpolator, true, true);
        buf = getTopFlatRigInnerCorner(fade, fadeLength, raw, interpolator);
        Color[][] segmentC = TileDissector.getBotRigRect(w2, w2, fade, fadeLength, buf, interpolator, true, true);
        return ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB, segmentC), Color.BLACK);
    }

    public static Color[][] getTopOppoInner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        Color[][] segmentA = TileDissector.getBotRect(w2, fade, fadeLength, raw, interpolator, true, true);
        raw = getTopFlatOppoInner(fade, fadeLength, raw, interpolator);
        Color[][] segmentB = TileDissector.getBotRect(w2, fade, fadeLength, raw, interpolator, true, true);
        return ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot180(segmentA), segmentB), Color.BLACK);
    }

    public static Color[][] getTopLefAndBotRigInnerCorner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        raw = getTopLefInnerCor(fade, fadeLength, raw, interpolator);
        Color[][] segmentA = TileDissector.getLefRect(w2,fade, fadeLength, raw, interpolator, true, true);
        Color[][] segmentB = ArrUtil.rot180(segmentA);
        return ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB), Color.BLACK);
    }

    public static Color[][] getExceptTopLefInnerCorner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w = raw.length;
        Color[][] topOppoInner = ArrUtil.rot180(getTopOppoInner(fade, fadeLength, raw, interpolator));
        Color[][] upperLeftTriangle = TileDissector.getTopLefTri(w,fade, fadeLength, topOppoInner, interpolator, true, true );
        Color[][] upperRightTriangle = TileDissector.getTopRigTri(w,fade, fadeLength, topOppoInner, interpolator, true, true );
        return ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), upperRightTriangle), Color.BLACK);
    }

    public static Color[][] getAllInnerCorner(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        raw = getTopOppoInner(fade, fadeLength, raw, interpolator);
        Color[][] segmentA = TileDissector.getBotRect(w2, fade, fadeLength, raw, interpolator, true, true );
        return ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot180(segmentA)), Color.BLACK);
    }

    public static Color[][] getCenter(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator) {
        int w2 = (int) (raw.length / 2.);
        Color[][] segmentA = TileDissector.getBotRect(w2, fade, fadeLength, raw, interpolator, true, true );
        return ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot180(segmentA)), Color.BLACK);
    }


/*
    public Color[][] getTopBotFlat() {
        if (topBotFlat != null) return topBotFlat;
        double w = this.size / 2.;
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y < w + pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 - pad);
        }

        Color[][] segmentB = ArrUtil.rot180(segmentA);

        return topBotFlat = ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB), Color.BLACK);
    }

    private Color[][] allFlat;

    public Color[][] getAllFlat() {
        if (allFlat != null) return allFlat;
        double w = this.size;
        Color[][] upperTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> (x / w + y / w <= (w + pad) / w) && (y <= x + pad));

        if (transparency) {
            ArrUtil.deNull(upperTriangle, Color.INVISIBLE);
            int iw2 = (int) (w / 2.);
            ArrUtil.fade(upperTriangle, iw2, iw2, iw2 + pad, iw2 + pad);
            ArrUtil.fade(upperTriangle, iw2, iw2, iw2 - pad, iw2 + pad);
        }

        return allFlat = ArrUtil.deNull(
                ArrUtil.mix(
                        ArrUtil.mix(upperTriangle, ArrUtil.rot180(upperTriangle)),
                        ArrUtil.mix(ArrUtil.rot90(upperTriangle), ArrUtil.rot270(upperTriangle))
                ), Color.BLACK);
//        return ArrUtil.deNull(upperTriangle, Color.BLACK);
    }

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return size;
    }
*/
}
