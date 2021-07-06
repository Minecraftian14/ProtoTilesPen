package com.mcxiv.app.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

class BlendingTest {
    public static void main(String[] args) throws IllegalAccessException, IOException {

        String path = System.getProperty("user.dir") + File.separator;

        String image1 = "one";
        String image2 = "two";

        BufferedImage one = null;
        BufferedImage two = null;
        try {
            one = ImageIO.read(new File(path + image1 + ".png"));
            two = ImageIO.read(new File(path + image2 + ".png"));
        } catch (IOException e) {
            try {
                path += "ProtoTilesPen" + File.separator;
                one = ImageIO.read(new File(path + image1 + ".png"));
                two = ImageIO.read(new File(path + image2 + ".png"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

//        System.exit(0);

        for (Field field : Blending.class.getFields()) {
            if (!Modifier.isStatic(field.getModifiers())) continue;
            if (!field.getType().getName().equals(Blending.class.getName()))
                if (Arrays.stream(field.getType().getInterfaces()).noneMatch(inter -> inter.getName().equals(Blending.class.getName())))
                    continue;

            Blending blend = (Blending) field.get(null);

            BufferedImage res = new BufferedImage(one.getWidth(), one.getHeight(), 2);

            for (int i = 0; i < res.getWidth(); i++) {
                for (int j = 0; j < res.getHeight(); j++) {

                    Color one_col = Color.newColorFromARGB(one.getRGB(i, j));
                    Color two_col = Color.newColorFromARGB(two.getRGB(i, j));

//                    res.setRGB(i, j, blend.blend(one_col, two_col).getRGB());
                    res.setRGB(i, j, Blending.blendingOptions(one_col, two_col, blend, Math::max).getRGB());

                }
            }
            System.out.println(field.getName());

            ImageIO.write(res, "PNG", new File(path + "results" + File.separator + "result_" + field.getName() + ".png"));

        }

    }


    @Test
    void hardCheck() {
        Color one = new Color(0, 1, 0, 1);
        Color two = new Color(1, 0, 1, 0);
        Assertions.assertEquals(Blending.darken.blend(one, two).toString(), "Color{ r=0.0000   g=0.0000   b=0.0000   a=0.0000 }");
        Assertions.assertEquals(Blending.lighten.blend(one, two).toString(), "Color{ r=1.0000   g=1.0000   b=1.0000   a=1.0000 }");
    }
}