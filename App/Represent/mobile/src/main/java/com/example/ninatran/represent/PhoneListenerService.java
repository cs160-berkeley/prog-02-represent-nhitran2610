package com.example.ninatran.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by ninatran on 3/3/16.
 */
public class PhoneListenerService extends WearableListenerService {
    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.

    private static final String PHONE_MESSAGE_PATH = "/message";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(PHONE_MESSAGE_PATH) ) {

            // Value contains the String we sent over in WatchToPhoneService, "good job"
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            // Make a toast with the String
            Context context = getApplicationContext();
            Intent intent = new Intent(this, DetailView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d("MESSAGE", " " + value);
            String[] s = value.split("\\|");
            Log.d("PRINT_S0", s[0]);
            Log.d("PRINT_S1", s[1]);
            Log.d("PRINT_S2", s[2]);
            Log.d("PRINT_S3", s[3]);
            intent.putExtra("title", s[0]);
            intent.putExtra("party", s[1]);
            intent.putExtra("term_end", s[2]);
            intent.putExtra("rep_id", s[3]);

            startActivity(intent);

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
