package com.napier.greystoriesgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.UiAutomation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Class created for the screen where the riddle is displayed
 * @author Pablo Sanchez
 * Last modification date: 26/03/2021
 */
public class RiddleActivity extends AppCompatActivity
{
    private TextView riddleTxtTitle, riddlePlot;
    private ImageView imageActionRiddle;
    private Button solutionButton, hintsButton, solvedButton, likeButton, shareButton, unsolvedButton, dislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riddle_screen);

        //Initialize all components
        initViews();

        //Hide Action bar
        getSupportActionBar().hide();

        //Save input from Intent to variables
        String titleRiddle = getIntent().getStringExtra("riddleTitle");
        String imageUrl = getIntent().getStringExtra("imageURL");
        String textRiddle = getIntent().getStringExtra("textRiddle");
        String hints = getIntent().getStringExtra("hints");
        String solution = getIntent().getStringExtra("solution");

        //Set Data from the Riddle to the screen
        setData(titleRiddle, imageUrl, textRiddle);

        //Button to display solution
        solutionButton.setOnClickListener(view -> {
            Intent intent = new Intent(RiddleActivity.this, SolutionOrHints.class);
            intent.putExtra("solutionOrHints", solution);
            intent.putExtra("imageUrl", imageUrl);
            RiddleActivity.this.startActivity(intent);

        });


        //Button to display hints
        hintsButton.setOnClickListener(view -> {
            Intent intent = new Intent(RiddleActivity.this, SolutionOrHints.class);
            intent.putExtra("solutionOrHints", hints);
            intent.putExtra("imageUrl", imageUrl);
            RiddleActivity.this.startActivity(intent);
        });

        //Handle solved riddles
        Riddle r = Utils.getInstance(this).getRiddleByName(titleRiddle);
        handleSolvedRiddles(r);

        //Handle liked riddles
        handleLikedRiddles(r);

        //Handle unsolved button
        handleUnsolvedRiddles(r);

        //Handle dislike button
        handleDislikeRiddles(r);

        //Handle share button
        handleShareButton(textRiddle);
    }


    /**
     * Function to initialize all components
     */
    private void initViews()
    {
        riddleTxtTitle = findViewById(R.id.riddleTxtTitle);
        imageActionRiddle = findViewById(R.id.imageActionRiddle);
        riddlePlot = findViewById(R.id.riddlePlot);
        solutionButton = findViewById(R.id.solutionButton);
        hintsButton = findViewById(R.id.hintsButton);
        solvedButton = findViewById(R.id.solvedButton);
        likeButton = findViewById(R.id.likeButton);
        shareButton = findViewById(R.id.shareButton);
        unsolvedButton = findViewById(R.id.unsolvedButton);
        dislikeButton = findViewById(R.id.dislikeButton);
    }


    /**
     * Function to set data
     * @param titleRiddle
     * @param imageUrl
     * @param textRiddle
     */
    private void setData(String titleRiddle, String imageUrl, String textRiddle)
    {
        riddleTxtTitle.setText(titleRiddle);
        riddlePlot.setText(textRiddle);
        Glide.with(this).asBitmap().load(imageUrl).into(imageActionRiddle);
    }


    /**
     * Enable and disable solved button
     * @param riddle
     */
    private void handleSolvedRiddles(Riddle riddle)
    {
        ArrayList<Riddle> solvedRiddles = Utils.getInstance(RiddleActivity.this).getSolvedRiddles();

        //Create boolean to check if the riddle is solved
        boolean existInSolved = false;

        for(Riddle r : solvedRiddles)
        {
            if(r == null)
            {
                continue;
            }
            else if (r.getTitle().equals(riddle.getTitle()))
            {
                //If it finds the riddle in the list, change the boolean to ture
                existInSolved = true;
            }
        }

        //Iterate through the solved riddles list
        //If exists in the list, disable the button so it cannot be added twice
        if(existInSolved)
        {
            solvedButton.setEnabled(false);
            solvedButton.setVisibility(View.GONE);
            //Make the unsolve button visible
            unsolvedButton.setVisibility(View.VISIBLE);
        }
        //If it doesn't exist in the list and the button is clicked, add to the list
        else
        {
            solvedButton.setOnClickListener(view -> {
                Utils.getInstance(this).addToSolvedRiddle(riddle);
                Utils.getInstance(this).removeFromAll(riddle);

                Toast.makeText(RiddleActivity.this, "Added to Solved", Toast.LENGTH_SHORT).show();

                //Navigate the user to the main screen
                Intent intent = new Intent(RiddleActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
    }


    /**
     * Enable and disable unsolved button
     * @param riddle
     */
    private void handleUnsolvedRiddles(Riddle riddle)
    {
        ArrayList<Riddle> solvedRiddles = Utils.getInstance(RiddleActivity.this).getSolvedRiddles();

        //Create boolean to check if the riddle is solved
        boolean existInSolved = false;

        //Iterate through the solved riddles list
        for (Riddle r : solvedRiddles)
        {
            if (r == null) {
                continue;
            } else if (r.getTitle().equals(riddle.getTitle())) {
                //If it finds the riddle in the list, change the boolean to ture
                existInSolved = true;
            }
        }

        //If it does not exist in the list, enable the solved button again
        if(!existInSolved)
        {
            solvedButton.setEnabled(true);
            solvedButton.setVisibility(View.VISIBLE);
            //Make the unsolve button invisible
            unsolvedButton.setVisibility(View.GONE);
        }
        //If it exists in the list and the button is clicked, remove from the list
        else
        {
            unsolvedButton.setOnClickListener(view -> {
                Utils.getInstance(RiddleActivity.this).removeFromSolved(riddle);
                Utils.getInstance(RiddleActivity.this).addToAllRiddle(riddle);
                Toast.makeText(RiddleActivity.this, "Removed from Solved", Toast.LENGTH_SHORT).show();

                //Navigate the user to the main screen
                Intent intent = new Intent(RiddleActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }
    }


    /**
     * Handle the situation of like button clicked
     * @param riddle
     */
    private void handleLikedRiddles(final Riddle riddle)
    {
        ArrayList<Riddle> likedRiddles = Utils.getInstance(this).getLikedRiddles();

        //Create boolean to check if the riddle is solved
        boolean existInLiked = false;

        //Iterate through the liked riddles list
        for(Riddle r : likedRiddles)
        {
            if(r == null)
            {
                continue;
            }
            else if (r.getTitle().equals(riddle.getTitle()))
            {
                //If it finds the riddle in the list, change the boolean to ture
                existInLiked = true;
            }
        }
        //If exists in the list, disable the button so it cannot be added twice
        if(existInLiked)
        {
            likeButton.setEnabled(false);
            likeButton.setVisibility(View.GONE);
            //Make the dislike button visible
            dislikeButton.setVisibility(View.VISIBLE);
        }
        //If it doesn't exist in the list and the button is clicked, add to the list
        else
        {
            likeButton.setOnClickListener(view -> {
                Utils.getInstance(this).addToLikedRiddle(riddle);
                Toast.makeText(RiddleActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                //nothingYet.setVisibility(View.INVISIBLE);

                //Navigate the user to the main screen
                Intent intent = new Intent(RiddleActivity.this, MainActivity.class);
                startActivity(intent);
            });
        }

    }


    /**
     * Enable and disable dislike button
     * @param riddle
     */
    private void handleDislikeRiddles(Riddle riddle)
    {
        ArrayList<Riddle> solvedRiddles = Utils.getInstance(RiddleActivity.this).getLikedRiddles();

        //Create boolean to check if the riddle is solved
        boolean existInSolved = false;

        //Iterate through the solved riddles list
        for(Riddle r : solvedRiddles)
        {
            if(r == null)
            {
                continue;
            }
            else if (r.getTitle().equals(riddle.getTitle()))
            {
                //If it finds the riddle in the list, change the boolean to ture
                existInSolved = true;
            }
        }
        //If it does not exist in the list, enable the solved button again
        if(!existInSolved)
        {
            likeButton.setEnabled(true);
            likeButton.setVisibility(View.VISIBLE);
            //Make the unsolve button invisible
            dislikeButton.setVisibility(View.GONE);
        }
        //If it exists in the list and the button is clicked, remove from the list
        else
        {
            dislikeButton.setOnClickListener(view -> {
                Utils.getInstance(RiddleActivity.this).removeFromLiked(riddle);
                Toast.makeText(RiddleActivity.this, "Removed from Favourite", Toast.LENGTH_SHORT).show();

                //Navigate the user to the main screen
                Intent intent2 = new Intent(RiddleActivity.this, MainActivity.class);
                startActivity(intent2);
            });
        }
    }

    /**
     * Handle the share button
     * @param textRiddle
     */
    private void handleShareButton(String textRiddle)
    {
        shareButton.setOnClickListener(view -> {
            //String containing the message to share
            String messageToShare = "I am playing Grey Stories, a mobile adaptation of Dark Stories!\n\n"+
                    "This riddle is so difficult! Would you help me? The story goes like this:\n\n"
                    + textRiddle + "\n\nThanks for the help! And follow the creator of this amazing app ;)\n"
                    +getString(R.string.linkedin);
            //Intent object created to share the riddle
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            //Message to share
            intent.putExtra(Intent.EXTRA_SUBJECT, "I am playing Grey Stories, a mobile adaptation of Dark Stories!");
            intent.putExtra(Intent.EXTRA_TEXT, messageToShare);
            startActivity(intent);
        });
    }
}
