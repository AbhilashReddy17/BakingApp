package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.RecipeStepClickedListener;
import models.Recipe;

/**
 * Created by ABHI on 10/18/2018.
 */

public class RecipeDetailsFragmentAdapter extends RecyclerView.Adapter<RecipeDetailsFragmentAdapter.RecipeDetailHolder>{
    Recipe recipe;
    Context context;
    RecipeStepClickedListener callback;
    public RecipeDetailsFragmentAdapter(Context context, Recipe recipe,  RecipeStepClickedListener callback) {
        this.context = context;
        this.recipe = recipe;
        this.callback = callback;
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
        }else{
            holder.itemText.setText(context.getResources().getString(R.string.notfirstItem,position)
                    +recipe.getSteps().get(position-1).getShortDescription());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onResponse(recipe.getId()-1,position);
//                Intent intent = new Intent(context, RecipeStepFragment.class);
//                intent.putExtra(RECIPE_CLICKED,recipe.getId()-1);
//                intent.putExtra(RECIPE_STEP_CLICKED,position);
//
//                context.startActivity(intent);
            }
        });
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
