import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class list_jstmp {

    public static Pattern rgx_listBox = Pattern.compile("(\\[([\\w\\n\\r ]+(?:[,][\\w\\n\\r ]+)+)\\])");

    public static String parseSource(String code) {

        String out = code;

        Matcher matcher;
        while ((matcher = rgx_listBox.matcher(out)).find()) {

            String pretext = out.substring(0, matcher.start(1));
            String suftext = out.substring(matcher.end(1));

//            String context = matcher.group(2);

            String context = "((java.util.function.Supplier<ArrayList<Object>>) () -> {" +
                    " ArrayList<Object> kutsdgcbtsdc = new ArrayList<>();";
            for (String var : matcher.group(2).split(","))
                context += " kutsdgcbtsdc.add("+var+");";
            context += " return kutsdgcbtsdc;" +
                    " }).get()";

//            ArrayList list = ((Supplier<ArrayList<?>>) () -> {
//                ArrayList<Object> kutsdgcbtsdc = new ArrayList<>();
//                kutsdgcbtsdc.add("A");
//                return kutsdgcbtsdc;
//            }).get();

            out = pretext + context + suftext;

        }

        return out;

    }
}
