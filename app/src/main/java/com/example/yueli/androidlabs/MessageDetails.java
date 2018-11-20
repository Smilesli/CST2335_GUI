package com.example.yueli.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class MessageDetails extends Activity {
    private static final String TAG = "ChatWindowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Log.i(TAG, "in onCreate()");
        String message = getIntent().getExtras().getString("message");
        int position = getIntent().getExtras().getInt("position");
        long id = getIntent().getExtras().getLong("id");

        BlankFragment newFragment = new BlankFragment();
        newFragment.iAmTablet = false;
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("position",position);
        bundle.putLong("id",id);
        newFragment.setArguments(bundle);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        ftrans.replace(R.id.message_detail, newFragment);
        ftrans.addToBackStack(null);
        ftrans.commit();
    }
}
