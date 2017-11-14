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
    private String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";
    private String INGREDIENTS_LIST_FRAGMENT_TAG = "ingredients_list_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fragmentManager = getFragmentManager();

        //Fragment containerFragment = fragmentManager.findFragmentById(R.id.details_container);
        ingredientListFragment = (IngredientsListFragment) fragmentManager.findFragmentByTag(INGREDIENTS_LIST_FRAGMENT_TAG);
        detailsFragment = fragmentManager.findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);
        // if ingredientListFragment && detailsFragment
        if (ingredientListFragment == null && detailsFragment == null) {
            Intent passedIntent = getIntent();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (passedIntent.getAction().equals("ACTION_SHOW_INGREDIENTS")) {
                ingredientListFragment = new IngredientsListFragment();
                fragmentTransaction.add(R.id.details_container, ingredientListFragment, INGREDIENTS_LIST_FRAGMENT_TAG);
                fragmentTransaction.commit();
            } else if (passedIntent.getAction().equals("ACTION_SHOW_STEP_INSTRUCTIONS")) {
                detailsFragment = new DetailsFragment();
                fragmentTransaction.add(R.id.details_container, detailsFragment, STEP_DETAILS_FRAGMENT_TAG);
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
        fragmentTransaction.replace(R.id.details_container, detailsFragment, STEP_DETAILS_FRAGMENT_TAG);
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);
        bundle.putInt("step_id", futurePosition);
        detailsFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }
}
