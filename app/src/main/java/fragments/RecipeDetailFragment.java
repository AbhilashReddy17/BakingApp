package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.bakingapp.R;
import com.abhi.bakingapp.RecipeDetailActivity;

import java.util.List;

import adapters.RecipeDetailsFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.RecipeStepClickedListener;
import models.Recipe;
import models.SingletonClass;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;

public class RecipeDetailFragment extends Fragment {


    @BindView(R.id.recipe_reclerview_id)
    RecyclerView recyclerView;

    RecipeDetailsFragmentAdapter recipeDetailAdapter;
    RecipeStepClickedListener callback;
    static RecipeDetailFragment fragment;
    public RecipeDetailFragment() {
        // Required empty public constructor
    }
    public static RecipeDetailFragment getInstance(int recipeClicked){
        if(fragment == null) fragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RECIPE_CLICKED,recipeClicked);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.recipe_details_fragments_layout,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        callback = (RecipeDetailActivity)getContext();

        int recipePosition=  getArguments().getInt(RECIPE_CLICKED);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        if(recyclerView!=null){
            recyclerView.setLayoutManager(manager);
            List<Recipe> recipeList = SingletonClass.getsInstance().getRecipes();

            if(recipeList != null){
                recipeDetailAdapter = new RecipeDetailsFragmentAdapter(getContext(),
                        recipeList.get(recipePosition),new RecipeStepClickedListener(){
                    @Override
                    public void onResponse(int recipePosition, int recipeStepClicked) {
                        callback.onResponse(recipePosition,recipeStepClicked);
                    }
                });
                recyclerView.setAdapter(recipeDetailAdapter);
            }

        }
    }
}
