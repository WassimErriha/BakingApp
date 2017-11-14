package com.example.wassim.bakingapp.Activities;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.DetailsFragment;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;

public class DetailsActivity extends Activity implements DetailsFragment.OnFragmentInteractionListener {

    private Fragment detailsFragment;
    private FragmentManager fragmentManager;
    private IngredientsListFragment ingredientListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fragmentManager = getFragmentManager();

        Fragment containerFragment = fragmentManager.findFragmentById(R.id.details_container);

        if (containerFragment == null) {
            Intent passedIntent = getIntent();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (passedIntent.getAction().equals("ACTION_SHOW_INGREDIENTS")) {
                ingredientListFragment = new IngredientsListFragment();
                fragmentTransaction.add(R.id.details_container, ingredientListFragment);
                fragmentTransaction.commit();
            } else if (passedIntent.getAction().equals("ACTION_SHOW_STEP_INSTRUCTIONS")) {
                detailsFragment = new DetailsFragment();
                fragmentTransaction.add(R.id.details_container, detailsFragment);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onNavigationButtonsInteraction(int navigationButtonId, int currentPosition) {

        Recipe recipe = getIntent().getExtras().getParcelable("recipe");

        int futurePosition;
        if (navigationButtonId == 1) futurePosition = currentPosition + 1;
        else futurePosition = currentPosition - 1;

        fragmentManager = getFragmentManager();
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
