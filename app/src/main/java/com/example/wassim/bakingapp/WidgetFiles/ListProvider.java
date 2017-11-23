package com.example.wassim.bakingapp.WidgetFiles;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.R;
import com.example.wassim.bakingapp.Utils.JsonUtils;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Recipe> mRecipes;
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.row);
        Recipe recipe = mRecipes.get(position);
        remoteView.setTextViewText(R.id.heading, recipe.getmName());
        Intent intent = new Intent(context, WidgetIngredientsActivity.class);
        intent.putExtra("recipe", recipe);
        remoteView.setOnClickFillInIntent(R.id.widget_item, intent);
        return remoteView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //Similar to getView of Adapter where instead of View we return RemoteViews

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
        mRecipes = new ArrayList<>();
        ArrayList<Recipe> recipes = JsonUtils.fetchRecipe();
        mRecipes.addAll(recipes);
    }

    @Override
    public void onDestroy() {
    }
}