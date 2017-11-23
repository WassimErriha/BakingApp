package com.example.wassim.bakingapp.WidgetFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;

public class WidgetIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_ingredients);

        if (!getIntent().getExtras().isEmpty()) {
            Recipe recipe = getIntent().getExtras().getParcelable("recipe");
            String recipeName = recipe.getmName();
            setTitle("Ingredients for " + recipeName);
        }
    }
}
