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
public class SeriesFragment extends Fragment {

    public SeriesFragment() {
        // Required empty public constructor
    }

    static TextView txtallseries;
    static RecyclerView allseries;
    static RecyclerCoverFlow list;
    View myView;
   public static Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       myView = inflater.inflate(R.layout.fragment_series, container, false);
        txtallseries = myView.findViewById(R.id.txtallmovie);
        allseries= myView.findViewById(R.id.allmovie);
        activity = getActivity();
        list = myView.findViewById(R.id.list);
        GoogleAds googleAds = new GoogleAds();
        googleAds.loadAdVerticalAds(myView,getContext(),getActivity());
        FirebaseConnect fConnect = new FirebaseConnect(getContext(),getFragmentManager());

        fConnect.getAllSeriesBySeriesFragment();
        fConnect.getTopTenSeries();;

        return myView;
    }



    public static void setHeader(String header)
    {
        activity.setTitle(header);
    }

}
