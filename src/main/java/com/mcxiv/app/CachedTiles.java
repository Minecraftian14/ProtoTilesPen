package com.mcxiv.app;

import com.badlogic.gdx.graphics.Color;
import com.mcxiv.app.util.ArrUtil;

public class CachedTiles {

    public final int width;
    public final int height;

    public Color[][] topFlat;
    public Color[][] rigFlat;
    public Color[][] lefFlat;
    public Color[][] botFlat;

    public Color[][] topCap;
    public Color[][] rigCap;
    public Color[][] lefCap;
    public Color[][] botCap;

    public Color[][] topRigCorner;
    public Color[][] topLefCorner;
    public Color[][] botRigCorner;
    public Color[][] botLefCorner;

    public Color[][] topRigInnerCorner;
    public Color[][] topLefInnerCorner;
    public Color[][] botRigInnerCorner;
    public Color[][] botLefInnerCorner;

    public Color[][] topRigDoubleCorner;
    public Color[][] topLefDoubleCorner;
    public Color[][] botRigDoubleCorner;
    public Color[][] botLefDoubleCorner;

    public Color[][] topFlatRigInner;
    public Color[][] topFlatLefInner;
    public Color[][] rigFlatTopInner;
    public Color[][] rigFlatBotInner;
    public Color[][] botFlatRigInner;
    public Color[][] botFlatLefInner;
    public Color[][] lefFlatTopInner;
    public Color[][] lefFlatBotInner;

    public Color[][] topFlatOppoInner;
    public Color[][] rigFlatOppoInner;
    public Color[][] botFlatOppoInner;
    public Color[][] lefFlatOppoInner;

    public Color[][] topOppoInner;
    public Color[][] rigOppoInner;
    public Color[][] botOppoInner;
    public Color[][] lefOppoInner;

    public Color[][] topRigAndBotLefOppoInner;
    public Color[][] topLefAndBotRigOppoInner;

    public Color[][] exceptTopRigInnerCorner;
    public Color[][] exceptTopLefInnerCorner;
    public Color[][] exceptBotRigInnerCorner;
    public Color[][] exceptBotLefInnerCorner;

    public Color[][] center;
    public Color[][] topBotFlat;
    public Color[][] rigLefFlat;
    public Color[][] allFlat;
    public Color[][] allInner;

    public CachedTiles(ProtoTiles tiles) {

        width = tiles.getWidth();
        height = tiles.getHeight();

        Color[][] topPixels = tiles.getTop();

        topFlat = topPixels;
        rigFlat = ArrUtil.rot90(topPixels);
        lefFlat = ArrUtil.rot270(topPixels);
        botFlat = ArrUtil.rot180(topPixels);

        Color[][] topCapPixels = tiles.get3Top();

        topCap = topCapPixels;
        rigCap = ArrUtil.rot90(topCapPixels);
        lefCap = ArrUtil.rot270(topCapPixels);
        botCap = ArrUtil.rot180(topCapPixels);

        Color[][] topLefCornerPixels = tiles.getTopLef();

        topLefCorner = topLefCornerPixels;
        topRigCorner = ArrUtil.rot90(topLefCornerPixels);
        botRigCorner = ArrUtil.rot180(topLefCornerPixels);
        botLefCorner = ArrUtil.rot270(topLefCornerPixels);

        Color[][] topLefInnerCornerPixels = tiles.getAntiTopLef();

        topLefInnerCorner = topLefInnerCornerPixels;
        topRigInnerCorner = ArrUtil.rot90(topLefInnerCornerPixels);
        botRigInnerCorner = ArrUtil.rot180(topLefInnerCornerPixels);
        botLefInnerCorner = ArrUtil.rot270(topLefInnerCornerPixels);

        Color[][] topLefDoubleCornerPixels = tiles.getDoubleCorner();

        topLefDoubleCorner = topLefDoubleCornerPixels;
        topRigDoubleCorner = ArrUtil.rot90(topLefDoubleCornerPixels);
        botRigDoubleCorner = ArrUtil.rot180(topLefDoubleCornerPixels);
        botLefDoubleCorner = ArrUtil.rot270(topLefDoubleCornerPixels);

        Color[][] topFlatRigInnerPixels = tiles.getTopFlatRigInnerCorner();

        topFlatRigInner = topFlatRigInnerPixels;
        rigFlatBotInner = ArrUtil.rot90(topFlatRigInnerPixels);
        botFlatLefInner = ArrUtil.rot180(topFlatRigInnerPixels);
        lefFlatTopInner = ArrUtil.rot270(topFlatRigInnerPixels);

        Color[][] topFlatLefInnerPixels = tiles.getTopFlatLefInnerCorner();

        topFlatLefInner = topFlatLefInnerPixels;
        rigFlatTopInner = ArrUtil.rot90(topFlatLefInnerPixels);
        botFlatRigInner = ArrUtil.rot180(topFlatLefInnerPixels);
        lefFlatBotInner = ArrUtil.rot270(topFlatLefInnerPixels);

        Color[][] topFlatOppoInnerPixels = tiles.getTopFlat_BotRigAndBotLefInnerCorner();

        topFlatOppoInner = topFlatOppoInnerPixels;
        rigFlatOppoInner = ArrUtil.rot90(topFlatOppoInnerPixels);
        botFlatOppoInner = ArrUtil.rot180(topFlatOppoInnerPixels);
        lefFlatOppoInner = ArrUtil.rot270(topFlatOppoInnerPixels);

        Color[][] topOppoInnerPixels = tiles.getBotRigAndBotLefInnerCorner();

        topOppoInner = topOppoInnerPixels;
        rigOppoInner = ArrUtil.rot90(topOppoInnerPixels);
        botOppoInner = ArrUtil.rot180(topOppoInnerPixels);
        lefOppoInner = ArrUtil.rot270(topOppoInnerPixels);

        Color[][] topRigAndBotLefOppoInnerPixels = tiles.getTopRigAndBotLefInnerCorner();

        topRigAndBotLefOppoInner = topRigAndBotLefOppoInnerPixels;
        topLefAndBotRigOppoInner = ArrUtil.rot90(topRigAndBotLefOppoInnerPixels);

        Color[][] exceptBotRigInnerCornerPixels = tiles.getTopRig_TopLefAndBotLefInnerCorner();

        exceptBotRigInnerCorner = exceptBotRigInnerCornerPixels;
        exceptBotLefInnerCorner = ArrUtil.rot90(exceptBotRigInnerCornerPixels);
        exceptTopLefInnerCorner = ArrUtil.rot180(exceptBotRigInnerCornerPixels);
        exceptTopRigInnerCorner = ArrUtil.rot270(exceptBotRigInnerCornerPixels);

        center = tiles.getCenter();
        allFlat = tiles.getAllFlat();
        allInner = tiles.getAllInnerCorner();

        Color[][] topBotPixels = tiles.getTopBot();

        topBotFlat = topBotPixels;
        rigLefFlat = ArrUtil.rot270(topBotPixels);

    }

}
