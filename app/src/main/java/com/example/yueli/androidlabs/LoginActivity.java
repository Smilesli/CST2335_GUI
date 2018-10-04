package com.example.yueli.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText ed1,ed2;
    Button b1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final String TAG = "LoginActivity";
    public static final String Login = "loginKey";
    public static final String Password = "passwordKey";

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(TAG,"In onCreate()");

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);

        b1=(Button)findViewById(R.id.button);
        sp = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //ed1.setText(sp.getString("DefaultEmail","email@domain.com"));
        ed1.setText(sp.getString(Login,"email@domain.com"));


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user  = ed1.getText().toString();
                String psw  = ed2.getText().toString();

                SharedPreferences.Editor editor = sp.edit();

                editor.putString(Login, user);
                editor.putString(Password, psw);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
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
