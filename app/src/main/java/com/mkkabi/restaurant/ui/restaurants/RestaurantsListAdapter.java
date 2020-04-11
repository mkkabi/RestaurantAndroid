package com.mkkabi.restaurant.ui.restaurants;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Restaurant;
import com.mkkabi.restaurant.utils.GlideApp;
import com.mkkabi.restaurant.utils.GlideApp2;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.ViewHolder> {
    private List<Restaurant> restaurants;


    public RestaurantsListAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(restaurants.get(position));
    }

    @Override
    public int getItemCount() {
            return restaurants.size();
    }

    public void setData(List<Restaurant> newData) {
        this.restaurants = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, idTextView;
        Button openMenuButton;
        ImageView restaurantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textView_restaurant_title);
            this.description = itemView.findViewById(R.id.textView_restaurant_description);
            this.idTextView = itemView.findViewById(R.id.textView_restaurantId);
            this.openMenuButton = itemView.findViewById(R.id.button_openRestaurantMenu);
            this.restaurantImage = itemView.findViewById(R.id.imageViewRestaurant);
        }

        void bind(Restaurant restaurant){
            title.setText(restaurant.getName());
            description.setText(restaurant.getshortDescription());
            idTextView.setText(restaurant.getId());
            GlideApp.with(itemView).load(restaurant.getImageUrl()).into(restaurantImage);

            openMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Todo need to learn about passing data to new destination on https://developer.android.com/guide/navigation/navigation-pass-data
                    // need to pass Restaurant ID to new destionation to load the menu in new Fragment

                    Bundle bundle = new Bundle();
                    bundle.putString("restaurantId", restaurant.getId());
                    Navigation.findNavController(v).navigate(R.id.nav_restaurant_menu, bundle);
                }
            });
        }

        // old ways of navigating to a new Fragment
        private void showMenu(Restaurant restaurant) {
//            RestaurantMenuFragment menuFragment = RestaurantMenuFragment.newInstance(restaurant.getId());
//            fragmentManager.beginTransaction().
//                    replace(R.id.nav_host_fragment, menuFragment, "menuFragment").
//                    addToBackStack(null).
//                    commit();
        }
    }


}
