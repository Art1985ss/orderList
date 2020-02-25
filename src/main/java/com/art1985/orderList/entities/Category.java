package com.art1985.orderList.entities;

public enum Category {
    VEGETABLES("Vegetables", 0),
    MUSHROOMS("Mushrooms", 1),
    FLOUR_PRODUCTS("Flour products", 2),
    SWEETS("Sweets", 3);

    private final String name;
    private final int num;

    Category(String name, int num) {
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return name;
    }
}
