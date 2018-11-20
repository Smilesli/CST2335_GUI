package com.example.yueli.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.example.yueli.androidlabs.ChatDatabaseHelper.TABLENAME;

public class ChatWindowActivity extends Activity {
    EditText ed1;
    Button b1;
    ListView lv1;
    private boolean isTab;
    ArrayList<String> list = new ArrayList<String>();
    ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private ChatAdapter messageAdapter;
    private static final String TAG = "ChatWindowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.message_detail);
        if(frameLayout == null){
            Log.i(TAG, "FrameLayout is not loaded");
            isTab = false;
        } else{
            Log.i(TAG, "FrameLayout is loaded");
            isTab = true;
        }

        ed1=(EditText)findViewById(R.id.editText3);
        lv1=(ListView)findViewById(R.id.chatView);
        b1=(Button)findViewById(R.id.sendButton);
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        messageAdapter = new ChatAdapter(this);
        lv1.setAdapter(messageAdapter);

        //Cursor c = db.query(true, ChatDatabaseHelper.TABLENAME, new String[] {ChatDatabaseHelper.KEY_MESSAGE }, null, null, null, null, null, null);
        Cursor c = db.rawQuery("select * from " + ChatDatabaseHelper.TABLENAME , new String[]{});

        int colIndex = c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        Log.i(TAG,"Cursor's column count=" + c.getColumnCount());
        c.moveToFirst();
        while(!c.isAfterLast() ){
            String message = c.getString( colIndex );
            long id = c.getLong(c.getColumnIndex(ChatDatabaseHelper.KEY_ID));

            list.add(message);

            Log.i(TAG,"SQL message:" + message);
            Log.i(TAG,"SQL ID: " + id);

            c.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();

        for(int i = 0;i < c.getColumnCount(); i++ ){
            Log.i(TAG,"Cursor's column name :" + c.getColumnName(i).toString());
        }

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = list.get(position);
                Log.i(TAG, "item clicked: position: " + position + " id: " + id);


                if(isTab){
                    Log.i(TAG,"handling tablet...");
                    BlankFragment newFragment = new BlankFragment();
                    newFragment.iAmTablet = true;

                    Bundle bundle = new Bundle();
                    bundle.putInt("position",position);
                    bundle.putLong("id",id);
                    bundle.putString("message", message);
                    newFragment.setArguments(bundle);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.message_detail, newFragment);
                    ftrans.addToBackStack(null);
                    ftrans.commit();
                }
                else{
                    Log.i(TAG,"handling phone...");

                    //go to new window:
                    Intent intent = new Intent(ChatWindowActivity.this, MessageDetails.class);

                    //intent.putExtra("message", message.getMessage());
                    intent.putExtra("position",position);
                    //intent.putExtra("id",message.getId());
                    intent.putExtra("id",id);
                    intent.putExtra("message", message);

                    startActivityForResult(intent, 10);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = ed1.getText().toString();
                ContentValues args = new ContentValues();
                args.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                long id = db.insert(ChatDatabaseHelper.TABLENAME,null, args);
                list.add(message);

                messageAdapter.notifyDataSetChanged();
                ed1.setText("");
            }
        });

    }

    public void onDestroy(){
        db.close();
        super.onDestroy();
        Log.i(TAG,"in onDestroy()");
    }

    public void deleteMessage(int position, long id){
        dbHelper.delete(id, db);
        list.remove(position);

        messageAdapter.notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if(requestCode == 10  && responseCode == 10) {
            Bundle bundle = data.getExtras();
            int position = bundle.getInt("position");
            long id = bundle.getLong("id");
            deleteMessage(position, id);

        }
    }

    //private class ChatAdapter extends ArrayAdapter<Message> {
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public long getItemId(int position) {

            Cursor cursor = db.rawQuery("select * from " + ChatDatabaseHelper.TABLENAME, new String[]{});
            cursor.moveToPosition(position);
            int  id = ChatDatabaseHelper.getIdFromCursor(cursor);
            return id;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            String tmp_str;
            tmp_str = list.get(position);
            return tmp_str;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindowActivity.this.getLayoutInflater();


            View rowView = null;
            if(position%2==0)
                rowView = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                rowView = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView textView = (TextView) rowView.findViewById(R.id.message_text);
            textView.setText(getItem(position));

            return rowView;
        }
    }
}
