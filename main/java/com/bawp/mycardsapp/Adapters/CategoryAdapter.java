package com.bawp.mycardsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.mycardsapp.Model.Category;
import com.bawp.mycardsapp.R;

import java.util.List;

public abstract class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context){
        this.context = context;
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.check_category_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.checkCategory.setChecked(category.isChecked());
        String categoryText = category.getCategoryName()+" ("+category.getNumCards() + ")";
        holder.checkCategory.setText(categoryText);
        holder.checkCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                category.setChecked(isChecked);
                updateChecked(category, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories != null){
            return categories.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkCategory = itemView.findViewById(R.id.checkCategoryName);
        }
    }

    public abstract void updateChecked(Category category, boolean isChecked);
}
