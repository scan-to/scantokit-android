Scanto aiqkit Android SDK sample implementation With Java
-----------------

The Android SDK gives you access to the powerful Scanto Vision Search platform to integrate into your Android app. It uses [AIQApi]("https://github.com/scan-to/AIQApi-Android") library for the api calls.

#### AppID and Ingestion

Before using the sdk, an AppID/Secret pair is required. This can be obtained from the client dashboard at https://dashboard.scanto.tech/. 
You also need to ingest some image/pdf/video before you can search for them.

#### Add aiqkit as a Dependency.

add to your build.gradle:
```
dependencies {
    implementation 'tech.aiq:aiqkit:3.0'
}
```
make sure you have the jcenter repo in your top level (project) build.gradle:

```
allprojects {
    repositories {
        jcenter()
    }
}
```

#### Initlaize the SDK with AppID and Secret
Before using the kit, we need to initialise the SDK with AppId and Secret.
Best practice to call this at Application class.
```
AIQKit.Companion.init(context, "AppID", "Secret", "UserId", isProduction, enableLog);
```
To use the example apps, define the following in your gradle.properties file:

```
AIQ_APP_ID=enter AppID here
AIQ_APP_SECRET=enter Secret here
```
**UserId**, a value that can be used to uniquely identify an application user. Maximum length 100 characters; Excess will be truncated automatically.

You are able to change this UserId with:
```
AIQKit.Companion.changeUserConfiguration(userId, age, gender);
```
Age and gender are optional, you can pass null.
Gender is either "male", "female", or "other".
These data are important for statistic report that you can see in Scanto dashboard.
UserId will be counted as how many user use your application. So it's best practice to set this UserId based on your app's user login id or android unique id (if you don't have login feature on your app)

**isProduction** you can set either true or false, if true then it will point to production url https://api.scanto.tech, and if false it will point to staging url https://api.stage.scanto.tech.
You can override the base url with:
```
AIQKit.Companion.setBaseUrl("url");
```
**enableLog** if true will print api call logs, else false.

#### Usages
##### Authentication
The SDK can do authentication with server automatically. Though you can manually authenticate by calling:
```
AIQKit.Companion.auth()
```

##### Launch Scanner Activity
There are 3 ways on using the scanner. 
1. is by using Scanto default scanner by calling :
```
AIQKit.Companion.startScanner(context);

//scan within the specific collectionId
AIQKit.Companion.startScanner(context, collectionId);
```
The scan result will be automatically opened in a web activity.

2. is by using AIQ default scanner but handle the payload by yourself :
```
AIQKit.Companion.startScannerForResult(activity, requestCode);

//scan within the specific collectionId
AIQKit.Companion.startScannerForResult(activity, collectionId, requestCode);

```
and handle the scan result :
```
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_AIQ_SCAN && resultCode == Activity.RESULT_OK) {
            final PayloadData payloadData = AIQKit.getPayloadDataFromIntentResult(data);
            //do what you want with the data
        }
    }
```

3. is by subclassing the IqKitScannerActivity class
```
public class MyScannerActivity extends IqKitScannerActivity {

    /**
     * override the payload data handler. 
     * if you don't override (or if call super.onPayloadDataReady(), by default it will launch the web activity
     */
    @Override
    protected void onPayloadDataReady(PayloadData payloadData) {
        //do your things here

        //after that call this function to start scanning again
        readyForNextTrigger();
    }
}
```
##### Matching Bitmap And Image Url Manually
If you prefer to build your scanner, or if you want to directly match the bitmap/image url, you can use AIQKit.Companion.matchImage() functions:
```
//1. match bitmap
AIQKit.Companion.matchImage(bitmap, new Continuation<PayloadData>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull final Object o) {
                if (o instanceof PayloadData) {
                    //process your payload
                }else{
                    //possible error happened
                }
            }
        });
        
//2. match bitmap with specific collection id
AIQKit.Companion.matchImage(bitmap, "collectionId", new Continuation<PayloadData>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull final Object o) {
                if (o instanceof PayloadData) {
                    //process your payload
                }else{
                    //possible error happened
                }
            }
        });
        
//3. match image url
AIQKit.Companion.matchImage(imageUrlString, new Continuation<PayloadData>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull final Object o) {
                if (o instanceof PayloadData) {
                    //process your payload
                }else{
                    //possible error happened
                }
            }
        });
        
//4. match image url with specific collection id
AIQKit.Companion.matchImage(imageUrlString, "collectionId", new Continuation<PayloadData>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull final Object o) {
                if (o instanceof PayloadData) {
                    //process your payload
                }else{
                    //possible error happened
                }
            }
        });
```

##### Miscellaneous
###### Theme
AIQ SDK has it's own theme. So if your application also has custom theme, then you need to add
```
tools:replace="android:theme"
```
in Manifest's application tag to resolve the merge failed problem.
###### Packaging Options
At build.gradle, add this to resolve duplicate issues that you may found later:
```
packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/ASL2.0'
        exclude 'META-INF/ktor-http.kotlin_module'
        exclude 'META-INF/kotlinx-io.kotlin_module'
        exclude 'META-INF/atomicfu.kotlin_module'
        exclude 'META-INF/ktor-utils.kotlin_module'
        exclude 'META-INF/kotlinx-coroutines-io.kotlin_module'
        exclude 'META-INF/ktor-client-json.kotlin_module'
        exclude 'META-INF/ktor-client-core.kotlin_module'
        exclude 'META-INF/ktor-client-logging.kotlin_module'
    }
```
#### Proguard
Add this to your proguard file:
```
-keep class io.ktor.** { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.atomicfu.**
-dontwarn io.netty.**
-dontwarn com.typesafe.**
-dontwarn org.slf4j.**
-keep class tech.aiq.api.** { *; }
-keep class tech.aiq.kit.core.room.entity.** { *; }
-keep class tech.aiq.kit.core.model.** { *; }
-optimizations !class/unboxing/enum
```
