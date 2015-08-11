package com.example.cansu.androidwebservice.app;

/**
 * Created by Cansu on 8/11/2015.
 */
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AndroidWebServer extends Application {

    public static final String TAG = AndroidWebServer.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static AndroidWebServer mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AndroidWebServer getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    // TODO adding request to queue
    // Request'i volley'in Q sirasina atiyoruz
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    // TODO adding request to queue
    // Request'i volley'in Q sirasina atiyoruz
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    // TODO cancel request from queue
    // Request'i volley'in Q sirasindan cikariyoruz
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
