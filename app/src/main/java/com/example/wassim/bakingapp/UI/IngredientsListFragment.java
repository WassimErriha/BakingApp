package com.example.wassim.bakingapp.UI;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wassim.bakingapp.Adapters.IngredientsAdapter;
import com.example.wassim.bakingapp.Objects.Ingredient;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;

import java.util.ArrayList;

public class IngredientsListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Recipe recipe = getActivity().getIntent().getExtras().getParcelable("recipe");
        ArrayList<Ingredient> ingredients = recipe.getmIngredientArrayList();
        View rootView = inflater.inflate(R.layout.ingredient_item_list, container, false);
        // Set the adapter
        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            RecyclerView recyclerView = (RecyclerView) rootView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new IngredientsAdapter(getActivity(), ingredients));
        }
        return rootView;
    }
}
