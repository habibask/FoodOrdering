package foodOrdering.model;

import java.util.*;

public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String email;
    private String password;
    private String phone;
    private HashSet<String> cuisines = new HashSet<String>();
    private String[] cuisineArr;

    private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
    private Order[] orders;

    private ArrayList<Review> reviews = new ArrayList<Review>();

    Restaurant() {
    }

    public Restaurant(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCuisines(String cuisine) {
        this.cuisines.add(cuisine);
    }

    public String[] getCuisineArr() {
        this.cuisineArr = new String[cuisines.size()];
        cuisineArr = cuisines.toArray(cuisineArr);
        return cuisineArr;
    }

    public void setCuisineArr(String[] cuisineArr) {
        this.cuisineArr = cuisineArr;
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
