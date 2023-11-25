package j2ee.project.DTO;

public class TopProductSellingDTO {
    private Long id;
    private String name;
    private Long quantity;

    public TopProductSellingDTO(Long id,String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public TopProductSellingDTO() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }
}
