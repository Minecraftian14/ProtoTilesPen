package com.mcxiv.app.util;

/**
 * <p> Creates a line object, which programmatically exists in the form <b> x = my + c </b> </p>
 */
public class InvertedSlopeConstantLine implements Line {

    /**
     * m = slope of the line as m = x/y.
     * c = width constant of the line.
     */
    float m, c;

    float magnum;
    float hypot;

    /**
     * <p> Stores a bool regarding if it is a line segment. </p>
     */
    boolean isSegment;

    /**
     * <p> part of line between it's abscissa belonging from yf to yt </p>
     */
    float yf, yt;

    /**
     * degrees = slope of the line as x/y.
     * c = width constant of the line.
     */
    InvertedSlopeConstantLine(float m, float c) {
        this.m = m;
        this.c = c;
        magnum = 1 + m * m;
//        hypot = (float) Math.hypot(m, 1);
        hypot = (float) Math.sqrt(magnum);
    }

    @Override
    public float yint() {
        return -c / m;
    }

    @Override
    public float xint() {
        return c;
    }

    @Override
    @Deprecated
    public float funcX(float x) {
        return (x - c) / m;
    }

    @Override
    public float funcY(float y) {
        return m * y + c;
    }

    @Override
    public float getSlope() {
        return 1 / m;
    }

    @Override
    public float distance(float x, float y) {
        return Math.abs(m * y - x + c) / hypot;
    }

    @Override
    public float distance(Line line) {
        if (isParallel(line)) return Math.abs(xint() - line.xint()) / hypot;
        return 0;
    }

    @Override
    public boolean isParallel(Line line) {
        if (line instanceof InvertedSlopeConstantLine) {
            InvertedSlopeConstantLine l = (InvertedSlopeConstantLine) line;
            return Float.compare(m, l.m) == 0 && Float.compare(c, l.c) != 0;

        } else if (line instanceof SlopeConstantLine) {
            SlopeConstantLine l = (SlopeConstantLine) line;
            return Float.compare(m, 1f / l.m) == 0 && Float.compare(c, -1f / l.c) != 0;

        }
        return false;
    }

    @Override
    public void normal(Vector2 vect, float x, float y) {
        normal(vect);
        vect.scl(-put(x, y));
    }

    @Override
    public void normal(Vector2 vect) {
        vect.set(1, -m);
        vect.nor();
    }

    @Override
    public float put(float x, float y) {
        return m * y + c - x;
    }

    @Override
    public boolean isBounded(float x, float y) {
        if (isSegment) return (m * yf + c < x && x < m * yt + c) && (yf < y && y < yt);
        return true;
    }

    @Override
    public boolean boundsOnSameSide(Line line) {
        return line.put(m * yf + c, yf) != line.put(m * yt + c, yt);
    }

    @Override
    public boolean intersects(float x, float y) {
        return isBounded(x, y) && Float.compare(x, m * y + c) == 0;
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
            if (line instanceof InvertedSlopeConstantLine) {
                InvertedSlopeConstantLine l = (InvertedSlopeConstantLine) line;
                vect.y = (l.c - c) / (m - l.m);
                vect.x = m * vect.y + c;

            } else if (line instanceof SlopeConstantLine) {
                SlopeConstantLine l = (SlopeConstantLine) line;
                vect.y = (l.m * c + l.c) / (1 - m * l.m);
                vect.x = m * vect.y + c;

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
        yf = Float.min(y1, y2);
        yt = Float.max(y1, y2);
    }

    @Override
    public Line perpendicularAt(float x, float y) {
        float dx = x + 1;
        float dy = m * x + y - m * dx;

        return Line.fromTwoPoints(x, y, dx, dy);
    }

    @Override
    public Line parallelBy(float d, float x, float y) {
        float dc = d * hypot;
        if (Math.signum(put(c + dc, 0)) != Math.signum(put(x, y)))
            dc = -dc;
        return new InvertedSlopeConstantLine(m, c + dc);
    }

    @Override
    public Line moveParallely(float d, float x, float y) {
        float dc = d * hypot;
        if (Math.signum(put(c + dc, 0)) != Math.signum(put(x, y)))
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
        return String.format("x = %3.3f * y + %3.3f", m, c);
    }

    @Override
    public Line clone() {
        return new InvertedSlopeConstantLine(m, c);
    }
}


