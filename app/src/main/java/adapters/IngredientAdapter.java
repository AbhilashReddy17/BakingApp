package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhi.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Ingredient;

/**
 * Created by ABHI on 10/19/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    Context context;
    List<Ingredient> ingredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_layout,parent,false);
        IngredientHolder holder = new IngredientHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        holder.quantity.setText(ingredients.get(position).getQuantity()+"");
        holder.measure.setText(ingredients.get(position).getMeasure());
        holder.ingredient.setText(ingredients.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.quantity_id)
        TextView quantity;
        @BindView(R.id.measure_id)
        TextView measure;
        @BindView(R.id.ingredient_id)
        TextView ingredient;
    public IngredientHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
}
