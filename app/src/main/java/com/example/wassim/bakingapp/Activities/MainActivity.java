package com.example.wassim.bakingapp.Activities;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.wassim.bakingapp.Adapters.RecipeRecyclerViewAdapter;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.RecipesLoader;
import com.example.wassim.bakingapp.Utils.ConnectivityUtils;
import com.example.wassim.bakingapp.WidgetFiles.NewAppWidget;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements RecipeRecyclerViewAdapter.OnRecipeClickListener, LoaderManager.LoaderCallbacks<ArrayList<Recipe>> {

    private static final int RECIPE_LOADER_ID = 1;
    private static boolean isTablet;
    public RecyclerView recyclerView;
    private Recipe currentSelectedRecipe;
    private RecipeRecyclerViewAdapter adapter;

    public static boolean isTablet() {
        return isTablet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (ConnectivityUtils.isNetworkAvailable(this)) {
            recyclerView = findViewById(R.id.main_recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.setHasFixedSize(true);
            adapter = new RecipeRecyclerViewAdapter(this, new ArrayList<Recipe>(), this);
            recyclerView.setAdapter(adapter);
            getLoaderManager().initLoader(RECIPE_LOADER_ID, null, MainActivity.this).forceLoad();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!ConnectivityUtils.isNetworkAvailable(this))
            return;
        setLastClickedRecipe();
        Intent intent = new Intent(this, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    public void setLastClickedRecipe() {
        int recipeId = 1;
        if (currentSelectedRecipe != null)
            recipeId = currentSelectedRecipe.getmRecipeId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit()
                .putInt("recipeId", recipeId)
                .apply();
    }

    @Override
    public void onRecipeClick(int position, Recipe recipe) {
        currentSelectedRecipe = recipe;
        Intent intent = new Intent(MainActivity.this, MasterListActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }

    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int i, Bundle bundle) {
        return new RecipesLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> recipes) {
        if (recipes != null && loader.getId() == RECIPE_LOADER_ID) {
            adapter.swapData(recipes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {
    }
}