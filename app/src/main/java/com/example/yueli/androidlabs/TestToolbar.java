package com.example.yueli.androidlabs;

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

public class TestToolbar extends AppCompatActivity {

    private static final String TAG = "TestToolbar";
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
                Log.i(TAG, "iteam one is selected");
                break;
            case R.id.action_two:
                Log.i(TAG, "iteam two is selected");
                break;
            case R.id.action_three:
                Log.i(TAG, "iteam three is selected");
                break;
            case R.id.action_4:
                Toast.makeText(getApplicationContext(),R.string.aboutinfo, Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view){
        Snackbar.make((Button)findViewById(R.id.button5),"Message to show",Snackbar.LENGTH_SHORT).show();
    }
}
