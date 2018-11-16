package com.example.yueli.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 6;
    static final String TABLENAME = "chartrecord";
    static final String KEY_MESSAGE = "MESSAGE ";
    static final String KEY_ID = "_id";
    private static final String DATABASE_CREATE = "create table " + TABLENAME + "( " + KEY_ID + " integer primary key autoincrement, " + KEY_MESSAGE + " text not null);";

    private static final String TAG = "ChatDatabaseHelper";

    ChatDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i(TAG, "Calling onCreate");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*VERSION_NUM++;*/
        Log.i(TAG, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }



    public void getMessage(SQLiteDatabase db) {
        Cursor res = db.rawQuery("select * from " + TABLENAME, null);
    }

    public static int getIdFromCursor(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
        return id;
    }

    public static boolean delete(long id, SQLiteDatabase db){

        Log.i(TAG, "Calling delete");
        return db.delete(TABLENAME, KEY_ID + "=" + id, null) > 0;
    }
}
