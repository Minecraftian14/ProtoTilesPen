package com.mcxiv.app.util;

import java.util.Objects;

public class Vector2 {
    public float x, y;

    public Vector2() {
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float len() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 set(float a, float b) {
        x = a;
        y = b;
        return this;
    }

    public Vector2 nor() {
        float len = len();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }

    public Vector2 scl(float scl) {
        x *= scl;
        y *= scl;
        return this;
    }

    public Vector2 add(int a, int b) {
        x+=a;
        y+=y;
        return this;
    }

    public int x() {
        return (int) x;
    }

    public int y() {
        return (int) y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 && Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
