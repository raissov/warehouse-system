package sample.crudsystem;

public class Products {
    private int id;
    private String name;
    private int cost;
    private String country;
    private String category;

    public Products(int id, String name, int cost, String country, String category) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.country = country;
        this.category = category;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
