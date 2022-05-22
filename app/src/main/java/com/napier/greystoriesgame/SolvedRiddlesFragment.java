package com.napier.greystoriesgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Fragment created to display the solved riddles in the screen
 * @author Pablo Sanchez
 * Last date of modification: 16/03/2022
 */
public class SolvedRiddlesFragment extends Fragment
{
    private RecyclerView solvedRiddlesRecView;
    private RiddleRecViewAdapter adapterSolved;
    private TextView nothingHereYet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solved_riddles, container, false);
        //Assign Recycler View (find in the view layout)
        solvedRiddlesRecView = view.findViewById(R.id.solvedRiddlesRecView);

        //Set text if there isn't any Riddles in the list
        nothingHereYet = view.findViewById(R.id.nothingHereYet);

        //Set adapter
        adapterSolved = new RiddleRecViewAdapter(view.getContext());
        adapterSolved.setRiddles(Utils.getInstance(view.getContext()).getSolvedRiddles());
        //If the list is not empty, make the text disappear
        if(adapterSolved.getItemCount() != 0)
        {
            nothingHereYet.setVisibility(View.GONE);
        }
        solvedRiddlesRecView.setAdapter(adapterSolved);
        solvedRiddlesRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

}