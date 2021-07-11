import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileDissectorOverloader_jstmp {

    /**
     * Matches two groups between "createDissectorMethod" the next occurring semi-colon.
     * Group 1 => Method Name
     * Group 2 => Other parameters to be matched by other patterns.
     */
    static Pattern rgx_methodCall = Pattern.compile("overloadDissectorMethod\\(\"(\\w+)\"\\)([^;]+) \\{");

    /**
     * Matches two groups between ".parameters" and next occurring closing parenthesis.
     * Group 1 => Parameter Name
     * Group 2 => A possible String block to be parsed further
     */
    static Pattern rgx_javadocParameter = Pattern.compile("\\.parameter\\(\"(.*)\", ([^)]+)\\)");

    /**
     * Matches a single group between ".description" and next occurring closing parenthesis.
     * Group 1 => A possible String block to be parsed further
     */
    static Pattern rgx_javadocReturn = Pattern.compile("\\.description\\(([^)]+)\\)");

    public static String parseSource(String code) {

        String javaCode = code;

        Matcher methodCall;
        while ((methodCall = rgx_methodCall.matcher(javaCode)).find()) {

            // All the code which lyes before the text "createDissectorMethod"
            String pretext = javaCode.substring(0, methodCall.start());
            // All the code which lyes after "createDissectorMethod" block, or more specifically, the code which lye after the first semi colon after "createDissectorMethod"
            String suftext = javaCode.substring(methodCall.end(2));

            String methodName = methodCall.group(1);

            String subContext = javaCode.substring(methodCall.start(2), methodCall.end(2));

            // A temporary list to store method names and their javadocs
            ArrayList<String> parameterNames = new ArrayList<>();
            ArrayList<String> parameterDocs = new ArrayList<>();

            // Creating a dedicated matcher to obtain the required parameter names and docs
            Matcher javadocsMat = rgx_javadocParameter.matcher(subContext);
            while (javadocsMat.find()) {
                parameterNames.add(javadocsMat.group(1));
                parameterDocs.add(parseStringBlock(javadocsMat.group(2)));
            }

            assert parameterNames.size() == parameterDocs.size();

            // Writing the big mess docs here
            StringBuilder contextJavaDocs = new StringBuilder("/**\n");
            for (int i = 0, s = parameterNames.size(); i < s; i++)
                param(contextJavaDocs, parameterNames.get(i), parameterDocs.get(i));
            param(contextJavaDocs, "fade", /*         */ "If true, fades the image about the cuts by fadeLength.");
            param(contextJavaDocs, "fadeLength", /*   */ "The length by which the fade takes effect.");
            param(contextJavaDocs, "raw", /*          */ "If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.");
            param(contextJavaDocs, "interpolator", /* */ "The interpolator to be used to fade the pixels.");
            param(contextJavaDocs, "clone", /*        */ "If true returns a completely new array and doesn't disturbs values in the given array (raw).");
            param(contextJavaDocs, "deNull", /*       */ "If true changes all null values to transparent colors.");
            if ((javadocsMat = rgx_javadocReturn.matcher(subContext)).find()) {
                contextJavaDocs.append("     * @return ");
                String[] strings = parseStringBlock(javadocsMat.group(1)).trim().split("\n");
                assert strings.length >= 1;
                contextJavaDocs.append(strings[0]).append("\n");
                for (int i = 1; i < strings.length; i++)
                    contextJavaDocs.append("     * ").append(strings[i].trim()).append("\n");
            }
            contextJavaDocs.append("     */\n");

            StringBuilder contextMethod = new StringBuilder("    public static Color[][] ");
            contextMethod.append(methodName).append("(");
            for (String parameterName : parameterNames)
                contextMethod.append("int ").append(parameterName).append(", ");
            contextMethod.append("boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull)");

            javaCode = pretext + contextJavaDocs + contextMethod + suftext;
        }


        return javaCode;
    }

    private static String parseStringBlock(String block) {
        return block.substring(3, block.length() - 3).replaceAll("[ ]{2,}", " ");
    }

    private static void param(StringBuilder builder, String name, String docs) {
        builder.append("     * @param ").append(name).append(" ").append(docs).append("\n");
    }

}


/*

    /**
     * @param height       The height from top of original image to be used as the height of the rectangle to be cropped.
     * @param fade         If true, fades the image about the cuts by fadeLength.
     * @param fadeLength   The length by which the fade takes effect.
     * @param raw          If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.
     * @param interpolator The interpolator to be used to fade the pixels.
     * @param clone        If true returns a completely new array and doesn't disturbs values in the given array (raw).
     * @param deNull       If true changes all null values to transparent colors.
     * @return Returns the top part of original pixels cropped like a rectangle of the same width.
     * The dimensions remain the same, ie, the remaining pixels are Null or Transparent.
     * /
    public static Color[][] getTopRect_lol(int height, boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {
        raw = arrayPreCheck(raw, clone);
        ArrUtil.retainByPredicate(raw, (x, y) -> y < height + fadeLength);
        if (fade) ArrUtil.fade(raw, interpolator, 0, height, 0, height + fadeLength);
        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);
        return raw;
    }

 */
