package com.mcxiv.app.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class AppUtil {

    public static final float ROOT_2 = (float) Math.sqrt(2);
    public static PrintStream ref = System.out;

    public static String path(String... dirs) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dirs.length - 1; i++)
            builder.append(dirs[i]).append(File.separator);
        return builder.append(dirs[dirs.length - 1]).toString();
    }

    public static float intr(float val) {
        return (float) Math.pow(val, 4);
    }

    public static void disableOut() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }
        }));
    }

    public static void enableOut() {
        System.setOut(ref);
    }

    // TODo: remove
    public static void print(Color[][] topCap, String name) {
        int w = topCap.length;
        int h = topCap[0].length;
        BufferedImage image = new BufferedImage(w, h, 2);
        for (int i = 0; i < w; i++) for (int j = 0; j < h; j++) image.setRGB(i, j, topCap[i][j].getRGB());
        try {
            ImageIO.write(image, "png", new File(System.getProperty("user.dir") + File.separator + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private final int BIG_ENOUGH_INT = 16 * 1024;
    static private final double BIG_ENOUGH_FLOOR = BIG_ENOUGH_INT;

    static public int ceil(float value) {
        return BIG_ENOUGH_INT - (int) (BIG_ENOUGH_FLOOR - value);
    }
}
