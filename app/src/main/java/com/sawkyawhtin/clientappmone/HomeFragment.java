
package com.sawkyawhtin.clientappmone;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    RewardedVideoAd rewardedVideoAd;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(rewardedVideoAd.isLoaded())
            {
                rewardedVideoAd.show();
            }
        }
    };

    public static CarouselView carouselView;

    public static ArrayList<String> sampleImages = new ArrayList<>();
    View myView;
    static Context context;
    static TextView txtallmovie,txtallseries,txtallcategory,txtAllLiveTV;
    static RecyclerView allMovie,allSeries,allCategory,allLiveTV;
    static ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(context)
                    .load(sampleImages.get(position))
                    .into(imageView);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        GoogleAds googleAds = new GoogleAds();
        rewardedVideoAd = googleAds.loadRewaredVideoAds(getContext());
        googleAds.loadThreeVerticalAds(myView,getContext(),getActivity());
        handler.postDelayed(runnable,10000);
        carouselView = myView.findViewById(R.id.carouselView);
        allMovie = myView.findViewById(R.id.allmovie);
        txtallmovie = myView.findViewById(R.id.txtmovie);
        txtallseries = myView.findViewById(R.id.txtseries);
        txtallcategory = myView.findViewById(R.id.txtcategory);
        allCategory = myView.findViewById(R.id.allcategory);
        allSeries  = myView.findViewById(R.id.allseies);
        allLiveTV = myView.findViewById(R.id.alllivetv);
        txtAllLiveTV = myView.findViewById(R.id.txtLiveTV);

        SeriesFragment.activity = getActivity();
        MovieFragment.activity =  getActivity();
        context = getContext();
        HomeFragment.carouselView.setPageCount(sampleImages.size());

        HomeFragment.carouselView.setImageListener(imageListener);

        FirebaseConnect fConnect = new FirebaseConnect(getContext(),getFragmentManager());
        fConnect.showSlide();
        fConnect.getAllCategory();
        fConnect.getAllMovies();
        fConnect.getAllSeries();
        fConnect.getAllLiveTV();


        return  myView;
    }




}
