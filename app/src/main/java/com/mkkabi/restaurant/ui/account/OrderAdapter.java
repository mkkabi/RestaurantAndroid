package com.mkkabi.restaurant.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mkkabi.restaurant.R;
import com.mkkabi.restaurant.model.Order;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Order> ordersList;
    private static Context context;

    public OrderAdapter(List<Order> ordersList) {
        this.ordersList = ordersList;
    }
	
	public OrderAdapter(Context cont) {
        context = cont;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);

        return new OrderAdapter.ViewHolder(v);
         
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ordersList.get(position));
		
		
    }

    @Override
    public int getItemCount() {
        if(ordersList!=null && ordersList.size() > 0) {
            return ordersList.size();
        }
        return 0;
    }

    public void setData(List<Order> newData) {
        this.ordersList = newData;
        notifyDataSetChanged();
    }

    static class ViewHolder  extends RecyclerView.ViewHolder {
        TextView orderID, totalAmount;
        LinearLayout orderedDishes;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderIDTextView);
            totalAmount = itemView.findViewById(R.id.totalPriceTextView);
            orderedDishes = itemView.findViewById(R.id.dishesListLinearLayout);
        }

        void bind(Order order){
            orderID.setText(order.getOrderId());
            totalAmount.setText(String.valueOf(order.getOrderAmount()));
            parseDishes(order, context, orderedDishes);
        }
		
		// todo function to parse dishes purchased in each order
		void parseDishes(Order order, Context context, LinearLayout layout){
            for(String dishId : order.getDishesIDs()){
                TextView tv = new TextView(context);
                tv.setText("Dish ID = " + dishId);
                layout.addView(tv);
            }
		}
    }
}
