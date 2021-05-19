package hector.developers.smartfarm.model;

import java.io.Serializable;

public class Products implements Serializable {

    private Long id;
    private String productName;
    private String quantity;
    private String price;
    private String state;
    private String location;
    private String productCategory;
    private Long userId;

    public Products() {
    }

    public Products(String productName, String quantity, String price, String state, String location,   String productCategory, Long userId) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.state = state;
        this.location = location;
        this.productCategory = productCategory;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
