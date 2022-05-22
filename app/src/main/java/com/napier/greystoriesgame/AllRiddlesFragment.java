package com.napier.greystoriesgame;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Fragment created to display all the riddles in the screen
 * @author Pablo Sanchez
 * Last date of modification: 16/03/2022
 */
public class AllRiddlesFragment extends Fragment
{
    private RecyclerView riddlesRecView;
    private RiddleRecViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_riddles, container, false);
        //Assign Recycler View (find in the view layout)
        riddlesRecView = view.findViewById(R.id.riddlesRecView);

        //Set adapter
        adapter = new RiddleRecViewAdapter(view.getContext());
        adapter.setRiddles(Utils.getInstance(view.getContext()).getAllRiddles());
        riddlesRecView.setAdapter(adapter);
        riddlesRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //Return the layout for this fragment
        return view;
    }

}