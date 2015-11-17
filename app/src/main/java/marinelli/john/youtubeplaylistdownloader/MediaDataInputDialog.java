package marinelli.john.youtubeplaylistdownloader;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Custom dialog box for inputting artist and title for a piece of media.
 */
public class MediaDataInputDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private EditText mTitleEntry, mArtistEntry;
    private Button mCancel, mContinue;

    public MediaDataInputDialog(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.media_data_input_dialog);

        mTitleEntry = (EditText) findViewById(R.id.title);
        mArtistEntry = (EditText) findViewById(R.id.artist);
        mContinue = (Button) findViewById(R.id.submit_media_data);
        mCancel = (Button) findViewById(R.id.cancel);

        mContinue.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_media_data: {
                 mActivity.finish();
            }
            break;
            case R.id.cancel: {
                dismiss();
            }
            break;
            default: {
                dismiss();
            }
            break;
        }
    }
}
