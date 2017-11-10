package com.example.wassim.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Wassim on 2017-11-06
 */

public class RecipesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    public RecipesAdapter( Context context, ArrayList<Recipe> recipeList) {
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
        if (convertView == null ){
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.recipe_item,parent,false);
        }
        TextView recipeNameTextView = convertView.findViewById(R.id.step_description);
        Recipe recipe =  mRecipeList.get(position);
        String recipeNamae = recipe.getmName();
        recipeNameTextView.setText(recipeNamae);

        return convertView;
    }
}
