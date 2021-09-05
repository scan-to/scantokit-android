package tech.scanto.scantokit.sample.camera;

import android.app.Application;
import android.util.Log;

import tech.scanto.kit.ScantoKit;

public class CameraApplication extends Application {
    private static final String TAG = CameraApplication.class.getSimpleName();

    private final String USER_ID = "99";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "CameraApplication Started");

        //initialize scantokit
        ScantoKit.Companion.init(this, BuildConfig.SCANTO_APP_ID, BuildConfig.SCANTO_APP_SECRET, USER_ID, true, true);
        Log.d(TAG, "Initialized ScantoKit SDK");
    }
}
