package com.example.tourproject11111.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourproject11111.R;
import com.example.tourproject11111.models.TourModel;

import java.util.List;

public class  Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<TourModel> tourModelList;
    private Context context;

    public Adapter(List<TourModel> tourModelList, Context context) {
        this.tourModelList = tourModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.contact_row,parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(tourModelList.get(position).getName());
        holder.date.setText(tourModelList.get(position).getFormatterdDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bundle bundle = new Bundle();
                bundle.putString("id",tourModelList.get(position).getId());

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_tourDetails,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tourModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tourNameId);
            date = itemView.findViewById(R.id.tour_DateId);
        }
    }
}
