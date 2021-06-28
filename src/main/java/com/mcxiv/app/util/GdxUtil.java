package com.mcxiv.app.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class GdxUtil {

    public static Pixmap getPixmap(Texture texture) {
        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        return texture.getTextureData().consumePixmap();
    }

    public static Color[][] getPixels(Pixmap pixmap) {
        Color[][] pixels = new Color[pixmap.getWidth()][pixmap.getHeight()];
        for(int i = 0; i < pixmap.getWidth(); i++)
            for (int j = 0; j < pixmap.getHeight(); j++)
                pixels[i][j] = new Color(pixmap.getPixel(i, j));
        return pixels;
    }

    public static Texture getTexture(Color[][] pixels) {
        return new Texture(getPixmap(pixels));
    }

    public static Pixmap getPixmap(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;

        Pixmap pixmap = new Pixmap(w, h, Pixmap.Format.RGBA8888);
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                Color c = pixels[i][j];
                pixmap.setColor(c.r, c.g, c.b, c.a);
                pixmap.drawPixel(i, j);
            }
        }

        return pixmap;
    }
}
