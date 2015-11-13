package marinelli.john.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utilities.
 */
public class StringUtilities {
    // LMAO JAVA WHY DO I EVEN HAVE TO WRITE THIS
    public static boolean regexMatches(String regex, String inputString) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(inputString);
        return m.find();
    }
}
