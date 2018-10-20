package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.Ingredients;
import com.abhi.bakingapp.R;
import com.abhi.bakingapp.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Recipe;

import static com.abhi.bakingapp.Constants.RECIPE_CLICKED;
import static com.abhi.bakingapp.Constants.RECIPE_STEP_CLICKED;

/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeDetailsFragmentAdapter extends RecyclerView.Adapter<RecipeDetailsFragmentAdapter.RecipeDetailHolder>{
    Recipe recipe;
    Context context;
    public RecipeDetailsFragmentAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public RecipeDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipedetails_recyclerview_item,parent,false);
        RecipeDetailHolder holder = new RecipeDetailHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailHolder holder, final int position) {

        if(position == 0){
            holder.itemText.setText(context.getResources().getString(R.string.firstItem)); // first item always reserved for ingredients
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Ingredients.class);
                    intent.putExtra(RECIPE_CLICKED,recipe.getId()-1); // -1 because list starts with 0
                    context.startActivity(intent);
                }
            });
        }else{
            holder.itemText.setText(context.getResources().getString(R.string.notfirstItem,position)
                    +recipe.getSteps().get(position-1).getShortDescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RecipeStep.class);
                    intent.putExtra(RECIPE_CLICKED,recipe.getId()-1);
                    intent.putExtra(RECIPE_STEP_CLICKED,position);

                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().size()+1; //+1 for the first ingredient item
    }

    public class RecipeDetailHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_text_id)
        TextView itemText;
        View itemView;
        public RecipeDetailHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
}
