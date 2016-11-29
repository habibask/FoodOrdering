package foodOrdering.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private int customerId;
    private int restaurantId;
    private MenuItem[] foodItems;
    private double totalCost;

    Order(){

    }

    public Order(int customerId, int restaurantId, double totalCost) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalCost = totalCost;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public MenuItem[] getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(MenuItem[] foodItems) {
        this.foodItems = foodItems;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
