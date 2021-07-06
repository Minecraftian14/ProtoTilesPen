package com.mcxiv.app.util;

/**
 * <p> Creates a line object, which programmatically exists in the form <b> y = mx + c </b> </p>
 */
public interface Line {

    static Line fromTwoPoints(float x1, float y1, float x2, float y2) {
        float cm = (y1 - y2) / (x1 - x2);
        float im = (x1 - x2) / (y1 - y2);

        Line line;

        if (Math.abs(cm) < Math.abs(im)) line = new SlopeConstantLine /*   */(cm, y1 - cm * x1);
        else /*                       */ line = new InvertedSlopeConstantLine(im, x1 - im * y1);

        return line;
    }

    float distance(float x, float y);

    float distance(Line line);

    void normal(Vector2 vect, float x, float y);

    void normal(Vector2 vect);

    float put(float x, float y);

    float yint();

    float xint();

    float funcX(float x);

    float funcY(float y);

    float getSlope();

    boolean isBounded(float x, float y);

    boolean intersects(float x, float y);

    boolean isParallel(Line line);

    boolean intersects(Line line);

    boolean boundsOnSameSide(Line line);

    void getIntersection(Vector2 vect, Line line);

    boolean isSegment();

    void makeSegment(float x1, float x2, float y1, float y2);

    Line perpendicularAt(float x, float y);

    Line parallelBy(float d, float x, float y);

    Line moveParallely(float d, float x, float y);

    Vector2 footOfPerpendicular(float x, float y);

    Line clone();

}
