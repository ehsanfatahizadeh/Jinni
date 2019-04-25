package ehsanfatahizadehgmail.com.jinni.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ehsanfatahizadehgmail.com.jinni.models.CheckGet;
import ehsanfatahizadehgmail.com.jinni.models.CheckGive;

public class CheckGiveDbHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "check-give-db";
    private static final String TABLE_NAME = "checkgive";

    private static final String KEY_ID = "id";
    private static final String KEY_ID_CHECK = "id_check";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_DAR_VAJHE = "dar_vajhe";
    private static final String KEY_AZ_HESABE = "az_hesabe";
    private static final String KEY_NAME_BANK = "name_bank";
    private static final String KEY_SHOMARE_CHECK = "shomare_hesab";
    private static final String KEY_MABLAGH = "mablagh";
    private static final String KEY_TOZIHAT = "tozihat";
    private static final String KEY_VAZIAT = "vaziat";
    private static final String KEY_YEAR = "year";
    private static final String KEY_MONTH = "month";
    private static final String KEY_DAY = "day";
    private static final String KEY_YEAR_SHAMSI = "year_shamsi";
    private static final String KEY_MONTH_SHAMSI = "month_shamsi";
    private static final String KEY_DAY_SHAMSI = "day_shamsi";


    public CheckGiveDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CHECK_TABLE = " CREATE TABLE " +
                TABLE_NAME + "(" + KEY_ID +
                " INTEGER PRIMARY KEY," + KEY_ID_CHECK +
                " TEXT," + KEY_MOBILE +
                " TEXT," + KEY_DAR_VAJHE +
                " TEXT," + KEY_AZ_HESABE +
                " TEXT," + KEY_NAME_BANK +
                " TEXT," + KEY_SHOMARE_CHECK +
                " TEXT," + KEY_MABLAGH +
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



    public void addCheck(CheckGive checkGive){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CHECK , checkGive.getId());
        values.put(KEY_MOBILE , checkGive.getMobile());
        values.put(KEY_DAR_VAJHE , checkGive.getDar_vajhe());
        values.put(KEY_AZ_HESABE , checkGive.getAz_hesabe());
        values.put(KEY_NAME_BANK , checkGive.getName_bank());
        values.put(KEY_SHOMARE_CHECK , checkGive.shomare_check);
        values.put(KEY_MABLAGH , checkGive.getMablagh());
        values.put(KEY_TOZIHAT , checkGive.getTozihat());
        values.put(KEY_VAZIAT , checkGive.getVaziat());
        values.put(KEY_YEAR , checkGive.getYear());
        values.put(KEY_MONTH , checkGive.getMonth());
        values.put(KEY_DAY , checkGive.getDay());
        values.put(KEY_YEAR_SHAMSI , checkGive.getYear_shamsi());
        values.put(KEY_MONTH_SHAMSI , checkGive.getMonth_shamsi());
        values.put(KEY_DAY_SHAMSI , checkGive.getDay_shamsi());

        db.insert(TABLE_NAME , null , values);
        db.close();

    }



    public List<CheckGive> getAllCheckGet(){
        List<CheckGive> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery , null);
        if (cursor.moveToFirst()){
            do {
                CheckGive checkGive = new CheckGive();
                checkGive.id = cursor.getString(1);
                checkGive.mobile = cursor.getString(2);
                checkGive.dar_vajhe = cursor.getString(3);
                checkGive.az_hesabe = cursor.getString(4);
                checkGive.name_bank = cursor.getString(5);
                checkGive.shomare_check = cursor.getString(6);
                checkGive.mablagh = cursor.getString(7);
                checkGive.tozihat = cursor.getString(8);
                checkGive.vaziat = cursor.getString(9);
                checkGive.year = cursor.getString(10);
                checkGive.month = cursor.getString(11);
                checkGive.day = cursor.getString(12);
                checkGive.year_shamsi = cursor.getString(13);
                checkGive.month_shamsi = cursor.getString(14);
                checkGive.day_shamsi = cursor.getString(15);

                list.add(checkGive);

            }while (cursor.moveToNext());

        }

        return list;
    }







    public void deleteCheckGet(String check_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME , KEY_ID_CHECK +  "=?" , new String[]{check_id});
        db.close();
    }



    public boolean checkExist(CheckGive checkGive){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME+" where "+KEY_ID_CHECK+" =?", new String[]{checkGive.getId()});
        if (c.moveToLast()) {
            return true;

        }else {
            return false;
        }



    }











}
