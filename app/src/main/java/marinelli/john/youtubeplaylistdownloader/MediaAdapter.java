package marinelli.john.youtubeplaylistdownloader;

import android.app.DownloadManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;

/**
 * Adapter for displaying song data and download button.
 */
public class MediaAdapter extends ArrayAdapter<MediaModel> {
    private ArrayList<MediaModel> mMedia;
    private DownloadManager mDownloadManager;
    private Context mContext;

    public MediaAdapter(Context context, int textViewResourceId, ArrayList<MediaModel> media,
                        DownloadManager dm) {
        super(context, textViewResourceId, media);
        mMedia = media;
        mContext = context;
        mDownloadManager = dm;
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
            BootstrapButton download = (BootstrapButton) v.findViewById(R.id.download_button);

            firstLine.setText(media.mTitle);
            secondLine.setText(media.mArtist);

            download.setOnClickListener(new MediaOnClickListener(media, mDownloadManager));
        }
        return v;
    }
}
