package com.example.tourproject11111;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourproject11111.Global.GlobalValues;
import com.example.tourproject11111.adapter.Adapter;
import com.example.tourproject11111.adapter.ImageAdapter;
import com.example.tourproject11111.databinding.FragmentTourDetailsBinding;
import com.example.tourproject11111.models.ExpenseModel;
import com.example.tourproject11111.models.MomentsModel;
import com.example.tourproject11111.models.TourModel;
import com.example.tourproject11111.viewmodel.TourViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TourDetails extends Fragment {

    private FragmentTourDetailsBinding binding;
    private TourViewModel tourViewModel;
    private String tourId;
    private String TourName;
    private FirebaseFirestore db;
    private Uri downloadUri;
    private String savCurrentDate,saveCurrentTime;


    public TourDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_delete_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.editId:

                break;

            case R.id.deleteId:
                tourViewModel.deletetour(tourId);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_tourDetails_to_homeFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
       binding = FragmentTourDetailsBinding.inflate(inflater);
       tourViewModel = new ViewModelProvider(requireActivity()).get(TourViewModel.class);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tourId = bundle.getString("id");
        }

        GlobalValues.tourID = tourId;

        tourViewModel.fetchTourById(tourId).observe(getViewLifecycleOwner(), tourModel -> {
            TourName = tourModel.getName();
            binding.tableTotalBudgetId.setText(String.valueOf(tourModel.getBudget()));

            getExpenesByTourId(tourModel);
        });

        binding.addExpenseButtonId.setOnClickListener(view1 -> addExpenseeDialog());


        binding.viewExpenseButtonId.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_tourDetails_to_expenseDetailsFragment));


        binding.detailsMoment.setOnClickListener(view13 -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, 999);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
        });

        tourViewModel.getAllMoment().observe(getViewLifecycleOwner(), new Observer<List<MomentsModel>>() {
            @Override
            public void onChanged(List<MomentsModel> momentsModels) {

                ImageAdapter adapter = new ImageAdapter(momentsModels, getActivity());
               // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                binding.ImageRecycleViewId.setAdapter(adapter);
                binding.ImageRecycleViewId.setLayoutManager(gridLayoutManager);

            }

        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intentData) {
        super.onActivityResult(requestCode, resultCode, intentData);

        if (requestCode == 999 && resultCode == RESULT_OK) {

            Bundle extras = intentData.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            final StorageReference rootRef = FirebaseStorage.getInstance().getReference();
            final StorageReference photoRef = rootRef.child(TourName + "/" + System.currentTimeMillis());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            // Upload Image storage
            UploadTask uploadTask = photoRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(getActivity(), "Upload faild", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            return null;
                        }
                    });
                    Toast.makeText(getActivity(), "Upload successfull", Toast.LENGTH_SHORT).show();
                }
            });

            //get download uri

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL

                   // downloadUri = photoRef.getDownloadUrl().toString();
                    return photoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                      downloadUri = task.getResult();

                        Toast.makeText(getActivity(),"url successfull",Toast.LENGTH_LONG).show();
                        MomentsModel momentsModel = new MomentsModel(null, tourId, downloadUri.toString());
                        tourViewModel.addMoments(momentsModel);
                        Log.d("tag", "image");

                    } else {
                        // Handle failures

                        // ...
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(),"url faild",Toast.LENGTH_LONG).show();
                }
            });


            // Observe state change events such as progress, pause, and resume
            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.e("TAG", "Upload is " + progress + "% done");
            }).addOnPausedListener(taskSnapshot -> Log.e("TAG", "Upload is paused"));
        }
    }


    private void getExpenesByTourId(TourModel tourModel) {

        tourViewModel.fetchExpenseByTourId(tourId).observe(getViewLifecycleOwner(), expenseModels -> {

            binding.tableTotalExpenseId.setText(String.valueOf(tourViewModel.expenseTotalAmount(expenseModels)));
            binding.tableTotalRemainingtId.setText(String.valueOf(tourModel.getBudget() - tourViewModel.expenseTotalAmount(expenseModels)));
        });
    }


    private void addExpenseeDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ADD NEW EXPENSE");
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.add_expense, null, true);
        builder.setView(layout);

        EditText titleEt = layout.findViewById(R.id.titelTextViewId);
        EditText amountEt = layout.findViewById(R.id.expenseAmoutnId);
        Button addBtn = layout.findViewById(R.id.popUpAddExpenseId);
        Button cancelBtn = layout.findViewById(R.id.cancelId);

        AlertDialog dialog = builder.create();
        dialog.show();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                savCurrentDate = currentDate.format(calendar.getTime());
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime = currentTime.format(calendar.getTime());

                final String title =titleEt.getText().toString();
                final String amount = amountEt.getText().toString();
                final long timeStamp = System.currentTimeMillis();
                final ExpenseModel expenseModel = new ExpenseModel(null,tourId,title,Double.parseDouble(amount),timeStamp,saveCurrentTime,savCurrentDate);

                tourViewModel.createExpenes(expenseModel);
                dialog.dismiss();

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialog.cancel();
            }
        });
    }



}