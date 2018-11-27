package com.example.yueli.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private static final String TAG = "TestToolbar";
    String currentMessage = "You selected item 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById((R.id.lab8_toolbar));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_one:
            {
                Snackbar.make(findViewById(R.id.action_one),currentMessage,Snackbar.LENGTH_SHORT).show();
                //Log.i(TAG, "iteam one is selected");
            }
            break;


            case R.id.action_two:{
                //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.dialog_title_toolbar)
                        .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){

                            }
                        })
                        .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                //Intent resultIntent = new Intent();
                                //resultIntent.putExtra("Response","Here is my response");
                                //setResult(Activity.RESULT_OK,resultIntent);
                                finish();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
                Log.i(TAG, "iteam two is selected");
                break;
            case R.id.action_three:
            {
                Log.i(TAG, "iteam three is selected");
                openDialog();

            }
                break;
            case R.id.action_4:
                Toast.makeText(getApplicationContext(),R.string.aboutinfo, Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example diaglog");
    }



    public void onButtonClick(View view){
        Snackbar.make((Button)findViewById(R.id.button5),"Message to show",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void applyTexts(String message) {
        currentMessage = message;
    }
}
