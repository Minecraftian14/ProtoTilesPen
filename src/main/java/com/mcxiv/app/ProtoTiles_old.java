package com.mcxiv.app;

import com.mcxiv.app.util.AppUtil;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Color;

@Deprecated
public class ProtoTiles_old {

    private final int size;
    private final Color[][] originalPixels;

    private int pad;
    private boolean transparency;

    public ProtoTiles_old(Color[][] pixels) {
        this(pixels, 20, true);
    }

    public ProtoTiles_old(Color[][] pixels, int pad, boolean transparency) {
        this.originalPixels = ArrUtil.forceSquare(pixels);
        this.size = originalPixels.length;
        this.pad = pad;
        this.transparency = transparency;
    }

    public void setPadding(int pad) {
        this.pad = pad;
    }

    public void setTransparencyEnabled(boolean transparency) {
        this.transparency = transparency;
    }

    private Color[][] topFlat;

    public Color[][] getTopFlat() {
        if (topFlat != null) return topFlat;
        return topFlat = ArrUtil.clonePixels(originalPixels);
//        return dissector.getRigRect(size / 2, transparency, pad, false);
//        return TileDissector.getTopRigTri(size / 2,size / 2, transparency, pad, originalPixels, AppUtil::intr, true, true);
    }

    private Color[][] topCap;

