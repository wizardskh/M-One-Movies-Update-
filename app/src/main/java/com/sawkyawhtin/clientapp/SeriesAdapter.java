package com.sawkyawhtin.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.MovieHolder> {
    ArrayList<SeriesModel> SeriesModels = new ArrayList<>();
    Context context;
    FragmentManager fm;

    private InterstitialAd interstitialAd;
    ArrayList<String> seriesIds = new ArrayList<>();

    public SeriesAdapter(ArrayList<SeriesModel> SeriesModels, final Context context, FragmentManager fm) {
        this.SeriesModels = SeriesModels;
        this.context = context;
        this.fm = fm;
       GoogleAds googleAds = new GoogleAds();
        interstitialAd = googleAds.loadInterstiialAds(context);
    }

    public SeriesAdapter(ArrayList<SeriesModel> seriesModels, final Context context, FragmentManager fm, ArrayList<String> seriesIds) {
        this.SeriesModels = seriesModels;
        this.context = context;
        this.fm = fm;
        this.seriesIds = seriesIds;
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
    public void onBindViewHolder(@NonNull final MovieHolder holder, final int position) {
        Glide.with(context)
                .load(SeriesModels.get(position).seriesImage)
                .into(holder.movieImage);
        holder.movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(interstitialAd.isLoaded())
               {

                   interstitialAd.show();
                   goToNext(position);
                   SeriesFragment.setHeader(SeriesModels.get(position).seriesName);
               }
               else
               {
                   goToNext(position);
                   SeriesFragment.setHeader(SeriesModels.get(position).seriesName);
               }
            }
        });
    }


    public void goToNext(int position)
    {


        if(MainActivity.preFrag.equals(context.getString(R.string.search_frag)))
        {
            MainActivity.preFrag = context.getString(R.string.search_frag);
        }
        else {
            MainActivity.preFrag = MainActivity.currentFrag;
        }
            MainActivity.currentFrag = context.getString(R.string.series_det_frag);
            SeriesDetailFragment detfrag = new SeriesDetailFragment();
            detfrag.model = SeriesModels.get(position);
            detfrag.seriesId = seriesIds.get(position);


        setFragment(detfrag);
    }

    public void setFragment(Fragment detfrag)
    {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.navContent,detfrag);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return SeriesModels.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);
        }
    }
}
