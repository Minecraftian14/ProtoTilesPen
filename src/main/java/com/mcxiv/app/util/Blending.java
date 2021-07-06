package com.mcxiv.app.util;

import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Math.max;
import static java.lang.Math.min;

public interface Blending {

    Color blend(Color one, Color two);



    Blending defaultMix = (one, two) -> {
        float sum = one.a + two.a;
        if (sum <= 0 || sum > 2) sum = 2;
        return new Color(
                (one.a * one.r + two.a * two.r) / sum,
                (one.a * one.g + two.a * two.g) / sum,
                (one.a * one.b + two.a * two.b) / sum,
                max(one.a, two.a)
        );
    };

    Blending oneOverTwo = (one, two) -> {
        float oma = 1 - one.a;
        float fin_a = oma * two.a + one.a;
        if (fin_a <= 0 || fin_a > 2) fin_a = 2;
        return new Color(
                (oma * two.a * two.r + one.a * one.r) / fin_a,
                (oma * two.a * two.g + one.a * one.g) / fin_a,
                (oma * two.a * two.b + one.a * one.b) / fin_a,
                fin_a
        );
    };

    // @off
    ElementWiseOperation normal         = new ElementWiseOperation((a, b) -> a);
    ElementWiseOperation lighten        = new ElementWiseOperation(Math::max);
    ElementWiseOperation darken         = new ElementWiseOperation(Math::min);
    ElementWiseOperation multiply       = new ElementWiseOperation((a, b) -> a * b);
    ElementWiseOperation average        = new ElementWiseOperation((a, b) -> (a + b) / 2);

    ElementWiseOperation geometricMean  = new ElementWiseOperation((a, b) -> (float) Math.sqrt(a * b));
    ElementWiseOperation subtractAndCap = new ElementWiseOperation((a, b) -> max(0, a - b));

    ElementWiseOperation addAndCap      = new ElementWiseOperation((a, b) -> min(1, a + b));
    ElementWiseOperation subtract       = new ElementWiseOperation((a, b) -> a + b < 1 ? 0 : a + b - 1);
    ElementWiseOperation difference     = new ElementWiseOperation((a, b) -> Math.abs(a - b));
    ElementWiseOperation negation       = new ElementWiseOperation((a, b) -> 1 - Math.abs(1 - a - b));
    ElementWiseOperation screen         = new ElementWiseOperation(convert((A, B) -> 255 - (((255 - A) * (255 - B)) >> 8)));

    ElementWiseOperation exclusion      = new ElementWiseOperation((a, b) -> a + b - 2 * a * b);
    ElementWiseOperation overlay        = new ElementWiseOperation((a, b) -> (b < 0.5) ? (2 * a * b):(1 - 2 * (1 - a) * (1 - b)));
    ElementWiseOperation softLight      = new ElementWiseOperation(convert((A, B) -> (int) ((B < 128)?(2*((A>>1)+64))*((float)B/255):(255-(2*(255-((A>>1)+64))*(float)(255-B)/255)))));
    Blending             hardLight      = (one, two) -> softLight.blend(two, one);
    ElementWiseOperation colorDodge     = new ElementWiseOperation(convert((A, B) -> (B == 255) ? B:min(255, ((A << 8 ) / (255 - B)))));

    ElementWiseOperation colorBurn      = new ElementWiseOperation(convert((A, B) -> (B == 0) ? B:max(0, (255 - ((255 - A) << 8 ) / B))));
    ElementWiseOperation linearDodge    = addAndCap;
    ElementWiseOperation linearBurn     = subtract;
    ElementWiseOperation linearLight    = new ElementWiseOperation((a, b) -> (b < .5f)? linearBurn.function.on(a, 2*b) : linearDodge.function.on(a, 2*(b-.5f)));
    ElementWiseOperation vividLight     = new ElementWiseOperation((a, b) -> (b < .5f)? colorBurn.function.on(a, 2*b) : colorDodge.function.on(a, 2*(b-.5f)));

    ElementWiseOperation pinLight       = new ElementWiseOperation((a, b) -> (b < .5f)? darken.function.on(a, 2*b) : lighten.function.on(a, 2*(b-.5f)));
    ElementWiseOperation hardMix        = new ElementWiseOperation((a, b) -> (vividLight.function.on(a,b) < 128) ? 0:255);
    ElementWiseOperation reflect        = new ElementWiseOperation((a, b) -> (b == 1) ? b:min(255, (a * b / (255 - b))));
    Blending             glow           = (one, two) -> reflect.blend(two, one);
    ElementWiseOperation phoenix        = new ElementWiseOperation((a, b) -> min(a,b) - max(a,b) + 255);

    Blending             hue            = (one, two) -> HSV_BlendUtil(one, two, 0);
    Blending             saturation     = (one, two) -> HSV_BlendUtil(one, two, 1);
    Blending             value          = (one, two) -> HSV_BlendUtil(one, two, 2);
    Blending             color          = (one, two) -> HSV_BlendUtil(one, two, 3);
    // @on

    float[] sHSB = new float[3];
    float[] dHSB = new float[3];

    /**
     * index = [0, 2]
     * if index = 0: alteration is around Hue
     * if index = 1: alteration is around Saturation
     * if index = 2: alteration is around Value
     * if index = 3: alteration is around Color
     */
    static Color HSV_BlendUtil(Color one, Color two, int index) {
        assert index >= 0 && index <= 3 : new IndexOutOfBoundsException();

        Color.RGBtoHSB(one.getRed255(), one.getGreen255(), one.getBlue255(), sHSB);
        Color.RGBtoHSB(two.getRed255(), two.getGreen255(), two.getBlue255(), dHSB);

        if (index < 3)
            dHSB[index] = sHSB[index];
        else {
            dHSB[0] = sHSB[0];
            dHSB[1] = sHSB[1];
        }

        return Color.newColorFromARGB(Color.HSBtoRGB(dHSB[0], dHSB[1], dHSB[2]));
    }


    static float alpha(float one, float two, float factor) {
        return factor * one + (1 - factor) * two;
    }

    static Color alpha(Color one, Color two, float factor) {
        return Color.newColor(alpha(one.r, two.r, factor), alpha(one.g, two.g, factor), alpha(one.b, two.b, factor), alpha(one.a, two.a, factor));
    }

    static Color alphaBlend(Color one, Color two, Blending blender, float factor) {
        return alpha(blender.blend(one, two), one, factor);
    }

    static Color blendingOptions(Color one, Color two, Blending RGB, FloatFunction A) {
        float a = A.on(one.a, two.a);
        Color col = RGB.blend(one, two);
        col.a = a;
        return col;
    }

    static FloatFunction convert(final IntFunction function) {
        return (a, b) -> 255f * function.on((int) (255 * a), (int) (255 * b));
    }

    class ElementWiseOperation implements Blending {
        private final FloatFunction function;

        public ElementWiseOperation(FloatFunction function) {
            this.function = function;
        }

        @Override
        public Color blend(Color one, Color two) {
            return Color.newColor(
                    function.on(one.r, two.r),
                    function.on(one.g, two.g),
                    function.on(one.b, two.b),
                    function.on(one.a, two.a)
            );
        }
    }

    interface FloatFunction {
        float on(float a, float b);
    }

    interface IntFunction {
        int on(int A, int B);
    }

}
