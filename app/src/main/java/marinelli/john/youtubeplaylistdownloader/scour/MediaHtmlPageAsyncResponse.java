package marinelli.john.youtubeplaylistdownloader.scour;

import java.util.ArrayList;

import marinelli.john.youtubeplaylistdownloader.MediaModel;

/**
 * little hack for .then() implementation
 * http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
 */
public interface MediaHtmlPageAsyncResponse {
    public void processInputUrlFinish(ArrayList<MediaModel> out);
}
