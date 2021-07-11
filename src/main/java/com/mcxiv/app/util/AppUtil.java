package com.mcxiv.app.util;

import java.io.File;

public class AppUtil {

    public static final float ROOT_2 = (float) Math.sqrt(2);

    public static String path(String... dirs) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < dirs.length - 1; i++)
            builder.append(dirs[i]).append(File.separator);
        return builder.append(dirs[dirs.length - 1]).toString();
    }

}
