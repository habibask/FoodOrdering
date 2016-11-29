package foodOrdering.model;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String email;
    //private String password;
    private String phone;
    private HashSet<String> cuisines = new HashSet<String>();
    private String[] cuisineArr;

    private List<MenuItem> menuItemsList = new ArrayList<MenuItem>();
    private MenuItem[] menuItems;

    Restaurant() {
    }

    public Restaurant(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
//        this.password = password;
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

    public List<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(ArrayList<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
    }


    public void addMenuItem(MenuItem item) {
        this.menuItemsList.add(item);
    }

    public MenuItem[] getMenuItems() {
        menuItems = new MenuItem[menuItemsList.size()];
        menuItems = menuItemsList.toArray(menuItems);
        return menuItems;
    }

    public void setMenuItems(MenuItem[] menuItems) {
        this.menuItems = menuItems;
    }

    //    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

}
