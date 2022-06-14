package com.sawkyawhtin.clientappmone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import project.aamir.sheikh.circletextview.CircleTextView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyItems> {

    ArrayList<CategoryModel> mArrayList;
    Context context;
    FragmentManager fm;



    public CategoryAdapter(ArrayList<CategoryModel> mArrayList, Context context,FragmentManager fm) {

        this.mArrayList = mArrayList;
        this.context = context;
        this.fm = fm;

    }


    @Override
    public MyItems onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoryitem, parent,false);
        MyItems items = new MyItems(v);
        return items;
    }

    @Override
    public void onBindViewHolder(MyItems holder, final int position) {
        holder.mTextView.setText(mArrayList.get(position).categoryName);
        holder.mCircleTextView.setCustomText(mArrayList.get(position).categoryName);
        holder.mCircleTextView.setSolidColor(position);
        holder.mCircleTextView.setTextColor(Color.WHITE);
        holder.mCircleTextView.setCustomTextSize(18);
        holder.mCircleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseConnect fConnect = new FirebaseConnect(context,fm);
                if(mArrayList.get(position).categoryName.equals(context.getString(R.string.all_str)))
                {
                    fConnect.getAllMovies();
                    fConnect.getAllSeries();
                }
                else {

                    fConnect.getAllMoviesByCategory(mArrayList.get(position).categoryName);
                    fConnect.getAllSeriesByCategory(mArrayList.get(position).categoryName);
                }
            }
        });
        holder.categoryitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseConnect fConnect = new FirebaseConnect(context);
                if(mArrayList.get(position).categoryName.equals(context.getString(R.string.all_str)))
                {
                    fConnect.getAllMovies();
                    fConnect.getAllSeries();
                }
                else {

                    fConnect.getAllMoviesByCategory(mArrayList.get(position).categoryName);
                    fConnect.getAllSeriesByCategory(mArrayList.get(position).categoryName);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyItems extends RecyclerView.ViewHolder {
        TextView mTextView;
        CircleTextView mCircleTextView;
        LinearLayout categoryitem;

        public MyItems(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv);
            mCircleTextView = (CircleTextView) itemView.findViewById(R.id.ctv);
            categoryitem =  itemView.findViewById(R.id.categoryitem);


        }
    }
}