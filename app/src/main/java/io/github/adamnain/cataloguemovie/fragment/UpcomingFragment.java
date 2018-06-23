package io.github.adamnain.cataloguemovie.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import io.github.adamnain.cataloguemovie.BuildConfig;
import io.github.adamnain.cataloguemovie.adapter.MoviesAdapter;
import io.github.adamnain.cataloguemovie.R;
import io.github.adamnain.cataloguemovie.model.ResponseMovies;
import io.github.adamnain.cataloguemovie.model.Result;
import io.github.adamnain.cataloguemovie.service.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    ArrayList<Result> listMovies;
    RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    ProgressDialog loading;
    private RecyclerView rvMovies;
    private final String language = "en-US";
    private final String sort_by = "popularity.desc";
    private final String include_adult = "false";
    private final String include_video = "false";
    private final String page = "1";

    View v;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_upcoming, container, false);

        rvMovies = v.findViewById(R.id.rv_list_coming);
        listMovies = new ArrayList<>();

        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);


        Call<ResponseMovies> call = UtilsApi.getAPIService().getUpcoming(BuildConfig.MOVIE_DB_API, language, sort_by, include_adult, include_video, page);
        call.enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    listMovies = (ArrayList<Result>) response.body().getResults();

                    rvMovies.setAdapter(new MoviesAdapter(getActivity(), listMovies));
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getActivity(), "not correct", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                loading.dismiss();

                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView = v.findViewById(R.id.rv_list_coming);

        mAdapter = new MoviesAdapter(getActivity(), listMovies);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

}
