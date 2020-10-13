package com.example.isc581_practica2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;

    private Switch storage_switch;
    private Switch location_switch;
    private Switch camera_switch;
    private Switch phone_switch;
    private Switch contacts_switch;

    private Button cancel_button;
    private Button continue_button;

    private ConstraintLayout settings_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage_switch = findViewById(R.id.switch_storage);
        location_switch = findViewById(R.id.switch_location);
        camera_switch = findViewById(R.id.switch_camera);
        phone_switch = findViewById(R.id.switch_phone);
        contacts_switch = findViewById(R.id.switch_contacts);

        cancel_button = findViewById(R.id.cancel_button);
        continue_button = findViewById(R.id.continue_button);

        settings_layout = findViewById(R.id.settingsLayout);

        checkPermissions();

        storage_switch.setOnCheckedChangeListener(switchButtonListenerCallback(storage_switch, READ_EXTERNAL_STORAGE));
        location_switch.setOnCheckedChangeListener(switchButtonListenerCallback(location_switch, ACCESS_FINE_LOCATION));
        camera_switch.setOnCheckedChangeListener(switchButtonListenerCallback(camera_switch, CAMERA));
        phone_switch.setOnCheckedChangeListener(switchButtonListenerCallback(phone_switch, CALL_PHONE));
        contacts_switch.setOnCheckedChangeListener(switchButtonListenerCallback(contacts_switch, READ_CONTACTS));

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        continue_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                List<String> permissionsList = new ArrayList<>();
                permissionsList.add(READ_EXTERNAL_STORAGE);

                if (storage_switch.isChecked() && !userHavePermission(READ_EXTERNAL_STORAGE))
                    permissionsList.add(READ_EXTERNAL_STORAGE);

                if (location_switch.isChecked() && !userHavePermission(ACCESS_FINE_LOCATION))
                    permissionsList.add(ACCESS_FINE_LOCATION);

                if (camera_switch.isChecked() && !userHavePermission(CAMERA))
                    permissionsList.add(CAMERA);

                if (phone_switch.isChecked() && !userHavePermission(CALL_PHONE))
                    permissionsList.add(CALL_PHONE);

                if (contacts_switch.isChecked() && !userHavePermission(READ_CONTACTS))
                    permissionsList.add(READ_CONTACTS);

                if( permissionsList.size() != 0 ) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissionsList.toArray(new String[0]), 1);
                } else {
                    Intent intent = new Intent(MainActivity.this, ButtonsActionsActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void checkPermissions() {
        if (userHavePermission(READ_EXTERNAL_STORAGE))
            storage_switch.setChecked(true);

        if (userHavePermission(ACCESS_FINE_LOCATION))
            location_switch.setChecked(true);

        if (userHavePermission(CAMERA))
            camera_switch.setChecked(true);

        if (userHavePermission(CALL_PHONE))
            phone_switch.setChecked(true);

        if (userHavePermission(READ_CONTACTS))
            contacts_switch.setChecked(true);
    }

    public CompoundButton.OnCheckedChangeListener switchButtonListenerCallback(final Switch switchOption, final String permission) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(userHavePermission(permission)) {
                    switchOption.setChecked(true);
                    Snackbar snackbar = Snackbar.make(settings_layout, "Permission Granted", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        };
    }

    public Boolean userHavePermission(String permission) {
        return ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(MainActivity.this, ButtonsActionsActivity.class);
        startActivity(intent);
    }
}