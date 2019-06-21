package com.nettox.nettoxwapps.DbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nettox.nettoxwapps.DbModel.DbModel_HrvData;

import java.util.ArrayList;
import java.util.List;

import static com.nettox.nettoxwapps.StaticFieldVariables.*;

public class DbHelper_HrvData extends SQLiteOpenHelper {

    private Context dbHelper_context;
    private SQLiteDatabase dbHelper_sqLiteDatabase;

    /**
     * Pada fungsi di bawah ini akan membuat fungsi untuk super dengan parameter
     * context, nama database, factory = null, dan versi database.
     * @param context
     */

    public DbHelper_HrvData (Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.dbHelper_context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * bagian ini kosongkan saja
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * bagian ini kosongkan saja
         */
    }

    /**
     * Pada fungsi openDatabase akan membuka lokasi/path dari database
     * lalu jika database tidak kosong dan bisa dibuka maka akan membuka database
     * tersebut.
     */

    public void openDatabase () {
        String dbPath = dbHelper_context.getDatabasePath(DB_NAME).getPath();
        if (dbHelper_sqLiteDatabase != null && dbHelper_sqLiteDatabase.isOpen()) {
            return;
        }
        dbHelper_sqLiteDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Selanjutnya pada fungsi closeDatabase akan menutup kembali database
     * yang telah diimport ke dalam aplikasi karena sudah tersimpan di dalam
     * SQLite pada aplikasi
     */

    public void closeDatabase () {
        if (dbHelper_sqLiteDatabase != null) {
            dbHelper_sqLiteDatabase.close();
        }
    }

    /**
     * Fungsi di bawah ini mengambil database dengan cursor dan query.
     * Cursor   : sebuah penunjuk untuk SQLiteDatabase
     * Query    : bahasa pemrograman untuk database
     *
     * Perlu dibuat sebuah ArrayList untuk mendapatkan data dari database yang telah diimpor
     * Lalu menggunakan cursor untuk menunjukkan data yang diambil dari database
     *
     * Setiap data yang diambil pada variabel "product" akan dimasukkan ke dalam variabel
     * array "product_list", cursor akan menuju ke row berikutnya.
     *
     * Kerja cursor:
     *  -> cursor.moveToFirst() : menuju row ke-1
     *  -> cursor.moveToNext()  : menuju row berikutnya
     *  -> cursor.isAfterLast() : mengecek ke row setelah yang terakhir
     *
     *  Pada penggunaan while, mengecek seluruh row sampai terakhir dan memasukkan ke
     *  dalam variabel "product" dan mendapatkan setiap data pada atribut di database.
     *
     * @return : hasil dari product_list
     */

    public List<DbModel_HrvData> getListProduct () {
        DbModel_HrvData product = null;
        List<DbModel_HrvData> product_list = new ArrayList<>();
        openDatabase();

        Cursor cursor = dbHelper_sqLiteDatabase.rawQuery("SELECT * FROM " + TB_HRVDATA, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            product = new DbModel_HrvData(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            product_list.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return product_list;
    }

    public void insertIntoHrvData (int hrv_result, int bpm_avg, String hrv_time, String comment, int emot) {
        SQLiteDatabase database = this.getWritableDatabase();

        String myQuery = "INSERT INTO " + TB_HRVDATA + " (hrv_result, bpm_avg, hrv_time, comment, emot) " +
                " VALUES (" +
                "" + hrv_result + "," +
                "" + bpm_avg + "," +
                "'" + hrv_time + "'," +
                "'" + comment + "'," +
                "" + emot + ")";

        database.execSQL(myQuery);
        database.close();
    }
}