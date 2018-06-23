package io.github.adamnain.cataloguemovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import io.github.adamnain.cataloguemovie.DetailActivity;
import io.github.adamnain.cataloguemovie.R;
import io.github.adamnain.cataloguemovie.model.Result;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Result> listMovies;

    public MoviesAdapter(Context context, ArrayList<Result> listMovies){
        this.context = context;
        this.listMovies = listMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movies, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mItemView.setLayoutParams(layoutParams);

        return new ViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result movies = listMovies.get(position);
        String imgPath = "http://image.tmdb.org/t/p/w185"+movies.getPosterPath();
        Glide.with(context)
                .load(imgPath)
                .into(holder.imgMovies);
        holder.tvTitle.setText(movies.getTitle());
        holder.tvOverview.setText(movies.getOverview());
        holder.tvRealease.setText(movies.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cvMovies;
        private ImageView imgMovies;
        private TextView tvTitle, tvOverview, tvRealease;
        final MoviesAdapter mAdapter;


        public ViewHolder(View itemView, MoviesAdapter adapter) {
            super(itemView);

            cvMovies = itemView.findViewById(R.id.cv_movies);
            imgMovies = itemView.findViewById(R.id.img_cover);
            tvTitle = itemView.findViewById(R.id.tv_judul);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRealease = itemView.findViewById(R.id.tv_release_date);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            Result movies = listMovies.get(mPosition);

            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("title", movies.getTitle());
            i.putExtra("backdrop", movies.getBackdropPath());
            i.putExtra("overview", movies.getOverview());
            i.putExtra("release", movies.getReleaseDate());
            i.putExtra("cover", movies.getPosterPath());

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            mAdapter.notifyDataSetChanged();

        }
    }


}
