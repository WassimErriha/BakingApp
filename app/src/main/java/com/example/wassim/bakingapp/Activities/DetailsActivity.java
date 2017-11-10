package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.UI.IngredientsListFragment;
import com.example.wassim.bakingapp.UI.InstructionsFragment;
import com.example.wassim.bakingapp.UI.MasterListFragment;
import com.example.wassim.bakingapp.UI.MediaPlayerFragment;

public class DetailsActivity extends AppCompatActivity implements MasterListFragment.OnFragmentInteractionListener {

    private MediaPlayerFragment mediaPlayerFragment;
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
            fragmentTransaction.add(R.id.step_instruction_container, ingredientListFragment);

            findViewById(R.id.exoplayer_container).setVisibility(View.GONE);
            findViewById(R.id.navigation_container).setVisibility(View.GONE);
            FrameLayout stepInstructionContainer = findViewById(R.id.step_instruction_container);
            stepInstructionContainer.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT));
            fragmentTransaction.commit();

        } else if (passedIntent.getAction().equals("ACTION_SHOW_STEP_INSTRUCTIONS")) {
            String videoUrl = passedIntent.getExtras().getString("url");
            String thumbnailUrl = passedIntent.getExtras().getString("thumbnail_url");


                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                mediaPlayerFragment = new MediaPlayerFragment();
                fragmentTransaction.add(R.id.exoplayer_container, mediaPlayerFragment);

                Fragment instructionsFragment = new InstructionsFragment();
                fragmentTransaction.add(R.id.step_instruction_container, instructionsFragment);

                fragmentTransaction.commit();
                // if no url for media is available, remove media Player container and expand instructions view
                if (videoUrl.isEmpty() && thumbnailUrl.isEmpty())  {
                    findViewById(R.id.exoplayer_container).setVisibility(View.GONE);
                    findViewById(R.id.navigation_container).setVisibility(View.GONE);
                    FrameLayout stepInstructionContainer = findViewById(R.id.step_instruction_container);
                    stepInstructionContainer.setLayoutParams(
                            new LinearLayout.LayoutParams(
                                    FrameLayout.LayoutParams.MATCH_PARENT,
                                    FrameLayout.LayoutParams.MATCH_PARENT));
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(mediaPlayerFragment);
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onRecyclerViewInteraction(String url, String stepDescription, String thumbnailUrl) {

    }

    @Override
    public void onIngredientCardInteraction() {

    }
}
