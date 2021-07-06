package com.mcxiv.app.tasks;

import com.mcxiv.app.util.ArrUtil;
import com.mcxiv.app.util.Color;
import com.mcxiv.app.util.functions.FloatFunction;

import java.util.function.Predicate;

public interface Task {


    Color[][] act(Color[][] pixels);

    class Sequence implements Task {

        @Override
        public Color[][] act(Color[][] pixels) {
            return new Color[0][];
        }
    }

    class Fade implements Task {
        FloatFunction interpolator;
        int x1, y1, x2, y2;

        public Fade(FloatFunction interpolator, int x1, int y1, int x2, int y2) {
            this.interpolator = interpolator;
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public Color[][] act(Color[][] pixels) {
            ArrUtil.fade(pixels, interpolator, x1, y1, x2, y2);
            return pixels;
        }
    }

    class Retain implements Task {
        ArrUtil.BiPredicate predicate;

        public Retain(ArrUtil.BiPredicate predicate) {
            this.predicate = predicate;
        }

        @Override
        public Color[][] act(Color[][] pixels) {
            ArrUtil.retainByPredicate(pixels,predicate);
            return pixels;
        }
    }

    class DeNull implements Task {
        @Override
        public Color[][] act(Color[][] pixels) {
            return ArrUtil.deNull(pixels, Color.INVISIBLE);
        }
    }

    static Task sequence(Task... tasks) {
        return pixels -> {
            for (Task task : tasks) pixels = task.act(pixels);
            return pixels;
        };
    }

    static Task fade(FloatFunction interpolator, int x1, int y1, int x2, int y2) {
        return new Fade(interpolator, x1, y1, x2, y2);
    }

    static Task retain(ArrUtil.BiPredicate predicate) {
        return new Retain(predicate);
    }

    static Task deNull() {
        return new DeNull();
    }

}
