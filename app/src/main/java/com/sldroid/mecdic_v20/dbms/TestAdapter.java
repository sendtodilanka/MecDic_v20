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

        Cursor mCursor = mDb.query(table, new String[] {"_id","word","definition"},
                null, null, null,null, " word COLLATE NOCASE");

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
        return wordList;
    }

    public ArrayList<Word> getWordByName(String table, String inputText) {
        ArrayList<Word> wordList = new ArrayList<Word>();

        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(table, new String[] {"_id","word","definition"},
                    null, null, null,null, " word COLLATE NOCASE");

        }
        else {
            mCursor = mDb.query(true, table, new String[] {"_id","word","definition"},
                    "word" + " like '" + inputText + "%'", null,
                    null, null, " word COLLATE NOCASE",null);
        }

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
        return wordList;
    }

    public void addDataToDB(Boolean state, String word, String definition){
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("word", word);
            initialValues.put("definition", definition);
            if (state)
                mDb.insert("siDictionary", null, initialValues);
            else
                mDb.insert("enDictionary", null, initialValues);
            Log.v("addDataToDB", "success");
        }catch (Exception e){
            Log.e("Error", e.toString());
        }
    }

    public void addWordTo(String table, String id_Dic, String word){
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("id_Dic", Integer.valueOf(id_Dic));
            initialValues.put("word", word);

            mDb.insertOrThrow(table, null, initialValues);

            Log.v("addWordTo", "success");
        }catch (Exception e){
            Log.e("Error", e.toString());
        }
    }

    public boolean delRow(String table, String where, String val) {
        int doneDelete = 0;
        doneDelete = mDb.delete(table, where + " = '" + val + "'",null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public Cursor getWord(String table)
    {
        Cursor mCursor = mDb.query(table, new String[] {"_id","id_Dic","word"},
                null, null, null,null, " word COLLATE NOCASE");

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean isAlreadyExist(String table, String inputText) throws SQLException {
        Cursor mCursor = null;
        Boolean curState = true;

        if (inputText != null  ||  inputText.length () != 0)  {
            mCursor = mDb.query(true, table, new String[] {"id_Dic"},
                    "id_Dic" + " like '" + inputText + "%'", null,
                    null, null, " word COLLATE NOCASE",null);
        }

        if (mCursor.getCount() == 0)
            curState = false;

        return curState;
    }
}
