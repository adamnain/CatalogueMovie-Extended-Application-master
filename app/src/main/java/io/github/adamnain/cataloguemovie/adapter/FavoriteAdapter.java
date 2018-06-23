package io.github.adamnain.cataloguemovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import io.github.adamnain.cataloguemovie.R;
import io.github.adamnain.cataloguemovie.model.Favorite;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewholder>{
    private Cursor listFavorit;
    private Activity activity;
    private Context context;


    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListFavorit(Cursor listFavorit) {
        this.listFavorit = listFavorit;
    }

    @Override
    public FavoriteViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movies, null, false);
        return new FavoriteViewholder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewholder holder, int position) {
        final Favorite movies = getItem(position);
        String imgPath = "http://image.tmdb.org/t/p/w185"+movies.getCover();
        Glide.with(activity)
                .load(imgPath)
                .into(holder.imgMovies);
        holder.tvTitle.setText(movies.getTitle());
        holder.tvOverview.setText(movies.getOverview());
        holder.tvRealease.setText(movies.getRelease());
    }

    @Override
    public int getItemCount() {
        if (listFavorit == null) return 0;
        return listFavorit.getCount();
    }

    private Favorite getItem(int position){
        if (!listFavorit.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(listFavorit);
    }

    class FavoriteViewholder extends RecyclerView.ViewHolder{
        private CardView cvMovies;
        private ImageView imgMovies;
        private TextView tvTitle, tvOverview, tvRealease;

        FavoriteViewholder(View itemView) {
            super(itemView);
            cvMovies = itemView.findViewById(R.id.cv_movies);
            imgMovies = itemView.findViewById(R.id.img_cover);
            tvTitle = itemView.findViewById(R.id.tv_judul);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvRealease = itemView.findViewById(R.id.tv_release_date);

        }

    }
}
