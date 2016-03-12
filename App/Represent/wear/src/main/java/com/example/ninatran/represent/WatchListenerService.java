package com.example.ninatran.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by ninatran on 3/3/16.
 */
public class WatchListenerService extends WearableListenerService {

    private static final String WEAR_MESSAGE_PATH = "/message";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());


        if( messageEvent.getPath().equalsIgnoreCase(WEAR_MESSAGE_PATH) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            intent.putExtra("JSON", value);
            Log.d("T", "about to start watch MainActivity with ZIP_CODE: 94709");
            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
