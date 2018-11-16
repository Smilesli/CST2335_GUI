package com.example.yueli.androidlabs;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

    ChatWindowActivity parent = null;
    public boolean iAmTablet;
    TextView msgView;
    TextView idView;
    Button deleteBtn;
    String message;
    int position;
    long id;
    private static final String TAG = "BlankFragment";

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance()
    {
        BlankFragment myFragment = new BlankFragment();

        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView()");
        // Inflate the layout for this fragment

        Bundle infoToPass = getArguments(); //returns the arguments set before

        message = infoToPass.getString("message");
        position = infoToPass.getInt("position");
        id = infoToPass.getLong("id");

        View screen = inflater.inflate(R.layout.fragment_blank, container, false);

        msgView = (TextView) screen.findViewById(R.id.messageView);
        msgView.setText(message);

        idView = (TextView) screen.findViewById(R.id.msgId);
        idView.setText(Double.toString(id).split("\\.")[0]);

        deleteBtn = (Button) screen.findViewById(R.id.deleteMsg);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"deleteBtn is clicked");
                if(iAmTablet) {
                    Log.i(TAG,"handling tablet.");
                    parent.deleteMessage(position, id); //call function from parent

                    // once deleted the fragment is disappeared
                    getActivity().getFragmentManager().popBackStack();
                }
                else {
                    Log.i(TAG,"handling phone.");
                    Intent intent = new Intent();
                    intent.putExtra("position", position);
                    intent.putExtra("id", id);
                    getActivity().setResult(10, intent);
                    getActivity().finish();// go to previous activity
                }
            }
        });

        return screen;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach()");

        if(iAmTablet)
            parent = (ChatWindowActivity)  context; //find out which activity has the fragment
    }
}
