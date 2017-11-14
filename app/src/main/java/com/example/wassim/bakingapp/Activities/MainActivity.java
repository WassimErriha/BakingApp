package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public GridView gridView;
    private Recipe recipe;
    private RecipesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Toast.makeText(this, "saved recipe", Toast.LENGTH_LONG).show();
            recipe = savedInstanceState.getParcelable("recipe");
        } else {
            Toast.makeText(this, "New recipe", Toast.LENGTH_LONG).show();
            new GetRecipes().execute();
        }

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

        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (recipe != null) {
            outState.putParcelable("recipe", recipe);
        }
        super.onSaveInstanceState(outState);
    }

    class GetRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>>{

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            ArrayList<Recipe> recipes = JsonUtils.fetchRecipe();
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            adapter = new RecipesAdapter(MainActivity.this, recipes);
            gridView.setAdapter(adapter);
        }
    }
}