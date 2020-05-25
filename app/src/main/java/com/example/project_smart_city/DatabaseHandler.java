package com.example.project_smart_city;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="SmartCity.db";

    // TABLES NAME
    private static final String TABLE_USER = "USER";
    private static final String TABLE_NETWORK = "NETWORK";
    private static final String TABLE_SHOP = "SHOP";
    private static final String TABLE_OFFER="OFFER";
    private static final String TABLE_POST="POST";

    //TABLE FIELDS

    // SHOP
    private static final String SHOP_id="ID";
    private static final String SHOP_NAME="NAME";
    private static final String SHOP_LATITUDE="LATITUDE";
    private static final String SHOP_LONGITUDE="LONGITUDE";
    private static final String SHOP_DESCRIPTION="DESCRIPTION";
    private static final String SHOP_PICTURE="IMAGE";
    private static final String SHOP_OFFERS="OFFERS";
    private static final String SHOP_SECTOR="SECTOR";


    // OFFER
    private static final String OFFER_ID="ID";
    private static final String OFFER_DESCRIPTION="DESCRIPTION";
    private static final String OFFER_PICTURE="IMAGE";
    private static final String OFFER_NAME="OFFER";
    private static final String OFFER_PRICE="PRICE";
    private static final String OFFER_SHOP_ID = "SHOP_ID";

    // POST
    private static final String POST_ID = "ID";
    private static final String POST_NETWORK = "POST_NETWORK";
    private static final String POST_AUTHOR = "AUTHOR";
    private static final String DATE = "DATE";
    private static final String DATA = "DATA";


    // NETWORK
    private static final String NETWORK_ID = "ID";
    private static final String NETWORK_NAME = "NAME";
    private static final String NETWORK_DESCRIPTION = "DESCRIPTION";
    private static final String NETWORK_STATUS = "STATUS";
    private static final String NETWORK_CREATOR = "CREATOR";
    private static final String NETWORK_MEMBERS = "MEMBERS";
    private static final String NETWORK_REQUEST = "REQUESTS";


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
        // creation user table
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + " ( "+ USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + NAME + " TEXT, " + SURNAME + " TEXT, " + PSEUDO + " TEXT, " + SEX + " TEXT, " +
                EMAIL + " TEXT, " + PASSWORD + " TEXT, " + BIRTHDAY + " TEXT, " + SIZE + " INTEGER, " + WEIGHT + " INTEGER, " + IMAGE + " BLOB, " + CHOICES + " BLOB, " + INTEREST +  " BLOB)" );
        // creation network table;
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NETWORK + " ( " + NETWORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + NETWORK_NAME + " TEXT, " + NETWORK_DESCRIPTION + " TEXT, "+
                NETWORK_STATUS + " TEXT, " + NETWORK_CREATOR + " INTEGER, " + NETWORK_REQUEST + " BLOP, " + NETWORK_MEMBERS + " BLOP)" );
        // creation post table
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_POST + " ( " + POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + POST_NETWORK + " INTEGER, " + POST_AUTHOR + " INTEGER, " + DATA + " TEXT, " + DATE + " TEXT )" );
        // creation shop table
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SHOP + " ( " + SHOP_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + SHOP_NAME + " TEXT, " + SHOP_DESCRIPTION + " TEXT, " + SHOP_LATITUDE + " INTEGER, " +
                SHOP_LONGITUDE +" INTEGER, " + SHOP_SECTOR + " TEXT, " + SHOP_PICTURE + " BLOB, " + SHOP_OFFERS + " TEXT)");

        // creation offer table
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_OFFER + " ( " + OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + OFFER_NAME + " TEXT, " + OFFER_DESCRIPTION + " TEXT, " + OFFER_PRICE + " INTEGER, " +
                OFFER_SHOP_ID + " TEXT, " + OFFER_PICTURE + " BLOB)");
    }


    public ArrayList<Shop> loadShop(){
        ArrayList<Shop> arrayList = new ArrayList<>();
        String qry = "SELECT * FROM " + TABLE_SHOP +" ORDER BY " + SHOP_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        while(cursor.moveToNext()){
            Shop shop = new Shop();
            shop.setId(cursor.getInt(0));
            shop.setName(cursor.getString(1));
            shop.setDescription(cursor.getString(2));
            shop.setLatitude(cursor.getDouble(3));
            shop.setLongitude(cursor.getDouble(4));
            shop.setSector(cursor.getString(5));
            shop.setPicture(cursor.getBlob(6));
            arrayList.add(shop);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public ArrayList<Offer> loadOfferByShop(int id_shop){
        ArrayList<Offer> arrayList = new ArrayList<>();
        String qry = "SELECT * FROM " + TABLE_OFFER + " WHERE " + OFFER_SHOP_ID + "=" + id_shop;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        while(cursor.moveToNext()){
            Offer offer = new Offer();
            offer.setId(cursor.getInt(0));
            offer.setName(cursor.getString(1));
            offer.setDescription(cursor.getString(2));
            offer.setPrice(cursor.getDouble(3));
            offer.setShop_id(cursor.getInt(4));
            offer.setPicture(cursor.getBlob(5));
            arrayList.add(offer);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public void addOffer(Offer offer){
        ContentValues arg = new ContentValues();
        arg.put(OFFER_NAME, offer.getName());
        arg.put(OFFER_DESCRIPTION, offer.getDescription());
        arg.put(OFFER_PRICE, offer.getPrice());
        arg.put(OFFER_SHOP_ID, offer.getShop_id());
        arg.put(OFFER_PICTURE, offer.getPicture());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_OFFER,null,arg);
        db.close();
    }

    public Shop findShop(String str){
        String query = "SELECT * FROM " + TABLE_SHOP + " WHERE " + SHOP_NAME + " = " + "'" + str + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Shop shop = new Shop();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            shop.setId(Integer.parseInt(cursor.getString(0)));
            shop.setName(cursor.getString(1));
            shop.setDescription((cursor.getString(2)));
            shop.setLatitude(cursor.getDouble(3));
            shop.setLongitude(cursor.getDouble(4));
            shop.setSector(cursor.getString(5));
        }
        else {
            shop = null;
        }
        db.close();
        return shop;
    }

    public void addShop(Shop shop){
        ContentValues values = new ContentValues();
        values.put(SHOP_DESCRIPTION,shop.getDescription());
        values.put(SHOP_LATITUDE, shop.getLatitude());
        values.put(SHOP_LONGITUDE, shop.getLongitude());
        values.put(SHOP_PICTURE, shop.getPicture());
        values.put(SHOP_SECTOR, shop.getSector());
        values.put(SHOP_NAME, shop.getName());
        ArrayList<Offer> arrayList = shop.getOffers();
        String offers = "";
        if(arrayList != null){
            for(int i =0; i<arrayList.size();++i){
                offers += arrayList.get(i).getId() + ";";
            }
            values.put(SHOP_OFFERS, offers);
        }

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SHOP,null,values);
        db.close();
    }


    public ArrayList<Post> loadPost(int idNetwork){
        ArrayList<Post> arrayList = new ArrayList<>();
        String qry = "SELECT * FROM " + TABLE_POST + " WHERE " + POST_NETWORK + "=" + idNetwork;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(qry, null);
        while(cursor.moveToNext()){
            Post post = new Post();
            post.setId(Integer.parseInt(cursor.getString(0)));
            post.setNetwork_id(Integer.parseInt(cursor.getString(1)));
            post.setAuthor_id(Integer.parseInt(cursor.getString(2)));
            post.setData(cursor.getString(3));
            post.setDate(cursor.getString(4));
            arrayList.add(post);
        }
        cursor.close();
        db.close();
        return arrayList;
    }

    public void addPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(POST_AUTHOR, post.getAuthor_id());
        values.put(DATA, post.getData());
        values.put(DATE, post.getDate());
        values.put(POST_NETWORK, post.getNetwork_id());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_POST, null, values);
        db.close();
    }

    public void deletePost(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POST, POST_ID + '=' + id,null);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public ArrayList<List> loadNetworks(){
        ArrayList<List> arrayList = new ArrayList<>();
        String result = "";
        String query = "SELECT * FROM " + TABLE_NETWORK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            List<String> list = new ArrayList<>(); // current row
            list.add(cursor.getString(0));      // id
            list.add(cursor.getString(1));      // name
            list.add(cursor.getString(2));      // description
            list.add(cursor.getString(3));      // status
            list.add(cursor.getString(4));      // creator
            list.add(cursor.getString(5));      // requests
            list.add(cursor.getString(6));      // members
            arrayList.add(list);
        }
        cursor.close();
        db.close();
        return arrayList;
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

    public void addNetwork(Network network) {
        ContentValues values = new ContentValues();
        //values.put((NETWORK_ID, network.getId());
        values.put(NETWORK_NAME, network.getName());
        values.put(NETWORK_DESCRIPTION, network.getDescription());
        values.put(NETWORK_CREATOR, network.getCreator());
        values.put(NETWORK_MEMBERS, network.getListMembers());
        values.put(NETWORK_STATUS, network.getStatus());
        values.put(NETWORK_REQUEST, network.getListRequest());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NETWORK, null, values);
        db.close();

    }

    public Network findNetwork(String name){
        String query = "SELECT * FROM " + TABLE_NETWORK + " WHERE " + NETWORK_NAME + " = " + "'" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Network network = new Network();
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            network.setId(Integer.parseInt(cursor.getString(0)));
            network.setName(cursor.getString(1));
            network.setDescription((cursor.getString(2)));
            network.setStatus(cursor.getString(3));
            network.setCreator(Integer.parseInt(cursor.getString(4)));
            network.setListRequest(cursor.getString(5));
            network.setListMembers(cursor.getString(6));
        }
        else {
            network = null;
        }
        db.close();
        return network;
    }

    public void addUser(User user){
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
        values.put(IMAGE, user.getProfilPicture());
        values.put(CHOICES, user.getListChoices());
        values.put(INTEREST, user.getListInterests());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();

    }


    public User findUser(String email) {
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
            user.setListChoices(cursor.getString(11));
            user.setListInterests(cursor.getString(12));
            cursor.close();
        } else {
            user = null;
        }
        db.close();
        return user;
    }

    public User findUserById(int id) {
        String query = "Select * FROM " + TABLE_USER + " WHERE " + USER_ID + " = " + "'" + id + "'";
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
            user.setListChoices(cursor.getString(11));
            user.setListInterests(cursor.getString(12));
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

        return result;
    }

    public void deleteNetwork(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( TABLE_NETWORK,NETWORK_ID + "=" + id,null);
        db.close();
    }

    public void updateProfilImg(int id , byte[] img ) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues args = new ContentValues();
            args.put(USER_ID, id);
            args.put(IMAGE, img);
        db.update(TABLE_USER, args, USER_ID + "=" + id, null);
        db.close();
    }

    // convert from bitmap to byte[]
    public static byte[] getByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public void updateInfoUser(int ID, String pseudo, Integer weight, Integer size, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(USER_ID, ID);
        args.put(PSEUDO, pseudo);
        args.put(WEIGHT, weight);
        args.put(SIZE, size);
        args.put(EMAIL, email);

        db.update(TABLE_USER, args, USER_ID + "=" + ID, null);
        db.close();
    }

    public Bitmap getProfilPicture(int id){
        String query = "SELECT IMAGE FROM " + TABLE_USER + " WHERE ID =" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            byte[] imgByte = cursor.getBlob(0);
            cursor.close();
            return BitmapFactory.decodeByteArray(imgByte,0, imgByte.length);
        }
        if (!cursor.isClosed()){
            cursor.close();
        }
        db.close();
        return null;
    }

    public void updataChoicesList(int id, JSONObject jsonObject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(USER_ID, id);
        args.put(CHOICES, String.valueOf(jsonObject));

        db.update(TABLE_USER, args, USER_ID + "=" + id, null);
        db.close();
    }

    public void updataInterestList(int id, JSONObject jsonObject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(USER_ID, id);
        args.put(INTEREST, String.valueOf(jsonObject));

        db.update(TABLE_USER, args, USER_ID + "=" + id, null);
        db.close();
    }

    public void updateNetwork(Network network){

        ContentValues args = new ContentValues();
        args.put(NETWORK_REQUEST, network.getListRequest());
        args.put(NETWORK_MEMBERS, network.getListMembers());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NETWORK,args,NETWORK_ID + "=" + network.getId(), null);
        db.close();
    }

}
