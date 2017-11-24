package com.example.wassim.bakingapp.WidgetFiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.wassim.bakingapp.Objects.Ingredient;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.Utils.JsonUtils;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    private ArrayList<Ingredient> ingredientArrayList;

    public ListProvider(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return ingredientArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.row);
        String ingredient = ingredientArrayList.get(position).getConcatenatedString();
        remoteView.setTextViewText(R.id.heading, ingredient);
        return remoteView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        ArrayList<Recipe> mRecipes = new ArrayList<>();
        ArrayList<Recipe> recipes = JsonUtils.fetchRecipe();
        mRecipes.addAll(recipes);
        int lastViewedRecipe = getLastClickedRecipe();
        Recipe singleRecipe = mRecipes.get(lastViewedRecipe - 1);
        ingredientArrayList = singleRecipe.getmIngredientArrayList();
    }

    public int getLastClickedRecipe() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int recipeId = sharedPreferences.getInt("recipeId", 0);
        return recipeId;
    }

    @Override
    public void onDestroy() {
    }
}