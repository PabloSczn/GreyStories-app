package com.napier.greystoriesgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * Main activity Class (First and main screen)
 * @author Pablo Sanchez
 * Last modification date: 20/03/2021
 */
public class MainActivity extends AppCompatActivity
{
    //Declare components
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private PagerAdater pagerAdater;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide "Grey Stories" from Action Bar
        getSupportActionBar().setTitle(" ");


        //Initialize views needed for the tablayouts with fragments
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        //Avoid Null pointer exception
        Utils.getInstance(this);

        //Set the View Pager Adapter
        FragmentManager fm = getSupportFragmentManager();
        pagerAdater = new PagerAdater(fm,getLifecycle());
        viewPager.setAdapter(pagerAdater);

        //Listener for selected tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            //When one tab is selected, move to that page
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            //My tab is not going to be unselected nor Reselected
            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position)
            {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }
        });

    }


    /**
     * ViewPager adapter to work with tablayout and fragments
     */
    private class PagerAdater extends FragmentStateAdapter
    {

        public PagerAdater(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle)
        {
            super(fragmentManager, lifecycle);
        }

        //Change fragments
        @NonNull
        @Override
        public Fragment createFragment(int position)
        {
            switch (position)
            {
                case 1:
                    return new SolvedRiddlesFragment();
                case 2:
                    return new LikedRiddlesFragment();
                default:
                    return new AllRiddlesFragment();
            }
        }
        //3 fragments
        @Override
        public int getItemCount()
        {
            return 3;
        }
    }

    /**
     * Inflate menu android
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    /**
     * Method to handle options in the menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.howToPlay:
                //Navigate the user to the How to play screen
                Intent intent = new Intent(MainActivity.this, HowToPlayActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                //Navigate the user to the about screen
                Intent intent2 = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}