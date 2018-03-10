package c.r.musicro;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


// to disable rotate

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
            public void onActivityDestroyed(Activity activity){}
            public void onActivityPaused(Activity activity){}
            public void onActivitySaveInstanceState(Activity activity, Bundle outState){}
            public void onActivityResumed(Activity activity){}
            public void onActivityStopped(Activity activity){}
            public void onActivityStarted(Activity activity){}
        });
    }
}

