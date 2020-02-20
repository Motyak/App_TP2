package com.ceri.tp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WineDbHelper extends SQLiteOpenHelper {

    private static final String TAG = WineDbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "wine.db";

    public static final String TABLE_NAME = "cellar";

    public static final String _ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WINE_REGION = "region";
    public static final String COLUMN_LOC = "localization";
    public static final String COLUMN_CLIMATE = "climate";
    public static final String COLUMN_PLANTED_AREA = "publisher";

    public WineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
	// db.execSQL() with the CREATE TABLE ... command

        Log.d(TAG, "onCreate: ");

        db.execSQL("create table " + TABLE_NAME + "(" +
                _ID + " integer primary key autoincrement," +
                COLUMN_NAME + " text," +
                COLUMN_WINE_REGION + " text," +
                COLUMN_LOC + " text," +
                COLUMN_CLIMATE + " text," +
                COLUMN_PLANTED_AREA + " text," +
                "unique(" + COLUMN_NAME + "," + COLUMN_WINE_REGION + ") on conflict rollback");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


   /**
     * Adds a new wine
     * @return  true if the wine was added to the table ; false otherwise (case when the pair (name, region) is
     * already in the data base
     */
    public boolean addWine(Wine wine) {
        SQLiteDatabase db = this.getWritableDatabase();



        // Inserting Row
        long rowID = 0;

//        create ContentValues from Wine
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, wine.getTitle());
        values.put(COLUMN_WINE_REGION, wine.getRegion());
        values.put(COLUMN_LOC, wine.getLocalization());
        values.put(COLUMN_CLIMATE, wine.getClimate());
        values.put(COLUMN_PLANTED_AREA, wine.getPlantedArea());

	// call db.insert()
        rowID = db.insert(TABLE_NAME, null, values);

        db.close(); // Closing database connection

        return (rowID != -1);
    }

    /**
     * Updates the information of a wine inside the data base
     * @return the number of updated rows
     */
    public int updateWine(Wine wine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, wine.getTitle());
        cv.put(COLUMN_CLIMATE, wine.getClimate());
        cv.put(COLUMN_LOC, wine.getLocalization());
        cv.put(COLUMN_PLANTED_AREA, wine.getPlantedArea());
        cv.put(COLUMN_WINE_REGION, wine.getRegion());

        // call db.update()
        return db.update(TABLE_NAME, cv, _ID+"="+wine.getId(), null);
    }

    /**
     * Returns a cursor on all the wines of the library
     */
    public Cursor fetchAllWines() {
        SQLiteDatabase db = this.getReadableDatabase();

	Cursor cursor;

	// call db.query()
        cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

     public void deleteWine(Wine wine) {
        SQLiteDatabase db = this.getWritableDatabase();
        // call db.delete();
         db.delete(TABLE_NAME, _ID+"="+String.valueOf(wine.getId()), null);

        db.close();
    }

    public Wine getWine(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return this.cursorToWine(cursor);
    }

    public Wine getWine(String title, String region) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;

        cursor = db.query(TABLE_NAME,
                null,
                COLUMN_NAME + " = ? and " + COLUMN_WINE_REGION + " = ?",
                new String[]{title, region},
                null,
                null,
                null);

//        if (cursor != null) {
//            cursor.moveToFirst();
//        }

        if(!cursor.moveToFirst() || cursor.getCount() == 0)
            return null;

        return this.cursorToWine(cursor);
    }

     public void populate() {
        Log.d(TAG, "call populate()");
        addWine(new Wine("Châteauneuf-du-pape", "vallée du Rhône ", "Vaucluse", "méditerranéen", "3200"));
        addWine(new Wine("Arbois", "Jura", "Jura", "continental et montagnard", "812"));
        addWine(new Wine("Beaumes-de-Venise", "vallée du Rhône", "Vaucluse", "méditerranéen", "503"));
        addWine(new Wine("Begerac", "Sud-ouest", "Dordogne", "océanique dégradé", "6934"));
        addWine(new Wine("Côte-de-Brouilly", "Beaujolais", "Rhône", "océanique et continental", "320"));
        addWine(new Wine("Muscadet", "vallée de la Loire", "Loire-Atlantique", "océanique", "9000"));
        addWine(new Wine("Bandol", "Provence", "Var", "méditerranéen", "1500"));
        addWine(new Wine("Vouvray", "Indre-et-Loire", "Indre-et-Loire", "océanique dégradé", "2000"));
        addWine(new Wine("Ayze", "Savoie", "Haute-Savoie", "continental et montagnard", "20"));
        addWine(new Wine("Meursault", "Bourgogne", "Côte-d'Or", "océanique et continental", "395"));
        addWine(new Wine("Ventoux", "Vallée du Rhône", "Vaucluse", "méditerranéen", "7450"));



        SQLiteDatabase db = this.getReadableDatabase();
        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM "+TABLE_NAME, null);
        Log.d(TAG, "nb of rows="+numRows);
        db.close();
    }


    public static Wine cursorToWine(Cursor cursor) {
        Wine wine = new Wine();
	// build a Wine object from cursor
        wine.setId(cursor.getLong(cursor.getColumnIndex(_ID)));
        wine.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        wine.setRegion(cursor.getString(cursor.getColumnIndex(COLUMN_WINE_REGION)));
        wine.setLocalization(cursor.getString(cursor.getColumnIndex(COLUMN_LOC)));
        wine.setClimate(cursor.getString(cursor.getColumnIndex(COLUMN_CLIMATE)));
        wine.setPlantedArea(cursor.getString(cursor.getColumnIndex(COLUMN_PLANTED_AREA)));

        return wine;
    }
}
