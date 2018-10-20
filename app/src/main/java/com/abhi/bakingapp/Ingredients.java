package com.abhi.bakingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import adapters.IngredientAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;

public class Ingredients extends AppCompatActivity {

    @BindView(R.id.ingredient_recyclerview_id)
    RecyclerView recyclerView;

    IngredientAdapter  ingredientAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients);
        ButterKnife.bind(this);

        int recipePosition = getIntent().getIntExtra(RECIPE_CLICKED,0);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        ingredientAdapter = new IngredientAdapter(this, SingletonClass.getsInstance().getRecipes().get(recipePosition).getIngredients());
        recyclerView.setAdapter(ingredientAdapter);

    }
}
