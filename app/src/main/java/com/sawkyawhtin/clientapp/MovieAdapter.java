package com.sawkyawhtin.clientapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

//        try {
//            Bitmap image = getImage(movieModels.get(position).movieImage);
//            holder.movieImage.setImageBitmap(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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

    public void goToNext(final int position)
    {
       /* MainActivity.preFrag  = MainActivity.currentFrag;
        MainActivity.currentFrag = context.getString(R.string.movie_det_frag);
        VideoDetailFragment detfrag = new VideoDetailFragment();
        detfrag.movieModel = movieModels.get(position);
        setFragment(detfrag);
        MovieFragment.setHeader(movieModels.get(position).movieName);*/


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage("Are You Sure To Watch?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(movieModels.get(position).movieVideo));
                        context.startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alert.show();
    }
    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class MovieHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView textView;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
        }
    }

    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();

    }

    public Bitmap getImage(String imageUrl) throws IOException {
        URL imagehttpurl = new URL(imageUrl);
        HttpURLConnection connection =(HttpURLConnection) imagehttpurl.openConnection();
        connection.setDoInput(true);
        InputStream inputStream = connection.getInputStream();
        Bitmap myImage = BitmapFactory.decodeStream(inputStream);
        return myImage;
    }

}
