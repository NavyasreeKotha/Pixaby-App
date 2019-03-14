package com.example.navyasree.pixaby;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>{
    Context context;
    ArrayList<ImageModel> models;

    public ImageAdapter(MainActivity mainActivity, ArrayList<ImageModel> imageModels) {
        this.context=mainActivity;
        this.models=imageModels;
    }


    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.design,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        ImageModel model=models.get(position);
        Picasso.with(context).load(model.image).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.imgview);
        }
    }
}
