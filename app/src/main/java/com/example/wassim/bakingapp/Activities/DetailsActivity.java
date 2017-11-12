package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.DetailsFragment;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;

public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener {

    private DetailsFragment detailsFragment;
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
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            detailsFragment = new DetailsFragment();
            fragmentTransaction.add(R.id.details_container, detailsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNavigationButtonsInteraction(int navigationButtonId, int currentPosition) {

        Recipe recipe = getIntent().getExtras().getParcelable("recipe");

        int futurePosition;
        if (navigationButtonId == 1) futurePosition = currentPosition + 1;
        else futurePosition = currentPosition - 1;

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        detailsFragment = new DetailsFragment();
        fragmentTransaction.replace(R.id.details_container, detailsFragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        bundle.putInt("step_id", futurePosition);
        detailsFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}
