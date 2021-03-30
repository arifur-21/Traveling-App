package com.example.tourproject11111;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.tourproject11111.databinding.FragmentNewTourBinding;
import com.example.tourproject11111.models.TourModel;
import com.example.tourproject11111.viewmodel.TourViewModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NewTourFragment extends Fragment {

    private TourViewModel tourViewModel;
    private FragmentNewTourBinding binding;
    private String formattedDate;
    private long timeStamp;
    private int year;
    private int month;

    public NewTourFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentNewTourBinding.inflate(inflater);
       tourViewModel = new ViewModelProvider(requireActivity()).get(TourViewModel.class);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.inputSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = binding.inputTourName.getText().toString();
                final String destination = binding.inputDestination.getText().toString();
                final String budget = binding.inputBudget.getText().toString();

                TourModel tourModel = new TourModel(null,name,destination,Double.parseDouble(budget),formattedDate,timeStamp,month,year);
                tourViewModel.createNewTour(tourModel);
                Navigation.findNavController(view).navigate(R.id.action_newTourFragment_to_homeFragment);


            }
        });

        binding.inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectDateFromUser();
            }
        });
    }

    private void selectDateFromUser() {

        final Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),listener,year,month,day);
      datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
            }
            public DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                    Calendar calendar = Calendar.getInstance();
                        calendar.set(i,i1, i2);
                        final SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
                        formattedDate = sfd.format(calendar.getTime());
                        timeStamp = calendar.getTimeInMillis();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                       binding.inputDate.setText(formattedDate);

                }
            };
}