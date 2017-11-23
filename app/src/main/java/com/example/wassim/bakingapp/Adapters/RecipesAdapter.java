package com.example.wassim.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-06
 */

public class RecipesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.recipe_item, parent, false);
        }
        TextView recipeNameTextView = convertView.findViewById(R.id.step_description);
        Recipe recipe = mRecipeList.get(position);
        String recipeName = recipe.getmName();
        recipeNameTextView.setText(recipeName);
        return convertView;
    }
}
