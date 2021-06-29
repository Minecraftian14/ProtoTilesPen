package com.mcxiv.app.util;

import com.badlogic.gdx.graphics.Color;

public class ArrUtil {

    public static Color[][] rot90(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] newPix = new Color[h][w];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                newPix[h - j - 1][i] = newColor(pixels[i][j]);

        return newPix;
    }

    public static Color[][] rot180(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] newPix = new Color[w][h];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                newPix[w - i - 1][h - j - 1] = newColor(pixels[i][j]);

        return newPix;
    }

    public static Color[][] rot270(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] newPix = new Color[h][w];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                newPix[j][w - i - 1] = newColor(pixels[i][j]);

        return newPix;
    }

    public static Color[][] flipAbtVer(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] newPix = new Color[w][h];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                newPix[w - i - 1][j] = newColor(pixels[i][j]);

        return newPix;
    }

    public static Color[][] flipAbtHor(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] newPix = new Color[w][h];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                newPix[i][h - j - 1] = newColor(pixels[i][j]);

        return newPix;
    }

    public static Color[][] slice(Color[][] pixels, int x, int y, int sw, int sh) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] slice = new Color[sw][sh];

        // Converting them to coords instead of bounds (width and height)
        sw = x + sw;
        sh = y + sh;

        for (int i = x, k = 0; i < sw; i++, k++)
            for (int j = y, l = 0; j < sh; j++, l++)
                slice[k][l] = newColor(pixels[i][j]);

        return slice;
    }

    public static Color[][] appendVertically(Color[][] one, Color[][] two) {
        int wo = one.length, wt = two.length;
        int ho = one[0].length, ht = two[0].length;
        Color[][] result = new Color[Math.max(wo, wt)][ho + ht];

        for (int i = 0; i < wo; i++)
            for (int j = 0; j < ho; j++)
                result[i][j] = newColor(one[i][j]);

        for (int i = 0; i < wt; i++)
            for (int j = 0; j < ht; j++)
                result[i][ho + j] = newColor(two[i][j]);

        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < ho + ht; j++)
                if (result[i][j] == null)
                    result[i][j] = newColor(Color.BLACK);

        return result;
    }


    public static Color[][] maskByPredicate(Color[][] pixels, BiPredicate function) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] result = new Color[w][h];

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (function.eval(i, j))
                    result[i][j] = newColor(pixels[i][j]);

        return result;
    }

    public static Color[][] leftOr(Color[][] one, Color[][] two) {
        int wo = one.length, wt = two.length;
        int ho = one[0].length, ht = two[0].length;

        int w = Math.max(wo, wt);
        int h = Math.max(ho, ht);

        Color[][] result = new Color[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                try {
                    result[i][j] = newColor(two[i][j]);
                    if (result[i][j] == null) result[i][j] = newColor(one[i][j]);
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }

        return result;
    }

    public static Color[][] deNull(Color[][] pixels, Color value) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] result = new Color[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = newColor(pixels[i][j]);
                if (c == null) c = newColor(value);
                result[i][j] = c;
            }
        }

        return result;
    }

    public static Color[][] mix(Color[][] one, Color[][] two) {
        int wo = one.length, wt = two.length;
        int ho = one[0].length, ht = two[0].length;
        assert wo == wt && ho == ht : "No luck this time, the two images should have the same size.";

        Color[][] result = new Color[wo][ho];

        for (int i = 0; i < wo; i++) {
            for (int j = 0; j < ho; j++) {
                Color o = newColor(one[i][j]);
                Color t = newColor(two[i][j]);

                if (o == null) {
                    if (t != null) result[i][j] = t;
                } else {
                    if (t == null) result[i][j] = o;
//                    else result[i][j] = new Color((o.r + t.r) / 2, (o.g + t.g) / 2, (o.b + t.b) / 2, (o.a + t.a) / 2);
                    else {
                        float s = o.a + t.a;
                        result[i][j] = new Color((o.a * o.r + t.a * t.r) / s, (o.a * o.g + t.a * t.g) / s, (o.a * o.b + t.a * t.b) / s, Math.max(o.a, t.a));
                    }
                }
            }
        }

        return result;
    }

    public static Color[][] gradient(Color[][] pixels, int x1, int y1, int x2, int y2) {
        int w = pixels.length;
        int h = pixels[0].length;

        float m = (x1 - x2) / (float) (y2 - y1);
        float c1 = -m * x1 + y1;
        float c2 = -m * x2 + y2;
        float LA_of_B = m * x2 - y2 + c1;
        float LB_of_A = m * x1 - y1 + c2;

        BiPredicate LA_of_P = (x, y) -> Math.signum(m * x - y + c1) == Math.signum(LA_of_B);
        BiPredicate LB_of_P = (x, y) -> Math.signum(m * x - y + c2) == Math.signum(LB_of_A);

        float L2M = (float) Math.sqrt(m * m + 1);
        BiFunction distanceToLB = (x, y) -> Math.abs(m * x - y + c2) / L2M;

        float d = distanceToLB.eval(x1, y1);

        Color[][] result = new Color[w][h];

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = newColor(pixels[i][j]);
                if (LA_of_P.eval(i, j) && LB_of_P.eval(i, j)) {
                    c.a = distanceToLB.eval(i, j) / d;
                }
                result[i][j] = c;
            }
        }

        return result;
    }

    public static Color newColor(Color c) {
        if (c == null) return null;
        return new Color(c.r, c.g, c.b, c.a);
    }

    public static Color[][] forceSquare(Color[][] pixels) {
        int w = Math.min(pixels.length, pixels[0].length);
        Color[][] result = new Color[w][w];
        for (int i = 0; i < w; i++)
            for (int j = 0; j < w; j++)
                result[i][j] = newColor(pixels[i][j]);
        return result;
    }

    public interface BiPredicate {
        boolean eval(float x, float y);
    }

    public interface BiFunction {
        float eval(float x, float y);
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

}
