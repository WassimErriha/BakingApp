package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;
import com.example.wassim.bakingapp.UI.MasterListFragment;
import com.example.wassim.bakingapp.UI.MediaPlayerWithInstructionsFragment;

public class MasterListActivity extends AppCompatActivity implements MasterListFragment.OnFragmentInteractionListener {

    boolean twoPaneLayout;
    private FragmentManager fragmentManager;
    private IngredientsListFragment ingredientListFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);

        if (findViewById(R.id.two_pane_activity_master_list) != null) {
            twoPaneLayout = true;
            Toast.makeText(this, " we are in tablet mode", Toast.LENGTH_LONG).show();
            fragmentManager = getSupportFragmentManager();

            fragmentTransaction = fragmentManager.beginTransaction();
            ingredientListFragment = new IngredientsListFragment();
            fragmentTransaction.add(R.id.item_details_container, ingredientListFragment);
            fragmentTransaction.commit();

        } else {
            twoPaneLayout = false;
            Toast.makeText(this, " we are in phone mode", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRecyclerViewInteraction(String videoUrl, String stepDescription, String thumbnailUrl) {
        if (twoPaneLayout) {
            addExoplayerFragment(videoUrl, stepDescription, thumbnailUrl);
            return;
        }
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("ACTION_SHOW_STEP_INSTRUCTIONS");
        intent.putExtra("videoUrl", videoUrl);
        intent.putExtra("step_description", stepDescription);
        intent.putExtra("thumbnail_url", thumbnailUrl);
        startActivity(intent);
        Toast.makeText(this, "Test " + videoUrl, Toast.LENGTH_LONG).show();
    }

    private void addExoplayerFragment(String videoUrl, String stepDescription, String thumbnailUrl) {

        fragmentTransaction = fragmentManager.beginTransaction();
        MediaPlayerWithInstructionsFragment mediaPlayerWithInstructionsFragment = new MediaPlayerWithInstructionsFragment();
        fragmentTransaction.replace(R.id.item_details_container, mediaPlayerWithInstructionsFragment);

        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", videoUrl);
        bundle.putString("thumbnail_url", thumbnailUrl);
        bundle.putString("step_description", stepDescription);
        mediaPlayerWithInstructionsFragment.setArguments(bundle);

        fragmentTransaction.commit();
    }

    @Override
    public void onIngredientCardInteraction() {
        if (twoPaneLayout) {
            // show Ingredients
            fragmentTransaction = fragmentManager.beginTransaction();
            ingredientListFragment = new IngredientsListFragment();
            fragmentTransaction.replace(R.id.item_details_container, ingredientListFragment);
            fragmentTransaction.commit();

            return;
        }
        Recipe recipe = getIntent().getExtras().getParcelable("recipe");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.setAction("ACTION_SHOW_INGREDIENTS");
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}
