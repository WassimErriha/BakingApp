package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;
import com.example.wassim.bakingapp.UI.MasterListFragment;
import com.example.wassim.bakingapp.UI.MediaPlayerWithInstructionsFragment;

public class DetailsActivity extends AppCompatActivity implements MasterListFragment.OnFragmentInteractionListener {

    private MediaPlayerWithInstructionsFragment mediaPlayerWithInstructionsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent passedIntent = getIntent();

        if (passedIntent.getAction().equals("ACTION_SHOW_INGREDIENTS")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment ingredientListFragment = new IngredientsListFragment();
            fragmentTransaction.add(R.id.details_container, ingredientListFragment);
            fragmentTransaction.commit();

        } else if (passedIntent.getAction().equals("ACTION_SHOW_STEP_INSTRUCTIONS")) {
            String videoUrl = passedIntent.getExtras().getString("videoUrl");
            String thumbnailUrl = passedIntent.getExtras().getString("thumbnail_url");


                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mediaPlayerWithInstructionsFragment = new MediaPlayerWithInstructionsFragment();
                fragmentTransaction.add(R.id.details_container, mediaPlayerWithInstructionsFragment);
                fragmentTransaction.commit();
        }
    }

    @Override
    public void onRecyclerViewInteraction(String url, String stepDescription, String thumbnailUrl) {

    }

    @Override
    public void onIngredientCardInteraction() {

    }
}