    public Color[][] getTopCap() {
        if (topCap != null) return topCap;

        double w = this.size;
        // points = top-left + top-right + center
        Color[][] upperTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> x + y <= w + pad && (y <= x + pad));
        // points = top-left + top-right + center + midpoint-or-left-side
        Color[][] upperLeftTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> (x + y <= (w + pad)) && (y < w / 2 + pad));
        // points = top-left + top-right + midpoint-or-right-side + center
        Color[][] upperRightTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> (y <= x + pad) && (y < w / 2 + pad));

        if (transparency) {
            ArrUtil.deNull(upperTriangle, Color.INVISIBLE);
            ArrUtil.deNull(upperLeftTriangle, Color.INVISIBLE);
            ArrUtil.deNull(upperRightTriangle, Color.INVISIBLE);

            int w2 = (int) (w / 2);

            ArrUtil.fade(upperTriangle, w2, w2, w2 + pad, w2 + pad);
            ArrUtil.fade(upperTriangle, w2, w2, w2 - pad, w2 + pad);

            ArrUtil.fade(upperLeftTriangle, w2, w2, w2 + pad, w2 + pad);
            ArrUtil.fade(upperLeftTriangle, w2, w2, w2, w2 + pad);

            ArrUtil.fade(upperRightTriangle, w2, w2, w2 - pad, w2 + pad);
            ArrUtil.fade(upperRightTriangle, w2, w2, w2, w2 + pad);
        }

        return topCap = ArrUtil.deNull(ArrUtil.mix(
                ArrUtil.rot270(upperLeftTriangle),
                upperTriangle,
                ArrUtil.rot90(upperRightTriangle)
        ), Color.BLACK);
    }

    private Color[][] topLefCor;

    public Color[][] getTopLefCor() {
        if (topLefCor != null) return topLefCor;
        double w = this.size;
        double h = this.size;
        // points = top-left + top-right + bot-left
        Color[][] upperLeftTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> x / w + y / h <= (h + pad) / h);
        // points = top-left + top-right + bot-right
        Color[][] upperRightTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> y <= x + pad);


        if (transparency) {
            ArrUtil.deNull(upperLeftTriangle, Color.INVISIBLE);
            ArrUtil.deNull(upperRightTriangle, Color.INVISIBLE);

            int w2 = (int) (w / 2), h2 = (int) (h / 2);
            ArrUtil.fade(upperLeftTriangle, w2, h2, w2 + pad, h2 + pad);
            ArrUtil.fade(upperRightTriangle, w2, h2, w2 - pad, h2 + pad);
        }

        return topLefCor = ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), upperRightTriangle), Color.BLACK);
    }

    private Color[][] topLefInnerCor;

    public Color[][] getTopLefInnerCor() {
        if (topLefInnerCor != null) return topLefInnerCor;
        double w = this.size;
        double h = this.size;

        Color[][] lowerRightTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> x / w + y / h >= (h - pad) / h);
        Color[][] lowerLeftTriangle = ArrUtil.maskByPredicate(originalPixels, (x, y) -> y >= x - pad);

        if (transparency) {
            ArrUtil.deNull(lowerRightTriangle, Color.INVISIBLE);
            ArrUtil.deNull(lowerLeftTriangle, Color.INVISIBLE);

            int w2 = (int) (w / 2), h2 = (int) (h / 2);
            ArrUtil.fade(lowerRightTriangle, w2, h2, w2 - pad, h2 - pad);
            ArrUtil.fade(lowerLeftTriangle, w2, h2, w2 + pad, h2 - pad);
        }

        return topLefInnerCor = ArrUtil.deNull(ArrUtil.mix(lowerLeftTriangle, ArrUtil.rot270(lowerRightTriangle)), Color.BLACK);
    }

    private Color[][] topLefDoubleCor;

    public Color[][] getTopLefDoubleCor() {
        if (topLefDoubleCor != null) return topLefDoubleCor;
        double w2 = this.size / 2.;
        double w = this.size;
        // points = top-left + top-right + right-mid + center
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopLefCor(), (x, y) -> (x > y - pad) && (y < w2 + pad));
        // points = top-left + center + bot-mid + bot-left
        Color[][] segmentB = ArrUtil.maskByPredicate(getTopLefCor(), (x, y) -> (x < y + pad) && (x < w2 + pad));
        // points = top-left + top-mid + center + left-mid
        Color[][] segmentC = ArrUtil.maskByPredicate(getTopLefInnerCor(), (x, y) -> (x < w2 + pad) && (y < w2 + pad));

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            ArrUtil.deNull(segmentB, Color.INVISIBLE);
            ArrUtil.deNull(segmentC, Color.INVISIBLE);

            int iw2 = (int) w2;

            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 + pad);
            ArrUtil.fade(segmentA, iw2, iw2, iw2 - pad, iw2 + pad);

            ArrUtil.fade(segmentB, iw2, iw2, iw2 + pad, iw2 - pad);
            ArrUtil.fade(segmentB, iw2, iw2, iw2 + pad, iw2);

            ArrUtil.fade(segmentC, iw2, iw2, iw2, iw2 + pad);
            ArrUtil.fade(segmentC, iw2, iw2, iw2 + pad, iw2);
        }

        return topLefDoubleCor = ArrUtil.deNull(ArrUtil.mix(
                segmentA,
                segmentB,
                ArrUtil.rot180(segmentC)
        ), Color.BLACK);
    }

    private Color[][] topFlatRigInner;

    public Color[][] getTopFlatRigInnerCorner() {
        if (topFlatRigInner != null) return topFlatRigInner;
        double w = this.size / 2.;
        // points = top-left + top-right + right-mid + left-mid
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y < w + pad);
        // points = top-left + top-right + right-mid + left-mid
        Color[][] segmentB = ArrUtil.maskByPredicate(getTopLefInnerCor(), (x, y) -> y < w + pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            ArrUtil.deNull(segmentB, Color.INVISIBLE);

            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 + pad);
            ArrUtil.fade(segmentB, iw2, iw2, iw2, iw2 + pad);
        }

        return topFlatRigInner = ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot180(segmentB)), Color.BLACK);
    }

    private Color[][] topFlatLefInner;

    public Color[][] getTopFlatLefInnerCorner() {
        if (topFlatLefInner != null) return topFlatLefInner;
        double w = this.size / 2.;
        // points = top-left + top-right + right-mid + left-mid
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y < w + pad);
        // points = top-left + top-right + right-mid + left-mid
        Color[][] segmentB = ArrUtil.maskByPredicate(getTopLefInnerCor(), (x, y) -> x < w + pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            ArrUtil.deNull(segmentB, Color.INVISIBLE);

            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 + pad);
            ArrUtil.fade(segmentB, iw2, iw2, iw2 + pad, iw2);
        }

        return topFlatLefInner = ArrUtil.deNull(ArrUtil.mix(segmentA, ArrUtil.rot270(segmentB)), Color.BLACK);
    }

    Color[][] topFlatOppoInner;

    public Color[][] getTopFlatOppoInner() {
        if (topFlatOppoInner != null) return topFlatOppoInner;
        double w = this.size / 2.;
        // points = top-left + top-right + right-mid + left-mid
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y < w + pad);
        // points = left-mid + center + bot-mid + bot-left
        Color[][] segmentB = ArrUtil.maskByPredicate(getTopFlatLefInnerCorner(), (x, y) -> (x < w + pad) && (y > w - pad));
        // points = center + rig-mid + bot-right + bot-mid
        Color[][] segmentC = ArrUtil.maskByPredicate(getTopFlatRigInnerCorner(), (x, y) -> (x > w - pad) && (y > w - pad));

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            ArrUtil.deNull(segmentB, Color.INVISIBLE);
            ArrUtil.deNull(segmentC, Color.INVISIBLE);

            int iw2 = (int) w;

            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 + pad);

            ArrUtil.fade(segmentB, iw2, iw2, iw2 + pad, iw2);
            ArrUtil.fade(segmentB, iw2, iw2, iw2, iw2 - pad);

            ArrUtil.fade(segmentC, iw2, iw2, iw2 - pad, iw2);
            ArrUtil.fade(segmentC, iw2, iw2, iw2, iw2 - pad);
        }

        return topFlatOppoInner = ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB, segmentC), Color.BLACK);
    }

    private Color[][] topOppoInner;

    public Color[][] getTopOppoInner() {
        if (topOppoInner != null) return topOppoInner;
        double w = this.size / 2.;
        // points = lef-mid + rig-mid + bot-rig + bot-lef
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y > w - pad);
        // points = lef-mid + rig-mid + bot-rig + bot-lef
        Color[][] segmentB = ArrUtil.maskByPredicate(getTopFlatOppoInner(), (x, y) -> y > w - pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            ArrUtil.deNull(segmentB, Color.INVISIBLE);

            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 - pad);
            ArrUtil.fade(segmentB, iw2, iw2, iw2, iw2 - pad);
        }

        return topOppoInner = ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot180(segmentA), segmentB), Color.BLACK);
    }

    private Color[][] topLefAndBotRigInner;

    public Color[][] getTopLefAndBotRigInnerCorner() {
        if (topLefAndBotRigInner != null) return topLefAndBotRigInner;
        double w = this.size / 2.;
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopLefInnerCor(), (x, y) -> x < w + pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2 + pad, iw2);
        }

        Color[][] segmentB = ArrUtil.rot180(segmentA);

        return topLefAndBotRigInner = ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB), Color.BLACK);
    }

    private Color[][] exceptTopLefInner;

    public Color[][] getExceptTopLefInnerCorner() {
        if (exceptTopLefInner != null) return exceptTopLefInner;
        double w = this.size;
        Color[][] topOppoInner = ArrUtil.rot180(getTopOppoInner());
        // points = top-left + top-right + bot-left
        Color[][] upperLeftTriangle = ArrUtil.maskByPredicate(topOppoInner, (x, y) -> x / w + y / w <= (w + pad) / w);
        // points = top-left + top-right + bot-right
        Color[][] upperRightTriangle = ArrUtil.maskByPredicate(topOppoInner, (x, y) -> y <= x + pad);


        if (transparency) {
            ArrUtil.deNull(upperLeftTriangle, Color.INVISIBLE);
            ArrUtil.deNull(upperRightTriangle, Color.INVISIBLE);

            int w2 = (int) (w / 2);
            ArrUtil.fade(upperLeftTriangle, w2, w2, w2 + pad, w2 + pad);
            ArrUtil.fade(upperRightTriangle, w2, w2, w2 - pad, w2 + pad);
        }

        return exceptTopLefInner = ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), upperRightTriangle), Color.BLACK);
    }

    private Color[][] allInner;

    public Color[][] getAllInnerCorner() {
        if (allInner != null) return allInner;
        double w = this.size / 2.;
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopOppoInner(), (x, y) -> y > w - pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 - pad);
        }

        Color[][] segmentB = ArrUtil.rot180(segmentA);

        return allInner = ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB), Color.BLACK);
    }

    private Color[][] center;

    public Color[][] getCenter() {
        if (center != null) return center;
        double w = this.size / 2.;
        Color[][] segmentA = ArrUtil.maskByPredicate(getTopFlat(), (x, y) -> y > w - pad);

        if (transparency) {
            ArrUtil.deNull(segmentA, Color.INVISIBLE);
            int iw2 = (int) w;
            ArrUtil.fade(segmentA, iw2, iw2, iw2, iw2 - pad);
        }

        Color[][] segmentB = ArrUtil.rot180(segmentA);

        return center = ArrUtil.deNull(ArrUtil.mix(segmentA, segmentB), Color.BLACK);
    }

    private Color[][] topBotFlat;

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
}
