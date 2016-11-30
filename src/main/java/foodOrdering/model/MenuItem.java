package foodOrdering.model;

public class MenuItem {

    private int id;
    private String name;
    private String cuisine;
    private String descr;
    private double cost;
    private int quantity; //For the order

    MenuItem(){

    }

    public MenuItem(int id, String name, String cuisine, String descr, double cost) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.cost = cost;
        this.descr = descr;
    }

    //For the order
    public MenuItem(int id, String name, String cuisine, int quantity, double cost) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.cost = cost;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
