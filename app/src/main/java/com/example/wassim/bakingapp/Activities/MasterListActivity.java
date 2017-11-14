package com.example.wassim.bakingapp.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.DetailsFragment;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;
import com.example.wassim.bakingapp.UI.MasterListFragment;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnFragmentInteractionListener, DetailsFragment.OnFragmentInteractionListener {

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


        if (findViewById(R.id.two_pane_activity_master_list) != null) {
            twoPaneLayout = true;
            fragmentManager = getFragmentManager();
            //Fragment containerFragment = fragmentManager.findFragmentByTag("fragment_details");
            //Fragment containerFragment2 = fragmentManager.findFragmentById(R.id.fragment_details_id);
            //Fragment containerFragment3 = fragmentManager.findFragmentByTag("fragment_details");
            Fragment containerFragment4 = fragmentManager.findFragmentById(R.id.item_details_container);
            if (containerFragment4 == null) {
                fragmentTransaction = fragmentManager.beginTransaction();
                ingredientListFragment = new IngredientsListFragment();
                fragmentTransaction.add(R.id.item_details_container, ingredientListFragment);
                fragmentTransaction.commit();
            }

        } else {
            twoPaneLayout = false;
        }
    }

    @Override
    public void onRecyclerViewInteraction(int stepId) {

        if (getIntent() != null) {
            recipe = getIntent().getExtras().getParcelable("recipe");
        }

        if (twoPaneLayout) {
            // show mediaPlayer and instructions fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            detailsFragment = new DetailsFragment();
            fragmentTransaction.replace(R.id.item_details_container, detailsFragment);
            Bundle bundle = new Bundle();
            bundle.putParcelable("recipe", recipe);
            bundle.putInt("step_id", stepId);
            detailsFragment.setArguments(bundle);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.setAction("ACTION_SHOW_STEP_INSTRUCTIONS");
            intent.putExtra("recipe", recipe);
            intent.putExtra("step_id", stepId);
            startActivity(intent);
            Toast.makeText(this, "Test " + stepId, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onIngredientCardInteraction() {
        recipe = getIntent().getExtras().getParcelable("recipe");
        if (twoPaneLayout) {
            // show Ingredients fragment
            fragmentTransaction = fragmentManager.beginTransaction();
            ingredientListFragment = new IngredientsListFragment();
            fragmentTransaction.replace(R.id.item_details_container, ingredientListFragment);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.setAction("ACTION_SHOW_INGREDIENTS");
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
