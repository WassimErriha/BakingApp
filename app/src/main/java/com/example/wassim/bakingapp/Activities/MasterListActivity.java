package com.example.wassim.bakingapp.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.DetailsFragment;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;
import com.example.wassim.bakingapp.UI.MasterListFragment;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener {

    private final String STEP_DETAILS_FRAGMENT_TAG = "step_details_tag";
    private final String INGREDIENTS_LIST_FRAGMENT_TAG = "ingredients_list_tag";
    private final String ACTION_SHOW_STEP_INSTRUCTIONS = "action_show_step_instruction";
    private final String ACTION_SHOW_INGREDIENTS = "action_show_ingredients";
    boolean twoPaneLayout;
    private FragmentManager fragmentManager;
    private IngredientsListFragment ingredientListFragment;
    private FragmentTransaction fragmentTransaction;
    private Recipe recipe;
    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        recipe = getIntent().getExtras().getParcelable("recipe");

        if (getResources().getBoolean(R.bool.isTablet)) {
            twoPaneLayout = true;
            fragmentManager = getFragmentManager();
            // in case of activity restart, check if details Fragments are retained.
            ingredientListFragment = (IngredientsListFragment) fragmentManager.findFragmentByTag(INGREDIENTS_LIST_FRAGMENT_TAG);
            detailsFragment = (DetailsFragment) fragmentManager.findFragmentByTag(STEP_DETAILS_FRAGMENT_TAG);
            if (ingredientListFragment == null && detailsFragment == null) {
                fragmentTransaction = fragmentManager.beginTransaction();
                ingredientListFragment = new IngredientsListFragment();
                fragmentTransaction.add(R.id.item_details_container, ingredientListFragment, INGREDIENTS_LIST_FRAGMENT_TAG);
                fragmentTransaction.commit();
            }
        } else {
            twoPaneLayout = false;
        }
    }

    // get StepPositionInDataSet instead of stepId because a stepId might be missing
    // this avoids causing a NPE when reaching the end of the array while using a navigation buttons
    @Override
    public void onRecyclerViewInteraction(int stepPositionInDataSet) {

        if (twoPaneLayout) {
            // show mediaPlayer and instructions fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            detailsFragment = new DetailsFragment();
            fragmentTransaction.replace(R.id.item_details_container, detailsFragment, STEP_DETAILS_FRAGMENT_TAG);
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipe", recipe);
            bundle.putInt("step_position", stepPositionInDataSet);
            detailsFragment.setArguments(bundle);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.setAction(ACTION_SHOW_STEP_INSTRUCTIONS);
            intent.putExtra("recipe", recipe);
            intent.putExtra("step_position", stepPositionInDataSet);
            startActivity(intent);
        }
    }

    @Override
    public void onIngredientCardInteraction() {

        if (twoPaneLayout) {
            // show Ingredients fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            ingredientListFragment = new IngredientsListFragment();
            fragmentTransaction.replace(R.id.item_details_container, ingredientListFragment, INGREDIENTS_LIST_FRAGMENT_TAG);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.setAction(ACTION_SHOW_INGREDIENTS);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }
    }

    @Override
    public void onNavigationButtonsInteraction(int navigationButtonId, int currentPosition) {
        // not used in twoPaneLayout
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }
}
