package com.example.wassim.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-25
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    public OnRecipeClickListener mOnRecipeClickListener;
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;

    public RecipeRecyclerViewAdapter(Context context, ArrayList<Recipe> recipeList, OnRecipeClickListener onRecipeClickListener) {
        mOnRecipeClickListener = onRecipeClickListener;
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        String recipeName = recipe.getmName();
        holder.recipeNameTextView.setText(recipeName);
        String imageUrl = recipe.getmImage();
        loadImage(imageUrl, holder.recipeImageView);
    }

    private void loadImage(String imageUrl, ImageView recipeImageView) {
        if (!imageUrl.isEmpty()) {
            Picasso.with(mContext)
                    .load(imageUrl)
                    .into(recipeImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void swapData(ArrayList<Recipe> recipes) {
        if (recipes != null) {
            mRecipeList = new ArrayList<>();
            mRecipeList.addAll(recipes);
            notifyDataSetChanged();
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(int position, Recipe recipe);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView recipeImageView;
        private final TextView recipeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeNameTextView = itemView.findViewById(R.id.step_description);
            recipeImageView = itemView.findViewById(R.id.recipe_image);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Recipe recipe = mRecipeList.get(position);
            mOnRecipeClickListener.onRecipeClick(position, recipe);
        }
    }
}
