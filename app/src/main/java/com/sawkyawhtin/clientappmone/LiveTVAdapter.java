package com.sawkyawhtin.clientappmone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LiveTVAdapter extends RecyclerView.Adapter<LiveTVAdapter.LiveTVHolder> {
    ArrayList<LiveTvModel> liveTvModels = new ArrayList<>();    //  we create bez arraylist
    Context context;
    FragmentManager fm;
    ArrayList<String> liveTVIds;

    public LiveTVAdapter(ArrayList<LiveTvModel> liveTvModels,
                         Context context,FragmentManager fm) {
        this.liveTvModels = liveTvModels;
        this.context = context;
        this.fm = fm;

    }



    @NonNull
    @Override
    public LiveTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.livetvitem,parent,false);
        return new LiveTVHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LiveTVHolder holder, final int position) {
        Glide.with(context)
                .load(liveTvModels.get(position).image)
                .into(holder.image);
        holder.title.setText(liveTvModels.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                         MainActivity.preFrag  = "Home"; //   for backpress
                MainActivity.currentFrag = "LiveTV";
                PlayLiveTVFragment tvFragment = new PlayLiveTVFragment();
                tvFragment.link = liveTvModels.get(position).link;
                setFragment(tvFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return liveTvModels.size();
    }

    public class LiveTVHolder extends RecyclerView.ViewHolder{
        ImageView image;                                        //  like category item xml
        TextView title;
        public LiveTVHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);



        }
    }
    public void setFragment(Fragment f)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.navContent,f);
        ft.commit();

    }
}
