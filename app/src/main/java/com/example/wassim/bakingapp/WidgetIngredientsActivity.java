package com.example.wassim.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wassim.bakingapp.Objects.Recipe;

public class WidgetIngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_ingredients);

        if (!getIntent().getExtras().isEmpty()) {
            Recipe recipe = getIntent().getExtras().getParcelable("recipe");
            String recipeName = recipe.getmName();
            Toast.makeText(this, recipeName, Toast.LENGTH_LONG).show();
            setTitle("Ingredients for " + recipeName);
        }
    }
}
