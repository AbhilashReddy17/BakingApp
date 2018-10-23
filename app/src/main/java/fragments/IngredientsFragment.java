package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.bakingapp.R;

import java.util.List;

import adapters.IngredientAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Recipe;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    public static IngredientsFragment fragment;
    @BindView(R.id.ingredient_recyclerview_id)
    RecyclerView recyclerView;

    IngredientAdapter  ingredientAdapter;
    public IngredientsFragment() {
        // Required empty public constructor
    }
public static IngredientsFragment getInstance(int recipeClicked){
        if(fragment == null) fragment = new IngredientsFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(RECIPE_CLICKED,recipeClicked);
    fragment.setArguments(bundle);
        return fragment;
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.ingredients, container, false);
        ButterKnife.bind(this,view);
        int recipePosition = getArguments().getInt(RECIPE_CLICKED);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        List<Recipe> recipeList = SingletonClass.getsInstance().getRecipes();

        if(recipeList != null){
            ingredientAdapter = new IngredientAdapter(getContext(), recipeList.get(recipePosition).getIngredients());
            recyclerView.setAdapter(ingredientAdapter);
        }
        return view;
    }

}
