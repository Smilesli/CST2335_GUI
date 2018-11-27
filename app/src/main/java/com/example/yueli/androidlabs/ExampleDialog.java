//android.support.v7.app.AppCompatDialogFragment

package com.example.yueli.androidlabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class ExampleDialog extends AppCompatDialogFragment {

    private EditText editTextMessage;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                })
                .setPositiveButton(R.string.ok,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //Intent resultIntent = new Intent();
                        //resultIntent.putExtra("Response","Here is my response");
                        //setResult(Activity.RESULT_OK,resultIntent);
                        //finish();
                        String message = editTextMessage.getText().toString();
                        listener.applyTexts(message);
                    }
                });
        editTextMessage = view.findViewById(R.id.edit_message);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String message);
    }
}
