package com.mcxiv.app;

import com.mcxiv.app.util.AppUtil;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Color;
import com.mcxiv.app.util.functions.FloatFunction;
import com.mcxiv.util.Line;

public class TileDissector {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// RECTANGLES WHICH ARE ALIGNED BY THE SIDES TOUCHING THREE SIDES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="RECTANGLES WHICH ARE ALIGNED BY THE SIDES TOUCHING THREE SIDES">;

    /**
     * @param height The height from top of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped like a rectangle of the same width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopRect(int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }



    /**
     * @param height The height from bottom of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped like a rectangle of the same width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotRect(int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl - height - fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in - height, 0, h_in - height - fadeLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }



    /**
     * @param width The width from left of original image to be used as the width of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the left part of original pixels cropped like a rectangle of the same height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getLefRect(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        ArrUtil.retainByPredicate(raw, (x, y) -> x < width + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width from right of original image to be used as the width of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the right part of original pixels cropped like a rectangle of the same height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRigRect(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        ArrUtil.retainByPredicate(raw, (x, y) -> x > w_fl - width - fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// RECTANGLES WHICH LYE IN BETWEEN THE IMAGE BUT TOUCHES TWO OPPOSITE SIDES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="RECTANGLES WHICH LYE IN BETWEEN THE IMAGE BUT TOUCHES TWO OPPOSITE SIDES">;

    /**
     * @param h_abv height_above: The height above the middle of original image to be summed with h_btw and be used as the height of the rectangle to be cropped.
     * @param h_blw height_below: The height below the middle of original image to be summed with h_abv and be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle with full width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getHorRect(int h_abv, int h_blw, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        int h_in_2 = (int) (h_fl / 2);
        float h_fl_2 = h_fl / 2;
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl_2 - h_abv - fadeLength && y < h_fl_2 + h_blw + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in_2 - h_abv, 0, h_in_2 - h_abv - fadeLength);
            ArrUtil.fade(raw, interpolator, 0, h_in_2 + h_blw, 0, h_in_2 + h_blw + fadeLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param height The total height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle with full width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getHorRect(int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        return getHorRect(height / 2, height / 2, fade, fadeLength, raw, interpolator, clone, deNull);
    }

    /**
     * @param w_lef width_left : The width left of the middle of original image to be summed with w_rig and be used as the width of the rectangle to be cropped.
     * @param w_rig width_right: The width right of the middle of original image to be summed with w_lef and be used as the width of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle with full height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getVerRect(int w_lef, int w_rig, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        int w_in_2 = (int) (w_fl / 2);
        float w_fl_2 = w_fl / 2;
        ArrUtil.retainByPredicate(raw, (x, y) -> x > w_fl_2 - w_lef - fadeLength && x < w_fl_2 + w_rig + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in_2 - w_lef, 0, w_in_2 - w_lef - fadeLength, 0);
            ArrUtil.fade(raw, interpolator, w_in_2 + w_rig, 0, w_in_2 + w_rig + fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The total width of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle with full height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getVerRect(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        return getVerRect(width / 2, width / 2, fade, fadeLength, raw, interpolator, clone, deNull);
    }

    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// RECTANGLES WHICH LYE INSIDE THE IMAGE AND TOUCHES NO SIDES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="RECTANGLES WHICH LYE INSIDE THE IMAGE AND TOUCHES NO SIDES">;

    /**
     * @param h_abv height_above: The height above the middle of original image to be summed with h_btw and be used as the height of the rectangle to be cropped.
     * @param h_blw height_below: The height below the middle of original image to be summed with h_abv and be used as the height of the rectangle to be cropped.
     * @param w_lef width_left : The width left of the middle of original image to be summed with w_rig and be used as the width of the rectangle to be cropped.
     * @param w_rig width_right: The width right of the middle of original image to be summed with w_lef and be used as the width of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRect(int h_abv, int h_blw, int w_lef, int w_rig, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int w_in_2 = (int) (w_fl / 2);
        int h_in_2 = (int) (h_fl / 2);
        float w_fl_2 = w_fl / 2;
        float h_fl_2 = h_fl / 2;
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl_2 - h_abv - fadeLength && y < h_fl_2 + h_blw + fadeLength);
        ArrUtil.retainByPredicate(raw, (x, y) -> x > w_fl_2 - w_lef - fadeLength && x < w_fl_2 + w_rig + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in_2 - h_abv, 0, h_in_2 - h_abv - fadeLength);
            ArrUtil.fade(raw, interpolator, 0, h_in_2 + h_blw, 0, h_in_2 + h_blw + fadeLength);
            ArrUtil.fade(raw, interpolator, w_in_2 - w_lef, 0, w_in_2 - w_lef - fadeLength, 0);
            ArrUtil.fade(raw, interpolator, w_in_2 + w_rig, 0, w_in_2 + w_rig + fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param size The size or side length of the square to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the middle part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getCenRect(int size, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        int sq = size / 2;
        return getRect(sq, sq, sq, sq, fade, fadeLength, raw, interpolator, clone, deNull);
    }

    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// RECTANGLES WHICH LYE ALONG THE CORNERS TOUCHING TWO SIDES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="RECTANGLES WHICH LYE ALONG THE CORNERS TOUCHING TWO SIDES">;

    /**
     * @param width The width from left of original image to be used as the width of the rectangle to be cropped.
     * @param height The height from top of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top left part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopLefRect(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength && x < width + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width from right of original image to be used as the width of the rectangle to be cropped.
     * @param height The height from top of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top right part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopRigRect(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength && x > w_fl - width - fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width from left of original image to be used as the width of the rectangle to be cropped.
     * @param height The height from bottom of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top left part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotLefRect(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl - height - fadeLength && x < width + fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in - height, 0, h_in - height - fadeLength);
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width from right of original image to be used as the width of the rectangle to be cropped.
     * @param height The height from bottom of original image to be used as the height of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top left part of original pixels cropped like a rectangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotRigRect(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl - height - fadeLength && x > w_fl - width - fadeLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in - height, 0, h_in - height - fadeLength);
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - fadeLength, 0);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// TRIANGLES WHICH ARE ALIGNED BY THE SIDES BUT TOUCH ONLY ONE SIDE
    //////////// CROP TOOL ONLY CUTS TRIANGLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="TRIANGLES WHICH ARE ALIGNED BY THE SIDES BUT TOUCH ONLY ONE SIDE">;

    /**
     * @param x_c The abscissa of the third point of crop triangle.
     * @param y_c The ordinate of the third point of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped by a triangle of one base same as the side.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopTri(int x_c, int y_c, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;
        float h_fl = raw[0].length;

        Line line1 = Line.fromTwoPoints(0, 0, x_c, y_c);
        Line line1FadeExtent = line1.clone();
        Line line2 = Line.fromTwoPoints(w_fl, 0, x_c, y_c);
        Line line2FadeExtent = line2.clone();
        line1FadeExtent.moveParallely(fadeLength, -5000, 5000);
        line2FadeExtent.moveParallely(fadeLength, +5000, 5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> line1FadeExtent.put(x, y) < 0 && line2FadeExtent.put(x, y) > 0);
        if (fade) {
            line1FadeExtent.moveParallely(fadeLength * .1f, -5000, 5000);
            line2FadeExtent.moveParallely(fadeLength * .1f, +5000, 5000);
            ArrUtil.fade(raw, interpolator, line1, line1FadeExtent);
            ArrUtil.fade(raw, interpolator, line2, line2FadeExtent);
        }

        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped by a triangle of one base same as the side and it's height equal to the half of image's height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopTri(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int w_in_2 = (int) (w_fl / 2);
        int h_in_2 = (int) (h_fl / 2);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < x + effectiveLength && x + y < w_fl + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 + effectiveLength, h_in_2 + effectiveLength);
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 - effectiveLength, h_in_2 + effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param x_c The abscissa of the third point of crop triangle.
     * @param y_c The ordinate of the third point of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped by a triangle of one base same as the side.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotTri(int x_c, int y_c, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;
        float h_fl = raw[0].length;

        Line line1 = Line.fromTwoPoints(0, h_fl, x_c, y_c);
        Line line1FadeExtent = line1.clone();
        Line line2 = Line.fromTwoPoints(w_fl, h_fl, x_c, y_c);
        Line line2FadeExtent = line2.clone();
        line1FadeExtent.moveParallely(fadeLength, -5000, -5000);
        line2FadeExtent.moveParallely(fadeLength, +5000, -5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> line1FadeExtent.put(x, y) < 0 && line2FadeExtent.put(x, y) < 0);
        if (fade) {
            line1FadeExtent.moveParallely(fadeLength, -5000, -5000);
            line2FadeExtent.moveParallely(fadeLength, +5000, -5000);
            ArrUtil.fade(raw, interpolator, line1, line1FadeExtent);
            ArrUtil.fade(raw, interpolator, line2, line2FadeExtent);
        }

        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped by a triangle of one base same as the side and it's height equal to the half of image's height.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotTri(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int w_in_2 = (int) (w_fl / 2);
        int h_in_2 = (int) (h_fl / 2);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x < y + effectiveLength && x + y > w_fl - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 + effectiveLength, h_in_2 - effectiveLength);
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 - effectiveLength, h_in_2 - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param x_c The abscissa of the third point of crop triangle.
     * @param y_c The ordinate of the third point of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the left part of original pixels cropped by a triangle of one base same as the side.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getLefTri(int x_c, int y_c, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;
        float h_fl = raw[0].length;

        Line line1 = Line.fromTwoPoints(0, 0, x_c, y_c);
        Line line1FadeExtent = line1.clone();
        Line line2 = Line.fromTwoPoints(0, h_fl, x_c, y_c);
        Line line2FadeExtent = line2.clone();
        line1FadeExtent.moveParallely(fadeLength, 5000, -5000);
        line2FadeExtent.moveParallely(fadeLength, 5000, +5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> line1FadeExtent.put(x, y) > 0 && line2FadeExtent.put(x, y) > 0);
        if (fade) {
            line1FadeExtent.moveParallely(fadeLength, 5000, -5000);
            line2FadeExtent.moveParallely(fadeLength, 5000, +5000);
            ArrUtil.fade(raw, interpolator, line1, line1FadeExtent);
            ArrUtil.fade(raw, interpolator, line2, line2FadeExtent);
        }

        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }
    
    /**
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the left part of original pixels cropped by a triangle of one base same as the side and it's width equal to the half of image's width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getLefTri(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int w_in_2 = (int) (w_fl / 2);
        int h_in_2 = (int) (h_fl / 2);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x < y + effectiveLength && x + y < w_fl + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 + effectiveLength, h_in_2 + effectiveLength);
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 + effectiveLength, h_in_2 - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param x_c The abscissa of the third point of crop triangle.
     * @param y_c The ordinate of the third point of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the right part of original pixels cropped by a triangle of one base same as the side.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRigTri(int x_c, int y_c, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;
        float h_fl = raw[0].length;

        Line line1 = Line.fromTwoPoints(w_fl, 0, x_c, y_c);
        Line line1FadeExtent = line1.clone();
        Line line2 = Line.fromTwoPoints(w_fl, h_fl, x_c, y_c);
        Line line2FadeExtent = line2.clone();
        line1FadeExtent.moveParallely(fadeLength, -5000, -5000);
        line2FadeExtent.moveParallely(fadeLength, -5000, +5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> line1FadeExtent.put(x, y) < 0 && line2FadeExtent.put(x, y) > 0);
        if (fade) {
            line1FadeExtent.moveParallely(fadeLength, -5000, -5000);
            line2FadeExtent.moveParallely(fadeLength, -5000, +5000);
            ArrUtil.fade(raw, interpolator, line1, line1FadeExtent);
            ArrUtil.fade(raw, interpolator, line2, line2FadeExtent);
        }

        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }   

    /**
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the right part of original pixels cropped by a triangle of one base same as the side and it's width equal to the half of image's width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRigTri(boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int w_in_2 = (int) (w_fl / 2);
        int h_in_2 = (int) (h_fl / 2);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x > y - effectiveLength && x + y > w_fl - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 - effectiveLength, h_in_2 + effectiveLength);
            ArrUtil.fade(raw, interpolator, w_in_2, h_in_2, w_in_2 - effectiveLength, h_in_2 - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// TRIANGLES WHICH ARE ALIGNED BY THE CORNERS AND TOUCH ONLY TWO SIDES
    //////////// CROP TOOL ONLY CUTS TRIANGLES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="TRIANGLES WHICH ARE ALIGNED BY THE CORNERS AND TOUCH ONLY TWO SIDES">;

    /**
     * @param width The width of top side of crop triangle.
     * @param height The height of left side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top left corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopLefTri(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);

        Line line = Line.fromTwoPoints(0, height, width, 0);
        Line lineFadeExtent = line.clone();
        lineFadeExtent.moveParallely(fadeLength, 5000, 5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> lineFadeExtent.put(x, y) > 0);
        if (fade) {
            lineFadeExtent.moveParallely(fadeLength * .1f, 5000, 5000);
            ArrUtil.fade(raw, interpolator, line, lineFadeExtent);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param width The width of top side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top left corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopLefTri(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x + y < width + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, width, 0, width + effectiveLength, effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width of top side of crop triangle.
     * @param height The height of right side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top right corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopRigTri(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;

        Line line = Line.fromTwoPoints(w_fl - width, 0, w_fl, height);
        Line lineFadeExtent = line.clone();
        lineFadeExtent.moveParallely(fadeLength, -5000, 5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> lineFadeExtent.put(x, y) < 0);
        if (fade) {
            lineFadeExtent.moveParallely(fadeLength * .1f, -5000, 5000);
            ArrUtil.fade(raw, interpolator, line, lineFadeExtent);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param width The width of top side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top right corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopRigTri(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < x - w_fl + width + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - effectiveLength, effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }



    /**
     * @param width The width of bottom side of crop triangle.
     * @param height The height of left side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom left corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotLefTri(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float h_fl = raw[0].length;

        Line line = Line.fromTwoPoints(0, h_fl - height, width, h_fl);
        Line lineFadeExtent = line.clone();
        lineFadeExtent.moveParallely(fadeLength, 5000, -5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> lineFadeExtent.put(x, y) > 0);
        if (fade) {
            lineFadeExtent.moveParallely(fadeLength * .1f, 5000, -5000);
            ArrUtil.fade(raw, interpolator, line, lineFadeExtent);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param width The width of bottom side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom left corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotLefTri(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y > x + h_fl - width - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, h_in - width, effectiveLength, h_in - width - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The width of bottom side of crop triangle.
     * @param height The height of left side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom left corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotRigTri(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        float w_fl = raw.length;
        float h_fl = raw[0].length;

        Line line = Line.fromTwoPoints(w_fl - width, h_fl, w_fl, h_fl - height);
        Line lineFadeExtent = line.clone();
        lineFadeExtent.moveParallely(fadeLength, -5000, -5000);
        ArrUtil.retainByPredicate(raw, (x, y) -> lineFadeExtent.put(x, y) < 0);
        if (fade) {
            lineFadeExtent.moveParallely(fadeLength * .1f, -5000, -5000);
            ArrUtil.fade(raw, interpolator, line, lineFadeExtent);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    /**
     * @param width The width of bottom side of crop triangle.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom right corner of original pixels cropped by a triangle.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotRigTri(int width, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x + y > w_fl + h_fl - width - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in - width, h_in, w_in - width - effectiveLength, h_in - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// THAT STRANGE POLYGON WHICH TOUCHES 2 SIDES AND HAS ONE INTERNAL ANGLE AS 135 DEGREES
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="THAT STRANGE POLYGON WHICH TOUCHES 2 SIDES AND HAS ONE INTERNAL ANGLE AS 135 DEGREES">;

    /**
     * @param width The height from top of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from left of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopLefPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength && x + y < width + height + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
            ArrUtil.fade(raw, interpolator, width, height, width + effectiveLength, height + effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from top of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from left of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the left part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getLefTopPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x < width + fadeLength && x + y < width + height + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
            ArrUtil.fade(raw, interpolator, width, height, width + effectiveLength, height + effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from top of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from right of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getTopRigPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength && y < x - w_fl + width + height + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
            ArrUtil.fade(raw, interpolator, w_in - width, height, w_in - width - effectiveLength, height + effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from top of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from right of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the right part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRigTopPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        float w_fl = w_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x > w_fl - width - fadeLength && y < x - w_fl + width + height + effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - fadeLength, 0);
            ArrUtil.fade(raw, interpolator, w_in - width, height, w_in - width - effectiveLength, height + effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from bottom of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from left of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotLefPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl - height - fadeLength && y > x + h_fl - height - width - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, 0, height, 0, height - fadeLength);
            ArrUtil.fade(raw, interpolator, width, h_in - height, width + effectiveLength, h_in - height - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from bottom of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from left of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the left part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getLefBotPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int h_in = raw[0].length;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x < width + fadeLength && y > x + h_fl - height - width - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
            ArrUtil.fade(raw, interpolator, width, h_in - height, width + effectiveLength, h_in - height - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from bottom of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from right of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getBotRigPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> y > h_fl - height - fadeLength && x + y > w_fl - width + h_fl - height - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, width, 0, width + fadeLength, 0);
            ArrUtil.fade(raw, interpolator, w_in - width, h_in - height, w_in - width - effectiveLength, h_in - height - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }


    /**
     * @param width The height from bottom of original image to be used as the the abscissa of fourth point of the rectangle to be cropped.
     * @param height The width from right of original image to be used as the the ordinate of fourth point of the rectangle to be cropped.
     * @param fade If true, fades the image about the cuts by fadeLength.
     * @param fadeLength The length by which the fade takes effect.
     * @param raw If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull If true changes all null values to transparent colors.
     * @return Returns the bottom part of original pixels cropped like by a quadrilateral.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     */
    public static Color[][] getRigBotPoly(int width, int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        int w_in = raw.length;
        int h_in = raw[0].length;
        float w_fl = w_in;
        float h_fl = h_in;
        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);
        ArrUtil.retainByPredicate(raw, (x, y) -> x > w_fl - width - fadeLength && x + y > w_fl - width + h_fl - height - effectiveLength);
        if (fade) {
            ArrUtil.fade(raw, interpolator, w_in - width, 0, w_in - width - fadeLength, 0);
            ArrUtil.fade(raw, interpolator, w_in - width, h_in - height, w_in - width - effectiveLength, h_in - height - effectiveLength);
        }
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

    
    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////// UTILITY METHODS
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold defaultstate="collapsed" desc="UTILITY METHODS">;

    /**
     * @param raw   The pixel array of concern.
     * @param clone If it's supposed to be cloned, thus, not changing values in raw array.
     * @return Checks if the given array is not a null value and has non null sub arrays.
     * Returns an array to be worked on.
     */
    private static Color[][] arrayPreCheck(Color[][] raw, boolean clone) {
        if (raw == null || raw[0] == null) throw new NullPointerException("The given array is null, or has null sub arrays.");
        if (clone) raw = ArrUtil.clonePixels(raw);
        return raw;
    }

    //</editor-fold>

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
