import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileDissectorMethod_jstmp {

    /**
     * Matches two groups between "createDissectorMethod" the next occurring semi-colon.
     * Group 1 => Method Name
     * Group 2 => Other parameters to be matched by other patterns.
     */
    static Pattern rgx_methodCall = Pattern.compile("createDissectorMethod\\(\"(\\w+)\"\\)([^;]+);");

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

    /**
     * Matches a single group between ".crop" and next occurring closing parenthesis.
     * Group 1 => A possible Lambda expression w/o starting clause, ie, w/o "(x, y) -> " part.
     */
    static Pattern rgx_cropCall = Pattern.compile("\\.crop\\(([^)]+)\\)");

    /**
     * Matches a single group between ".crop" and next occurring closing parenthesis.
     * Group 1 => Four Integral Parameters.
     */
    static Pattern rgx_fadeCall = Pattern.compile("\\.fade\\(([^)]+)\\)");

    public static String parseSource(String code) {

        String javaCode = code;

        Matcher methodCall;
        while ((methodCall = rgx_methodCall.matcher(javaCode)).find()) {

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // All the code which lyes before the text "createDissectorMethod"
            String pretext = javaCode.substring(0, methodCall.start());
            // All the code which lyes after "createDissectorMethod" block, or more specifically, the code which lye after the first semi colon after "createDissectorMethod"
            String suftext = javaCode.substring(methodCall.end());

            String methodName = methodCall.group(1);

            String subContext = javaCode.substring(methodCall.start(2), methodCall.end(2));

            // A temporary list to store method names and their javadocs
            ArrayList<String> parameterNames = new ArrayList<>();
            ArrayList<String> parameterDocs = new ArrayList<>();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Creating a dedicated matcher to obtain the required parameter names and docs
            Matcher javadocsMat = rgx_javadocParameter.matcher(subContext);
            while (javadocsMat.find()) {
                parameterNames.add(javadocsMat.group(1));
                parameterDocs.add(parseStringBlock(javadocsMat.group(2)));
            }

            assert parameterNames.size() == parameterDocs.size();

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Writing the big mess docs here

            StringBuilder contextJavaDocs = new StringBuilder("/**\n");

            // Adding in user defined @params javadoc elements
            for (int i = 0, s = parameterNames.size(); i < s; i++)
                param(contextJavaDocs, parameterNames.get(i), parameterDocs.get(i));

            // Adding in default @params javadoc elements, applicable to all methods
            param(contextJavaDocs, "fade", /*         */ "If true, fades the image about the cuts by fadeLength.");
            param(contextJavaDocs, "fadeLength", /*   */ "The length by which the fade takes effect.");
            param(contextJavaDocs, "raw", /*          */ "If it's null, a new array is returned based on the original pixels taken in by the constructor. If it's not null, the effect os applied on this array.");
            param(contextJavaDocs, "interpolator", /* */ "The interpolator to be used to fade the pixels.");
            param(contextJavaDocs, "clone", /*        */ "If true returns a completely new array and doesn't disturbs values in the given array (raw).");
            param(contextJavaDocs, "deNull", /*       */ "If true changes all null values to transparent colors.");

            // Adding in user defined @return javadoc elements
            if ((javadocsMat = rgx_javadocReturn.matcher(subContext)).find()) {
                contextJavaDocs.append("     * @return ");
                String[] strings = parseStringBlock(javadocsMat.group(1)).trim().split("\n");

                assert strings.length >= 1; // Hopefully, at least one line is written... lazy developer Minecraftian14
                contextJavaDocs.append(strings[0]).append("\n");

                for (int i = 1; i < strings.length; i++)
                    contextJavaDocs.append("     * ").append(strings[i].trim()).append("\n");
            }
            contextJavaDocs.append("     */\n");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Method definition starts
            StringBuilder contextMethod = new StringBuilder("    public static Color[][] ");
            contextMethod.append(methodName).append("(");

            // Adding in user defined parameters
            for (String parameterName : parameterNames)
                contextMethod.append("int ").append(parameterName).append(", ");

            // Adding in default initialisation all methods have in common
            contextMethod.append("boolean fade, int fadeLength, Color[][] raw, FloatFunction interpolator, boolean clone, boolean deNull) {").append("\n");
            contextMethod.append("        raw = arrayPreCheck(raw, clone);").append("\n");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Boom factor is an array of 0s or 1s storing information like, whether the user has use a specific variable or not.
            // Say, the user used `w_in` which is supposed to be an integral representation of width.
            // Then, the ones place (in binary) of boomFactor should be 1.
            int boomFactor = 0;

            // Here we create a list to store these method calls for later use.
            // We do so in order to keep track of user required variables in boomFactor.
            // That allows us to first add code for defining the "only" variables used.
            // Finally adding the code for crop and fade.
            ArrayList<String> cropCalls = new ArrayList<>();
            ArrayList<String> fadeCalls = new ArrayList<>();

            // Matching all `.crop()` calls and adding it to a list.
            Matcher cropMat = rgx_cropCall.matcher(subContext);
            while (cropMat.find()) {
                String match = cropMat.group(1);
                cropCalls.add(match);
                // read it's own doc
                boomFactor = updateBoomFactor(boomFactor, match);
            }

            // Matching all `.fade()` calls and adding it to a list.
            Matcher fadeMat = rgx_fadeCall.matcher(subContext);
            while (fadeMat.find()) {
                String match = fadeMat.group(1);
                fadeCalls.add(match);
                // read it's own doc
                boomFactor = updateBoomFactor(boomFactor, match);
            }

            // read it's own doc
            writeBoomFactor(contextMethod,boomFactor);

            // Writing the crop and fade calls
            for (String cropCall : cropCalls)
                contextMethod.append("        ArrUtil.retainByPredicate(raw, (x, y) -> ").append(cropCall).append(");").append("\n");

            contextMethod.append("        if (fade) {").append("\n");
            for (String fadeCall : fadeCalls)
                contextMethod.append("            ArrUtil.fade(raw, interpolator, ").append(fadeCall).append(");").append("\n");
            contextMethod.append("        }").append("\n");

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // Writing all the common actions required after the operations.
            contextMethod.append("        if (deNull) ArrUtil.deNull(raw, Color.INVISIBLE);").append("\n");
            contextMethod.append("        return raw;").append("\n");
            contextMethod.append("    }").append("\n");

            // Updating the java code.
            javaCode = pretext + contextJavaDocs + contextMethod + suftext;
        }

        return javaCode;
    }

    /**
     * A low quality String Block parser.
     * Here, String Block is any String data enclosed within a triplet of double quotes.
     */
    private static String parseStringBlock(String block) {
        return block.substring(3, block.length() - 3).replaceAll("[ ]{2,}", " ");
    }

    /**
     * A utility method for adding in params.
     * Ah... I guess i need JShorty here too xD
     */
    private static void param(StringBuilder builder, String name, String docs) {
        builder.append("     * @param ").append(name).append(" ").append(docs).append("\n");
    }

    /**
     * First read the boomFactor description written inside the above method.
     * This method changes the 0s to 1s in boomFactor at a specific index respective to the variable used.
     *
     * Note, that in certain cases we set more than one 0s to 1s.
     * That means that both the variables defined by those places are required.
     * It's useful in cases one variable depends on another.
     *
     * @param boomFactor the current boom factor.
     * @param context
     * @return the updated boomFactor.
     */
    private static int updateBoomFactor(int boomFactor, String context) {

        if ((boomFactor & 0b1) == 0 && context.contains("w_in"))
            boomFactor |= 0b1;

        if ((boomFactor & 0b10) == 0 && context.contains("h_in"))
            boomFactor |= 0b10;

        if ((boomFactor & 0b100) == 0 && context.contains("w_fl"))
            boomFactor |= 0b101;

        if ((boomFactor & 0b100_0) == 0 && context.contains("h_fl"))
            boomFactor |= 0b101_0;

        if ((boomFactor & 0b100_00) == 0 && context.contains("w_in_2"))
            boomFactor |= 0b101_00;

        if ((boomFactor & 0b100_000) == 0 && context.contains("h_in_2"))
            boomFactor |= 0b101_000;

        if ((boomFactor & 0b100_000_0) == 0 && context.contains("w_fl_2"))
            boomFactor |= 0b100_010_0;

        if ((boomFactor & 0b100_000_00) == 0 && context.contains("h_fl_2"))
            boomFactor |= 0b100_010_00;

        if ((boomFactor & 0b100_000_000) == 0 && context.contains("effectiveLength"))
            boomFactor |= 0b100_000_000;

        return boomFactor;
    }

    /**
     * Appends code required to define the variables required specifically by the user.
     *
     * @param builder the place where code is appended
     * @param boomFactor
     */
    private static void writeBoomFactor(StringBuilder builder, int boomFactor) {

        if ((boomFactor & 0b1) != 0)
            builder.append("        int w_in = raw.length;").append("\n");

        if ((boomFactor & 0b10) != 0)
            builder.append("        int h_in = raw[0].length;").append("\n");

        if ((boomFactor & 0b100) != 0)
            builder.append("        float w_fl = w_in;").append("\n");

        if ((boomFactor & 0b100_0) != 0)
            builder.append("        float h_fl = h_in;").append("\n");

        if ((boomFactor & 0b100_00) != 0)
            builder.append("        int w_in_2 = (int) (w_fl / 2);").append("\n");

        if ((boomFactor & 0b100_000) != 0)
            builder.append("        int h_in_2 = (int) (h_fl / 2);").append("\n");

        if ((boomFactor & 0b100_000_0) != 0)
            builder.append("        float w_fl_2 = w_fl / 2;").append("\n");

        if ((boomFactor & 0b100_000_00) != 0)
            builder.append("        float h_fl_2 = h_fl / 2;").append("\n");

        if ((boomFactor & 0b100_000_000) != 0)
            builder.append("        int effectiveLength = (int) (fadeLength * AppUtil.ROOT_2);").append("\n");
    }

}