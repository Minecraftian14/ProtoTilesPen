package com.mcxiv.app;

import com.mcxiv.app.util.AppUtil;
import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Color;
import com.mcxiv.app.util.functions.FloatFunction;

import java.util.function.Function;

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

    public Color[][] topRigAndBotLefInner;
    public Color[][] topLefAndBotRigInner;

    public Color[][] exceptTopRigInnerCorner;
    public Color[][] exceptTopLefInnerCorner;
    public Color[][] exceptBotRigInnerCorner;
    public Color[][] exceptBotLefInnerCorner;

    public Color[][] center;
    public Color[][] topBotFlat;
    public Color[][] rigLefFlat;
    public Color[][] allFlat;
    public Color[][] allInner;

    public CachedTiles(ProtoTiles_old tiles) {

        width = tiles.getWidth();
        height = tiles.getHeight();

        boolean fade = true;
        int fadeLength = 20;
        FloatFunction interpolator = AppUtil::intr;

        Color[][] topPixels = tiles.getTopFlat();

        topFlat = topPixels;
        rigFlat = ArrUtil.rot90(topPixels);
        lefFlat = ArrUtil.rot270(topPixels);
        botFlat = ArrUtil.rot180(topPixels);

        Color[][] topCapPixels = ProtoTiles_new.getTopCap(fade, fadeLength, topPixels, interpolator);
//        Color[][] topCapPixels = tiles.getTopCap();

        topCap = topCapPixels;
        rigCap = ArrUtil.rot90(topCapPixels);
        lefCap = ArrUtil.rot270(topCapPixels);
        botCap = ArrUtil.rot180(topCapPixels);

        Color[][] topLefCornerPixels = ProtoTiles_new.getTopLefCor(fade, fadeLength, topPixels, interpolator);
//        Color[][] topLefCornerPixels = tiles.getTopLefCor();

        topLefCorner = topLefCornerPixels;
        topRigCorner = ArrUtil.rot90(topLefCornerPixels);
        botRigCorner = ArrUtil.rot180(topLefCornerPixels);
        botLefCorner = ArrUtil.rot270(topLefCornerPixels);

        Color[][] topLefInnerCornerPixels = ProtoTiles_new.getTopLefInnerCor(fade, fadeLength, topPixels, interpolator);

        topLefInnerCorner = topLefInnerCornerPixels;
        topRigInnerCorner = ArrUtil.rot90(topLefInnerCornerPixels);
        botRigInnerCorner = ArrUtil.rot180(topLefInnerCornerPixels);
        botLefInnerCorner = ArrUtil.rot270(topLefInnerCornerPixels);

        Color[][] topLefDoubleCornerPixels = ProtoTiles_new.getTopLefDoubleCor(fade, fadeLength, topPixels, interpolator);

        topLefDoubleCorner = topLefDoubleCornerPixels;
        topRigDoubleCorner = ArrUtil.rot90(topLefDoubleCornerPixels);
        botRigDoubleCorner = ArrUtil.rot180(topLefDoubleCornerPixels);
        botLefDoubleCorner = ArrUtil.rot270(topLefDoubleCornerPixels);

        Color[][] topFlatRigInnerPixels = ProtoTiles_new.getTopFlatRigInnerCorner(fade, fadeLength, topPixels, interpolator);

        topFlatRigInner = topFlatRigInnerPixels;
        rigFlatBotInner = ArrUtil.rot90(topFlatRigInnerPixels);
        botFlatLefInner = ArrUtil.rot180(topFlatRigInnerPixels);
        lefFlatTopInner = ArrUtil.rot270(topFlatRigInnerPixels);

        Color[][] topFlatLefInnerPixels = ProtoTiles_new.getTopFlatLefInnerCorner(fade, fadeLength, topPixels, interpolator);

        topFlatLefInner = topFlatLefInnerPixels;
        rigFlatTopInner = ArrUtil.rot90(topFlatLefInnerPixels);
        botFlatRigInner = ArrUtil.rot180(topFlatLefInnerPixels);
        lefFlatBotInner = ArrUtil.rot270(topFlatLefInnerPixels);

        Color[][] topFlatOppoInnerPixels = ProtoTiles_new.getTopFlatOppoInner(fade, fadeLength, topPixels, interpolator);

        topFlatOppoInner = topFlatOppoInnerPixels;
        rigFlatOppoInner = ArrUtil.rot90(topFlatOppoInnerPixels);
        botFlatOppoInner = ArrUtil.rot180(topFlatOppoInnerPixels);
        lefFlatOppoInner = ArrUtil.rot270(topFlatOppoInnerPixels);

        Color[][] topOppoInnerPixels = ProtoTiles_new.getTopOppoInner(fade, fadeLength, topPixels, interpolator);

        topOppoInner = topOppoInnerPixels;
        rigOppoInner = ArrUtil.rot90(topOppoInnerPixels);
        botOppoInner = ArrUtil.rot180(topOppoInnerPixels);
        lefOppoInner = ArrUtil.rot270(topOppoInnerPixels);

        Color[][] topLefAndBotRigInnerPixels = ProtoTiles_new.getTopLefAndBotRigInnerCorner(fade, fadeLength, topPixels, interpolator);

        topLefAndBotRigInner = topLefAndBotRigInnerPixels;
        topRigAndBotLefInner = ArrUtil.rot90(topLefAndBotRigInnerPixels);

        Color[][] exceptBotRigInnerCornerPixels = ProtoTiles_new.getExceptTopLefInnerCorner(fade, fadeLength, topPixels, interpolator);

        exceptBotRigInnerCorner = exceptBotRigInnerCornerPixels;
        exceptBotLefInnerCorner = ArrUtil.rot90(exceptBotRigInnerCornerPixels);
        exceptTopLefInnerCorner = ArrUtil.rot180(exceptBotRigInnerCornerPixels);
        exceptTopRigInnerCorner = ArrUtil.rot270(exceptBotRigInnerCornerPixels);

        center = ProtoTiles_new.getCenter(fade, fadeLength, topPixels, interpolator);
        allFlat = tiles.getAllFlat();
        allInner = ProtoTiles_new.getAllInnerCorner(fade, fadeLength, topPixels, interpolator);

        Color[][] topBotPixels = tiles.getTopBotFlat();

        topBotFlat = topBotPixels;
        rigLefFlat = ArrUtil.rot270(topBotPixels);

    }

}
