package ehsanfatahizadehgmail.com.jinni.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.models.CheckGet;


public class CheckGetDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "check-get-db";
    private static final String TABLE_NAME = "checkget";
    private static final String KEY_ID = "id";
    private static final String KEY_ID_CHECK = "id_check";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_TAVASOTE = "tavasote";
    private static final String KEY_SHOMARE_CHECK = "shomare_check";
    private static final String KEY_GHEYMAT = "gheymat";
    private static final String KEY_TOZIHAT = "tozihat";
    private static final String KEY_VAZIAT = "vaziat";
    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";
    private static final String KEY_YEAR_SHAMSI = "year_shamsi";
    private static final String KEY_MONTH_SHAMSI = "month_shamsi";
    private static final String KEY_DAY_SHAMSI = "day_shamsi";







    public CheckGetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHECK_TABLE = " CREATE TABLE " +
                TABLE_NAME + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_ID_CHECK +
                " TEXT," + KEY_MOBILE +
                " TEXT," + KEY_TAVASOTE +
                " TEXT," + KEY_SHOMARE_CHECK +
                " TEXT," + KEY_GHEYMAT +
                " TEXT," + KEY_TOZIHAT +
                " TEXT," + KEY_VAZIAT +
                " TEXT," + KEY_YEAR +
                " TEXT," + KEY_MONTH +
                " TEXT," + KEY_DAY +
                " TEXT," + KEY_YEAR_SHAMSI +
                " TEXT," + KEY_MONTH_SHAMSI +
                " TEXT," + KEY_DAY_SHAMSI +
                " TEXT" + ")";
        db.execSQL(CREATE_CHECK_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCheck(CheckGet checkGet){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CHECK , checkGet.getId());
        values.put(KEY_MOBILE , checkGet.getMobile());
        values.put(KEY_TAVASOTE , checkGet.getTavasote());
        values.put(KEY_SHOMARE_CHECK , checkGet.shomare_check);
        values.put(KEY_GHEYMAT , checkGet.getGheymat());
        values.put(KEY_TOZIHAT , checkGet.getTozihat());
        values.put(KEY_VAZIAT , checkGet.getVaziat());
        values.put(KEY_YEAR , checkGet.getYear());
        values.put(KEY_MONTH , checkGet.getMonth());
        values.put(KEY_DAY , checkGet.getDay());
        values.put(KEY_YEAR_SHAMSI , checkGet.getYear_shamsi());
        values.put(KEY_MONTH_SHAMSI , checkGet.getMonth_shamsi());
        values.put(KEY_DAY_SHAMSI , checkGet.getDay_shamsi());

        db.insert(TABLE_NAME , null , values);
        db.close();

    }


//    CheckGet getCheckGet(int id){
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME , new String[]{
//                KEY_ID , KEY_ID_CHECK, KEY_MOBILE ,KEY_TAVASOTE,
//                KEY_SHOMARE_CHECK, KEY_GHEYMAT, KEY_TOZIHAT,
//                KEY_VAZIAT, KEY_YEAR, KEY_MONTH, KEY_DAY,
//                KEY_YEAR_SHAMSI, KEY_MONTH_SHAMSI,
//                KEY_DAY_SHAMSI} , KEY_ID + "=?" ,
//                new String[]{String.valueOf(id)} , null , null,
//                null , null);
//        if (cursor != null){
//            cursor.moveToFirst();
//        }
//
//        CheckGet checkGet = new CheckGet();
//
//
//        checkGet.id = cursor.getString(1);
//        checkGet.mobile = cursor.getString(2);
//        checkGet.tavasote = cursor.getString(3);
//        checkGet.shomare_check = cursor.getString(4);
//        checkGet.gheymat = cursor.getString(5);
//        checkGet.tozihat = cursor.getString(6);
//        checkGet.vaziat = cursor.getString(7);
//        checkGet.year = cursor.getString(8);
//        checkGet.month = cursor.getString(9);
//        checkGet.day = cursor.getString(10);
//        checkGet.year_shamsi = cursor.getString(11);
//        checkGet.month_shamsi = cursor.getString(12);
//        checkGet.day_shamsi = cursor.getString(13);
//
//
//        return checkGet;
//
//    }

    public List<CheckGet> getAllCheckGet(){
        List<CheckGet> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery , null);
        if (cursor.moveToFirst()){
            do {
                CheckGet checkGet = new CheckGet();
                checkGet.id = cursor.getString(1);
                checkGet.mobile = cursor.getString(2);
                checkGet.tavasote = cursor.getString(3);
                checkGet.shomare_check = cursor.getString(4);
                checkGet.gheymat = cursor.getString(5);
                checkGet.tozihat = cursor.getString(6);
                checkGet.vaziat = cursor.getString(7);
                checkGet.year = cursor.getString(8);
                checkGet.month = cursor.getString(9);
                checkGet.day = cursor.getString(10);
                checkGet.year_shamsi = cursor.getString(11);
                checkGet.month_shamsi = cursor.getString(12);
                checkGet.day_shamsi = cursor.getString(13);

                list.add(checkGet);

            }while (cursor.moveToNext());

        }

        return list;
    }



    public void deleteCheckGet(String check_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME , KEY_ID_CHECK +  "=?" , new String[]{check_id});
        db.close();
    }



    public boolean checkExist(CheckGet checkGet){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME+" where "+KEY_ID_CHECK+" =?", new String[]{checkGet.getId()});
        if (c.moveToLast()) {
            return true;

        }else {
            return false;
        }



    }







}
