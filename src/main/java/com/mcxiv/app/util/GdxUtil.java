package com.mcxiv.app.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GdxUtil {

    public static Pixmap getPixmap(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

    public static Color[][] getPixels(Pixmap pixmap) {
        Color[][] pixels = new Color[pixmap.getWidth()][pixmap.getHeight()];
        for (int i = 0; i < pixmap.getWidth(); i++)
            for (int j = 0; j < pixmap.getHeight(); j++)
                pixels[i][j] = new Color(pixmap.getPixel(i, j));
        return pixels;
    }

    public static Texture getTexture(Color[][] pixels) {
        return new Texture(getPixmap(pixels));
    }

    public static TextureRegion getTextureRegion(Texture texture) {
        return new TextureRegion(texture);
    }

    public static Pixmap getPixmap(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;

        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = pixels[i][j];
                pixmap.setColor(c.r, c.g, c.b, c.a);
                pixmap.drawPixel(i, j);
            }
        }

        return pixmap;
    }

    public static TextureRegion getBlackTextureRegion(int s) {
        return getTextureRegion(getBlackTexture(s));
    }

    private static Texture getBlackTexture(int s) {
        return GdxUtil.getTexture(getBlackPixels(s));
    }

    private static Color[][] getBlackPixels(int s) {
        Color[][] pixels = new Color[s][s];
        for (int i = 0; i < s; i++) for (int j = 0; j < s; j++) pixels[i][j] = Color.BLACK;
        return pixels;
    }

    public static Pixmap getPixmap(TextureRegion textureRegion) {
        TextureData data = textureRegion.getTexture().getTextureData();
        if (!data.isPrepared()) data.prepare();

        int w = textureRegion.getRegionWidth();
        int h = textureRegion.getRegionHeight();

        Pixmap pixmap = new Pixmap(w, h, data.getFormat());

        pixmap.drawPixmap(
                data.consumePixmap(),
                0,
                0,
                textureRegion.getRegionX(),
                textureRegion.getRegionY(),
                w,
                h
        );
        return pixmap;
    }


}
