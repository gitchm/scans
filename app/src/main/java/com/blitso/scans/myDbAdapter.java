package com.blitso.scans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertData(String name, String age, String sex, String address, String phone, String email, String temperature, String q1, String q2, String q3, String q4, String currdate, String currtime)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.AGE, age);
        contentValues.put(myDbHelper.SEX, sex);
        contentValues.put(myDbHelper.ADDRESS, address);
        contentValues.put(myDbHelper.PHONE, phone);
        contentValues.put(myDbHelper.EMAIL, email);
        contentValues.put(myDbHelper.TEMPERATURE, temperature);
        contentValues.put(myDbHelper.Q1, q1);
        contentValues.put(myDbHelper.Q2, q2);
        contentValues.put(myDbHelper.Q3, q3);
        contentValues.put(myDbHelper.Q4, q4);
        contentValues.put(myDbHelper.CURRDATE, currdate);
        contentValues.put(myDbHelper.CURRTIME, currtime);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getwholedata()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.AGE,myDbHelper.SEX,myDbHelper.ADDRESS,myDbHelper.PHONE,myDbHelper.EMAIL,myDbHelper.TEMPERATURE,myDbHelper.Q1,myDbHelper.Q2,myDbHelper.Q3,myDbHelper.Q4,myDbHelper.CURRDATE,myDbHelper.CURRTIME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String age =cursor.getString(cursor.getColumnIndex(myDbHelper.AGE));
            String sex =cursor.getString(cursor.getColumnIndex(myDbHelper.SEX));
            String address =cursor.getString(cursor.getColumnIndex(myDbHelper.ADDRESS));
            String phone =cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
            String email =cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
            String temp =cursor.getString(cursor.getColumnIndex(myDbHelper.TEMPERATURE));
            String q1 =cursor.getString(cursor.getColumnIndex(myDbHelper.Q1));
            String q2 =cursor.getString(cursor.getColumnIndex(myDbHelper.Q2));
            String q3 =cursor.getString(cursor.getColumnIndex(myDbHelper.Q3));
            String q4 =cursor.getString(cursor.getColumnIndex(myDbHelper.Q4));
            String currdate =cursor.getString(cursor.getColumnIndex(myDbHelper.CURRDATE));
            String currtime =cursor.getString(cursor.getColumnIndex(myDbHelper.CURRTIME));
            buffer.append(cid+","+name+","+age+","+sex+","+address+","+phone+","+email+","+temp+","+q1+","+q2+","+q3+","+q4+","+currdate+","+currtime+"\n");
        }
        return buffer.toString();
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.TEMPERATURE};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        if(cursor.getCount() == 0){
            buffer.append("Table is empty");
        }else {
            while (cursor.moveToNext()) {
                int cid = cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String temp = cursor.getString(cursor.getColumnIndex(myDbHelper.TEMPERATURE));
                buffer.append(cid + "   " + name + "   " + temp + " \n");
            }
        }
        return buffer.toString();
    }
    public String detailedData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME,myDbHelper.PHONE,myDbHelper.TEMPERATURE,myDbHelper.CURRTIME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        if(cursor.getCount() == 0){
            buffer.append("Table is empty");
        }else {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String phone = cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
                String temp = cursor.getString(cursor.getColumnIndex(myDbHelper.TEMPERATURE));
                String time = cursor.getString(cursor.getColumnIndex(myDbHelper.CURRTIME));
                buffer.append("Full name: "+name+"\nPhone Number: " +phone+"\nTemperature: " + temp + "°C\nID: "+time+";");
            }
        }
        return buffer.toString();
    }
    public String clickedddata()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.NAME,myDbHelper.AGE,myDbHelper.SEX,myDbHelper.ADDRESS,myDbHelper.PHONE,myDbHelper.EMAIL,myDbHelper.TEMPERATURE,myDbHelper.CURRDATE,myDbHelper.CURRTIME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        if(cursor.getCount() == 0){
            buffer.append("Table is empty");
        }else {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
                String age = cursor.getString(cursor.getColumnIndex(myDbHelper.AGE));
                String sex = cursor.getString(cursor.getColumnIndex(myDbHelper.SEX));
                String address = cursor.getString(cursor.getColumnIndex(myDbHelper.ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndex(myDbHelper.PHONE));
                String email = cursor.getString(cursor.getColumnIndex(myDbHelper.EMAIL));
                String temp = cursor.getString(cursor.getColumnIndex(myDbHelper.TEMPERATURE));
                String date = cursor.getString(cursor.getColumnIndex(myDbHelper.CURRDATE));
                String time = cursor.getString(cursor.getColumnIndex(myDbHelper.CURRTIME));
                buffer.append("Full name: "+name+"\nBirthdate: "+age+"\nGender: "+sex+"\nAddress: "+address+"\nPhone Number: "+phone+"\nEmail: "+email+"\nTemperature: "+temp+"°C\nDate: "+date+"\nTime: "+time+";");
            }
        }
        return buffer.toString();
    }


    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.UID+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "mydb";    // Database Name
        private static final String TABLE_NAME = "githcm";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String AGE= "Age";    // Column III
        private static final String SEX = "Sex";
        private static final String ADDRESS = "Address";
        private static final String PHONE = "Phone";
        private static final String EMAIL = "Email";
        private static final String TEMPERATURE = "Temperature";
        private static final String Q1 = "Q1";
        private static final String Q2 = "Q2";
        private static final String Q3 = "Q3";
        private static final String Q4 = "Q4";
        private static final String CURRDATE = "Currdate";
        private static final String CURRTIME = "Currtime";

        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                "("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ""+NAME+" VARCHAR(255) ," +
                ""+AGE+" VARCHAR(225)," +
                ""+SEX+" VARCHAR(225)," +
                ""+ADDRESS+" VARCHAR(225)," +
                ""+PHONE+" VARCHAR(225)," +
                ""+EMAIL+" VARCHAR(225)," +
                ""+TEMPERATURE+" VARCHAR(225)," +
                ""+Q1+" VARCHAR(225)," +
                ""+Q2+" VARCHAR(225)," +
                ""+Q3+" VARCHAR(225)," +
                ""+Q4+" VARCHAR(225)," +
                ""+CURRDATE+" VARCHAR(225)," +
                ""+CURRTIME+" VARCHAR(225))";


        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
        public boolean deleteAll() {
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
            return true;
        }

    }
}