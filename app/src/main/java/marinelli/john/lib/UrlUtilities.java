package marinelli.john.lib;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utilities to handle URLs.
 */
public class UrlUtilities {
    public static URL encodeURL(URL url) {
        URI uri = null;
        URL encodedUrl = null;
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
                    url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            encodedUrl = uri.toURL();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return encodedUrl;
    }
}
