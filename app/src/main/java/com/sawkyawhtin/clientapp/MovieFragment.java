package com.sawkyawhtin.clientapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import recycler.coverflow.RecyclerCoverFlow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    static TextView txtallmvoie;
    static RecyclerView allmovies;
    static RecyclerCoverFlow list;
    public MovieFragment() {
        // Required empty public constructor
    }

View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     myView = inflater.inflate(R.layout.fragment_movie, container, false);


        GoogleAds googleAds = new GoogleAds();
        googleAds.loadAdVerticalAds(myView,getContext(),getActivity());
     txtallmvoie = myView.findViewById(R.id.txtallmovie);
     allmovies = myView.findViewById(R.id.allmovie);
     list = myView.findViewById(R.id.list);
     activity = getActivity();
     FirebaseConnect fConnect = new FirebaseConnect(getContext(),getFragmentManager());

     fConnect.getAllMoviesByMovieFragment();
     fConnect.getTopTenMovies();;

     return myView;
    }







    public static Activity activity;
    public static void setHeader(String header)
    {
        activity.setTitle(header);
    }

}
