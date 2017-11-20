package com.example.wassim.bakingapp.UI;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Objects.Step;
import com.example.wassim.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class DetailsFragment extends Fragment {

    private static String videoUrl;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private String thumbnailUrl;
    private String stepDescription;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private int stepId;
    private Recipe recipe;
    private Step step;
    private Button previousButton;
    private Button nextButton;
    private ExtractorMediaSource mediaSource;
    private Handler mainHandler;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (getArguments() != null) {
            recipe = getArguments().getParcelable("recipe");
            stepId = getArguments().getInt("step_id");
        }
        // intent
        else {
            recipe = getActivity().getIntent().getExtras().getParcelable("recipe");
            stepId = getActivity().getIntent().getExtras().getInt("step_id");
        }
        step = recipe.getmStepArrayList().get(stepId);
        videoUrl = step.getVideoUrl();
        thumbnailUrl = step.getThumbnailUrl();
        stepDescription = step.getShortDiscription();


        mainHandler = new Handler();
        BandwidthMeter bandwidthMeter1 = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter1);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"), new DefaultBandwidthMeter());
        mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, new DefaultExtractorsFactory(), mainHandler, null);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView stepInstructionsTextView = rootView.findViewById(R.id.step_instruction_text_view);
        stepInstructionsTextView.setText(stepDescription);

        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);

        nextButton = rootView.findViewById(R.id.next_recipe_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onNavigationButtonsInteraction(1, stepId);
            }
        });

        previousButton = rootView.findViewById(R.id.previous_recipe_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onNavigationButtonsInteraction(0, stepId);
            }
        });


        // this block is only for testing, the data provided does not contain any thumbnails
        if (!thumbnailUrl.isEmpty()) {
            ImageView exoPlayerThumbnail = rootView.findViewById(R.id.exo_thumbnail);
            Picasso.with(getActivity())
                    .load("http://www.simplyrecipes.com/wp-content/uploads/2014/12/perfect-cheesecake-horiz-a-1200.jpg")
                    .into(exoPlayerThumbnail);
            simpleExoPlayerView.setUseController(false);

        }
        return rootView;
    }

    @Override
    public void onStart() {
        // get size of array to remove next button when user gets to the end of the array
        int endOfArray = (recipe.getmStepArrayList().size() - 1);
        super.onStart();
        if (stepId == 0) {
            previousButton.setVisibility(View.GONE);
        } else if (stepId == endOfArray) {
            nextButton.setVisibility(View.GONE);
        }
        // if in tablet mode, remove navigation buttons
        if (getActivity().findViewById(R.id.two_pane_activity_master_list) != null) {
            previousButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        }
        if (videoUrl.isEmpty() && thumbnailUrl.isEmpty()) {
            simpleExoPlayerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        // do not pause player after configuration change
        if (!getActivity().isChangingConfigurations()) {
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }

    public interface OnFragmentInteractionListener {
        void onNavigationButtonsInteraction(int navigationButtonId, int currentPosition);
    }
}
