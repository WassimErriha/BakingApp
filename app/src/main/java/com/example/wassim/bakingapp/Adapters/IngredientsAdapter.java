package com.example.wassim.bakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wassim.bakingapp.Objects.Ingredient;
import com.example.wassim.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Wassim on 2017-11-07
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> ingredients;

    public IngredientsAdapter(Context context, ArrayList<Ingredient> data) {
        mContext = context;
        ingredients = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        String qty = ingredient.getQuantity() + " ";
        String measure = ingredient.getMeasure() + " ";
        String singleIngredient = ingredient.getIngredient();
        String ingredientRowString = (position + 1) + ") " + qty + measure + singleIngredient;
        holder.measureTextView.setText(ingredientRowString);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView measureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            measureTextView = itemView.findViewById(R.id.id_ingredient_text_view);
        }
    }

}
