package com.example.tourproject11111.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tourproject11111.models.ExpenseModel;
import com.example.tourproject11111.models.MomentsModel;
import com.example.tourproject11111.models.TourModel;
import com.example.tourproject11111.repository.FirebaseRepository;
import com.google.rpc.context.AttributeContext;

import java.util.List;

public class TourViewModel extends AndroidViewModel {
    private FirebaseRepository repository;
    public TourViewModel(@NonNull Application application) {
        super(application);

        repository = new FirebaseRepository();
    }
    public void createNewTour(TourModel tourModel)

    {

        repository.addNewTour(tourModel);
    }
    public void createExpenes(ExpenseModel expenseModel)

    {
        repository.addExpense(expenseModel);
    }

    public MutableLiveData<List<TourModel>> fetchAllData()
    {
        return repository.getAllTours();
    }


    //get Toru By Id
    public MutableLiveData<TourModel> fetchTourById(String id)
    {
        return repository.getTourById(id);
    }

    //get Expenes
    public MutableLiveData<List<ExpenseModel>>fetchExpenseByTourId(String tourId)
    {

        return repository.getExpenseByTourId(tourId);
    }

    public MutableLiveData<List<ExpenseModel>>getExpenseModel(){
        return repository.getAllExpense();
    }

   //get Moment
    public MutableLiveData<List<MomentsModel>> getAllMoment()
    {
        return repository.getAllMoment();
    }
    public double expenseTotalAmount(List<ExpenseModel> expenseModels)
    {
        double  totalExpense = 0.0;

        for (ExpenseModel expenseModel : expenseModels)
        {
            totalExpense += expenseModel.amount;
        }
        return totalExpense;
    }

    public void addMoments(MomentsModel momentsModel)
    {

        repository.AddNewMoments(momentsModel);
    }


    public void deletetour(String id)
    {
        repository.deleteTour(id);
    }

}
