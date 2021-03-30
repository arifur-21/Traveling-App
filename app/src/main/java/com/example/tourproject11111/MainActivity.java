package com.example.tourproject11111;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tourproject11111.viewmodel.LocationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private FusedLocationProviderClient providerClient;
    private LocationViewModel locationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providerClient = LocationServices.getFusedLocationProviderClient(this);
        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);



        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration);

//check user permission allow or not
        if (isLocationPermissionGranted())
        {
            getUserCurrentLocation();
        }
        else {

            requestLocationPermissionFromUser();
            }
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.loginFragment)
                {
                    getSupportActionBar().hide();
                }
                else {

                    getSupportActionBar().show();
                }
            }
        });

    }


    //os Permission
    private boolean isLocationPermissionGranted() {
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
//user permission
    private void requestLocationPermissionFromUser() {
        final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions, 111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            getUserCurrentLocation();
        }
        else
        {
            Toast.makeText(this, "Denied by user",Toast.LENGTH_LONG).show();
        }
    }


    private void getUserCurrentLocation()
    {
        providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null)
                {
                    return;
                }
                locationViewModel.setNewLocation(location );
            }
        });
    }

}