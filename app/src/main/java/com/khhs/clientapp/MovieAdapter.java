package com.khhs.clientapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {
    ArrayList<MovieModel> movieModels = new ArrayList<>();
    Context context;
    FragmentManager fm;

    private InterstitialAd interstitialAd;
    public MovieAdapter(ArrayList<MovieModel> movieModels, Context context,FragmentManager fm) {
        this.movieModels = movieModels;
        this.context = context;
        this.fm = fm;

        GoogleAds googleAds = new GoogleAds();
        interstitialAd = googleAds.loadInterstiialAds(context);


    }public MovieAdapter(ArrayList<MovieModel> movieModels, Context context) {
        this.movieModels = movieModels;
        this.context = context;
        this.fm = fm;

        GoogleAds googleAds = new GoogleAds();
        interstitialAd = googleAds.loadInterstiialAds(context);


    }


    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movieitem,parent,false);

        return new MovieHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {
        Glide.with(context)
                .load(movieModels.get(position).movieImage)
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interstitialAd.isLoaded())
                {
                    interstitialAd.show();
                    goToNext(position);

                }
                else
                {
                    goToNext(position);
                }
            }
        });
    }

    public void goToNext(int position)
    {
        MainActivity.preFrag  = MainActivity.currentFrag;
        MainActivity.currentFrag = context.getString(R.string.movie_det_frag);
        VideoDetailFragment detfrag = new VideoDetailFragment();
        detfrag.movieModel = movieModels.get(position);
        setFragment(detfrag);
        MovieFragment.setHeader(movieModels.get(position).movieName);
    }
    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);
        }
    }

    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();

    }
}
