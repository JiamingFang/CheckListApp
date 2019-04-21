package com.example.jiaming.login;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class myDbAdapter {
  myDbHelper myhelper;
  public myDbAdapter(Context context){
    myhelper = new myDbHelper(context);
  }

  public long insertData(String title, String description, int selected, int accountID)
  {
    SQLiteDatabase dbb = myhelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(myDbHelper.TITLE, title);
    contentValues.put(myDbHelper.DESCRIPTION, description);
    contentValues.put(myDbHelper.SELECTED, selected);
    contentValues.put(myDbHelper.ACCOUNTID, accountID);
    long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
    return id;
  }

  public void insertAccount(String name,String password){
    SQLiteDatabase dbb = myhelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(myDbHelper.NAME, name);
    contentValues.put(myDbHelper.PASSWORD, password);
    long count = DatabaseUtils.queryNumEntries(dbb, "accountTable");
    contentValues.put(myDbHelper.ID, count);
    Log.d("account","name: "+name);
    Log.d("account","password: "+password);
    Log.d("account","count: "+count);
    Log.d("account","id: "+myDbHelper.ID);
    dbb.insert(myDbHelper.ACCOUNT_TABLE, null , contentValues);
  }

  public void getData(int accountID)
  {
    SQLiteDatabase db = myhelper.getWritableDatabase();
    String[] columns = {myDbHelper.TITLE,myDbHelper.DESCRIPTION,myDbHelper.SELECTED,myDbHelper.ACCOUNTID};
    Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
    try {
      while (cursor.moveToNext()) {
        if(cursor.getInt((cursor.getColumnIndex(myDbHelper.ACCOUNTID))) == accountID) {
          Log.d("check123", "123");
          String title = cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
          String description = cursor.getString(cursor.getColumnIndex(myDbHelper.DESCRIPTION));
          int selected = cursor.getInt(cursor.getColumnIndex(myDbHelper.SELECTED));
          MainActivity.list.add(title);
          MainActivity.list2.add((description));
          MainActivity.list4.add(false);
        }
      }
    }
    finally{
      cursor.close();
      db.close();
    }
  }

  public int getAccount(String name1, String password1){
    SQLiteDatabase db = myhelper.getWritableDatabase();
    String[] columns = {myDbHelper.NAME,myDbHelper.PASSWORD,myDbHelper.ID};
    Cursor cursor =db.query(myDbHelper.ACCOUNT_TABLE,columns,null,null,null,null,null);
    int id = -1;
    try {
      while (cursor.moveToNext()) {
        String name2 = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
        String password2 = cursor.getString(cursor.getColumnIndex(myDbHelper.PASSWORD));

        long count = DatabaseUtils.queryNumEntries(db, "accountTable");

        if(name1.equals(name2) && password1.equals(password2)){
          id = cursor.getInt(cursor.getColumnIndex(myDbHelper.ID));
        }
      }
    }
    finally{
      cursor.close();
      db.close();
      return id;
    }
  }

  public int checkExist(String name1){
    SQLiteDatabase db = myhelper.getWritableDatabase();
    String[] columns = {myDbHelper.NAME,myDbHelper.PASSWORD,myDbHelper.ID};
    Cursor cursor =db.query(myDbHelper.ACCOUNT_TABLE,columns,null,null,null,null,null);
    int check = 0;
    try {
      while (cursor.moveToNext()) {
        String name2 = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
        if(name1.equals(name2)){
          check = 1;
        }
      }
    }
    finally{
      cursor.close();
      db.close();
      return check;
    }
  }

  public void deleteContact (String title) {
    SQLiteDatabase db = myhelper.getWritableDatabase();
    db.delete(myDbHelper.TABLE_NAME,
        myDbHelper.TITLE+"= ?",
        new String[] { title });
    db.close();
  }

  static class myDbHelper extends SQLiteOpenHelper
  {
    private static final String DATABASE_NAME = "myDatabase";    // Database Name
    private static final int DATABASE_Version = 1;

    private static final String TABLE_NAME = "myTable";   // Table Name
    private static final String TITLE = "title";    //Column I
    private static final String DESCRIPTION= "description";    // Column II
    private static final String SELECTED="selected";
    private static final String ACCOUNTID= "accountID";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
        " ("+TITLE+" VARCHAR(255) ,"+ DESCRIPTION+" VARCHAR(225) ,"+ SELECTED+" INTEGER,"+ ACCOUNTID+ " INTEGER);";

    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;

    private static final String ACCOUNT_TABLE = "accountTable";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String ID = "ID";
    private static final String CREATE_TABLE2 = "CREATE TABLE "+ACCOUNT_TABLE+
        " ("+NAME+" VARCHAR(255) ,"+ PASSWORD+" VARCHAR(225) ,"+ ID+" INTEGER);";
    private static final String DROP_TABLE2 ="DROP TABLE IF EXISTS "+ACCOUNT_TABLE;

    private Context context;

    public myDbHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_Version);
      this.context=context;
    }

    public void onCreate(SQLiteDatabase db) {

      try {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
      } catch (Exception e) {
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      try {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE2);
        onCreate(db);
      }catch (Exception e) {
      }
    }
  }
}
