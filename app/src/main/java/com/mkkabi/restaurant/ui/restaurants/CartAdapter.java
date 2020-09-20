package com.mkkabi.restaurant.ui.restaurants;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Cart;
import com.mkkabi.restaurant.model.Dish;
import com.mkkabi.restaurant.model.Ingredient;
import com.mkkabi.restaurant.utils.LoggerToaster;

import java.util.List;
import java.util.Set;

import static com.mkkabi.restaurant.utils.Constants.DUBUG;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    //    private static CartAdapter instance;
    private static CartViewModel cartViewModel;
    private List<Dish> dishList;
    private Context context;


    public CartAdapter(Context cont, CartViewModel vm) {
        this.context = cont;
        cartViewModel = vm;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new CartAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.bind(dishList.get(position));
//        holder.additionalItemsTextHeader.setOnClickListener(v -> {
//            // Get the current state of the item and change the state
//            holder.isExpandedAdditionalItems = !holder.isExpandedAdditionalItems;
//            // Notify the adapter that item has changed
//            notifyItemChanged(position);
//            holder.updateAdditionalItemsViewState();
//        });
    }

    @Override
    public int getItemCount() {
        if (dishList != null) {
            return dishList.size();
        }
        return 0;
    }

    public void setData(List<Dish> newData) {
        this.dishList = newData;
        notifyDataSetChanged();
    }

    // Viewholder Class START
    static class ViewHolder extends RecyclerView.ViewHolder {

        Context viewholderContext;
        private TextView title, dishPrice, description;
        private Button removeFromCartButton;
        private LinearLayout additionalItemsListHolder, additionalItemsOpener;
        private TextView additionalItemsTextHeader;
        private ImageView imageViewDish, imageView_arrow;
        private boolean isExpandedAdditionalItems = false;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.textView_strava_title);
            this.dishPrice = itemView.findViewById(R.id.textView_dishPrice);

            this.description = itemView.findViewById(R.id.textView_strava_description);
            description.setVisibility(View.GONE);

            this.removeFromCartButton = itemView.findViewById(R.id.button_addToBasket);
            removeFromCartButton.setText("Remove");

            // в additionalItemsListHolder будут добавляться список доп элементов блюда динамически
//            this.additionalItemsTextHeader = itemView.findViewById(R.id.textView_additionalItemsTextHeader);
            this.additionalItemsListHolder = itemView.findViewById(R.id.linearLayout_additionalDishItems);

            this.additionalItemsOpener = itemView.findViewById(R.id.linearLayoutAdditionalItemsOpener);
            additionalItemsOpener.setVisibility(View.GONE);

            this.imageViewDish = itemView.findViewById(R.id.imageViewStrava);
            this.imageView_arrow = itemView.findViewById(R.id.imageView_arrow);
            viewholderContext = itemView.getContext();


        }

        void bind(Dish dish) {
            dishPrice.setText(String.valueOf(dish.getPrice()));
            title.setText(dish.getName());
            description.setText(dish.getDescription());
            Glide.with(itemView).load(dish.getImageUrl()).into(imageViewDish);

            if (dish.getSelectedIngredients() != null && additionalItemsListHolder.getChildCount() <= 0) {
                setAdditionalItems(dish, additionalItemsListHolder);
            }

            removeFromCartButton.setOnClickListener(v -> {
                cartViewModel.removeDish(dish);
                LoggerToaster.printD(viewholderContext, "Removed from cart " + dish.getName() + "\n Cart total: " + Cart.getInstance().getCartTotalPrice());

            });
        }


//        void updateAdditionalItemsViewState() {
//            this.additionalItemsListHolder.setVisibility(isExpandedAdditionalItems ? View.VISIBLE : View.GONE);
//            this.imageView_arrow.setImageDrawable(isExpandedAdditionalItems ? viewholderContext.getDrawable(R.drawable.ic_arrow_drop_up_black_24dp) :
//                    viewholderContext.getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
//        }

        private void setAdditionalItems(Dish dish, LinearLayout parent) {
            Set<Ingredient> ingredienSet = dish.getSelectedIngredients().keySet();
            for (Ingredient ingredient : ingredienSet) {
                int number = 0;
                try {
                    number = dish.getSelectedIngredients().get(ingredient);
                } catch (NullPointerException e) {
                    Log.e(DUBUG, "error parsing Integer from the number of selected additional items", e);
                }
                int ingredientsPrice = ingredient.getPrice() * number;
                // overal weight/volume of each additional element
                int ingredientAmount = ingredient.getAmount() * number;

                LinearLayout dynamicView = (LinearLayout) LayoutInflater.from(viewholderContext).inflate(R.layout.item_additional_ingredient, null, false);

                LinearLayout linearLayout_buttonsHolder = dynamicView.findViewById(R.id.linearLayout_buttonsHolder);
                linearLayout_buttonsHolder.setVisibility(View.GONE);

                TextView name = dynamicView.findViewById(R.id.textViewIngredientName);
                TextView textViewAmount = dynamicView.findViewById(R.id.textViewAmount);
                TextView textViewMeasureType = dynamicView.findViewById(R.id.textViewMeasureType);
                TextView textViewPrice = dynamicView.findViewById(R.id.textViewPrice);
//                CheckBox checkBox = dynamicView.findViewById(R.id.checkBoxIngredient);

//                Button increaseIngredient = dynamicView.findViewById(R.id.buttonIngredientIncrease);
//                Button decreaseAmount = dynamicView.findViewById(R.id.buttonIngredientDecrease);
//                TextView itemsNumber = dynamicView.findViewById(R.id.textViewIngredientsToAdd);

                name.setText(ingredient.getName());
                textViewAmount.setText(String.valueOf(ingredientAmount));
                textViewMeasureType.setText(ingredient.getMeasureUnit());
                textViewPrice.setText(String.valueOf(ingredientsPrice));

//                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                    if (isChecked) {
//                        dish.addAdditionalIngredientForOrder(ingredient, Integer.parseInt(String.valueOf(itemsNumber.getText())));
//                    }
//                    if (!isChecked) {
//                        dish.removeAdditionalIngredientIfContains(ingredient);
//                    }
//                    LoggerToaster.printD(viewholderContext, "Strava total price = " + dish.calculateOverallDishPrice() + "\n" +
//                            "Ingredient price = " + ingredient.getPrice() + "\n" +
//                            "Ingredient amount = " + Integer.parseInt(String.valueOf(itemsNumber.getText())));
//                });

//                int[] numberOfIngredientsToAdd = {1};
//                increaseIngredient.setOnClickListener(v -> itemsNumber.setText(String.valueOf(++numberOfIngredientsToAdd[0])));
//
//                decreaseAmount.setOnClickListener(v -> {
//                    if (numberOfIngredientsToAdd[0] > 1) {
//                        itemsNumber.setText(String.valueOf(--numberOfIngredientsToAdd[0]));
//                    }
//                });
                parent.addView(dynamicView);
            }

        }
    }

    // Viewholder Class END
}
