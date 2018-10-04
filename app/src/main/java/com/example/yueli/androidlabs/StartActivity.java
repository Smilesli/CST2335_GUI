package com.example.yueli.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    Button b1;
    private static final String TAG = "StartActivity";
    int request_Code = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(TAG,"In onCreate()");

        b1 = findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, ListItemActivity.class);
                startActivityForResult(intent,request_Code);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 50){
            if(resultCode == RESULT_OK){
                Log.i(TAG,"Returned to StartActivityResult");

                String messagePassed = data.getStringExtra("Response");
                Toast toast = Toast.makeText(StartActivity.this , messagePassed, Toast.LENGTH_LONG);
                toast.show(); //display your message boxur message
            }
        }
    }

    public void onStart(){
        super.onStart();
        Log.i(TAG,"in onStart");
    }

    public void onResume(){
        super.onResume();
        Log.i(TAG,"in onResume()");
    }

    public void onPause(){
        super.onPause();
        Log.i(TAG,"in onPause()");
    }

    public void onStop(){
        super.onStop();
        Log.i(TAG,"in onStop()");
    }

    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG,"in onDestroy()");
    }
}
