package tech.scanto.scantokit.sample.photo;

import android.app.Application;
import android.util.Log;

import tech.scanto.kit.ScantoKit;

public class PhotoApplication extends Application {
    private static final String TAG = PhotoApplication.class.getSimpleName();
    private final String USER_ID = "99";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "PhotoApplication Started");

        //initialize scantokit
        ScantoKit.Companion.init(this, BuildConfig.SCANTO_APP_ID, BuildConfig.SCANTO_APP_SECRET, USER_ID, true, true);
        Log.d(TAG, "Initialized ScantoKit SDK");
    }
}
