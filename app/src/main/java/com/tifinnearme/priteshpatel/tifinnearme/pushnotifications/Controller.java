package com.tifinnearme.priteshpatel.tifinnearme.pushnotifications;

import android.app.Application;
import android.content.Context;

import com.tifinnearme.priteshpatel.tifinnearme.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by pritesh.patel on 20-05-15.
 */
public class Controller extends Application {
    private static int MAXIMUM_ATTEMPTS=5;
    private Random random=new Random();
    void register(Context context,String name,String email,String regId){

        String serverUrl=Config.YOUR_SERVER_URL;
        Map<String, String> params=new HashMap<String,String>();
        params.put("regId",regId);
        params.put("name",name);
        params.put("email",email);
        long backoff = 2000 + random.nextInt(1000);

        for(int i=1; i<=MAXIMUM_ATTEMPTS;i++){
            displayMessageOnScreen(context,context.getString(R.string.server_registring,i,MAXIMUM_ATTEMPTS));
            post(serverUrl,params);
            try {
                Config.gcm.register(Config.GOOGLE_SENDER_ID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void post(String serverUrl, Map<String, String> params) {
    }

    private void displayMessageOnScreen(Context context, String string) {

    }

}
