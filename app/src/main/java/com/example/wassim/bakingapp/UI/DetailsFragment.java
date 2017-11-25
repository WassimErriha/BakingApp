package com.example.wassim.bakingapp.UI;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.bakingapp.Activities.MainActivity;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Objects.Step;
import com.example.wassim.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
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

    private static final String RECIPE_KEY = "recipe";
    private static final String STEP_POSITION_KEY = "step_position";
    private static final String CONTENT_POSITION_KEY = "content_position";
    private static String videoUrl;
    private final int PREVIOUS_BUTTON_ID = 0;
    private final int NEXT_BUTTON_ID = 1;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private String thumbnailUrl;
    private String stepDescription;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private int stepPositionInDataSet;
    private Recipe recipe;
    private Button previousButton;
    private Button nextButton;
    private long contentPosition;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);


        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_KEY);
            stepPositionInDataSet = savedInstanceState.getInt(STEP_POSITION_KEY);
            contentPosition = savedInstanceState.getLong(CONTENT_POSITION_KEY);
        }
        // Activity arguments
        else if (getArguments() != null) {
            recipe = getArguments().getParcelable(RECIPE_KEY);
            stepPositionInDataSet = getArguments().getInt(STEP_POSITION_KEY);
        }
        // Activity intent
        else {
            recipe = getActivity().getIntent().getExtras().getParcelable(RECIPE_KEY);
            stepPositionInDataSet = getActivity().getIntent().getExtras().getInt(STEP_POSITION_KEY);
        }
        Step step = recipe.getmStepArrayList().get(stepPositionInDataSet);
        videoUrl = step.getVideoUrl();
        thumbnailUrl = step.getThumbnailUrl();
        stepDescription = step.getShortDiscription();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(RECIPE_KEY, recipe);
        outState.putInt(STEP_POSITION_KEY, stepPositionInDataSet);
        outState.putLong(CONTENT_POSITION_KEY, player.getContentPosition());
    }

    private void initializePlayer(SimpleExoPlayerView simpleExoPlayerView) {
        BandwidthMeter bandwidthMeter =
                new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"));
        ExtractorsFactory extractorsFactory =
                new DefaultExtractorsFactory();
        String contentUrl = videoUrl;
        MediaSource contentMediaSource =
                new ExtractorMediaSource(Uri.parse(contentUrl), dataSourceFactory, extractorsFactory, null, null);
        player.seekTo(contentPosition);
        player.prepare(contentMediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        TextView stepInstructionsTextView = rootView.findViewById(R.id.step_instruction_text_view);
        stepInstructionsTextView.setText(stepDescription);
        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);
        initializePlayer(simpleExoPlayerView);
        nextButton = rootView.findViewById(R.id.next_recipe_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onNavigationButtonsInteraction(NEXT_BUTTON_ID, stepPositionInDataSet);
            }
        });
        previousButton = rootView.findViewById(R.id.previous_recipe_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onNavigationButtonsInteraction(PREVIOUS_BUTTON_ID, stepPositionInDataSet);
            }
        });
        // this block is only for testing, the data provided does not contain any thumbnails
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            ImageView exoPlayerThumbnail = rootView.findViewById(R.id.exo_thumbnail);
            Picasso.with(getActivity())
                    .load(thumbnailUrl)
                    .into(exoPlayerThumbnail);
            simpleExoPlayerView.setUseController(false);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        // get size of array to remove next button when user gets to the end of the array
        int endOfArray = (recipe.getmStepArrayList().size() - 1);
        // if in tablet mode, remove navigation buttons
        if (MainActivity.isTablet()) {
            previousButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
        } else if (stepPositionInDataSet == 0)
            previousButton.setVisibility(View.GONE);
        else if (stepPositionInDataSet == endOfArray)
            nextButton.setVisibility(View.GONE);
        else if (videoUrl.isEmpty() && thumbnailUrl.isEmpty())
            simpleExoPlayerView.setVisibility(View.GONE);
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
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer(simpleExoPlayerView);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            contentPosition = player.getContentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }

    public interface OnFragmentInteractionListener {
        void onNavigationButtonsInteraction(int navigationButtonId, int currentPosition);
    }
}
