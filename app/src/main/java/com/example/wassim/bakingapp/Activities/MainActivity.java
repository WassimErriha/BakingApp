package com.example.wassim.bakingapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.wassim.bakingapp.Adapters.RecipesAdapter;
import com.example.wassim.bakingapp.Utils.JsonUtils;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.main_grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Recipe recipe = (Recipe) adapterView.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this,MasterListActivity.class);
                intent.putExtra("recipe",recipe);
                startActivity(intent);
            }
        });
        new GetRecipes().execute();
    }

    class GetRecipes extends AsyncTask<Void, Void, ArrayList<Recipe>>{

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            ArrayList<Recipe> recipes = JsonUtils.fetchRecipe();
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            BaseAdapter adapter = new RecipesAdapter(MainActivity.this,recipes);
            gridView.setAdapter(adapter);
        }
    }
}