package com.example.isc581_practica2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class ButtonsActionsActivity extends AppCompatActivity {

    Button storage_button;
    Button location_button;
    Button camera_button;
    Button phone_button;
    Button contacts_button;
    ConstraintLayout actionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_actions);

        actionLayout = findViewById(R.id.actionLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storage_button = findViewById(R.id.storage_button);
        location_button = findViewById(R.id.location_button);
        camera_button = findViewById(R.id.camera_button);
        phone_button = findViewById(R.id.phone_button);
        contacts_button = findViewById(R.id.contacts_button);

        storage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userHavePermission(MainActivity.READ_EXTERNAL_STORAGE))
                    showSnackbar("This permission is already granted.", MainActivity.READ_EXTERNAL_STORAGE );
                else
                    showSnackbar("You need to request this permission.", "");
            }
        });
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userHavePermission(MainActivity.ACCESS_FINE_LOCATION))
                    showSnackbar("This permission is already granted.", MainActivity.ACCESS_FINE_LOCATION );
                else
                    showSnackbar("You need to request this permission.", "");
            }
        });
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userHavePermission(MainActivity.CAMERA))
                    showSnackbar("This permission is already granted.", MainActivity.CAMERA );
                else
                    showSnackbar("You need to request this permission.", "");
            }
        });
        phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userHavePermission(MainActivity.CALL_PHONE))
                    showSnackbar("This permission is already granted.", MainActivity.CALL_PHONE );
                else
                    showSnackbar("You need to request this permission.", "");
            }
        });
        contacts_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userHavePermission(MainActivity.READ_CONTACTS))
                    showSnackbar("This permission is already granted.", MainActivity.READ_CONTACTS );
                else
                    showSnackbar("You need to request this permission.", "");
            }
        });

    }

    public Boolean userHavePermission(String permission) {
        return ContextCompat.checkSelfPermission(ButtonsActionsActivity.this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    public void showSnackbar(String text, String permission) {
        Snackbar snackbar = Snackbar.make(actionLayout, text, Snackbar.LENGTH_SHORT);

        switch(permission) {
            case MainActivity.READ_EXTERNAL_STORAGE:
                snackbar.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivity(intent);
                    }
                });
                break;
            case MainActivity.ACCESS_FINE_LOCATION:
                snackbar.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q=google"));
                        startActivity(intent);
                    }
                });
                break;
            case MainActivity.CAMERA:
                snackbar.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(intent);
                    }
                });
                break;
            case MainActivity.CALL_PHONE:
                snackbar.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:18095813000"));
                        startActivity(intent);
                    }
                });
                break;
            case MainActivity.READ_CONTACTS:
                snackbar.setAction("OPEN", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
                        startActivity(intent);
                    }
                });
                break;
        }

        snackbar.show();
    }
}