package com.mkkabi.restaurant.ui.restaurants;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mkkabi.restaurant.ContextProvider;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Ingredient;
import com.mkkabi.restaurant.model.MenuCategory;
import com.mkkabi.restaurant.model.Strava;
import com.mkkabi.restaurant.utils.GlideApp;

import java.util.List;

public class StravaListAdapter extends RecyclerView.Adapter<StravaListAdapter.ViewHolder> {
    private List<Strava> stravaList;
    private static Context context;

    public StravaListAdapter(Context cont, List<Strava> stravaList) {
        context = cont;
        this.stravaList = stravaList;
    }

    @NonNull
    @Override
    public StravaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dish, parent, false);
        StravaListAdapter.ViewHolder vh = new StravaListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StravaListAdapter.ViewHolder holder, int position) {
        holder.bind(stravaList.get(position));

        holder.additionalItemsTextHeader.setOnClickListener(v -> {
            // Get the current state of the item

            // Change the state
            holder.isExpandedAdditionalItems = !holder.isExpandedAdditionalItems;
            // Notify the adapter that item has changed
            notifyItemChanged(position);
        });


    }

    @Override
    public int getItemCount() {
        return stravaList.size();
    }

    public void setData(List<Strava> newData) {
        this.stravaList = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, dishPrice, description, idTextView, dishesNumber;
        Button addToCartButton, decreaseStrava, increaseStrava;
        LinearLayout additionalItemsListHolder;
        TextView additionalItemsTextHeader;
        ImageView imageViewDish;
        boolean isExpandedAdditionalItems = false;

        private int totalDishPrice;
        int numberOfDishes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textView_strava_title);
            this.dishPrice = itemView.findViewById(R.id.textView_dishPrice);
            this.description = itemView.findViewById(R.id.textView_strava_description);
            this.idTextView = itemView.findViewById(R.id.textView_dish_id);
            this.dishesNumber = itemView.findViewById(R.id.textView_dishesNumberAddToCart);
            this.addToCartButton = itemView.findViewById(R.id.button_addToBasket);
            this.decreaseStrava = itemView.findViewById(R.id.button_decreaseStravaNumber);
            this.increaseStrava = itemView.findViewById(R.id.button_increaseStravaNumber);
            // в additionalItemsListHolder будут добавляться список доп элементов блюда динамически
            this.additionalItemsTextHeader = itemView.findViewById(R.id.textView_additionalItemsTextHeader);
            this.additionalItemsListHolder = itemView.findViewById(R.id.linearLayout_additionalDishItems);
            this.imageViewDish = itemView.findViewById(R.id.imageViewStrava);
        }

        private void setAdditionalItems(Ingredient ingredient, LinearLayout parent){
            LinearLayout dynamicView = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.item_additional_ingredient, null, false);

            TextView name = (TextView) dynamicView.findViewById(R.id.textViewIngredientName);
            TextView textViewAmount = (TextView) dynamicView.findViewById(R.id.textViewAmount);
            TextView textViewMeasureType = (TextView) dynamicView.findViewById(R.id.textViewMeasureType);
            TextView textViewPrice = (TextView) dynamicView.findViewById(R.id.textViewPrice);
            CheckBox checkBox = (CheckBox) dynamicView.findViewById(R.id.checkBoxIngredient);

            Button increaseAmount = (Button) dynamicView.findViewById(R.id.buttonIngredientDecrease);
            Button decreaseAmount = (Button) dynamicView.findViewById(R.id.buttonIngredientIncrease);
            TextView itemsNumber = (TextView) dynamicView.findViewById(R.id.textViewIngredientsToAdd);

            name.setText(ingredient.getName());
            textViewAmount.setText(ingredient.getAmount()+"");
            textViewMeasureType.setText(ingredient.getMeasureUnit());
            textViewPrice.setText(ingredient.getPrice()+"");

            parent.addView(dynamicView);
        }

        void bind(Strava strava) {
            numberOfDishes = Integer.parseInt(dishesNumber.getText().toString());
            totalDishPrice = numberOfDishes * strava.getPrice();
            title.setText(strava.getName());
            dishPrice.setText(strava.getPrice() + "");
            description.setText(strava.getDescription());
            idTextView.setText(strava.getId());
            GlideApp.with(itemView).load(strava.getImageurl()).into(imageViewDish);

            this.additionalItemsListHolder.setVisibility(isExpandedAdditionalItems ? View.VISIBLE : View.GONE);

            for (Ingredient ingredient : strava.getIngredientList()){
                setAdditionalItems(ingredient, additionalItemsListHolder);
            }

            decreaseStrava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numberOfDishes > 1) {
                        numberOfDishes--;
                        dishesNumber.setText(numberOfDishes+"");
                        dishPrice.setText(String.valueOf(strava.getPrice()*numberOfDishes));
                    }
                }
            });

            increaseStrava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int numberOfDishes = Integer.parseInt(dishesNumber.getText().toString());
                    numberOfDishes++;
                    dishesNumber.setText(numberOfDishes+"");
                    dishPrice.setText(String.valueOf(strava.getPrice()*numberOfDishes));
                }
            });

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
