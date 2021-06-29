package com.mcxiv.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.GdxUtil;

public class PackedTiles {

    private final int w, h;
    private final Color[][] pixels;

    public PackedTiles(CachedTiles tiles) {
        w = tiles.width;
        h = tiles.height;
        pixels = new Color[w * 11][h * 5];

        for (int i = 0; i < pixels.length; i++)
            for (int j = 0; j < pixels[0].length; j++)
                pixels[i][j] = Color.BLACK;

        // sml_sq = The most basic tiles
        add(tiles.topLefCorner, 0, 0);
        add(tiles.topFlat, 1, 0);
        add(tiles.topRigCorner, 2, 0);
        add(tiles.lefFlat, 0, 1);
        add(tiles.center, 1, 1);
        add(tiles.rigFlat, 2, 1);
        add(tiles.botLefCorner, 0, 2);
        add(tiles.botFlat, 1, 2);
        add(tiles.botRigCorner, 2, 2);

        // cap_vert = The Caps (top-down) on right of sml_sq
        add(tiles.topCap, 3, 0);
        add(tiles.rigLefFlat, 3, 1);
        add(tiles.botCap, 3, 2);
        // cap_hor  = The Caps (top-down) below the sml_sq
        add(tiles.lefCap, 0, 3);
        add(tiles.topBotFlat, 1, 3);
        add(tiles.rigCap, 2, 3);
        // all_cap = The all flat icon placed along with cap_vert and cap_hor to form a bigger square together with sml_sq
        add(tiles.allFlat, 3, 3);

        // big_sq = More of the complicated tiles. A big square (4x4) aside the previously formed one
        // Row 1
        add(tiles.topLefDoubleCorner, 4, 0);
        add(tiles.topFlatRigInner, 5, 0);
        add(tiles.topFlatLefInner, 6, 0);
        add(tiles.topRigDoubleCorner, 7, 0);
        // Row 2
        add(tiles.lefFlatBotInner, 4, 1);
        add(tiles.botRigInnerCorner, 5, 1);
        add(tiles.botLefInnerCorner, 6, 1);
        add(tiles.rigFlatBotInner, 7, 1);
        // Row 3
        add(tiles.lefFlatTopInner, 4, 2);
        add(tiles.topRigInnerCorner, 5, 2);
        add(tiles.topLefInnerCorner, 6, 2);
        add(tiles.rigFlatTopInner, 7, 2);
        // Row 4
        add(tiles.botLefDoubleCorner, 4, 3);
        add(tiles.botFlatRigInner, 5, 3);
        add(tiles.botFlatLefInner, 6, 3);
        add(tiles.botRigDoubleCorner, 7, 3);

        // comp_vert = The 4 advanced tiles on right of the big_sq
        add(tiles.topFlatOppoInner, 8, 0);
        add(tiles.topOppoInner, 8, 1);
        add(tiles.botOppoInner, 8, 2);
        add(tiles.botFlatOppoInner, 8, 3);
        // comp_hor = The 4 advanced tiles below the big_sq
        add(tiles.lefFlatOppoInner, 4, 4);
        add(tiles.lefOppoInner, 5, 4);
        add(tiles.rigOppoInner, 6, 4);
        add(tiles.rigFlatOppoInner, 7, 4);
        // all_inn = The all inner corners together icon placed along with comp_vert and comp_hor to form a bigger square together with big_sq
        add(tiles.allInner, 8, 4);

        // oppo_corn = The two opposite inner corner tiles just after comp_vert in the top.
        add(tiles.topRigAndBotLefOppoInner, 9, 0);
        add(tiles.topLefAndBotRigOppoInner, 9, 1);

        // triple_corn = The small square (2x2) placed right below oppo_corn and right after comp_vert
        add(tiles.exceptBotRigInnerCorner, 9, 2);
        add(tiles.exceptBotLefInnerCorner, 10, 2);
        add(tiles.exceptTopRigInnerCorner, 9, 3);
        add(tiles.exceptTopLefInnerCorner, 10, 3);

    }

    private void add(Color[][] image, int x_b, int y_b) {
        for (int i = 0, x = x_b * w; i < w; i++, x++)
            for (int j = 0, y = y_b * h; j < h; j++, y++)
                pixels[x][y] = ArrUtil.newColor(image[i][j]);
    }

    public Color[][] getPixels() {
        return pixels;
    }
}
