package tech.scanto.scantokit.sample.scanner;

import android.app.Application;
import android.util.Log;

import tech.scanto.kit.ScantoKit;

public class ScannerApplication extends Application {

    private static final String TAG = ScannerApplication.class.getSimpleName();

    private final String USER_ID = "99";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "ScannerApplication Started");

        //initialize scantokit
        ScantoKit.Companion.init(this, BuildConfig.SCANTO_APP_ID, BuildConfig.SCANTO_APP_SECRET, USER_ID, true, true);
        Log.d(TAG, "Initialized ScantoKit SDK");
    }
}
