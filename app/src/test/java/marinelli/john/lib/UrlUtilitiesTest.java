package marinelli.john.lib;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Tests UrlUtilities.
 */
public class UrlUtilitiesTest {

    @Test
    public void testEncodeURL() throws Exception {
        URL url = new URL("http://google.com?a=b c d");
        assertEquals("UrlUtilities::encodeUrl encodes Url.",
                "http://google.com?a=b%20c%20d", UrlUtilities.encodeURL(url).toString());
    }
}