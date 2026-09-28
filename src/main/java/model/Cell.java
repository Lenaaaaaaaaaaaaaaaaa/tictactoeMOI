package model;

public class Cell {

    private Player value;
    private Category category;

    public Player getValue() {
        return value;
    }

    public void setValue(Player value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}