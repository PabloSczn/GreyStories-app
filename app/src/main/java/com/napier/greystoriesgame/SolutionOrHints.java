package com.napier.greystoriesgame;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

/**
 * Class created for the Solutions or Hints Screen
 * @author Pablo Sanchez
 * Last date of modification: 10/03/2022
 */
public class SolutionOrHints extends AppCompatActivity
{
    private ImageView imageRiddleSolution;
    private TextView solutionOrHintsText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riddle_solution_screen);

        //Hide Action bar
        getSupportActionBar().hide();

        //Save input from Intent to variables
        String solutionOrHints = getIntent().getStringExtra("solutionOrHints");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        //Initialize all components
        initViews();

        //Set data
        setData(solutionOrHints, imageUrl);
    }

    /**
     * Function to initialize all components
     */
    private void initViews()
    {
        imageRiddleSolution = findViewById(R.id.imageRiddleSolution);
        solutionOrHintsText = findViewById(R.id.solutionOrHintsText);
    }

    /**
     * Function to set data
     * @param solutionOrHints
     * @param imageUrl
     */
    private void setData(String solutionOrHints, String imageUrl)
    {
        //Set text
        solutionOrHintsText.setText(solutionOrHints);
        //Set the image with Glide
        Glide.with(this).asBitmap().load(imageUrl).into(imageRiddleSolution);
    }
}
