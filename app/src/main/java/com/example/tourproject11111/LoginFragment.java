package com.example.tourproject11111;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourproject11111.databinding.FragmentLoginBinding;
import com.example.tourproject11111.viewmodel.LoginViewModel;


public class LoginFragment extends Fragment {

private FragmentLoginBinding binding;
private LoginViewModel loginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginViewModel.authenticationMutableLiveData.observe(getViewLifecycleOwner(), new Observer<LoginViewModel.Authentication>() {
            @Override
            public void onChanged(LoginViewModel.Authentication authentication) {
                if (authentication == LoginViewModel.Authentication.AUTHENTICATION)
                {
                    Navigation.findNavController(container).navigate(R.id.action_loginFragment_to_homeFragment);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loginViewModel.errorMsg.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.errorTextViewId.setText(s);
            }
        });
        binding.loginId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.emailId.getText().toString();
                final String password = binding.passwordId.getText().toString();

                loginViewModel.login(email,password);
            }
        });

        binding.registerId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.emailId.getText().toString();
                final String password = binding.passwordId.getText().toString();


                loginViewModel.register(email,password);
            }
        });
    }
}