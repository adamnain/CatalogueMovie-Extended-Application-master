package io.github.adamnain.cataloguemovie.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import io.github.adamnain.cataloguemovie.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.getColumnInt;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.getColumnString;

public class Favorite implements Parcelable {
    private int id;
    private String title;
    private String cover;
    private String backdrop;
    private String overview;
    private String release;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.cover);
        dest.writeString(this.backdrop);
        dest.writeString(this.overview);
        dest.writeString(this.release);
    }

    public Favorite() {

    }

    public Favorite(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.FavoriteColumns.TITLE);
        this.cover = getColumnString(cursor, DatabaseContract.FavoriteColumns.COVER);
        this.backdrop = getColumnString(cursor, DatabaseContract.FavoriteColumns.BACKDROP);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.OVERVIEW);
        this.release = getColumnString(cursor, DatabaseContract.FavoriteColumns.RELEASE);

    }

    protected Favorite(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.cover = in.readString();
        this.backdrop = in.readString();
        this.overview = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<Favorite> CREATOR = new Parcelable.Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
