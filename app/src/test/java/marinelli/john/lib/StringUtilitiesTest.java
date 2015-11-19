package marinelli.john.lib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests StringUtilities.
 */
public class StringUtilitiesTest {

    @Test
    public void testNaiveRegexMatches() throws Exception {
        String s1 = "abcdefghijklmnopqrstuvwxyz";
        String regex = "abc";
        assertEquals("Naive regex doesn't match.", StringUtilities.regexMatches(regex, s1), true);
    }

    @Test
    public void testAppSpecificRegexMatches() throws Exception {
        String s1 = "youtube.com/playlist?list=PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E";
        String regex = "youtube.com";
        assertEquals("App specific regex doesn't match.", StringUtilities.regexMatches(regex, s1), true);
    }
}