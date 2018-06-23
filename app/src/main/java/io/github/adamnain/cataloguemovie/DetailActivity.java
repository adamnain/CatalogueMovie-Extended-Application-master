package io.github.adamnain.cataloguemovie;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.adamnain.cataloguemovie.model.Favorite;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.CONTENT_URI;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.BACKDROP;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.COVER;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.OVERVIEW;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.RELEASE;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.FavoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.img_backdrop_detail)
    ImageView imgBackdrop;

    @BindView(R.id.tv_title_detail)
    TextView tvTitle;

    @BindView(R.id.tv_release_detail)
    TextView tvRelease;

    @BindView(R.id.tv_overview_detail)
    TextView tvOverview;

    @BindView(R.id.iv_star)
    ImageView ivStar;

    private Favorite favorite;

    private long id;

    private String title, cover, backdrop, release, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        getIntentData();
        backButton();
        favoriteCheck();

    }


    private void getIntentData(){
        title = getIntent().getStringExtra("title");
        cover = getIntent().getStringExtra("cover");
        backdrop = getIntent().getStringExtra("backdrop");
        release = getIntent().getStringExtra("release");
        overview = getIntent().getStringExtra("overview");


        String imgPath = "http://image.tmdb.org/t/p/w185"+backdrop;
        Glide.with(this)
                .load(imgPath)
                .into(imgBackdrop);
        tvTitle.setText(title);
        tvRelease.setText(release);
        tvOverview.setText(overview);
    }

    //untuk enampilkan back button
    public void backButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
    }

    //fungsi back ketika tombol back diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_star)
    public void submitFavorite(){
        if(favoriteCheck() == true){
            Uri uri = Uri.parse(CONTENT_URI+"/"+id);
            getContentResolver().delete(uri, null, null);
            Toast.makeText(this, title+" sudah tidak difavoritkan", Toast.LENGTH_SHORT).show();
            ivStar.setImageResource(R.drawable.ic_star_before);
        }
        else{
            ContentValues values = new ContentValues();
            values.put(TITLE,title);
            values.put(COVER,cover);
            values.put(BACKDROP,backdrop);
            values.put(RELEASE,release);
            values.put(OVERVIEW,overview);

            getContentResolver().insert(CONTENT_URI,values);

            Toast.makeText(this, title+" Sudah Masuk Favorite", Toast.LENGTH_SHORT).show();
            setResult(101);
            finish();
        }

    }

    public boolean favoriteCheck(){
        Uri uri = Uri.parse(CONTENT_URI+"");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))){
                    ivStar.setImageResource(R.drawable.ic_star_after);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;

    }

}
