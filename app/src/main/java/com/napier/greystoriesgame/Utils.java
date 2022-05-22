package com.napier.greystoriesgame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Singleton class, working as initializer of the different lists needed
 * @author Pablo Sanchez
 * Last date of modification 26/03/2022
 */
public class Utils
{
    //Use of Shared Preferences for Data Persistance
    private static Utils instance;
    private SharedPreferences sharedPreferences;
    //Define KEYS for Shared Preferences
    private static final String ALL_RIDDLES = "all_riddles";
    private static final String SOLVED_RIDDLES = "solved_riddles";
    private static final String LIKE_RIDDLES = "like_riddles";


    private Utils(Context context)
    {
        //Shared preferences to persist the data
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if(getAllRiddles() == null)
        {
            //Load all riddles to the all riddles list
            loadRiddles(context);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getSolvedRiddles() == null)
        {
            editor.putString(SOLVED_RIDDLES, gson.toJson(new ArrayList<Riddle>()));
            editor.commit();
        }
        if(getLikedRiddles() == null)
        {
            editor.putString(LIKE_RIDDLES, gson.toJson(new ArrayList<Riddle>()));
            editor.commit();
        }
    }

    /**
     * Get instance class for the Singleton pattern
     * @param context
     * @return
     */
    public static Utils getInstance(Context context)
    {
        if(instance != null)
        {
            return instance;
        }
        else
        {
            instance = new Utils(context);
            return instance;
        }
    }


