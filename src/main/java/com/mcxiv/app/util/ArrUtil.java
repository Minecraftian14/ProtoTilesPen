package com.mcxiv.app.util;

import com.mcxiv.app.util.functions.FloatFunction;
import com.mcxiv.util.InvertedSlopeConstantLine;
import com.mcxiv.util.Line;
import com.mcxiv.util.SlopeConstantLine;
import com.mcxiv.util.Vector2;

public class ArrUtil {

    private static Vector2 cache = new Vector2(0, 0);

    private static void getRandomPoint(Line line) {
        if (line instanceof SlopeConstantLine)
            cache.set(0, line.funcX(0));
        else if (line instanceof InvertedSlopeConstantLine)
            cache.set(line.funcY(0), 0);
    }

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


    public static Color[][] retainByPredicate(Color[][] pixels, BiPredicate function) {
        int w = pixels.length;
        int h = pixels[0].length;

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (!function.eval(i, j))
                    pixels[i][j] = Color.INVISIBLE;

        return pixels;
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

        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (pixels[i][j] == null) pixels[i][j] = value;

        return pixels;
    }

    public static Color[][] mix(Color[][] one, Color[][] two, Blending blender) {
        int wo = one.length, wt = two.length;
        int ho = one[0].length, ht = two[0].length;
        assert wo == wt && ho == ht : "No luck this time, the two images should have the same size.";

        Color[][] result = new Color[wo][ho];

        for (int i = 0; i < wo; i++) {
            for (int j = 0; j < ho; j++) {
                Color o = one[i][j];
                Color t = two[i][j];

                if (o == null) {
                    if (t == null) result[i][j] = newColor(Color.INVISIBLE);
                    else result[i][j] = newColor(t);

                } else {
                    if (t == null) result[i][j] = newColor(o);
                    else result[i][j] = blender.blend(o, t);
                }
            }
        }

        return result;
    }

    public static Color[][] mix(Color[][] one, Color[][] two, Color[][] thr, Blending blender) {
        return mix(one, mix(two, thr, blender), blender);
    }

    public static void fade(Color[][] pixels, FloatFunction interpolator, int x1, int y1, int x2, int y2) {
        int w = pixels.length;
        int h = pixels[0].length;

        Line lAB = Line.fromTwoPoints(x1, y1, x2, y2);

        Line lpA = lAB.perpendicularAt(x1, y1);
        Line lpB = lAB.perpendicularAt(x2, y2);

        float LA_of_B = lpA.put(x2, y2);
        float LB_of_A = lpB.put(x1, y1);

        float sign_LA_of_B = Math.signum(LA_of_B);
        float sign_LB_of_A = Math.signum(LB_of_A);

        float d = lpB.distance(x1, y1);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = pixels[i][j];
                if (Math.signum(lpA.put(i, j)) == sign_LA_of_B && Math.signum(lpB.put(i, j)) == sign_LB_of_A) {
                    float alp = lpB.distance(i, j) / d;
                    c.a = Math.min(interpolator.on(alp), c.a);
                }
            }
        }
    }

    public static void fade(Color[][] pixels, FloatFunction interpolator, Line from, Line to) {
        int x1, y1, x2, y2;
        getRandomPoint(from);
        Line a_perpendicular = from.perpendicularAt(cache.x, cache.y);
        a_perpendicular.getIntersection(cache, from);
        x1 = cache.x();
        y1 = cache.y();
        a_perpendicular.getIntersection(cache, to);
        x2 = cache.x();
        y2 = cache.y();
        fade(pixels, interpolator, x1, y1, x2, y2);
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

    public static Color[][] clonePixels(Color[][] pixels) {
        int w = pixels.length;
        int h = pixels[0].length;
        Color[][] result = new Color[w][h];
        for (int i = 0; i < w; i++) for (int j = 0; j < h; j++) result[i][j] = newColor(pixels[i][j]);
        return result;
    }

    public static Color[][] newColorArray(int w, int h) {
        return new Color[w][h];
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
