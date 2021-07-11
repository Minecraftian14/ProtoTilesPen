package com.mcxiv.app.util;

public class Color {

    public static final Color BLACK = new Color(0, 0, 0, 1);
    public static final Color INVISIBLE = new Color(0, 0, 0, 0);

    public float r = 0, g = 0, b = 0, a = 0;

    public Color() {
    }

    public static Color newColor() {
        return new Color();
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static Color newColor(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    public static Color newColorFromARGB(int value) {
        Color color = newColor();
        color.a = ((value & 0xff000000) >>> 24) / 255f;
        color.r = ((value & 0x00ff0000) >>> 16) / 255f;
        color.g = ((value & 0x0000ff00) >>> 8) / 255f;
        color.b = ((value & 0x000000ff)) / 255f;
        return color;
    }

    public static Color newColorFromRGBA(int value) {
        Color color = newColor();
        color.r = ((value & 0xff000000) >>> 24) / 255f;
        color.g = ((value & 0x00ff0000) >>> 16) / 255f;
        color.b = ((value & 0x0000ff00) >>> 8) / 255f;
        color.a = ((value & 0x000000ff)) / 255f;
        return color;
    }

//    public static Color newColorFromHSL(float hue, float lumination, float saturation, float alpha) {
//        return toRGB(hue, lumination, saturation, alpha);
//    }

    @Override
    public String toString() {
        return String.format("Color{ r=%.4f   g=%.4f   b=%.4f   a=%.4f }", r, g, b, a);

    }

    public int getRGB() {
        return ((((int) (a * 255 + 0.5)) & 0xFF) << 24) |
                ((((int) (r * 255 + 0.5)) & 0xFF) << 16) |
                ((((int) (g * 255 + 0.5)) & 0xFF) << 8) |
                ((((int) (b * 255 + 0.5)) & 0xFF) << 0);
    }

    public int getRed255() {
        return (int) (r * 255);
    }

    public int getGreen255() {
        return (int) (g * 255);
    }

    public int getBlue255() {
        return (int) (b * 255);
    }

    public int getAlpha255() {
        return (int) (a * 255);
    }

    public static void RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        int cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }

}
