package com.example.tourproject11111.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourproject11111.R;
import com.example.tourproject11111.models.MomentsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<MomentsModel> momentsModels;
    private Context context;

    public ImageAdapter(List<MomentsModel> tourModels, Context context) {
        this.momentsModels = tourModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_view,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MomentsModel momentsModel = momentsModels.get(position);
        Picasso.get().load(momentsModel.getImagedownload()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return momentsModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titel;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageViewId);



    }
}
}
