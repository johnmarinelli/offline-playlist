package marinelli.john.youtubeplaylistdownloader;

import java.util.ArrayList;

/**
 * little hack for .then() implementation
 * http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
 */
public interface YoutubeLinkRetrieverAsyncResponse {
    public void processYoutubeLinkRetrievalFinish(ArrayList<String> out);
}
