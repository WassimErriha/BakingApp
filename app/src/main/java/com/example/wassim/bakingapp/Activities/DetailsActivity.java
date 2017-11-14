package com.example.wassim.bakingapp.Activities;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.DetailsFragment;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;

public class DetailsActivity extends FragmentActivity implements DetailsFragment.OnFragmentInteractionListener {

    private android.app.Fragment detailsFragment;
    private android.app.FragmentManager fragmentManager;
    private IngredientsListFragment ingredientListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fragmentManager = getFragmentManager();
        //fragment_details
        Fragment containerFragment = fragmentManager.findFragmentById(R.id.item_details_container);
        Fragment containerFragme = fragmentManager.findFragmentById(R.id.fragment_details_id);
        Fragment conerFragme = fragmentManager.findFragmentById(R.id.item_details_container);
        Fragment conragme = fragmentManager.findFragmentById(R.id.item_details_container);
        //if (containerFragment != null){
        //FragmentManager childFragmentManager = containerFragment.getChildFragmentManager();
        //detailsFragment = childFragmentManager.findFragmentById(R.id.fragment_details_id);
        //detailsFragment = childFragmentManager.findFragmentByTag("fragment_details");
        //detailsFragment = childFragmentManager.findFragmentByTag("DetailsFragment");
        // }

        //detailsFragment = fragmentManager.findFragmentByTag("fragment_details");
        //fragmentManager.findFragmentByTag("fragment_details") == null
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
