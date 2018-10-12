package com.example.yueli.androidlabs;

import android.app.Activity;
import android.content.Context;
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

public class ChatWindowActivity extends Activity {
    EditText ed1;
    Button b1;
    ListView lv1;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        ed1=(EditText)findViewById(R.id.editText3);
        lv1=(ListView)findViewById(R.id.chatView);
        b1=(Button)findViewById(R.id.sendButton);



        final ChatAdapter messageAdapter = new ChatAdapter(this);
        lv1.setAdapter(messageAdapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.add(ed1.getText().toString());
                messageAdapter.notifyDataSetChanged();
                ed1.setText("");
            }
        });

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
