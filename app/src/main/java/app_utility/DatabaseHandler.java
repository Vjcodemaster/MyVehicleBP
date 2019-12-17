package app_utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MY_VEHICLE";

    private static final String TABLE_MAIN = "TABLE_MAIN";
    private static final String TABLE_SERVICE = "TABLE_SERVICE";

    private static final String KEY_ID = "_id";

    //Table Main keys
    private static final String KEY_MAIN_CATEGORY = "KEY_MAIN_CATEGORY";
    private static final String KEY_SUB_CATEGORY = "KEY_SUB_CATEGORY";
    private static final String KEY_IMAGE_PATH = "KEY_IMAGE_PATH";
    private static final String KEY_INDIVIDUAL_DENT_COUNT = "KEY_INDIVIDUAL_DENT_COUNT";
    private static final String KEY_INDIVIDUAL_TIME = "KEY_INDIVIDUAL_TIME";
    private static final String KEY_INDIVIDUAL_COST = "KEY_INDIVIDUAL_COST";
    private static final String KEY_INDIVIDUAL_LENGTH = "KEY_INDIVIDUAL_LENGTH";
    private static final String KEY_INDIVIDUAL_WIDTH = "KEY_INDIVIDUAL_WIDTH";
    private static final String KEY_INDIVIDUAL_DEPTH = "KEY_INDIVIDUAL_DEPTH";
    private static final String KEY_TOTAL_DENT_COUNT = "KEY_TOTAL_DENT_COUNT";
    private static final String KEY_TOTAL_TIME = "KEY_TOTAL_TIME";
    private static final String KEY_TOTAL_COST = "KEY_TOTAL_COST";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAIN_TABLE = "CREATE TABLE " + TABLE_MAIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_MAIN_CATEGORY + " TEXT, " //can be in multiple rows
                + KEY_SUB_CATEGORY + " TEXT)"; //single data


       /* String CREATE_SERVICE_TABLE = "CREATE TABLE " + TABLE_DENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_MAIN_CATEGORY + " TEXT, " //can be in multiple rows
                + KEY_SUB_CATEGORY + " TEXT, " //can be in multiple rows
                + KEY_IMAGE_PATH + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_DENT_COUNT + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_TIME + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_COST + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_LENGTH + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_WIDTH + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_DEPTH + " TEXT, " //separated by comma
                + KEY_TOTAL_DENT_COUNT + " INTEGER, " //single data
                + KEY_TOTAL_TIME + " TEXT, " //single data
                + KEY_TOTAL_COST + " TEXT)"; //single data*/

        String CREATE_SERVICE_TABLE = "CREATE TABLE " + TABLE_SERVICE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_MAIN_CATEGORY + " TEXT, " //can be in multiple rows
                + KEY_SUB_CATEGORY + " TEXT, " //can be in multiple rows
                + KEY_IMAGE_PATH + " TEXT, " //separated by comma
                //+ KEY_INDIVIDUAL_DENT_COUNT + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_TIME + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_COST + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_LENGTH + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_WIDTH + " TEXT, " //separated by comma
                + KEY_INDIVIDUAL_DEPTH + " TEXT, " //separated by comma
                + KEY_TOTAL_DENT_COUNT + " INTEGER, " //single data
                + KEY_TOTAL_TIME + " TEXT, " //single data
                + KEY_TOTAL_COST + " TEXT)"; //single data

        db.execSQL(CREATE_MAIN_TABLE);
        db.execSQL(CREATE_SERVICE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE);

        // Create tables again
        onCreate(db);
    }

    public void addDataToTableMain(DataBaseHelper dataBaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID, dataBaseHelper.getID());
        values.put(KEY_MAIN_CATEGORY, dataBaseHelper.get_main_category());
        values.put(KEY_SUB_CATEGORY, dataBaseHelper.get_sub_category());

        // Inserting Row
        db.insert(TABLE_MAIN, null, values);

        db.close(); // Closing database connection
    }

    public void addDataToServiceTable(DataBaseHelper dataBaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID, dataBaseHelper.getID());
        values.put(KEY_MAIN_CATEGORY, dataBaseHelper.get_main_category());
        values.put(KEY_SUB_CATEGORY, dataBaseHelper.get_sub_category());
        values.put(KEY_IMAGE_PATH, dataBaseHelper.get_image_path());
        //values.put(KEY_INDIVIDUAL_DENT_COUNT, dataBaseHelper.get_individual_dent_count());
        values.put(KEY_INDIVIDUAL_TIME, dataBaseHelper.get_individual_time());
        values.put(KEY_INDIVIDUAL_COST, dataBaseHelper.get_individual_cost());
        values.put(KEY_INDIVIDUAL_LENGTH, dataBaseHelper.get_individual_length());
        values.put(KEY_INDIVIDUAL_WIDTH, dataBaseHelper.get_individual_width());
        values.put(KEY_INDIVIDUAL_DEPTH, dataBaseHelper.get_individual_depth());
        values.put(KEY_TOTAL_DENT_COUNT, dataBaseHelper.get_total_dent_count());
        values.put(KEY_TOTAL_TIME, dataBaseHelper.get_total_time());
        values.put(KEY_TOTAL_COST, dataBaseHelper.get_total_cost());
        // Inserting Row
        db.insert(TABLE_SERVICE, null, values);

        db.close(); // Closing database connection
    }

    public long addImagePathToServiceTable(DataBaseHelper dataBaseHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(KEY_ID, dataBaseHelper.getID());
        values.put(KEY_MAIN_CATEGORY, dataBaseHelper.get_main_category());
        values.put(KEY_SUB_CATEGORY, dataBaseHelper.get_sub_category());
        values.put(KEY_IMAGE_PATH, dataBaseHelper.get_image_path());
        //values.put(KEY_INDIVIDUAL_DENT_COUNT, dataBaseHelper.get_individual_dent_count());
       /* values.put(KEY_INDIVIDUAL_TIME, dataBaseHelper.get_individual_time());
        values.put(KEY_INDIVIDUAL_COST, dataBaseHelper.get_individual_cost());
        values.put(KEY_INDIVIDUAL_LENGTH, dataBaseHelper.get_individual_length());
        values.put(KEY_INDIVIDUAL_WIDTH, dataBaseHelper.get_individual_width());
        values.put(KEY_INDIVIDUAL_DEPTH, dataBaseHelper.get_individual_depth());
        values.put(KEY_TOTAL_DENT_COUNT, dataBaseHelper.get_total_dent_count());
        values.put(KEY_TOTAL_TIME, dataBaseHelper.get_total_time());
        values.put(KEY_TOTAL_COST, dataBaseHelper.get_total_cost());*/
        // Inserting Row
        long lRowID = db.insert(TABLE_SERVICE, null, values);

        db.close(); // Closing database connection
        return lRowID;
    }

    public List<DataBaseHelper> getAllServiceData() {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        /*String selectQuery = "SELECT  " + KEY_VEHICLE_ID_ODOO + " FROM " + USER_VEHICLE_TABLE ;*/
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();

                dataBaseHelper.set_main_category(cursor.getString(0));
                dataBaseHelper.set_sub_category(cursor.getString(1));
                dataBaseHelper.set_image_path(cursor.getString(2));
                dataBaseHelper.set_individual_dent_count(cursor.getString(3));
                dataBaseHelper.set_individual_time(cursor.getString(4));
                dataBaseHelper.set_individual_cost(cursor.getString(5));
                dataBaseHelper.set_individual_length(cursor.getString(6));
                dataBaseHelper.set_individual_width(cursor.getString(7));
                dataBaseHelper.set_individual_depth(cursor.getString(8));
                dataBaseHelper.set_total_dent_count(cursor.getInt(9));
                dataBaseHelper.set_total_time(cursor.getString(10));
                dataBaseHelper.set_total_cost(cursor.getString(11));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }

        // return recent list
        return dataBaseHelperList;
    }

    public List<DataBaseHelper> getMainCategory() {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        /*String selectQuery = "SELECT  " + KEY_VEHICLE_ID_ODOO + " FROM " + USER_VEHICLE_TABLE ;*/
        String selectQuery = "SELECT " + KEY_MAIN_CATEGORY + " FROM " + TABLE_MAIN;
        //String selectQuery = "SELECT  * FROM " + TABLE_DENTS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();

                dataBaseHelper.set_main_category(cursor.getString(0));

                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }

        // return recent list
        return dataBaseHelperList;
    }

    public List<DataBaseHelper> getDataByMainCategory(String sMainCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY + "," + KEY_IMAGE_PATH + "," + KEY_TOTAL_DENT_COUNT + "," + KEY_TOTAL_TIME
                + "," + KEY_TOTAL_COST + " FROM " + TABLE_SERVICE + " WHERE " + KEY_MAIN_CATEGORY + "= '" + sMainCategory + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();
                dataBaseHelper.set_sub_category(cursor.getString(0));
                dataBaseHelper.set_image_path(cursor.getString(1));
                dataBaseHelper.set_total_dent_count(cursor.getInt(2));
                dataBaseHelper.set_total_time(cursor.getString(3));
                dataBaseHelper.set_total_cost(cursor.getString(4));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }
        // return recent list
        return dataBaseHelperList;
    }

    public List<DataBaseHelper> getDataBySubCategory(String sSubCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query

        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_IMAGE_PATH + "," + KEY_INDIVIDUAL_TIME + ","
                + KEY_INDIVIDUAL_COST + "," + KEY_INDIVIDUAL_LENGTH + "," + KEY_INDIVIDUAL_WIDTH + "," + KEY_INDIVIDUAL_DEPTH + ","
                + KEY_TOTAL_DENT_COUNT + "," + KEY_TOTAL_TIME + "," + KEY_TOTAL_COST + " FROM " + TABLE_SERVICE + " WHERE "
                + KEY_SUB_CATEGORY + "= '" + sSubCategory + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();
                dataBaseHelper.set_image_path(cursor.getString(0));
                //dataBaseHelper.set_individual_dent_count(cursor.getString(1));
                dataBaseHelper.set_individual_time(cursor.getString(1));
                dataBaseHelper.set_individual_cost(cursor.getString(2));
                dataBaseHelper.set_individual_length(cursor.getString(3));
                dataBaseHelper.set_individual_width(cursor.getString(4));
                dataBaseHelper.set_individual_depth(cursor.getString(5));
                dataBaseHelper.set_total_dent_count(cursor.getInt(6));
                dataBaseHelper.set_total_time(cursor.getString(7));
                dataBaseHelper.set_total_cost(cursor.getString(8));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }
        // return recent list
        return dataBaseHelperList;
    }


    public String getSCByMCNameMainTable(String sMainCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY + " FROM " + TABLE_MAIN + " WHERE " + KEY_MAIN_CATEGORY + "= '" + sMainCategory + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String sSubCategory = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            //do {
            DataBaseHelper dataBaseHelper = new DataBaseHelper();
            dataBaseHelper.set_sub_category(cursor.getString(0));
            //dataBaseHelper.set_image_path(cursor.getString(1));
            // Adding data to list
            dataBaseHelperList.add(dataBaseHelper);
            sSubCategory = dataBaseHelperList.get(0).get_sub_category();
            //} while (cursor.moveToNext());
        }
        // return recent list
        return sSubCategory;
    }

    // ST = Service Table MC = MainCategory SC = SubCategory
    public List<DataBaseHelper> getSCFromSTByMCName(String sMainCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY + "," + KEY_IMAGE_PATH + " FROM " + TABLE_SERVICE + " WHERE " + KEY_MAIN_CATEGORY + "= '" + sMainCategory + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //String sSubCategory = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            //do {
            DataBaseHelper dataBaseHelper = new DataBaseHelper();
            dataBaseHelper.set_sub_category(cursor.getString(0));
            dataBaseHelper.set_image_path(cursor.getString(1));
            // Adding data to list
            dataBaseHelperList.add(dataBaseHelper);
            //sSubCategory = dataBaseHelperList.get(0).get_sub_category();
            //} while (cursor.moveToNext());
        }
        // return recent list
        return dataBaseHelperList;
    }

    public List<DataBaseHelper> getImagePathFromServiceTable(String sMainCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_SUB_CATEGORY + "," + KEY_IMAGE_PATH + "," + KEY_TOTAL_DENT_COUNT + ","
                + KEY_TOTAL_TIME + "," + KEY_TOTAL_COST + " FROM " + TABLE_SERVICE + " WHERE " + KEY_MAIN_CATEGORY
                + "= '" + sMainCategory + "'";
        //String selectQuery = "SELECT " + KEY_SUB_CATEGORY + "," + KEY_IMAGE_PATH + " FROM " + TABLE_SERVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //String sSubCategory = "";
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();
                dataBaseHelper.set_sub_category(cursor.getString(0));
                dataBaseHelper.set_image_path(cursor.getString(1));
                dataBaseHelper.set_total_dent_count(cursor.getInt(2));
                dataBaseHelper.set_total_time(cursor.getString(3));
                dataBaseHelper.set_total_cost(cursor.getString(4));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
                //sSubCategory = dataBaseHelperList.get(0).get_sub_category();
                //} while (cursor.moveToNext());
            } while (cursor.moveToNext());
        }
        // return recent list
        return dataBaseHelperList;
    }

    public void updateImagePath(DataBaseHelper dataBaseHelper, int KEY_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String column = "last_seen";
        ContentValues values = new ContentValues();
        //values.put(KEY_NAME, dataBaseHelper.getName());
        //values.put(KEY_NUMBER, dataBaseHelper.getPhoneNumber());
        values.put(KEY_IMAGE_PATH, dataBaseHelper.get_image_path());

        // updating row
        //return db.update(TABLE_RECENT, values, column + "last_seen", new String[] {String.valueOf(KEY_ID)});
        db.update(TABLE_SERVICE, values, "_id" + " = " + KEY_ID, null);
    }

    /*// Updating single data in all tab
    public int updateImagePath(DataBaseHelper dataBaseHelper, int KEY_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String column = "last_seen";
        ContentValues values = new ContentValues();
        //values.put(KEY_NAME, dataBaseHelper.getName());
        //values.put(KEY_NUMBER, dataBaseHelper.getPhoneNumber());
        values.put(KEY_LAST_SEEN_TIME, dataBaseHelper.getLastSeen());

        // updating row
        //return db.update(TABLE_RECENT, values, column + "last_seen", new String[] {String.valueOf(KEY_ID)});
        return db.update(TABLE_ALL, values, "_id" + " = " + KEY_ID, null);
        *//*ContentValues data=new ContentValues();
        data.put("Field1","bob");
        DB.update(Tablename, data, "_id=" + id, null);*//*
    }*/


    /*public List<DataBaseHelper> getDentsBySubCategory(String sSubCategory) {
        List<DataBaseHelper> dataBaseHelperList = new ArrayList<>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sType+"'";
        String selectQuery = "SELECT " + KEY_PRODUCT_ODOO_ID + "," + KEY_PRODUCT_NAME + "," + KEY_PRODUCT_PRICE + " FROM " +
                TABLE_FOOD_BEVERAGES + " WHERE " + KEY_PRODUCT_TYPE + "= '" + sSubCategory+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataBaseHelper dataBaseHelper = new DataBaseHelper();
                dataBaseHelper.set_product_odoo_id(cursor.getInt(0));
                dataBaseHelper.set_product_name(cursor.getString(1));
                dataBaseHelper.set_product_price(cursor.getString(2));
                // Adding data to list
                dataBaseHelperList.add(dataBaseHelper);
            } while (cursor.moveToNext());
        }
        // return recent list
        return dataBaseHelperList;
    }*/

    /*public int getIdForStringTablePermanent(String str) {
        int res;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOOD_BEVERAGES, new String[]{KEY_PRODUCT_ODOO_ID,
                }, KEY_PRODUCT_NAME + "=?",
                new String[]{str}, null, null, null, null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            res = cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_ODOO_ID));
        } else {
            res = -1;
        }
        if (cursor != null) {
            cursor.close();
        }
        return res;
    }*/
}
