package com.sldroid.mecdic_v20.dbms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sldroid.mecdic_v20.extra.Word;

import java.io.IOException;
import java.util.ArrayList;

public class TestAdapter
{
    protected static final String TAG = "DataAdapter";

    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;

    public TestAdapter(Context context)
    {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    public TestAdapter createDatabase() throws SQLException
    {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public TestAdapter open() throws SQLException
    {
        try
        {
            try {
                mDbHelper.openDataBase();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {
            Log.e(TAG, "open >>"+ mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }

    public void close()
    {
        mDbHelper.close();
    }

    public ArrayList<Word> getAllWord(String table) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        String[] fields = new String[]{"_id","word","definition","favourite","usage"};
        open();

        Cursor mCursor = mDb.query(table, fields, null, null, null,null, " word COLLATE NOCASE");

        // looping through all rows and adding to list
        if (mCursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.set_id(Integer.valueOf(mCursor.getString(mCursor.getColumnIndex("_id"))));
                word.setWord(mCursor.getString(mCursor.getColumnIndex("word")));
                word.setDefinition(mCursor.getString(mCursor.getColumnIndex("definition")));
                wordList.add(word);
            } while (mCursor.moveToNext());
        }
        close();
        return wordList;
    }

    public void favUpdate(String table, String id, int num){

        ContentValues cv = new ContentValues();
        cv.put("favourite", num);

        mDb.update(table, cv, "_id="+id, null);
    }
}
