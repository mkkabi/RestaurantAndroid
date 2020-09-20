package com.mkkabi.restaurant.ui.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.MenuCategory;


import java.util.List;

import static com.mkkabi.restaurant.utils.Constants.MENUCATEGORY_ID;

class MenuCategoriesListAdapter extends RecyclerView.Adapter<MenuCategoriesListAdapter.ViewHolder> {
    private List<MenuCategory> menuCategoriesList;

    public MenuCategoriesListAdapter(List<MenuCategory> menuCategoriesList) {
        this.menuCategoriesList = menuCategoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menucategory, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(menuCategoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        if(menuCategoriesList!=null && menuCategoriesList.size() > 0) {
            return menuCategoriesList.size();
        }
        return 0;
    }

    public void setData(List<MenuCategory> newData) {
        this.menuCategoriesList = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, idTextView;
        Button openMenuCategoryButton;
        ImageView imageViewMenuCategoryItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textView_menucategoryitem_title);
            this.description = itemView.findViewById(R.id.textView_menucategoryitem_description);
            this.idTextView = itemView.findViewById(R.id.textView_menucategoryitemId);
            this.openMenuCategoryButton = itemView.findViewById(R.id.button_openmenucategoryitem);
            this.imageViewMenuCategoryItem = itemView.findViewById(R.id.imageViewMenuCategoryItem);

        }

        void bind(MenuCategory menuCategory){
            title.setText(menuCategory.getName());
            description.setText(menuCategory.getDescriptionShort());
            idTextView.setText(menuCategory.getDocumentId());
            Glide.with(itemView).load(menuCategory.getImageUrl()).into(imageViewMenuCategoryItem);

//            GlideApp.with(itemView).load(menuCategory.getImageUrl()).into(imageViewMenuCategoryItem);

            openMenuCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Todo need to learn about passing data to new destination on https://developer.android.com/guide/navigation/navigation-pass-data
                    Bundle bundle = new Bundle();
                    bundle.putString(MENUCATEGORY_ID, menuCategory.getDocumentId());
                    Navigation.findNavController(v).navigate(R.id.nav_strava_list, bundle);
                }
            });
        }

    }
}