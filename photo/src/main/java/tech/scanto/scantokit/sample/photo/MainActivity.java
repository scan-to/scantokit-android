package tech.scanto.scantokit.sample.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import tech.scanto.api.model.PayloadData;
import tech.scanto.kit.ScantoKit;
import tech.scanto.kit.core.util.BitmapUtils;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.pick_image)), 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri == null) {
                Log.e(TAG, "No uri returned, cannot search");
                return;
            }

            //load bitmap and resize it
            final Bitmap bitmap = BitmapUtils.getSizeRestrictedBitmap(this,
                    uri,
                    ScantoKit.LONGEST_SIDE_DESIRED_PIXELS,
                    false, false);

            ScantoKit.Companion.matchImage(bitmap, new Continuation<PayloadData>() {
                @NotNull
                @Override
                public CoroutineContext getContext() {
                    return EmptyCoroutineContext.INSTANCE;
                }

                @Override
                public void resumeWith(@NotNull final Object o) {

                    bitmap.recycle();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (o instanceof PayloadData) {
                                PayloadData payloadData = (PayloadData) o;
                                if (payloadData.isSuccess()) {
                                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(payloadData.getPayloadUrl()));
                                    startActivity(myIntent);
                                } else {
                                    showToast("No match found");
                                }
                            } else {
                                showToast("No match found");
                            }
                        }
                    });
                }
            });
        }
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
