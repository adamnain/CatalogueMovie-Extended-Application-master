package io.github.adamnain.cataloguemovie.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.adamnain.cataloguemovie.R;
import io.github.adamnain.cataloguemovie.adapter.FavoriteAdapter;
import io.github.adamnain.cataloguemovie.db.FavoriteHelper;
import io.github.adamnain.cataloguemovie.model.Favorite;

import static android.provider.BaseColumns._ID;
import static io.github.adamnain.cataloguemovie.db.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    View v;
    private Cursor list;
    private FavoriteAdapter adapter;
    ArrayList<Favorite> listMovies;

    @BindView(R.id.rv_list_favorite)
    RecyclerView rvMovies;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, v);

        FavoriteHelper favoriteHelper = new FavoriteHelper(getActivity());

        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setHasFixedSize(true);

        list = getActivity().getContentResolver().query(CONTENT_URI,null,null,null,_ID + " DESC" );
        adapter = new FavoriteAdapter(getActivity());
        adapter.setListFavorit(list);
        rvMovies.setAdapter(adapter);

        return v;
    }

}
