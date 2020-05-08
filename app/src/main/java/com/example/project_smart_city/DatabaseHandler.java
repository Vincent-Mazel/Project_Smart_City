package com.example.project_smart_city;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="SmartCity.db";

    //TABLE NAME
    private static final String TABLE_USER = "USER";
    private static final String TABLE_NETWORK = "NETWORK";
    private static final String TABLE_SHOP = "SHOP";

    //TABLE FIELDS
    // USER
    private static final String USER_ID = "ID";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String PSEUDO = "PSEUDO";
    private static final String SEX = "SEX";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String BIRTHDAY = "BIRTHDAY";
    private static final String SIZE = "SIZE";
    private static final String WEIGHT = "WEIGHT";
    private static final String IMAGE = "IMAGE";
    private static final String CHOICES = "CHOICES";
    private static final String INTEREST = "INTERESTS";

    SQLiteDatabase database;


    public DatabaseHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + " ( "+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + NAME + " TEXT, " + SURNAME + " TEXT, " + PSEUDO + " TEXT, " + SEX + " TEXT, " +
                EMAIL + " TEXT, " + PASSWORD + " TEXT, " + BIRTHDAY + " TEXT, " + SIZE + " INTEGER, " + WEIGHT + " INTEGER, " + IMAGE + " TEXT, " + CHOICES + " TEXT, " + INTEREST +  " TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public String loadHandler() {
        String result = "";
        String query = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        while (cursor.moveToNext()){
            int result_0 = cursor.getInt(1);
            String result_1 = cursor.getString(1);
            result += result_0 + " " + result_1 + System.getProperty("line.separator");
        }
        cursor.close();
        database.close();
        return result;
    }

    public void addHandler(User user){
        ContentValues values = new ContentValues();
        //values.put(USER_ID, user.getId());
        values.put(NAME, user.getName());
        values.put(SURNAME, user.getSurname());
        values.put(PSEUDO, user.getPseudo());
        values.put(SEX, user.getSexe());
        values.put(EMAIL, user.getEmail());
        values.put(PASSWORD, user.getPassword());
        values.put(BIRTHDAY, user.getBirthday());
        values.put(SIZE, user.getSize());
        values.put(WEIGHT, user.getWeight());
        //values.put(IMAGE, user.getProfilPicture().toString());
        //values.put(CHOICES, user.getListChoices().toString());
        //values.put(INTEREST, user.getListInterests().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }


    public User findHandler(String email) {
        String query = "Select * FROM " + TABLE_USER + " WHERE " + EMAIL + " = " + "'" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setSurname(cursor.getString(2));
            user.setPseudo(cursor.getString(3));
            user.setSexe(cursor.getString(4));
            user.setEmail(cursor.getString(5));
            user.setPassword(cursor.getString(6));
            user.setBirthday(cursor.getString(7));
            user.setSize(Integer.parseInt(cursor.getString(8)));
            user.setWeight(Integer.parseInt(cursor.getString(9)));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;
    }


    public boolean deleteHandler(int ID) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_USER + " WHERE " + USER_ID + "= '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user= new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_USER, USER_ID + "=?",
                    new String[] {
                String.valueOf(user.getId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }



    public boolean updateHandler(int ID, String pseudo, Integer weight, Integer size, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(USER_ID, ID);
        args.put(PSEUDO, pseudo);
        args.put(WEIGHT, weight);
        args.put(SIZE, size);
        args.put(EMAIL, email);

        return db.update(TABLE_USER, args, USER_ID + "=" + ID, null) > 0;
    }
}
