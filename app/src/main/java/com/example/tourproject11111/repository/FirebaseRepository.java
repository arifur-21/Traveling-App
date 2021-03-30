package com.example.tourproject11111.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.tourproject11111.Global.GlobalValues;
import com.example.tourproject11111.models.ExpenseModel;
import com.example.tourproject11111.models.MomentsModel;
import com.example.tourproject11111.models.TourModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseRepository {


    private FirebaseFirestore db;
    private String COLLECTION_TOUR = "TourProject";
    private String COLLECTION_EXPENSES = "expensesProject";
    private String COLLECTION_MOMENTS = "momentProject";

    public FirebaseRepository() {

        db = FirebaseFirestore.getInstance();
    }

    // adding Tour
    public void addNewTour(TourModel tourModel)
    {        final DocumentReference docRef = db.collection(COLLECTION_TOUR).document();
       tourModel.setId(docRef.getId());
       docRef.set(tourModel).addOnCompleteListener(task -> {
           if (task.isSuccessful())
           { Log.e(TAG,"Save");}
       }).addOnFailureListener(e -> Log.e(TAG,e.getLocalizedMessage()));
    }

    //getAll Tour
    public MutableLiveData<List<TourModel>> getAllTours()
    {  MutableLiveData<List<TourModel>> listMutableLiveData = new MutableLiveData<>();
        db.collection(COLLECTION_TOUR).addSnapshotListener((value, error) -> {
            if (error != null)
            { return;}
            List<TourModel> modelList = new ArrayList<>();
            for (DocumentSnapshot snapshot : value.getDocuments()){
                TourModel tourModel = snapshot.toObject(TourModel.class);
                modelList.add(tourModel);
            }
            listMutableLiveData.postValue(modelList);  }); return listMutableLiveData;
    }

    //getSingle Tour item id
    public MutableLiveData<TourModel> getTourById(String id)
    {
        MutableLiveData<TourModel> tourModelMutableLiveData = new MutableLiveData<>();
        db.collection(COLLECTION_TOUR).document(id).addSnapshotListener((value, error) -> {
            if (error != null)
            { return; }
            tourModelMutableLiveData.postValue(value.toObject(TourModel.class));
        });
        return tourModelMutableLiveData;
    }

    //Adding Expenses
    public void addExpense(ExpenseModel expenseModel)
    {
        final DocumentReference docRef = db.collection(COLLECTION_EXPENSES).document();
        expenseModel.expId = docRef.getId();
        docRef.set(expenseModel).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            { Log.e(TAG,"Save");}
        }).addOnFailureListener(e -> Log.e(TAG,e.getLocalizedMessage()));
    }

    //getAll Exp
    public MutableLiveData<List<ExpenseModel>> getAllExpense()
    {  MutableLiveData<List<ExpenseModel>> mutableLiveData = new MutableLiveData<>();
        db.collection(COLLECTION_EXPENSES).addSnapshotListener((value, error) -> {
            if (error != null)
            { return;}
            List<ExpenseModel> modelList = new ArrayList<>();
            for (DocumentSnapshot snapshot : value.getDocuments()){
                ExpenseModel expenseModel = snapshot.toObject(ExpenseModel.class);
                if(expenseModel.tourId.equalsIgnoreCase( GlobalValues.tourID)){
                    modelList.add(expenseModel);
                }


            }
            mutableLiveData.postValue(modelList);  }); return mutableLiveData;
    }

    //get Expense By Id
  public MutableLiveData<List<ExpenseModel>> getExpenseByTourId(String tourId){
        MutableLiveData<List<ExpenseModel>>listMutableLiveData = new MutableLiveData<>();
        db.collection(COLLECTION_EXPENSES).whereEqualTo("tourId",tourId).addSnapshotListener((value, error) -> {
            if (error != null)
            { return;}
            listMutableLiveData.postValue(value.toObjects(ExpenseModel.class));
        });
        return listMutableLiveData;
  }


    //Add Moments


    public void  AddNewMoments(MomentsModel momentsModel){
        final DocumentReference docRef = db.collection(COLLECTION_MOMENTS).document();
        momentsModel.momentId = docRef.getId();
        docRef.set(momentsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.e("Moment", "Save Moment");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Moment", e.getLocalizedMessage());
            }
        });
    }
    // get All Moment
   public MutableLiveData<List<MomentsModel>> getAllMoment ()
    {
        MutableLiveData<List<MomentsModel>>mutableLiveData = new MutableLiveData<>();
        db.collection(COLLECTION_MOMENTS).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                {
                    return;
                }


                List<MomentsModel> modelList = new ArrayList<>();
                for (DocumentSnapshot snapshot : value.getDocuments()){
                    MomentsModel momentsModel = snapshot.toObject(MomentsModel.class);

                    if (momentsModel.tourId.equalsIgnoreCase(GlobalValues.tourID)){
                        modelList.add(momentsModel);
                    }

                    mutableLiveData.postValue(modelList);

                }
            }
        });

        return mutableLiveData;
    }

    //tour delete
    public void deleteTour(String id)
    {
        WriteBatch batch = db.batch();
        db.collection(COLLECTION_MOMENTS).whereEqualTo("tourId",id).get().addOnCompleteListener(task -> {

            for (QueryDocumentSnapshot q : task.getResult())
            {
                final MomentsModel momentsModel = q.toObject(MomentsModel.class);
                batch.delete(db.collection(COLLECTION_MOMENTS).document(momentsModel.momentId));
            }
           db.collection(COLLECTION_EXPENSES).whereEqualTo("tourId",id).get().addOnCompleteListener(task1 -> {
               for (QueryDocumentSnapshot q : task1.getResult())
               {
                   final ExpenseModel expenseModel = q.toObject(ExpenseModel.class);

                   batch.delete(db.collection(COLLECTION_EXPENSES).document(expenseModel.expId));
               }
               batch.delete(db.collection(COLLECTION_TOUR).document(id));
               batch.commit();
           });

        });
    }
}
