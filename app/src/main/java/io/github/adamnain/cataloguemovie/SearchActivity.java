package io.github.adamnain.cataloguemovie;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

import io.github.adamnain.cataloguemovie.adapter.MoviesAdapter;
import io.github.adamnain.cataloguemovie.model.ResponseMovies;
import io.github.adamnain.cataloguemovie.model.Result;
import io.github.adamnain.cataloguemovie.service.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<Result> listMovies;
    RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    private RecyclerView rvMovies;
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Memasang Toolbar pada Aplikasi
        Toolbar toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getStringExtra("keyword"));

        rvMovies = findViewById(R.id.rv_list_movies);
        listMovies = new ArrayList<>();

        if (getIntent().getStringExtra("keyword") != null){
            searchMovie(getIntent().getStringExtra("keyword"));
            Toast.makeText(getApplicationContext(),getIntent().getStringExtra("keyword"),Toast.LENGTH_SHORT).show();

        }
        else{
            getAllMovie();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navbar, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchMovie(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void getAllMovie(){
        Call<ResponseMovies> call = UtilsApi.getAPIService().getAllMovies(BuildConfig.MOVIE_DB_API, language, sort_by, include_adult, include_video, page);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    listMovies = (ArrayList<Result>) response.body().getResults();

                    rvMovies.setAdapter(new MoviesAdapter(getApplicationContext(), listMovies));
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(SearchActivity.this, "not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView = findViewById(R.id.rv_list_movies);

        mAdapter = new MoviesAdapter(getApplicationContext(), listMovies);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    public void searchMovie(String keyword){

        Call<ResponseMovies> call = UtilsApi.getAPIService().searchMovie(BuildConfig.MOVIE_DB_API, language, keyword, page, include_adult);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    listMovies = (ArrayList<Result>) response.body().getResults();

                    rvMovies.setAdapter(new MoviesAdapter(getApplicationContext(), listMovies));
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(SearchActivity.this, "not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView = findViewById(R.id.rv_list_movies);

        mAdapter = new MoviesAdapter(getApplicationContext(), listMovies);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



}
