package j2ee.project.DTO;

public class TopEmployeeSalesDTO
{
    private Long id;
    private String name;
    private Long quantity;

    public TopEmployeeSalesDTO(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public TopEmployeeSalesDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }
}
