package app.cekongkir.java.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import app.cekongkir.java.data.model.Resi;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "LazdayResiV1";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create histori_resi table
        db.execSQL(Resi.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Resi.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    public int updateResii(String resi, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Resi.COLUMN_RESI, resi);
        values.put(Resi.COLUMN_STATUS, status);

        // updating row
        return  db.update(Resi.TABLE_NAME, values,  "resi=" + resi, null );
    }

    public long insertResi(String resi, String kurir, String status) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Resi.COLUMN_RESI, resi);
        values.put(Resi.COLUMN_KURIR, kurir);
        values.put(Resi.COLUMN_KURIR, kurir);
        values.put(Resi.COLUMN_STATUS, status);

        // insert row
        long id = db.insert(Resi.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Resi getResi(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Resi.TABLE_NAME,
                new String[]{Resi.COLUMN_ID, Resi.COLUMN_RESI, Resi.COLUMN_KURIR, Resi.COLUMN_STATUS, Resi.COLUMN_TIMESTAMP},
                Resi.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Resi resi = new Resi(
                cursor.getInt(cursor.getColumnIndex(Resi.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Resi.COLUMN_RESI)),
                cursor.getString(cursor.getColumnIndex(Resi.COLUMN_KURIR)),
                cursor.getString(cursor.getColumnIndex(Resi.COLUMN_STATUS)),
                cursor.getString(cursor.getColumnIndex(Resi.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return resi;
    }

    public List<Resi> getAllResi() {
        List<Resi> resis = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Resi.TABLE_NAME + " ORDER BY " +
                Resi.COLUMN_TIMESTAMP + " DESC LIMIT 100";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Resi resi = new Resi();
                resi.setId(cursor.getInt(cursor.getColumnIndex(Resi.COLUMN_ID)));
                resi.setResi(cursor.getString(cursor.getColumnIndex(Resi.COLUMN_RESI)));
                resi.setKurir(cursor.getString(cursor.getColumnIndex(Resi.COLUMN_KURIR)));
                resi.setStatus(cursor.getString(cursor.getColumnIndex(Resi.COLUMN_STATUS)));
                resi.setTimestamp(cursor.getString(cursor.getColumnIndex(Resi.COLUMN_TIMESTAMP)));

                resis.add(resi);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return resis;
    }

    public int getResisCount() {
        String countQuery = "SELECT * FROM " + Resi.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getResiExists(String resi) {
        String countQuery = "SELECT * FROM " + Resi.TABLE_NAME + " WHERE resi = '" + resi + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateResi(Resi resi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Resi.COLUMN_RESI, resi.getResi());
        values.put(Resi.COLUMN_KURIR, resi.getKurir());
        values.put(Resi.COLUMN_STATUS, resi.getStatus());

        // updating row
        return db.update(Resi.TABLE_NAME, values, Resi.COLUMN_ID + " = ?",
                new String[]{String.valueOf(resi.getId())});
    }

    public void deleteResi(Resi resi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Resi.TABLE_NAME, Resi.COLUMN_ID + " = ?",
                new String[]{String.valueOf(resi.getId())});
        db.close();
    }
}