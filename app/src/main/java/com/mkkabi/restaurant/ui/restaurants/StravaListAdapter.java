package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Cart;
import com.mkkabi.restaurant.model.Ingredient;
import com.mkkabi.restaurant.model.Dish;
import com.mkkabi.restaurant.ui.account.FirebaseLoginActivity;
import com.mkkabi.restaurant.utils.LoggerToaster;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_SIGN_IN;
import static com.mkkabi.restaurant.utils.Constants.FIREBASE_USER;

public class StravaListAdapter extends RecyclerView.Adapter<StravaListAdapter.ViewHolder> {
    private List<Dish> dishList;
    private Context context;

    public StravaListAdapter(Context cont, List<Dish> dishList) {
        this.context = cont;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public StravaListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new StravaListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StravaListAdapter.ViewHolder holder, int position) {
        holder.bind(dishList.get(position));
        holder.additionalItemsTextHeader.setOnClickListener(v -> {
            // Get the current state of the item and change the state
            holder.isExpandedAdditionalItems = !holder.isExpandedAdditionalItems;
            // Notify the adapter that item has changed
            notifyItemChanged(position);
            holder.updateAdditionalItemsViewState();
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public void setData(List<Dish> newData) {
        this.dishList = newData;
        notifyDataSetChanged();
    }

    // Viewholder Class START
    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, dishPrice, description;
        private Button addToCartButton;
        private LinearLayout additionalItemsListHolder;
        private TextView additionalItemsTextHeader;
        private ImageView imageViewDish, imageView_arrow;
        private boolean isExpandedAdditionalItems = false;
        Context viewholderContext;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textView_strava_title);
            this.dishPrice = itemView.findViewById(R.id.textView_dishPrice);
            this.description = itemView.findViewById(R.id.textView_strava_description);
            this.addToCartButton = itemView.findViewById(R.id.button_addToBasket);

            // в additionalItemsListHolder будут добавляться список доп элементов блюда динамически
            this.additionalItemsTextHeader = itemView.findViewById(R.id.textView_additionalItemsTextHeader);
            this.additionalItemsListHolder = itemView.findViewById(R.id.linearLayout_additionalDishItems);
            this.imageViewDish = itemView.findViewById(R.id.imageViewStrava);
            this.imageView_arrow = itemView.findViewById(R.id.imageView_arrow);
            viewholderContext = itemView.getContext();
        }

        void bind(Dish dish) {
            dishPrice.setText(String.valueOf(dish.getPrice()));
            title.setText(dish.getName());
            description.setText(dish.getDescription());
            Glide.with(itemView).load(dish.getImageUrl()).into(imageViewDish);

            if (dish.getAdditionalIngredients() != null && additionalItemsListHolder.getChildCount() <= 0) {
                setAdditionalItems(dish, additionalItemsListHolder);
            }

            addToCartButton.setOnClickListener(v -> {
				Cart.getInstance().addDish(dish);
                Snackbar.make(v, "Added to Cart " + dish.getName()+"\n Cart total: "+Cart.getInstance().getCartTotalPrice(), Snackbar.LENGTH_LONG)
                        .setAction("Remove", v1 -> {
                            Cart.getInstance().removeDish(dish);
                            LoggerToaster.printD(viewholderContext, "Removed from cart " + dish.getName()+"\n Cart total: "+Cart.getInstance().getCartTotalPrice());
                        }).show();
            });
        }




        void updateAdditionalItemsViewState() {
            this.additionalItemsListHolder.setVisibility(isExpandedAdditionalItems ? View.VISIBLE : View.GONE);
            this.imageView_arrow.setImageDrawable(isExpandedAdditionalItems ? viewholderContext.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp) :
                    viewholderContext.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
        }

        private void setAdditionalItems(Dish dish, LinearLayout parent) {
            for (Ingredient ingredient : dish.getAdditionalIngredients()) {
                LinearLayout dynamicView = (LinearLayout) LayoutInflater.from(viewholderContext).inflate(R.layout.item_additional_ingredient, null, false);

                TextView name = dynamicView.findViewById(R.id.textViewIngredientName);
                TextView textViewAmount = dynamicView.findViewById(R.id.textViewAmount);
                TextView textViewMeasureType = dynamicView.findViewById(R.id.textViewMeasureType);
                TextView textViewPrice = dynamicView.findViewById(R.id.textViewPrice);
                CheckBox checkBox = dynamicView.findViewById(R.id.checkBoxIngredient);

                Button increaseIngredient = dynamicView.findViewById(R.id.buttonIngredientIncrease);
                Button decreaseAmount = dynamicView.findViewById(R.id.buttonIngredientDecrease);
                TextView itemsNumber = dynamicView.findViewById(R.id.textViewIngredientsToAdd);

                name.setText(ingredient.getName());
                textViewAmount.setText(String.valueOf(ingredient.getAmount()));
                textViewMeasureType.setText(ingredient.getMeasureUnit());
                textViewPrice.setText(String.valueOf(ingredient.getPrice()));

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        dish.addAdditionalIngredientForOrder(ingredient, Integer.parseInt(String.valueOf(itemsNumber.getText())));
                    }
                    if (!isChecked) {
                        dish.removeAdditionalIngredientIfContains(ingredient);
                    }
                    LoggerToaster.printD(viewholderContext, "Strava total price = " + dish.calculateOverallDishPrice() + "\n" +
                            "Ingredient price = " + ingredient.getPrice() + "\n" +
                            "Ingredient amount = " + Integer.parseInt(String.valueOf(itemsNumber.getText())));
                });

                int[] numberOfIngredientsToAdd = {1};
                increaseIngredient.setOnClickListener(v -> itemsNumber.setText(String.valueOf(++numberOfIngredientsToAdd[0])));

                decreaseAmount.setOnClickListener(v -> {
                    if(numberOfIngredientsToAdd[0]>1){
                        itemsNumber.setText(String.valueOf(--numberOfIngredientsToAdd[0]));
                    }
                });
                parent.addView(dynamicView);
            }

        }
    }

    // Viewholder Class END
}
