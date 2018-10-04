package com.example.yueli.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemActivity extends Activity {
    Switch sw;
    CheckBox cb;
    ImageButton ib;

    private static final String TAG = "ListItemActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        Log.i(TAG,"In onCreate()");


        sw = (Switch)findViewById(R.id.switch4);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean on){
                if(on)
                {
                    //Do something when Switch button is on/checked
                    CharSequence text = "Switch is On";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(ListItemActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message box
                }
                else
                {
                    //Do something when Switch is off/unchecked
                    CharSequence text = "Switch is off";// "Switch is Off"
                    int duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off

                    Toast toast = Toast.makeText(ListItemActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message boxur message box
                }
            }
        });

        cb = (CheckBox)findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if(cb.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemActivity.this);
                    builder.setTitle(R.string.dialog_title)
                            .setMessage(R.string.dialog_message)
                            .setNegativeButton("CANCEL",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    cb.toggle();
                                }
                            })
                            .setPositiveButton("YES",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("Response","Here is my response");
                                    setResult(Activity.RESULT_OK,resultIntent);
                                    finish();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        ib = findViewById(R.id.imageButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ib.setImageBitmap(imageBitmap);
        }
    }
}
