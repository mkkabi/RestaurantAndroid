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

import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.MenuCategory;
import com.mkkabi.restaurant.utils.GlideApp;

import java.util.List;

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

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(menuCategoriesList.get(position));
    }

    @Override
    public int getItemCount() {
        return menuCategoriesList.size();
    }

    public void setData(List<MenuCategory> newData) {
        this.menuCategoriesList = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, idTextView;
        Button openMenuCategoryButton;
        ImageView imageViewMenuCategoryItem;

        public ViewHolder(@NonNull View itemView) {
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
            idTextView.setText(menuCategory.getId());
            GlideApp.with(itemView).load(menuCategory.getImageurl()).into(imageViewMenuCategoryItem);

            openMenuCategoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Todo need to learn about passing data to new destination on https://developer.android.com/guide/navigation/navigation-pass-data
                    // need to pass Restaurant ID to new destionation to load the menu in new Fragment

                    Bundle bundle = new Bundle();
                    bundle.putString("menucategoryid", menuCategory.getId());
                    bundle.putString("restaurantid", menuCategory.getRestaurantId());
                    Navigation.findNavController(v).navigate(R.id.nav_strava_list, bundle);
                }
            });
        }

    }
}