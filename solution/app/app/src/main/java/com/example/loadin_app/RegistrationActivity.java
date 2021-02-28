package com.example.loadin_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import odu.edu.loadin.common.User;
import odu.edu.loadin.common.UserService;

import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.UserServiceImpl;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, phoneInput, fNameInput, lNameInput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        emailInput = (EditText) findViewById(R.id.registration_username);
        passwordInput = (EditText) findViewById(R.id.registration_password);
        phoneInput = (EditText) findViewById(R.id.phone_number);
        fNameInput = (EditText) findViewById(R.id.registration_fname);
        lNameInput = (EditText) findViewById(R.id.registration_lname);

        registerButton = (Button) findViewById(R.id.registration_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addRegistrationToDB(emailInput.getText().toString(), passwordInput.getText().toString(), fNameInput.getText().toString(),
                        lNameInput.getText().toString(), phoneInput.getText().toString());
            }
        });

    }

    private void addRegistrationToDB(String inputEmail, String inputPassword, String inputFname, String inputLname, String inputPhone){

        User user = new User();
        user.setEmail(inputEmail);
        user.setPassword(inputPassword);
        user.setFirstName(inputFname);
        user.setLastName(inputLname);
        user.setPhoneNumber(inputPhone);

        UserServiceImpl service = new UserServiceImpl("http://10.0.2.2:9000/");
        try{
            service.addUser(user);
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }
        //TODO: figure out what happens
        //what happens here?
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_toolbar, menu);
        return true;
    }
}