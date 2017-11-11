package com.example.wassim.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wassim.bakingapp.Adapters.StepsAdapter;
import com.example.wassim.bakingapp.Activities.DetailsActivity;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Objects.Step;
import com.example.wassim.bakingapp.R;
import java.util.ArrayList;

public class MasterListFragment extends Fragment implements StepsAdapter.OnStepInstructionClickListener{

    public OnFragmentInteractionListener onFragmentInteractionListener;

    public MasterListFragment() {
        // Required empty public constructor
    }

    public interface OnFragmentInteractionListener {
        void onRecyclerViewInteraction(String url, String stepDescription, String thumbnailUrl);
        void onIngredientCardInteraction();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Recipe recipe = getActivity().getIntent().getExtras().getParcelable("recipe");
        ArrayList<Step> stepsArrayList = recipe.getmStepArrayList();
        View rootView  = inflater.inflate(R.layout.fragment_master_list, container, false);

        CardView cardView = rootView.findViewById(R.id.ingredients_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentInteractionListener.onIngredientCardInteraction();
            }
        });
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.master_recycler_view);
        stepsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        stepsRecyclerView.setHasFixedSize(true);
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(),stepsArrayList,this);
        stepsRecyclerView.setAdapter(stepsAdapter);
         return rootView;
    }

    @Override
    public void onStepClick(String videoUrl, String stepDescription, String thumbnailUrl) {
        onFragmentInteractionListener.onRecyclerViewInteraction(videoUrl,stepDescription,thumbnailUrl);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }


}
