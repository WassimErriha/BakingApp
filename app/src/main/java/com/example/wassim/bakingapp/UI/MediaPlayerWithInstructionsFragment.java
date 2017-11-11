package com.example.wassim.bakingapp.UI;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
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

public class MediaPlayerWithInstructionsFragment extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private static String videoUrl;
    private String thumbnailUrl;
    String stepDescription;

//    private OnFragmentInteractionListener onFragmentInteractionListener;

    public MediaPlayerWithInstructionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        videoUrl = getActivity().getIntent().getExtras().getString("videoUrl");
        thumbnailUrl = getActivity().getIntent().getExtras().getString("thumbnail_url");
        stepDescription = getActivity().getIntent().getExtras().getString("step_description");
        //TODO pass in stepInstruction from activity



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_media_player, container, false);

        TextView stepInstructionsTextView = rootView.findViewById(R.id.step_instruction_text_view);
        stepInstructionsTextView.setText(stepDescription);

        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter1 = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter1);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "com.example.wassim.bakingapp"),new DefaultBandwidthMeter());
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), dataSourceFactory, new DefaultExtractorsFactory(), mainHandler, null);
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer_view);
        simpleExoPlayerView.requestFocus();
        simpleExoPlayerView.setPlayer(player);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

        // this block is only for testing, the data provided does not contain any thumbnails
        if (! thumbnailUrl.isEmpty()) {
            ImageView exoPlayerThumbnail = rootView.findViewById(R.id.exo_thumbnail);
            Picasso.with(getActivity())
                    .load("http://www.simplyrecipes.com/wp-content/uploads/2014/12/perfect-cheesecake-horiz-a-1200.jpg")
                    .into(exoPlayerThumbnail);
            simpleExoPlayerView.setUseController(false);

        }

        return rootView;
    }

//    public void onButtonPressed(Uri uri) {
//        if (onFragmentInteractionListener != null) {
//            onFragmentInteractionListener.onRecyclerViewInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        onFragmentInteractionListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onRecyclerViewInteraction(Uri uri);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}
