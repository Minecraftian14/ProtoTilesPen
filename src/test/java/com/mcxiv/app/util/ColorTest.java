package com.mcxiv.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorTest {

    @Test
    void simple() {

        Color color = Color.newColorFromARGB(java.awt.Color.ORANGE.getRGB());
        System.out.println(color);
        System.out.println(Integer.toBinaryString(java.awt.Color.ORANGE.getRGB()));
        System.out.println(Integer.toBinaryString(color.getRGB()));

    }
}