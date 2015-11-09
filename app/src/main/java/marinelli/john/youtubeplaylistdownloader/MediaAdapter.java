package marinelli.john.youtubeplaylistdownloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for displaying song data and download button.
 */
public class MediaAdapter extends ArrayAdapter<MediaModel> {
    private ArrayList<MediaModel> mMedia;
    Context mContext;

    public MediaAdapter(Context context, int textViewResourceId, ArrayList<MediaModel> media) {
        super(context, textViewResourceId, media);
        mMedia = media;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.video_list_item, null);
        }

        MediaModel media = mMedia.get(position);
        if (media != null) {
            TextView firstLine = (TextView) v.findViewById(R.id.first_line);
            TextView secondLine = (TextView) v.findViewById(R.id.second_line);

            if (firstLine != null) firstLine.setText(media.mTitle);
            if (secondLine != null) secondLine.setText(media.mArtist);
        }
        return v;
    }
}
