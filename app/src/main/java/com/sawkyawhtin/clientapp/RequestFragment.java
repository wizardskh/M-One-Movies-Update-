package com.sawkyawhtin.clientapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    public RequestFragment() {
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
    FirebaseFirestore db;
    CollectionReference ref;
View myView;
    Button btnsave,btncancel;
    EditText edtname,edtimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      myView = inflater.inflate(R.layout.fragment_request, container, false);

      GoogleAds googleAds = new GoogleAds();
      rewardedVideoAd = googleAds.loadRewaredVideoAds(getContext());
      googleAds.loadAdVerticalAds(myView,getContext(),getActivity());

      handler.postDelayed(runnable,5000);


      edtname = myView.findViewById(R.id.edtname);
      edtimage = myView.findViewById(R.id.edtimage);
      btnsave = myView.findViewById(R.id.btnsave);
      btncancel = myView.findViewById(R.id.btncancel);
      db = FirebaseFirestore.getInstance();
      final CollectionReference ref = db.collection(getString(R.string.request_ref));
      btnsave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(rewardedVideoAd.isLoaded())
              {
                  rewardedVideoAd.show();
              }
             if(!edtimage.getText().toString().trim().equals("")
             || !edtname.getText().toString().trim().equals(""))
             {
                 RequestModel model = new RequestModel();
                 model.name = edtname.getText().toString().trim();
                 model.imagelink = edtimage.getText().toString().trim();
                 SimpleDateFormat format = new SimpleDateFormat("ddMMyyyyhhMMss");
                 model.createdAt = format.format(new Date());
                 ref.add(model);
                 edtname.setText("");
                 edtimage.setText("");
                 Toasty.success(getContext(),"Request Send",Toasty.LENGTH_LONG).show();
             }
             else {
                 Toasty.error(getContext(),"Please Fill Data",Toasty.LENGTH_LONG).show();
             }
          }
      });


        return  myView;

    }



}
