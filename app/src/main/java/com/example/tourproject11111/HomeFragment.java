package com.example.tourproject11111;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourproject11111.adapter.Adapter;
import com.example.tourproject11111.databinding.FragmentHomeBinding;
import com.example.tourproject11111.models.TourModel;
import com.example.tourproject11111.viewmodel.LoginViewModel;
import com.example.tourproject11111.viewmodel.TourViewModel;

import java.util.List;

public class  HomeFragment extends Fragment {

private LoginViewModel loginViewModel;
private TourViewModel tourViewModel;
private FragmentHomeBinding binding;
    public HomeFragment() {
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
        inflater.inflate(R.menu.main_mannu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logoutId:

                loginViewModel.logOut();
                break;

            case R.id.googleMapId:

                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_mapsFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater);

      //  FirebaseAuth.getInstance().signOut();
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
       tourViewModel = new ViewModelProvider(requireActivity()).get(TourViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel.authenticationMutableLiveData.observe(getViewLifecycleOwner(), new Observer<LoginViewModel.Authentication>() {
            @Override
            public void onChanged(LoginViewModel.Authentication authentication) {
                if (authentication == LoginViewModel.Authentication.UNAUTHENTICATION) {
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment);
                }
                else {
                    tourViewModel.fetchAllData().observe(getViewLifecycleOwner(), new Observer<List<TourModel>>() {
                        @Override
                        public void onChanged(List<TourModel> tourModels) {

                            final Adapter adapter = new Adapter(tourModels,getActivity());
                            final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                            binding.recyclceViewId.setLayoutManager(llm);
                            binding.recyclceViewId.setAdapter(adapter);
                        }
                    });
                }
            }

        });

        binding.fabId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_newTourFragment);
            }
        });
    }
}