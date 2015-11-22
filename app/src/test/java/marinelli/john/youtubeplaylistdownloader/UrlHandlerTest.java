package marinelli.john.youtubeplaylistdownloader;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test UrlHandler.
 */
public class UrlHandlerTest {

    public UrlHandler mUrlHandler;

    @Before
    public void setUp() throws Exception {
        mUrlHandler = new UrlHandler();
    }

    @Test
    public void testGetUrlTypeForIrrelevantUrl() throws Exception {
        UrlHandler.UrlType type = mUrlHandler.getUrlType(new URL("http://google.com"));
        assertEquals("Empty URL string gives None type", UrlHandler.UrlType.NONE, type);
    }

    @Test
    public void testGetUrlTypeForYoutubePlaylistUrl() throws Exception {
        UrlHandler.UrlType type = mUrlHandler.getUrlType(
                new URL("https://www.youtube.com/playlist?list=PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E"));
        assertEquals("Youtube URL playlist string gives Youtube playlist type", UrlHandler.UrlType.YOUTUBE_PLAYLIST, type);
    }

    @Test
    public void testGetUrlTypeForYoutubeSongUrl() throws Exception {
        UrlHandler.UrlType type = mUrlHandler.getUrlType(
                new URL("https://www.youtube.com/watch?v=zQCc1KrqwbM&index=1&list=PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E"));
        assertEquals("Youtube URL song string gives Youtube song type", UrlHandler.UrlType.YOUTUBE_VIDEO, type);
    }

    @Test
    public void testGetUrlTypeForSoundcloudPlaylistUrl() throws Exception {
        UrlHandler.UrlType type = mUrlHandler.getUrlType(
                new URL("https://soundcloud.com/to-pimp-a-butterfly/sets/slime-season-2-young-thug"));
        assertEquals("Soundcloud URL playlist string gives soundcloud playlist type", UrlHandler.UrlType.SOUNDCLOUD_PLAYLIST, type);
    }

    @Test
    public void testGetUrlTypeForSoundcloudSongUrl() throws Exception {
        UrlHandler.UrlType type = mUrlHandler.getUrlType(
                new URL("https://soundcloud.com/lays-stay-kettle-cooked/future-thought-it-was-a-drought-dirty-sprite-2"));
        assertEquals("Soundcloud URL song string gives soundcloud song type", UrlHandler.UrlType.SOUNDCLOUD_SINGLE, type);
    }

    @Test
    public void testGetMediaIdFromUrlWithNoneType() throws Exception {
        String mediaId = mUrlHandler.getMediaId(UrlHandler.UrlType.NONE, new URL("http://google.com"));
        assertEquals("Irrelevant link gives no media Id", -1, mediaId);
    }

    @Test
    public void testGetMediaIdFromUrlWithYoutubePlaylistType() throws Exception {
        String mediaId = mUrlHandler.getMediaId(UrlHandler.UrlType.YOUTUBE_PLAYLIST,
                new URL("https://www.youtube.com/playlist?list=PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E"));
        assertEquals("UrlHandler retrieves youtube playlist id", "PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E", mediaId);
    }

    @Test
    public void testGetMediaIdFromUrlWithYoutubeVideoType() throws Exception {
        String mediaId = mUrlHandler.getMediaId(UrlHandler.UrlType.YOUTUBE_VIDEO,
                new URL("https://www.youtube.com/watch?v=zQCc1KrqwbM&index=1&list=PLVnVsHKss1OUT6tBQLembiZTmhrny8N8E"));
        assertEquals("UrlHandler retrieves youtube video id", "zQCc1KrqwbM", mediaId);
    }

    @Test
    public void testGetMediaIdFromUrlWithSoundcloudSongType() throws Exception {
        String mediaId = mUrlHandler.getMediaId(UrlHandler.UrlType.SOUNDCLOUD_SINGLE,
                new URL("https://soundcloud.com/lays-stay-kettle-cooked/future-thought-it-was-a-drought-dirty-sprite-2"));
        assertEquals("UrlHandler retrieves soundcloud song id", -1, mediaId);
    }

    @Test
    public void testGetMediaIdFromUrlWithSoundcloudPlaylistType() throws Exception {
        String mediaId = mUrlHandler.getMediaId(UrlHandler.UrlType.SOUNDCLOUD_SINGLE,
                new URL("https://soundcloud.com/to-pimp-a-butterfly/sets/slime-season-2-young-thug"));
        assertEquals("UrlHandler retrieves soundcloud playlist id", -1, mediaId);
    }
}