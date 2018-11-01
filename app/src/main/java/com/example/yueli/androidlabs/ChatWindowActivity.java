package com.example.yueli.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.icu.text.MessagePattern.ArgType.SELECT;
import static com.example.yueli.androidlabs.ChatDatabaseHelper.TABLENAME;

public class ChatWindowActivity extends Activity {
    EditText ed1;
    Button b1;
    ListView lv1;
    ArrayList<String> list = new ArrayList<String>();
    ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;
    private static final String TAG = "ChatWindowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ed1=(EditText)findViewById(R.id.editText3);
        lv1=(ListView)findViewById(R.id.chatView);
        b1=(Button)findViewById(R.id.sendButton);
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        lv1.setAdapter(messageAdapter);
/*
        Cursor c = db.query(true, ChatDatabaseHelper.TABLENAME, new String[] {ChatDatabaseHelper.KEY_MESSAGE }, null, null, null, null, null, null);
*/
        Cursor c = db.rawQuery("select * from chartrecord", new String[]{});
        /*Cursor c = db.rawQuery("select * from ?", new String[]{ChatDatabaseHelper.TABLENAME});*/
        int colIndex = c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        colIndex = 1;
        Log.i(TAG,"Cursor's column count=" + c.getColumnCount());
        c.moveToFirst();
        while(!c.isAfterLast() ){
            String fn = c.getString( colIndex );
            list.add(fn);
            messageAdapter.notifyDataSetChanged();
            Log.i(TAG,"SQL message:" + fn);
            c.moveToNext();
        }

        for(int i = 0;i < c.getColumnCount(); i++ ){
            Log.i(TAG,"Cursor's column name :" + c.getColumnName(i).toString());
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(ed1.getText().toString());
                messageAdapter.notifyDataSetChanged();
                ContentValues args = new ContentValues();
                args.put(ChatDatabaseHelper.KEY_MESSAGE, ed1.getText().toString());
                db.insert(ChatDatabaseHelper.TABLENAME,null, args);
                ed1.setText("");
            }
        });

    }

    public void onDestroy(){
        db.close();
        super.onDestroy();
        Log.i(TAG,"in onDestroy()");
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
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
