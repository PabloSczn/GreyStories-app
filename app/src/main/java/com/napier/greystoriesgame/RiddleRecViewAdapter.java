package com.napier.greystoriesgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Recycler View Adapter for the Riddles
 * @author Pablo Sanchez
 * Last modification date: 08/03/2021
 */
public class RiddleRecViewAdapter extends RecyclerView.Adapter<RiddleRecViewAdapter.ViewHolder>
{

    private ArrayList<Riddle> riddles = new ArrayList<>();
    private Context context;

    public RiddleRecViewAdapter(Context context)
    {
        this.context = context;
    }

    /**
     * Inflates the layout from xml when needed
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Inflates the riddles_list_item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riddles_list_item, parent, false);
        //Creates the holder
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    /**
     * Binds the data into each item
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position)
    {
        holder.txtTitle.setText(riddles.get(position).getTitle());
        Glide.with(context).asBitmap().load(riddles.get(position).getImageUrl()).into(holder.imageRiddle);

        //Variables
        String riddleTitle = riddles.get(position).getTitle();
        String riddleImageUrl = riddles.get(position).getImageUrl();
        String textRiddle = riddles.get(position).getTextRiddle();
        String hints = riddles.get(position).getHints();
        String solution = riddles.get(position).getSolution();

        //Move the user to the Riddle screen
        holder.riddleCardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RiddleActivity.class);

            //Pass title
            intent.putExtra("riddleTitle", riddleTitle);
            //Pass image URL
            intent.putExtra("imageURL", riddleImageUrl);
            //Pass the text of the riddle
            intent.putExtra("textRiddle", textRiddle);
            //Pass the hints of the riddles
            intent.putExtra("hints", hints);
            //Pass the solution of the riddles
            intent.putExtra("solution", solution);

            context.startActivity(intent);
        });

    }

    /**
     * Number of items
     * @return number of riddles
     */
    @Override
    public int getItemCount()
    {
        return riddles.size();
    }

    /**
     * Sets the riddles in the list and keeps track of changes
     * @param riddles
     */
    public void setRiddles(ArrayList<Riddle> riddles)
    {
        this.riddles = riddles;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private CardView riddleCardView;
        private ImageView imageRiddle;
        private TextView txtTitle;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            riddleCardView = itemView.findViewById(R.id.riddleCardView);
            imageRiddle = itemView.findViewById(R.id.imageRiddle);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }

    }
}
