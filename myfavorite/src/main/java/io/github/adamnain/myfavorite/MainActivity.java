package io.github.adamnain.myfavorite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.adamnain.myfavorite.adapter.FavoriteAdapter;
import io.github.adamnain.myfavorite.model.Favorite;

import static android.provider.BaseColumns._ID;
import static io.github.adamnain.myfavorite.db.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private Cursor list;
    private FavoriteAdapter adapter;
    ArrayList<Favorite> listMovies;

    @BindView(R.id.rv_list_favorite)
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //FavoriteHelper favoriteHelper = new FavoriteHelper(this);

        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setHasFixedSize(true);

        list = getContentResolver().query(CONTENT_URI,null,null,null,_ID + " DESC" );
        adapter = new FavoriteAdapter(this);
        adapter.setListFavorit(list);
        rvMovies.setAdapter(adapter);
    }
}
