package marinelli.john.youtubeplaylistdownloader;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

public class MainActivityTest
    extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mMainActivity;
    private EditText mUrlInput;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        mUrlInput = (EditText) mMainActivity.findViewById(R.id.playlist_url);
    }
}