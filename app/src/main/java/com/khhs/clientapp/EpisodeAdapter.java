package com.khhs.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder> {
    ArrayList<EpisodeModel> models = new ArrayList<>();
    Context context;
    FragmentManager fm;

    private  RewardedVideoAd mRewardedVideoAd;
    public EpisodeAdapter(ArrayList<EpisodeModel> models, Context context, FragmentManager fm) {
        this.models = models;
        this.context = context;
        this.fm = fm;
        GoogleAds googleAds = new GoogleAds();
        this.mRewardedVideoAd = googleAds.loadRewaredVideoAds(context);

    }

    @NonNull
    @Override
    public EpisodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodeitem,parent,false);
        EpisodeHolder holder = new EpisodeHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeHolder holder, final int position) {

        holder.epName.setText(models.get(position).episodeName);

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mRewardedVideoAd.isLoaded())
                {
                    mRewardedVideoAd.show();
                }
                EpisodeModel model = models.get(position);
                MovieModel movieModel = new MovieModel();
                movieModel.movieName = model.episodeName;
                movieModel.movieVideo = model.episodeVideo;
                goToNext(position,movieModel);
            }
        });


    }

    public void goToNext(int position,MovieModel movieModel)
    {
        if(MainActivity.preFrag.equals(context.getString(R.string.search_frag)))
        {
            MainActivity.preFrag = context.getString(R.string.search_frag);
        }
        else {
            MainActivity.preFrag = MainActivity.currentFrag;
        }
        MainActivity.currentFrag = context.getString(R.string.movie_det_frag);
        VideoDetailFragment detfrag = new VideoDetailFragment();
        detfrag.movieModel = movieModel;
        setFragment(detfrag);
        MovieFragment.setHeader(movieModel.movieName);
    }
    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();

    }
    @Override
    public int getItemCount() {
        return models.size();
    }

    public class EpisodeHolder extends RecyclerView.ViewHolder{
       TextView epName;
       ImageView play,download;
        public EpisodeHolder(@NonNull View itemView) {
            super(itemView);
            epName = itemView.findViewById(R.id.epname);
            play = itemView.findViewById(R.id.play_ep);
            download  = itemView.findViewById(R.id.download_ep);
        }
    }



}
