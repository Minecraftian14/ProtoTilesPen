package com.mcxiv.app;

import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Blending;
import com.mcxiv.app.util.Color;
import com.mcxiv.app.util.functions.FloatFunction;

public class CachedTiles {

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

    public CachedTiles(Color[][] original, boolean fade, int fadeLength, FloatFunction interpolator, Blending blender) {

        Color[][] topPixels = ProtoTiles.getTopFlat(original);

        topFlat = topPixels;
        rigFlat = ArrUtil.rot90(topPixels);
        lefFlat = ArrUtil.rot270(topPixels);
        botFlat = ArrUtil.rot180(topPixels);

        Color[][] topCapPixels = ProtoTiles.getTopCap(fade, fadeLength, original, interpolator, blender);

        topCap = topCapPixels;
        rigCap = ArrUtil.rot90(topCapPixels);
        lefCap = ArrUtil.rot270(topCapPixels);
        botCap = ArrUtil.rot180(topCapPixels);

        Color[][] topLefCornerPixels = ProtoTiles.getTopLefCor(fade, fadeLength, original, interpolator, blender);

        topLefCorner = topLefCornerPixels;
        topRigCorner = ArrUtil.rot90(topLefCornerPixels);
        botRigCorner = ArrUtil.rot180(topLefCornerPixels);
        botLefCorner = ArrUtil.rot270(topLefCornerPixels);

        Color[][] topLefInnerCornerPixels = ProtoTiles.getTopLefInnerCor(fade, fadeLength, original, interpolator, blender);

        topLefInnerCorner = topLefInnerCornerPixels;
        topRigInnerCorner = ArrUtil.rot90(topLefInnerCornerPixels);
        botRigInnerCorner = ArrUtil.rot180(topLefInnerCornerPixels);
        botLefInnerCorner = ArrUtil.rot270(topLefInnerCornerPixels);

        Color[][] topLefDoubleCornerPixels = ProtoTiles.getTopLefDoubleCor(fade, fadeLength, original, interpolator, blender);

        topLefDoubleCorner = topLefDoubleCornerPixels;
        topRigDoubleCorner = ArrUtil.rot90(topLefDoubleCornerPixels);
        botRigDoubleCorner = ArrUtil.rot180(topLefDoubleCornerPixels);
        botLefDoubleCorner = ArrUtil.rot270(topLefDoubleCornerPixels);

        Color[][] topFlatRigInnerPixels = ProtoTiles.getTopFlatRigInnerCorner(fade, fadeLength, original, interpolator, blender);

        topFlatRigInner = topFlatRigInnerPixels;
        rigFlatBotInner = ArrUtil.rot90(topFlatRigInnerPixels);
        botFlatLefInner = ArrUtil.rot180(topFlatRigInnerPixels);
        lefFlatTopInner = ArrUtil.rot270(topFlatRigInnerPixels);

        Color[][] topFlatLefInnerPixels = ProtoTiles.getTopFlatLefInnerCorner(fade, fadeLength, original, interpolator, blender);

        topFlatLefInner = topFlatLefInnerPixels;
        rigFlatTopInner = ArrUtil.rot90(topFlatLefInnerPixels);
        botFlatRigInner = ArrUtil.rot180(topFlatLefInnerPixels);
        lefFlatBotInner = ArrUtil.rot270(topFlatLefInnerPixels);

        Color[][] topFlatOppoInnerPixels = ProtoTiles.getTopFlatOppoInner(fade, fadeLength, original, interpolator, blender);

        topFlatOppoInner = topFlatOppoInnerPixels;
        rigFlatOppoInner = ArrUtil.rot90(topFlatOppoInnerPixels);
        botFlatOppoInner = ArrUtil.rot180(topFlatOppoInnerPixels);
        lefFlatOppoInner = ArrUtil.rot270(topFlatOppoInnerPixels);

        Color[][] topOppoInnerPixels = ProtoTiles.getTopOppoInner(fade, fadeLength, original, interpolator, blender);

        topOppoInner = topOppoInnerPixels;
        rigOppoInner = ArrUtil.rot90(topOppoInnerPixels);
        botOppoInner = ArrUtil.rot180(topOppoInnerPixels);
        lefOppoInner = ArrUtil.rot270(topOppoInnerPixels);

        Color[][] topLefAndBotRigInnerPixels = ProtoTiles.getTopLefAndBotRigInnerCorner(fade, fadeLength, original, interpolator, blender);

        topLefAndBotRigInner = topLefAndBotRigInnerPixels;
        topRigAndBotLefInner = ArrUtil.rot90(topLefAndBotRigInnerPixels);

        Color[][] exceptBotRigInnerCornerPixels = ProtoTiles.getExceptTopLefInnerCorner(fade, fadeLength, original, interpolator, blender);

        exceptBotRigInnerCorner = exceptBotRigInnerCornerPixels;
        exceptBotLefInnerCorner = ArrUtil.rot90(exceptBotRigInnerCornerPixels);
        exceptTopLefInnerCorner = ArrUtil.rot180(exceptBotRigInnerCornerPixels);
        exceptTopRigInnerCorner = ArrUtil.rot270(exceptBotRigInnerCornerPixels);

        center = ProtoTiles.getCenter(fade, fadeLength, original, interpolator, blender);
        allFlat = ProtoTiles.getAllFlat(fade, fadeLength, original, interpolator, blender);
        allInner = ProtoTiles.getAllInnerCorner(fade, fadeLength, original, interpolator, blender);

        Color[][] topBotPixels = ProtoTiles.getTopBotFlat(fade, fadeLength, original, interpolator, blender);

        topBotFlat = topBotPixels;
        rigLefFlat = ArrUtil.rot270(topBotPixels);

    }

}
