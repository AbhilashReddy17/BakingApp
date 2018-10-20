package com.abhi.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import adapters.RecipeDetailsFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;


/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeDetailsFragment extends AppCompatActivity {
    @BindView(R.id.recipe_reclerview_id)
    RecyclerView recyclerView;
    @BindView(R.id.main_activity_progressbar_id)
    ProgressBar progressBar;
RecipeDetailsFragmentAdapter recipeDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.GONE);
        int recipePosition = getIntent().getIntExtra(RECIPE_CLICKED,0);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recipeDetailAdapter = new RecipeDetailsFragmentAdapter(this, SingletonClass.getsInstance().getRecipes().get(recipePosition));
        recyclerView.setAdapter(recipeDetailAdapter);

    }
}
