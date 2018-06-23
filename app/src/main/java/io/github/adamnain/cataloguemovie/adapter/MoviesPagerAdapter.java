package io.github.adamnain.cataloguemovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import io.github.adamnain.cataloguemovie.fragment.FavoriteFragment;
import io.github.adamnain.cataloguemovie.fragment.NowPlayingFragment;
import io.github.adamnain.cataloguemovie.fragment.UpcomingFragment;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    private int number_tabs;

    public MoviesPagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    //Mengembalikan Fragment yang terkait dengan posisi tertentu
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NowPlayingFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    //Mengembalikan jumlah tampilan yang tersedia.
    @Override
    public int getCount() {
        return number_tabs;
    }
}
