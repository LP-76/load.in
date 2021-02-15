package com.example.loadin_app;
import android.os.Bundle;

import com.example.loadin_app.data.services.BoxServiceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import odu.edu.loadin.common.BoxSize;

public class BoxInputActivity extends AppCompatActivity
{
    private EditText widthInput, depthInput, heightInput;
    private Button addBoxSizeButton;

    //used this video a bit for reference here https://youtu.be/V0AETAjxqLI -jason
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_input);

        widthInput = (EditText) findViewById(R.id.BoxWidthField);
        depthInput = (EditText) findViewById(R.id.BoxDepthField);
        heightInput = (EditText) findViewById(R.id.BoxHeightField);

        addBoxSizeButton = (Button) findViewById(R.id.AddBoxSizeButton);
        addBoxSizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AddBoxSizeToDB(Float.parseFloat(widthInput.getText().toString()), Float.parseFloat(depthInput.getText().toString()), Float.parseFloat(heightInput.getText().toString()));
            }
        });
    }

    private void AddBoxSizeToDB( float inputWidth, float inputDepth, float inputHeight)
    {
        System.out.println("Creating a box of width: " + inputWidth + ", depth: " + inputDepth +", height: " + inputHeight + "!");
        //beep boop do DB stuff or whatever

        String dimension = inputWidth + "x" + inputHeight + "x" + inputDepth;


        //we're going to use the box service implementation to acheive this
        BoxSize bs = new BoxSize();
        bs.setDescription("Test box from UI");
        bs.setDimensions(dimension);

        BoxServiceImpl service = new BoxServiceImpl("http://10.0.2.2:9000/");
        try{
            service.addBoxSize(bs);
        }
        catch(Exception ex){
            System.out.println(ex);
            //ooops we had an error
            //TODO: make the user aware
        }




    }
}