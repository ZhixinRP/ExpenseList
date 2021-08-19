package sg.edu.rp.c346.id20047401.expenselist;

import java.io.Serializable;

public class Expense implements Serializable {
    private int id;
    private String name;
    private Double cost;

    public Expense(int id, String name, Double cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
