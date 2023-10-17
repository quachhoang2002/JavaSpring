package j2ee.project.DTO;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    @NotNull(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Price is mandatory")
    private double price;

    @NotNull(message = "Category is mandatory")
    private Integer categoryID;

    @NotNull(message = "Manufacture is mandatory")
    private Integer manufactureID;

    @NotNull(message = "Image is mandatory")
    private MultipartFile image;

    private String description;

    public ProductDTO() {
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



    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
