package com.mcxiv.app.util;

/**
 * <p> Creates a line object, which programmatically exists in the form <b> y = mx + c </b> </p>
 */
public class SlopeConstantLine implements Line {

    /**
     * m = slope of the line as m = y/x.
     * c = width constant of the line.
     */
    float m, c;

    float hypot;
    float magnum;

    /**
     * <p> Stores a bool regarding if it is a line segment. </p>
     */
    boolean isSegment;

    /**
     * <p> part of line between it's abscissa belonging from xf to xt </p>
     */
    float xf, xt;

    /**
     * m = slope of the line as y/x.
     * c = width constant of the line.
     */
    SlopeConstantLine(float m, float c) {
        this.m = m;
        this.c = c;
        magnum = m * m + 1;
        hypot = (float) Math.sqrt(magnum);
    }

    @Override
    public float yint() {
        return c;
    }

    @Override
    public float xint() {
        return -c / m;
    }

    @Override
    public float funcX(float x) {
        return m * x + c;
    }

    @Override
    @Deprecated
    public float funcY(float y) {
        return (y - c) / m;
    }

    @Override
    public float getSlope() {
        return m;
    }

    @Override
    public float distance(float x, float y) {
        return Math.abs(m * x - y + c) / hypot;
    }

    @Override
    public float distance(Line line) {
        if (isParallel(line)) return Math.abs(yint() - line.yint()) / hypot;
        return 0;
    }

    @Override
    public boolean isParallel(Line line) {
        if (line instanceof SlopeConstantLine) {
            SlopeConstantLine l = (SlopeConstantLine) line;
            return Float.compare(m, l.m) == 0 && Float.compare(c, l.c) != 0;

        } else if (line instanceof InvertedSlopeConstantLine) {
            InvertedSlopeConstantLine l = (InvertedSlopeConstantLine) line;
            return Float.compare(m, 1f / l.m) == 0 && Float.compare(c, -1f / l.c) != 0;

        }
        return false;
    }

    @Override
    public void normal(Vector2 vect, float x, float y) {
        normal(vect);
        vect.scl(put(x, y));
    }

    @Override
    public void normal(Vector2 vect) {
        vect.set(m, -1);
        vect.nor();
    }

    @Override
    public float put(float x, float y) {
        return m * x + c - y;
    }

    @Override
    public boolean isBounded(float x, float y) {
        if (isSegment) return (xf < x && x < xt) && (m * xf + c < y && y < m * xt + c);
        return true;
    }

    @Override
    public boolean intersects(float x, float y) {
        return isBounded(x, y) && Float.compare(y, m * x + c) == 0;
    }

    @Override
    public boolean boundsOnSameSide(Line line) {
        return line.put(xf, m * xf + c) != line.put(xt, m * xt + c);
    }

    @Override
    public boolean intersects(Line line) {
        if (isSegment) {
            if (boundsOnSameSide(line)) {
                if (line.isSegment()) {
                    if (line.boundsOnSameSide(this))
                        return !isParallel(line);
                    else
                        return false;
                }
                return !isParallel(line);
            }
            return false;
        }
        return !isParallel(line);
    }

    @Override
    public void getIntersection(Vector2 vect, Line line) {
        if (intersects(line)) {
            if (line instanceof SlopeConstantLine) {
                SlopeConstantLine l = (SlopeConstantLine) line;
                vect.x = (l.c - c) / (m - l.m);
                vect.y = m * vect.x + c;

            } else if (line instanceof InvertedSlopeConstantLine) {
                InvertedSlopeConstantLine l = (InvertedSlopeConstantLine) line;
                vect.x = (l.m * c + l.c) / (1 - m * l.m);
                vect.y = m * vect.x + c;

            }

        } else vect = null;
    }

    @Override
    public boolean isSegment() {
        return isSegment;
    }

    @Override
    public void makeSegment(float x1, float x2, float y1, float y2) {
        isSegment = true;
        xf = Float.min(x1, x2);
        xt = Float.max(x1, x2);
    }

    @Override
    public Line perpendicularAt(float x, float y) {
        float dy = y + 1;
        float dx = m * y + x - m * dy;

        return Line.fromTwoPoints(x, y, dx, dy);
    }

    @Override
    public Line parallelBy(float d, float x, float y) {
        float dc = d * hypot;
        if (Math.signum(put(0, c + dc)) != Math.signum(put(x, y)))
            dc = -dc;
        return new SlopeConstantLine(m, c + dc);
    }

    @Override
    public Line moveParallely(float d, float x, float y) {
        float dc = d * hypot;
        if (Math.signum(put(0, c + dc)) != Math.signum(put(x, y)))
            dc = -dc;
        c = c + dc;
        return this;
    }

    @Override
    public Vector2 footOfPerpendicular(float x, float y) {
        float matter = -put(x, y) / magnum;
        return new Vector2(x + m * matter, y - matter);
    }

    @Override
    public String toString() {
        return String.format("y = %3.3f * x + %3.3f", m, c);
    }

    @Override
    public Line clone() {
        return new SlopeConstantLine(m, c);
    }
}


