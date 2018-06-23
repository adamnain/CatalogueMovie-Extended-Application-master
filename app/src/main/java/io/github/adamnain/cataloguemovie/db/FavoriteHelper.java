package io.github.adamnain.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import io.github.adamnain.cataloguemovie.model.Favorite;

import static android.provider.BaseColumns._ID;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.*;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.TABLE_FAVORITE;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }


    /**
     * Gunakan method ini untuk ambil semua note yang ada
     * Otomatis di parsing ke dalam model Note
     * @return hasil query berbentuk array model note
     */
    public ArrayList<Favorite> query(){
        ArrayList<Favorite> arrayList = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount()>0) {
            do {

                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COVER)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Gunakan method ini untuk query insert
     * @param favorite model note yang akan dimasukkan
     * @return id dari data yang baru saja dimasukkan
     */
    public long insert(Favorite favorite){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, favorite.getTitle());
        initialValues.put(COVER, favorite.getTitle());
        initialValues.put(BACKDROP, favorite.getTitle());
        initialValues.put(OVERVIEW, favorite.getTitle());
        initialValues.put(RELEASE, favorite.getTitle());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    /**
     * Gunakan method ini untuk query update
     * @param favorite model note yang akan diubah
     * @return int jumlah dari row yang ter-update, jika tidak ada yang diupdate maka nilainya 0
     */
    public int update(Favorite favorite){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, favorite.getTitle());
        initialValues.put(COVER, favorite.getTitle());
        initialValues.put(BACKDROP, favorite.getTitle());
        initialValues.put(OVERVIEW, favorite.getTitle());
        initialValues.put(RELEASE, favorite.getTitle());
        return database.update(DATABASE_TABLE, initialValues, _ID + "= '" + favorite.getId() + "'", null);
    }

    /**
     * Gunakan method ini untuk query delete
     * @param id id yang akan di delete
     * @return int jumlah row yang di delete
     */
    public int delete(int id){
        return database.delete(TABLE_FAVORITE, _ID + " = '"+id+"'", null);
    }



    /*
    METHOD DI BAWAH INI ADALAH QUERY UNTUK CONTENT PROVIDER
    NILAI BALIK CURSOR
    */

    /**
     * Ambil data dari note berdasarakan parameter id
     * Gunakan method ini untuk ambil data di dalam provider
     * @param id id note yang dicari
     * @return cursor hasil query
     */
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     * @return cursor hasil query
     */
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " DESC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    /**
     * Update data dalam database
     * @param id data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    /**
     * Delete data dalam database
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }
}
