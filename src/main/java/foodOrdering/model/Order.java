package foodOrdering.model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private int restaurantId;
    private String restaurantName;
    private MenuItem[] foodItems;
    private double totalCost;
    private Timestamp time;
    private String status;
    Order(){

    }

    public Order(int id, int customerId, int restaurantId, double totalCost) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
