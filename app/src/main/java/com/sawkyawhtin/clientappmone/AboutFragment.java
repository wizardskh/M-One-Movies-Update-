package com.sawkyawhtin.clientappmone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }


    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      myView = inflater.inflate(R.layout.fragment_about, container, false);
      GoogleAds googleAds = new GoogleAds();
      googleAds.loadAdVerticalAds(myView,getContext(),getActivity());
      return myView;

    }



}
