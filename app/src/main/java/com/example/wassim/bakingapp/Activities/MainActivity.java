package com.example.wassim.bakingapp.Activities;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.wassim.bakingapp.Adapters.RecipesAdapter;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.Utils.ConnectivityUtils;
import com.example.wassim.bakingapp.Utils.JsonUtils;
import com.example.wassim.bakingapp.WidgetFiles.NewAppWidget;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static boolean isTablet;
    public GridView gridView;
    private Recipe recipe;

    public static boolean isTablet() {
        return isTablet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (ConnectivityUtils.isNetworkAvailable(this)) {
            gridView = findViewById(R.id.main_grid_view);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    recipe = (Recipe) adapterView.getAdapter().getItem(position);
                    Intent intent = new Intent(MainActivity.this, MasterListActivity.class);
                    intent.putExtra("recipe", recipe);
                    startActivity(intent);
                }
            });
            new GetRecipes().execute();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

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
        if (recipe != null)
            recipeId = recipe.getmRecipeId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit()
                .putInt("recipeId", recipeId)
                .apply();
    }

    @SuppressLint("StaticFieldLeak")
    class GetRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            return JsonUtils.fetchRecipe();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            RecipesAdapter adapter = new RecipesAdapter(MainActivity.this, recipes);
            gridView.setAdapter(adapter);
        }
    }
}