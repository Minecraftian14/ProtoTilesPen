package com.mcxiv.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.GdxUtil;

public class ProtoTiles {

    public static final Color INVISIBLE = new Color(0, 0, 0, 0f);

    private final int w;
    private final Color[][] pixels;

    private int pad = 5;
    private boolean transparency = false;

    public ProtoTiles(TextureRegion region) {
        this(GdxUtil.getPixmap(region));
    }

    public ProtoTiles(Texture texture) {
        this(GdxUtil.getPixmap(texture));
    }

    public ProtoTiles(Pixmap pixmap) {
        this(GdxUtil.getPixels(pixmap));
    }

    public ProtoTiles(Color[][] pixels) {
        this.pixels = ArrUtil.forceSquare(pixels);
        w = this.pixels.length;
    }

    public Color[][] getTop() {
        return pixels.clone();
    }

    public Color[][] get3Top() {
        double w = this.w;
        double h = this.w;
        Color[][] upperTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> (x / w + y / h <= (h + pad) / h) && (y <= x + pad));
        Color[][] upperLeftTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> (x / w + y / h <= (h + pad) / h) && (y < h / 2 + pad));
        Color[][] upperRightTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> (y <= x + pad) && (y < h / 2 + pad));
        return ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), ArrUtil.mix(upperTriangle, ArrUtil.rot90(upperRightTriangle))), Color.BLACK);
    }

    public Color[][] getTopLef() {
        double w = this.w;
        double h = this.w;
        Color[][] upperLeftTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> x / w + y / h <= (h + pad) / h);
        Color[][] upperRightTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> y <= x + pad);
        upperLeftTriangle = ArrUtil.deNull(upperLeftTriangle, INVISIBLE);
        upperRightTriangle = ArrUtil.deNull(upperRightTriangle, INVISIBLE);

        if (transparency) {
            int w2 = (int) (w / 2), h2 = (int) (h / 2);
            upperLeftTriangle = ArrUtil.gradient(upperLeftTriangle, w2, h2, w2 + pad, h2 + pad);
            upperRightTriangle = ArrUtil.gradient(upperRightTriangle, w2, h2, w2 - pad, h2 + pad);
        }

        return ArrUtil.deNull(ArrUtil.mix(ArrUtil.rot270(upperLeftTriangle), upperRightTriangle), Color.BLACK);
    }

    public Color[][] getAntiTopLef() {
        double w = this.w;
        double h = this.w;
        Color[][] lowerRightTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> x / w + y / h >= (h - pad) / h);
        Color[][] lowerLeftTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> y >= x - pad);
        return ArrUtil.deNull(ArrUtil.mix(lowerLeftTriangle, ArrUtil.rot270(lowerRightTriangle)), Color.BLACK);
    }

    public Color[][] getDoubleCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] quadrant123 = ArrUtil.maskByPredicate(getTopLef(), (x, y) -> (x < w + pad) || (y < h + pad));
        Color[][] quadrant4 = ArrUtil.maskByPredicate(getAntiTopLef(), (x, y) -> (x < w + pad) && (y < h + pad));
        return ArrUtil.deNull(ArrUtil.mix(quadrant123, ArrUtil.rot180(quadrant4)), Color.BLACK);
    }

    public Color[][] getTopFlatRigInnerCorner() {
        double h = this.w / 2.;
        Color[][] quadrant12 = ArrUtil.maskByPredicate(getTop(), (x, y) -> y < h + pad);
        Color[][] quadrant34 = ArrUtil.maskByPredicate(getAntiTopLef(), (x, y) -> y < h + pad);
        return ArrUtil.deNull(ArrUtil.mix(quadrant12, ArrUtil.rot180(quadrant34)), Color.BLACK);
    }

    public Color[][] getTopFlatLefInnerCorner() {
        return ArrUtil.flipAbtVer(getTopFlatRigInnerCorner());
    }

    public Color[][] getTopFlat_BotRigAndBotLefInnerCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] quadrant123 = ArrUtil.maskByPredicate(getTopFlatLefInnerCorner(), (x, y) -> (x < w + pad) || (y < h + pad));
        Color[][] quadrant4 = ArrUtil.maskByPredicate(getTopFlatRigInnerCorner(), (x, y) -> (x > w - pad) && (y > h - pad));
        return ArrUtil.deNull(ArrUtil.mix(quadrant123, quadrant4), Color.BLACK);
    }

    public Color[][] getBotRigAndBotLefInnerCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] antiTopLef = getAntiTopLef();
        Color[][] quadrant23 = ArrUtil.maskByPredicate(antiTopLef, (x, y) -> x < w + pad);
        Color[][] quadrant14 = ArrUtil.maskByPredicate(ArrUtil.rot90(antiTopLef), (x, y) -> x > w - pad);
        return ArrUtil.deNull(ArrUtil.rot180(ArrUtil.mix(quadrant23, quadrant14)), Color.BLACK);
    }

    public Color[][] getTopRigAndBotLefInnerCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] antiTopLef = getAntiTopLef();
        Color[][] quadrant23 = ArrUtil.flipAbtHor(ArrUtil.maskByPredicate(antiTopLef, (x, y) -> x < w + pad));
        Color[][] quadrant14 = ArrUtil.maskByPredicate(ArrUtil.rot90(antiTopLef), (x, y) -> x > w - pad);
        return ArrUtil.deNull(ArrUtil.rot180(ArrUtil.mix(quadrant23, quadrant14)), Color.BLACK);
    }

    public Color[][] getTopRig_TopLefAndBotLefInnerCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] quadrant12 = ArrUtil.flipAbtHor(ArrUtil.maskByPredicate(getBotRigAndBotLefInnerCorner(), (x, y) -> y > h - pad));
        Color[][] quadrant34 = ArrUtil.maskByPredicate(ArrUtil.rot270(getAntiTopLef()), (x, y) -> y > h - pad);
        return ArrUtil.deNull(ArrUtil.mix(quadrant12, quadrant34), Color.BLACK);
    }

    public Color[][] getAllInnerCorner() {
        double w = this.w / 2.;
        double h = this.w / 2.;
        Color[][] quadrant12 = ArrUtil.maskByPredicate(getBotRigAndBotLefInnerCorner(), (x, y) -> y > h - pad);
        Color[][] quadrant34 = ArrUtil.rot180(quadrant12);
        return ArrUtil.deNull(ArrUtil.mix(quadrant12, quadrant34), Color.BLACK);
    }

    public Color[][] getCenter() {
        int gap = (int) (w * 0.5);
        Color[][] bottom = ArrUtil.slice(pixels, 0, w - gap, w, gap);
        return ArrUtil.appendVertically(ArrUtil.rot180(bottom), bottom);
    }

    public Color[][] getTopBot() {
        int gap = (int) (w * 0.5);
        Color[][] top = ArrUtil.slice(pixels, 0, 0, w, gap);
        return ArrUtil.appendVertically(top, ArrUtil.rot180(top));
    }

    public Color[][] getAllFlat() {
        double w = this.w;
        double h = this.w;
        Color[][] upperTriangle = ArrUtil.maskByPredicate(pixels, (x, y) -> (x / w + y / h <= (h + pad) / h) && (y <= x + pad));
        return ArrUtil.deNull(
                ArrUtil.mix(
                        ArrUtil.mix(upperTriangle, ArrUtil.rot180(upperTriangle)),
                        ArrUtil.mix(ArrUtil.rot90(upperTriangle), ArrUtil.rot270(upperTriangle))
                ), Color.BLACK);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return w;
    }
}
