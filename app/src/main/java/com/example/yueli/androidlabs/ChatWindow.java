package com.example.yueli.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatWindow extends Activity {
    ListView listView;
    Button sendButton;
    EditText message;
    List<String> chatMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView= (ListView)findViewById(R.id.Listview);
        sendButton=(Button)findViewById(R.id.ButtonSend);
        message=(EditText)findViewById(R.id.messageEditText);
        chatMessages = new ArrayList<String>();
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);
       sendButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               chatMessages.add(message.getText().toString());
               messageAdapter.notifyDataSetChanged();
               message.setText("");
           }
       });


    }
    private class ChatAdapter extends ArrayAdapter<String>{

        private ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
    }

    public int getCount(){
        return chatMessages.size();
    }
    public String getItem(int position){
        return chatMessages.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        View result = null;
        if(position==0)
            result = inflater.inflate(R.layout.chat_row_incoming,null);
        else
            result = inflater.inflate(R.layout.chat_row_outgoing,null);
        TextView message = (TextView)result.findViewById(R.id.messageEditText);
        message.setText(getItem(position));
        return result;
    }
    public long getItemId(int position){
        return position;
    }
}
