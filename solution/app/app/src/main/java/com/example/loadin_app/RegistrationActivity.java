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
import android.widget.Toast;

import odu.edu.loadin.common.User;
import odu.edu.loadin.common.UserService;

import com.example.loadin_app.data.services.BaseServiceUrlProvider;
import com.example.loadin_app.data.services.InventoryServiceImpl;
import com.example.loadin_app.data.services.UserServiceImpl;
import com.example.loadin_app.ui.login.LoginActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, phoneInput, fNameInput, lNameInput;
    private Button registerButton;
    private String sUsername, sPassword, sFirstName, sLastName, sPhoneNumber;

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
                //sets user input to strings
                sUsername = emailInput.getText().toString();
                sPassword = passwordInput.getText().toString();
                sFirstName = fNameInput.getText().toString();
                sLastName = lNameInput.getText().toString();
                sPhoneNumber = phoneInput.getText().toString();
                //checks to ensure all fields are filled out, pretty ugly but works
                if(passwordInput.length() < 6)
                {
                    Toast.makeText(RegistrationActivity.this, "Error: Password must be at least 6 characters long", Toast.LENGTH_LONG).show();
                }
                else if(sUsername.matches("") || sPassword.matches("") || sFirstName.matches("") ||
                        sLastName.matches("")|| sPhoneNumber.matches("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Fill Each Category", Toast.LENGTH_SHORT).show();
                }
                else{
                    addRegistrationToDB(sUsername, sPassword, sFirstName, sLastName, sPhoneNumber);
                    Intent switchToLogin = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(switchToLogin);
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                }

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

        UserServiceImpl service = new UserServiceImpl(BaseServiceUrlProvider.getCurrentConfig());
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