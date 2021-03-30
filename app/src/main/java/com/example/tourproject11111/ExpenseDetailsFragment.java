package com.example.tourproject11111;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourproject11111.adapter.ExpenseAdapter;
import com.example.tourproject11111.databinding.FragmentExpenseDetailsBinding;
import com.example.tourproject11111.models.ExpenseModel;
import com.example.tourproject11111.models.TourModel;
import com.example.tourproject11111.viewmodel.TourViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ExpenseDetailsFragment extends Fragment {

    private TourViewModel tourViewModel;
    private String tourId;
    private FragmentExpenseDetailsBinding binding;

    public ExpenseDetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExpenseDetailsBinding.inflate(inflater);
        tourViewModel = new ViewModelProvider(getActivity()).get(TourViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tourViewModel.getExpenseModel().observe(getViewLifecycleOwner(), expenseModels -> {
            final ExpenseAdapter adapter = new ExpenseAdapter(getActivity(), expenseModels);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            binding.expenseRecycleViewId.setAdapter(adapter);
            binding.expenseRecycleViewId.setLayoutManager(layoutManager);
        });

    }
}