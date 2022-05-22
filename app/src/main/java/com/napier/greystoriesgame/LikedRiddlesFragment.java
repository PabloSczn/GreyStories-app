package com.napier.greystoriesgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment created to display the liked riddles in the screen
 * @author Pablo Sanchez
 * Last date of modification: 15/03/2022
 */
public class LikedRiddlesFragment extends Fragment
{
    private RecyclerView likedRiddlesRecView;
    private RiddleRecViewAdapter adapterLiked;
    private TextView nothingHereYet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solved_riddles, container, false);
        //Assign Recycler View (find in the view layout)
        likedRiddlesRecView = view.findViewById(R.id.solvedRiddlesRecView);

        //Set text if there isn't any Riddles in the list
        nothingHereYet = view.findViewById(R.id.nothingHereYet);

        //Set adapter
        adapterLiked = new RiddleRecViewAdapter(view.getContext());
        adapterLiked.setRiddles(Utils.getInstance(view.getContext()).getLikedRiddles());
        likedRiddlesRecView.setAdapter(adapterLiked);
        //If the list is not empty, make the text disappear
        if(adapterLiked.getItemCount() != 0)
        {
            nothingHereYet.setVisibility(View.GONE);
        }
        likedRiddlesRecView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
}