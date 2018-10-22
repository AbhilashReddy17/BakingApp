package com.abhi.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragments.IngredientsFragment;
import fragments.RecipeDetailFragment;
import fragments.RecipeStepFragment;
import interfaces.RecipeStepClickedListener;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;
import static com.abhi.bakingapp.Constants.RECIPE_STEP_CLICKED;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepClickedListener{

    public static final String TAG = RecipeDetailActivity.class.getSimpleName();

    @BindView(R.id.framelayout_recipestep_container)
    @Nullable
    FrameLayout recipeStepFragmentContainer;
    int recipeClicked;
    int recipeStepClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_details_activity_layout);
        ButterKnife.bind(this);

        if(savedInstanceState != null){
            recipeClicked = savedInstanceState.getInt(RECIPE_CLICKED);
            recipeStepClicked = savedInstanceState.getInt(RECIPE_STEP_CLICKED);
        }
         recipeClicked = getIntent().getIntExtra(RECIPE_CLICKED,0);

        }

    @Override
    protected void onResume() {
        super.onResume();
        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.getInstance(recipeClicked);
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_container,recipeDetailFragment).commit();

        if(recipeStepFragmentContainer != null){
            //loading first RecipeDetail object which is ingredient
            if(recipeStepClicked==0){
                setIngredientsFragment(recipeClicked);
            }else{
                RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipeClicked,recipeStepClicked);
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,recipeStepFragment).commit();
            }

        }

    }

    private void setIngredientsFragment(int recipeClicked) {
        if(recipeStepFragmentContainer == null){
            IngredientsFragment ingredientsFragment = IngredientsFragment.getInstance(recipeClicked);
            getSupportFragmentManager().beginTransaction().addToBackStack(TAG).replace(R.id.framelayout_container,ingredientsFragment).commit();
        }else{
            IngredientsFragment ingredientsFragment = IngredientsFragment.getInstance(recipeClicked);
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,ingredientsFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count>0){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_CLICKED,recipeClicked);
        outState.putInt(RECIPE_STEP_CLICKED,recipeStepClicked);
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count>0){
            getSupportFragmentManager().popBackStack();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResponse(int recipePosition, int recipeStepClicked) {
        this.recipeClicked = recipePosition;
        this.recipeStepClicked = recipeStepClicked;
        if(recipeStepClicked == 0){
            //clicked ingredients
            setIngredientsFragment(recipePosition);

        }else{
            //clicked recipe step
            if(recipeStepFragmentContainer != null){
                RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipePosition,recipeStepClicked);
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_recipestep_container,recipeStepFragment).commit();
            }else{
                RecipeStepFragment recipeStepFragment = RecipeStepFragment.getInstance(recipePosition,recipeStepClicked);
                getSupportFragmentManager().beginTransaction().addToBackStack(TAG).replace(R.id.framelayout_container,recipeStepFragment).commit();
            }

        }
    }
}
