package com.example.thegardenofeatn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private OnCategoryClickListener onCategoryClickListener;


    public CategoryAdapter (List<Category> categoryList, OnCategoryClickListener onCategoryClickListener){
        this.categoryList = categoryList;
        this.onCategoryClickListener = onCategoryClickListener;

    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryImage.setImageResource(category.getImage());
        holder.categoryName.setText(category.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != RecyclerView.NO_POSITION) {
                    Category clickedCategory = categoryList.get(position);
                    onCategoryClickListener.onCategoryClick(clickedCategory);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    // ViewHolder class
    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView){
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
        }

    }
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

}
