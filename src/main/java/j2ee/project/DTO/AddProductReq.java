package j2ee.project.DTO;

import jakarta.validation.constraints.NotNull;

public class AddProductReq {
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Price is mandatory")
    private double price;

    @NotNull(message = "Category is mandatory")
    private Integer categoryID;

    @NotNull(message = "Manufacture is mandatory")
    private Integer manufactureID;

    private String description;

    private String imagePath;

    public AddProductReq() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public Integer getManufactureID() {
        return manufactureID;
    }

    public void setManufactureID(Integer manufactureID) {
        this.manufactureID = manufactureID;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
