package com.example.tourproject11111.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourproject11111.R;
import com.example.tourproject11111.models.ExpenseModel;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private Context context;
    private List<ExpenseModel> expenseModelList;

    public ExpenseAdapter(Context context, List<ExpenseModel> expenseModelList) {
        this.context = context;
        this.expenseModelList = expenseModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_expense, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(expenseModelList.get(position).title);
        holder.date.setText(String.valueOf(expenseModelList.get(position).getTime()));
        holder.amount.setText(String.valueOf(expenseModelList.get(position).amount));

    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleId);
            amount = itemView.findViewById(R.id.getExpenseAmountId);
            date = itemView.findViewById(R.id.dateId);

        }
    }
}
