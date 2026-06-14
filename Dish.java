package model.entity;

public class Dish {
    private String dishId;
    private String name;
    private String category;
    private double price;

    public Dish(String dishId, String name, String category, double price) {
        this.dishId = dishId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getDishId() { return dishId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}