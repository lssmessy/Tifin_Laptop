package com.tifinnearme.priteshpatel.tifinnearme.pushnotifications;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by pritesh.patel on 20-05-15.
 */
public interface Config {


    // CONSTANTS
    static final String YOUR_SERVER_URL =
            "whtsnext.net63.net/apis/register.php";

    // Google project id
    static final String GOOGLE_SENDER_ID = "936411899766";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM Android Example";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.tifinnearme.priteshpatel.tifinnearme.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";
    GoogleCloudMessaging gcm=new GoogleCloudMessaging();


}