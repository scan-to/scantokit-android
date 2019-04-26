package tech.aiq.aiqkit.sample.photo;

import android.app.Application;
import android.util.Log;

import tech.aiq.kit.AIQKit;

public class PhotoApplication extends Application {
    private static final String TAG = PhotoApplication.class.getSimpleName();
    private final String USER_ID = "99";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "PhotoApplication Started");

        //initialize aiqkit
        AIQKit.Companion.init(this, BuildConfig.AIQ_APP_ID, BuildConfig.AIQ_APP_SECRET, USER_ID, true, true);
        Log.d(TAG, "Initialized AIQKit SDK");
    }
}