    /**
     * Method to add Riddles to the solved riddles list
     * @param riddle
     */
    public void addToSolvedRiddle(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getSolvedRiddles();
        if(null != riddles)
        {
            if(riddles.add(riddle))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(SOLVED_RIDDLES);
                editor.putString(SOLVED_RIDDLES, gson.toJson(riddles));
                editor.apply();
            }
        }
    }

    /**
     * Method to add Riddles to the all riddles list
     * @param riddle
     */
    public void addToAllRiddle(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getAllRiddles();
        if(null != riddles)
        {
            if(riddles.add(riddle))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALL_RIDDLES);
                editor.putString(ALL_RIDDLES, gson.toJson(riddles));
                editor.apply();
            }
        }
    }

    /**
     * Method to add Riddles to the liked riddles list
     * @param riddle
     */
    public void addToLikedRiddle(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getLikedRiddles();
        if(null != riddles)
        {
            if(riddles.add(riddle))
            {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(LIKE_RIDDLES);
                editor.putString(LIKE_RIDDLES, gson.toJson(riddles));
                editor.apply();
            }
        }
    }

    /**
     * Method to remove Riddles from the Solved list
     * @param riddle
     */
    public void removeFromSolved(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getSolvedRiddles();
        if(riddles !=null)
        {
            for(int i = 0; i < riddles.size(); i++)
            {
                if(riddles.get(i).getTitle().equals(riddle.getTitle()))
                {
                    if(riddles.remove(riddles.get(i)))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(SOLVED_RIDDLES);
                        editor.putString(SOLVED_RIDDLES, gson.toJson(riddles));
                        editor.apply();
                    }
                }
            }
        }
    }

    /**
     * Method to remove Riddles from the Solved list
     * @param riddle
     */
    public void removeFromAll(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getAllRiddles();
        if(riddles !=null)
        {
            for(int i = 0; i < riddles.size(); i++)
            {
                if(riddles.get(i).getTitle().equals(riddle.getTitle()))
                {
                    if(riddles.remove(riddles.get(i)))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALL_RIDDLES);
                        editor.putString(ALL_RIDDLES, gson.toJson(riddles));
                        editor.apply();
                    }
                }
            }
        }
    }

    /**
     * Method to remove Riddles from the Liked list
     * @param riddle
     */
    public void removeFromLiked(Riddle riddle)
    {
        ArrayList<Riddle> riddles = getLikedRiddles();
        if(riddles !=null)
        {
            for(int i = 0; i < riddles.size(); i++)
            {
                if(riddles.get(i).getTitle().equals(riddle.getTitle()))
                {
                    if(riddles.remove(riddles.get(i)))
                    {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(LIKE_RIDDLES);
                        editor.putString(LIKE_RIDDLES, gson.toJson(riddles));
                        editor.apply();
                    }
                }
            }
        }
    }

    /**
     * Method to find the Riddle by the name
     * @param riddleName
     * @return Riddle object if found
     */
    public Riddle getRiddleByName(String riddleName)
    {
        //We are going to look for the riddle both in the All riddles list and the Solved list
        ArrayList<Riddle> riddles = getAllRiddles();
        ArrayList<Riddle> solvedRiddles = getSolvedRiddles();
        if(null!= riddles)
        {
            for(Riddle r : riddles)
            {
                if(r.getTitle().equals(riddleName))
                {
                    return r;
                }
            }
        }
        if(null!=solvedRiddles)
        {
            for(Riddle r2 : solvedRiddles)
            {
                if(r2.getTitle().equals(riddleName))
                {
                    return r2;
                }
            }
        }
        return null;
    }

    /**
     * Method to load riddles to the list
     * @param context
     */
    private void loadRiddles(Context context)
    {
        ArrayList<Riddle> riddles = new ArrayList<>();
        //Add the riddles to the all riddles list
        riddles.add(new Riddle("Lethal Decision", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1552360708-ebcdf76845ac?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=581&q=80",
                context.getString(R.string.riddle1_text),
                context.getString(R.string.riddle1_hints),
                context.getString(R.string.riddle1_solution)));
        riddles.add(new Riddle("Last meal", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1545238377-dee9b7db2414?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle2_text),
                context.getString(R.string.riddle2_hints),
                context.getString(R.string.riddle2_solution)));
        riddles.add(new Riddle("Wrong place", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1644705697675-8e13764dabc9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle3_text),
                context.getString(R.string.riddle3_hints),
                context.getString(R.string.riddle3_solution)));
        riddles.add(new Riddle("Unfortunate accident", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1627312130477-d99aca7919ce?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle4_text),
                context.getString(R.string.riddle4_hints),
                context.getString(R.string.riddle4_solution)));
        riddles.add(new Riddle("Birdwatcher", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1603980328923-2e5627e5d1de?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle5_text),
                context.getString(R.string.riddle5_hints),
                context.getString(R.string.riddle5_solution)));
        riddles.add(new Riddle("Last dance", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1575217764381-134358bc7d8c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=550&q=80",
                context.getString(R.string.riddle6_text),
                context.getString(R.string.riddle6_hints),
                context.getString(R.string.riddle6_solution)));
        riddles.add(new Riddle("Believe me!", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1617878910364-4bc6afef746e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle7_text),
                context.getString(R.string.riddle7_hints),
                context.getString(R.string.riddle7_solution)));
        riddles.add(new Riddle("Unlocked door", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1560205405-65624a949206?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle8_text),
                context.getString(R.string.riddle8_hints),
                context.getString(R.string.riddle8_solution)));
        riddles.add(new Riddle("High heels", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1630915649592-44ee08af50c6?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle9_text),
                context.getString(R.string.riddle9_hints),
                context.getString(R.string.riddle9_solution)));
        riddles.add(new Riddle("Last shot", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1525010847828-14e5f0b8e127?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
                context.getString(R.string.riddle10_text),
                context.getString(R.string.riddle10_hints),
                context.getString(R.string.riddle10_solution)));
        riddles.add(new Riddle("Punch", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1626201339405-e3912075c1d2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=475&q=80",
                context.getString(R.string.riddle11_text),
                context.getString(R.string.riddle11_hints),
                context.getString(R.string.riddle11_solution)));
        riddles.add(new Riddle("He’s lying", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1497008386681-a7941f08011e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle12_text),
                context.getString(R.string.riddle12_hints),
                context.getString(R.string.riddle12_solution)));
        riddles.add(new Riddle("Great professional", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1543121955-8dfb9e9e255f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle13_text),
                context.getString(R.string.riddle13_hints),
                context.getString(R.string.riddle13_solution)));
        riddles.add(new Riddle("A note", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1522283816429-a1430b600a20?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle14_text),
                context.getString(R.string.riddle14_hints),
                context.getString(R.string.riddle14_solution)));
        riddles.add(new Riddle("The yacht", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1505080857763-eec772cd197d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80",
                context.getString(R.string.riddle15_text),
                context.getString(R.string.riddle15_hints),
                context.getString(R.string.riddle15_solution)));
        riddles.add(new Riddle("Hanged man", "Pablo Sanchez",
                "https://images.unsplash.com/photo-1613477092384-6ada43052438?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=574&q=80",
                context.getString(R.string.riddle16_text),
                context.getString(R.string.riddle16_hints),
                context.getString(R.string.riddle16_solution)));



        //Shared preferences implementation
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        //Save the all riddles list to shared preferences with the help of Gson
        editor.putString(ALL_RIDDLES, gson.toJson(riddles));
        //Does not interrupt the main thread
        editor.apply();
    }


    /**
     *  ********** Getters **********
     */

    public ArrayList<Riddle> getAllRiddles()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Riddle>>(){}.getType();
        //Get the list from shared preferences
        ArrayList<Riddle> riddles = gson.fromJson(sharedPreferences.getString(ALL_RIDDLES, null), type);
        return riddles;
    }

    public ArrayList<Riddle> getSolvedRiddles()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Riddle>>(){}.getType();
        //Get the list from shared preferences
        ArrayList<Riddle> riddles2 = gson.fromJson(sharedPreferences.getString(SOLVED_RIDDLES, null), type);
        return riddles2;
    }

    public ArrayList<Riddle> getLikedRiddles()
    {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Riddle>>(){}.getType();
        //Get the list from shared preferences
        ArrayList<Riddle> riddles3 = gson.fromJson(sharedPreferences.getString(LIKE_RIDDLES, null), type);
        return riddles3;
    }
}
