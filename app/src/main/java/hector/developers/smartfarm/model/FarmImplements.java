package hector.developers.smartfarm.model;

import java.io.Serializable;

public class FarmImplements implements Serializable {
    private Long id;
    private String implementName;
    private String size;
    private String price;
    private String state;
    private String lga;
    private String implementCategory;
    private String location;

    public FarmImplements() {}

//    public FarmImplements(Long id, String implementName, String size, String price, String state, String lga, String location) {
//        this.implementName = implementName;
//        this.size = size;
//        this.price = price;
//        this.state = state;
//        this.lga = lga;
//        this.location = location;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImplementName() {
        return implementName;
    }

    public void setImplementName(String implementName) {
        this.implementName = implementName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getLocation() {
        return location;
    }

    public String getImplementCategory() {
        return implementCategory;
    }

    public void setImplementCategory(String implementCategory) {
        this.implementCategory = implementCategory;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
